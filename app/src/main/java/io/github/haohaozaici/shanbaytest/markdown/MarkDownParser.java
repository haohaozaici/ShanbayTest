package io.github.haohaozaici.shanbaytest.markdown;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import io.github.haohaozaici.shanbaytest.markdown.parser.ImageLine;
import io.github.haohaozaici.shanbaytest.markdown.parser.Line;
import io.github.haohaozaici.shanbaytest.markdown.parser.LineQueue;
import io.github.haohaozaici.shanbaytest.markdown.parser.StyleBuilder;
import io.github.haohaozaici.shanbaytest.markdown.parser.Tag;
import io.github.haohaozaici.shanbaytest.markdown.parser.TagHandler;
import io.github.haohaozaici.shanbaytest.markdown.parser.TagHandlerImpl;
import io.github.haohaozaici.shanbaytest.markdown.style.ScaleHeightSpan;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhou on 16-6-25.
 */
class MarkDownParser {

  private BufferedReader reader;
  private TagHandler tagHandler;


  MarkDownParser(BufferedReader reader, StyleBuilder styleBuilder) {
    this.reader = reader;
    tagHandler = new TagHandlerImpl(styleBuilder);
  }


  MarkDownParser(InputStream inputStream, StyleBuilder styleBuilder) {
    this(new BufferedReader(new InputStreamReader(inputStream)), styleBuilder);
  }


  MarkDownParser(String text, StyleBuilder styleBuilder) {
    this(new BufferedReader(new StringReader(text == null ? "" : text)), styleBuilder);
  }


  public List<Line> parse() throws IOException {
    LineQueue queue = collect();
    return parse(queue);
  }


  /**
   * 收集 String -> LineQueue
   *
   * @return LineQueue
   * @throws IOException
   */
  private LineQueue collect() throws IOException {
    String line;
    Line root = null;
    LineQueue queue = null;
    while ((line = reader.readLine()) != null) {
      Line l = new Line(line);
      if (root == null) {
        root = l;
        queue = new LineQueue(root);
      } else {
        queue.append(l);
      }
    }
    return queue;
  }


  /**
   * 解析LineQueue
   *
   * @param queue LineQueue
   * @return Spanned
   */
  private List<Line> parse(final LineQueue queue) {
    if (queue == null) {
      return null;
    }
    tagHandler.setQueueProvider(() -> queue);
    removeCurrBlankLine(queue);
    if (queue.empty()) {
      return null;
    }
    do {
      removeNextBlankLine(queue);

      // 解析style
      if (tagHandler.ol(queue.currLine())
          || tagHandler.ul(queue.currLine())
          || tagHandler.h(queue.currLine())) {
        continue;
      }
      queue.currLine().setStyle(SpannableStringBuilder.valueOf(queue.currLine().getSource()));
      tagHandler.inline(queue.currLine());
    } while (queue.next());
    return merge(queue);
  }


  /**
   * 合并LineQueue -> Spanned
   *
   * @param queue LineQueue
   * @return Spanned
   */
  private List<Line> merge(LineQueue queue) {
    queue.reset();
    List<Line> lineList = new ArrayList<>();
    do {
      if (queue.currLine() instanceof ImageLine) {
        lineList.add(queue.currLine());
        continue;
      }
      SpannableStringBuilder builder = new SpannableStringBuilder();
      Line curr = queue.currLine();
      Line next = queue.nextLine();
      builder.append(curr.getStyle());
      if (next == null) {
        break;
      }
      builder.append('\n');
      switch (curr.getType()) {
        case Line.LINE_TYPE_UL:
          if (next.getType() == Line.LINE_TYPE_UL) { builder.append(listMarginBottom()); }
          break;
        case Line.LINE_TYPE_OL:
          if (next.getType() == Line.LINE_TYPE_OL) {
            builder.append(listMarginBottom());
          }
          break;
        default:
          break;
      }
      curr.setStyle(builder);
      lineList.add(curr);
    } while (queue.next());
    return lineList;
  }


  /**
   * 从下个Line开始移除空Line
   *
   * @param queue LineQueue
   * @return 是否移除了
   */
  private boolean removeNextBlankLine(LineQueue queue) {
    boolean flag = false;
    while (queue.nextLine() != null) {
      if (tagHandler.find(Tag.BLANK, queue.nextLine())) {
        queue.removeNextLine();
        flag = true;
      } else {
        break;
      }
    }
    return flag;
  }


  /**
   * 从当前行开始移除空Line
   *
   * @param queue LineQueue
   * @return true：移除了Line
   */
  private boolean removeCurrBlankLine(LineQueue queue) {
    boolean flag = false;
    while (queue.currLine() != null) {
      if (tagHandler.find(Tag.BLANK, queue.currLine())) {
        queue.removeCurrLine();
        flag = true;
      } else {
        break;
      }
    }
    return flag;
  }


  private SpannableString listMarginBottom() {
    SpannableString ss = new SpannableString(" ");
    ss.setSpan(new ScaleHeightSpan(0.4f), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    return ss;
  }

}
