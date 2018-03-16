package io.github.haohaozaici.shanbaytest.markdown.parser;

import android.text.SpannableStringBuilder;
import android.util.SparseArray;
import io.github.haohaozaici.shanbaytest.markdown.style.CodeSpan;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhou on 16-7-10.
 * TagHandlerImpl
 */
public class TagHandlerImpl implements TagHandler {

  private static final Matcher matcherH = Pattern.compile("^\\s*#{1,6}\\s*([^#]*)(\\s*#)?").matcher("");
  private static final Matcher matcherH1 = Pattern.compile("^\\s*#\\s*([^#]*)(\\s*#)?").matcher("");
  private static final Matcher matcherH2 = Pattern.compile("^\\s*#{2}\\s*([^#]*)(\\s*#)?").matcher("");
  private static final Matcher matcherH3 = Pattern.compile("^\\s*#{3}\\s*([^#]*)(\\s*#)?").matcher("");

  private static final Matcher matcherUl = Pattern.compile("^\\s*[*+-]\\s+(.*)").matcher("");
  private static final Matcher matcherOl = Pattern.compile("^\\s*\\d+\\.\\s+(.*)").matcher("");

  private static final Matcher matcherItalic = Pattern.compile("[^*_]*(([*_])([^*_].*?)\\2)").matcher("");
  private static final Matcher matcherEm = Pattern.compile("[^*_]*(([*_])\\2([^*_].*?)\\2\\2)").matcher("");
  private static final Matcher matcherEmItalic = Pattern.compile("[^*_]*(([*_])\\2\\2([^*_].*?)\\2\\2\\2)").matcher("");

  private static final Matcher matcherLink = Pattern.compile(".*?(\\[\\s*(.*?)\\s*]\\(\\s*(\\S*?)(\\s+(['\"])(.*?)\\5)?\\s*?\\))").matcher("");
  private static final Matcher matcherImage = Pattern.compile(".*?(!\\[\\s*(.*?)\\s*]\\(\\s*(\\S*?)(\\s+(['\"])(.*?)\\5)?\\s*?\\))").matcher("");

  private static final Matcher matcherEndSpace = Pattern.compile("(.*?) {2} *$").matcher("");

  private static final Matcher matcherBlankLine = Pattern.compile("^\\s*$").matcher("");

  private static final SparseArray<Matcher> matchers = new SparseArray<>();// matcher缓冲区


  static {
    matchers.put(Tag.H1, matcherH1);
    matchers.put(Tag.H2, matcherH2);
    matchers.put(Tag.H3, matcherH3);
    matchers.put(Tag.H, matcherH);
    matchers.put(Tag.UL, matcherUl);
    matchers.put(Tag.OL, matcherOl);
    matchers.put(Tag.EM, matcherEm);
    matchers.put(Tag.ITALIC, matcherItalic);
    matchers.put(Tag.EM_ITALIC, matcherEmItalic);
    matchers.put(Tag.LINK, matcherLink);
    matchers.put(Tag.IMAGE, matcherImage);
    matchers.put(Tag.BLANK, matcherBlankLine);
    matchers.put(Tag.NEW_LINE, matcherEndSpace);
  }


  private StyleBuilder styleBuilder;
  private QueueProvider queueProvider;


  public TagHandlerImpl(StyleBuilder styleBuilder) {
    this.styleBuilder = styleBuilder;
  }


  @Override
  public boolean h(Line line) {
    return h3(line) || h2(line) || h1(line);
  }


  @Override
  public boolean h1(Line line) {
    Matcher matcher = obtain(Tag.H1, line.getSource());
    if (matcher != null && matcher.find()) {
      line.setType(Line.LINE_TYPE_H1);
      line.setStyle(SpannableStringBuilder.valueOf(matcher.group(1)));
      inline(line);
      line.setStyle(styleBuilder.h1(line.getStyle()));
      return true;
    }
    return false;
  }


  @Override
  public boolean h2(Line line) {
    Matcher matcher = obtain(Tag.H2, line.getSource());
    if (matcher.find()) {
      line.setType(Line.LINE_TYPE_H2);
      line.setStyle(SpannableStringBuilder.valueOf(matcher.group(1)));
      inline(line);
      line.setStyle(styleBuilder.h2(line.getStyle()));
      return true;
    }
    return false;
  }


  @Override
  public boolean h3(Line line) {
    Matcher matcher = obtain(Tag.H3, line.getSource());
    if (matcher.find()) {
      line.setType(Line.LINE_TYPE_H3);
      line.setStyle(SpannableStringBuilder.valueOf(matcher.group(1)));
      inline(line);
      line.setStyle(styleBuilder.h3(line.getStyle()));

      return true;
    }
    return false;
  }


  @Override
  public boolean ul(Line line) {
    Matcher matcher = obtain(Tag.UL, line.getSource());
    if (matcher.find()) {
      line.setType(Line.LINE_TYPE_UL);
      line.setStyle(new SpannableStringBuilder(matcher.group(1)));
      inline(line);
      line.setStyle(styleBuilder.ul(line.getStyle()));
      return true;
    }
    return false;
  }


  @Override
  public boolean ol(Line line) {
    Matcher matcher = obtain(Tag.OL, line.getSource());
    if (matcher.find()) {
      line.setType(Line.LINE_TYPE_OL);

      Line prev = line.prevLine();
      if (prev != null && prev.getType() == Line.LINE_TYPE_OL) {
        line.setCount(prev.getCount() + 1);
      } else {
        line.setCount(1);
      }

      line.setStyle(new SpannableStringBuilder(matcher.group(1)));
      inline(line);
      line.setStyle(styleBuilder.ol(line.getStyle(), line.getCount()));
      return true;
    }
    return false;
  }


  @Override
  public boolean em(Line line) {
    line = line.get();
    SpannableStringBuilder builder = (SpannableStringBuilder) line.getStyle();
    Matcher matcher = obtain(Tag.EM, builder);
    while (matcher.find()) {
      int start = matcher.start(1);
      int end = matcher.end(1);
      if (checkInCode(builder, start, end)) {
        continue;
      }
      SpannableStringBuilder sb = (SpannableStringBuilder) builder.subSequence(matcher.start(3), matcher.end(3));
      builder.delete(matcher.start(1), matcher.end(1));
      builder.insert(matcher.start(1), styleBuilder.em(sb));
      em(line);
      return true;
    }
    return false;
  }


  @Override
  public boolean italic(Line line) {
    line = line.get();
    SpannableStringBuilder builder = (SpannableStringBuilder) line.getStyle();
    Matcher matcher = obtain(Tag.ITALIC, builder);
    while (matcher.find()) {
      int start = matcher.start(1);
      int end = matcher.end(1);
      if (checkInCode(builder, start, end)) {
        continue;
      }
      SpannableStringBuilder sb = (SpannableStringBuilder) builder.subSequence(matcher.start(3), matcher.end(3));
      builder.delete(matcher.start(1), matcher.end(1));
      builder.insert(matcher.start(1), styleBuilder.italic(sb));
      italic(line);
      return true;
    }
    return false;
  }


  @Override
  public boolean link(Line line) {
    line = line.get();
    SpannableStringBuilder builder = (SpannableStringBuilder) line.getStyle();
    Matcher matcher = obtain(Tag.LINK, builder);
    if (matcher.find()) {
      String title = matcher.group(2);
      String link = matcher.group(3);
      String hint = matcher.group(6);
      builder.delete(matcher.start(1), matcher.end(1));
      builder.insert(matcher.start(1), styleBuilder.link(title, link, hint));
      link(line);
      return true;
    }
    return false;
  }


  @Override
  public boolean image(Line line) {
    line = line.get();
    SpannableStringBuilder builder = (SpannableStringBuilder) line.getStyle();
    Matcher matcher = obtain(Tag.IMAGE, builder);
    if (matcher.find()) {
      String title = matcher.group(2);
      String link = matcher.group(3);
      String hint = matcher.group(6);
      builder.delete(matcher.start(1), matcher.end(1));
      builder.insert(matcher.start(1), styleBuilder.image(title, link, hint));

      if (!(line instanceof ImageLine)) {
        LineQueue queue = queueProvider.getQueue();
        ImageLine imageLine = new ImageLine(line.getSource());
        imageLine.setTitle(title);
        imageLine.setLink(link);
        queue.removeCurrLine();
        queue.insert(imageLine);
      }

      image(line);
      return true;
    }
    return false;
  }


  @Override
  public boolean inline(Line line) {
    boolean flag = image(line);
    flag = link(line) || flag;
    flag = em(line) || flag;
    flag = italic(line) || flag;
    return flag;
  }


  @Override
  public boolean find(int tag, Line line) {
    return line != null && find(tag, line.getSource());
  }


  @Override
  public boolean find(int tag, String line) {
    if (line == null) {
      return false;
    }
    Matcher m = obtain(tag, line);
    return m != null && m.find();
  }


  private boolean checkInCode(SpannableStringBuilder builder, int start, int end) {
    CodeSpan[] css = builder.getSpans(0, builder.length(), CodeSpan.class);
    for (CodeSpan cs : css) {
      int c_start = builder.getSpanStart(cs);
      int c_end = builder.getSpanEnd(cs);
      if (!(c_start >= end || c_end <= start)) {
        return true;
      }
    }
    return false;
  }


  private Matcher obtain(int tag, CharSequence src) {
    Matcher m = matchers.get(tag, null);
    if (m != null) {
      m.reset(src);
    }
    return m;
  }


  @Override public void setQueueProvider(QueueProvider provider) {
    this.queueProvider = provider;
  }
}
