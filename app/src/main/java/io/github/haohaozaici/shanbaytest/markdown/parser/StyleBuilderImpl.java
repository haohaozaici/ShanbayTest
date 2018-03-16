package io.github.haohaozaici.shanbaytest.markdown.parser;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.widget.TextView;
import io.github.haohaozaici.shanbaytest.R;
import io.github.haohaozaici.shanbaytest.markdown.style.FontSpan;
import io.github.haohaozaici.shanbaytest.markdown.style.LinkSpan;
import io.github.haohaozaici.shanbaytest.markdown.style.MarkDownBulletSpan;

/**
 * Created by zhou on 16-6-28.
 * StyleBuilderImpl
 */
public class StyleBuilderImpl implements StyleBuilder {

  private static final float scale_h1 = 2.25f;
  private static final float scale_h2 = 1.75f;
  private static final float scale_h3 = 1.5f;
  private static final float scale_h4 = 1.25f;
  private static final float scale_h5 = 1, scale_h6 = 1;
  private static final float scale_normal = 1;

  private final int h1_text_color;
  private final int h6_text_color;
  private final int quota_color;
  private final int quota_text_color;
  private final int code_text_color;
  private final int code_background_color;
  private final int link_color;
  private final int h_under_line_color;


  public StyleBuilderImpl(Context context) {

    TypedArray a = context.obtainStyledAttributes(null, R.styleable.MarkdownTheme, R.attr.markdownStyle, 0);
    final boolean failed = !a.hasValue(0);
    if (failed) {
      Log.w("Markdown", "Missing markdownStyle in your theme, using hardcoded color.");

      h1_text_color = 0xdf000000;
      h6_text_color = 0x8a000000;
      quota_color = 0x4037474f;
      quota_text_color = 0x61000000;
      code_text_color = 0xd8000000;
      code_background_color = 0x0c37474f;
      link_color = 0xFFFF4081;
      h_under_line_color = 0x1837474f;
    } else {
      h1_text_color = a.getColor(R.styleable.MarkdownTheme_h1TextColor, 0);
      h6_text_color = a.getColor(R.styleable.MarkdownTheme_h6TextColor, 0);
      quota_color = a.getColor(R.styleable.MarkdownTheme_quotaColor, 0);
      quota_text_color = a.getColor(R.styleable.MarkdownTheme_quotaTextColor, 0);
      code_text_color = a.getColor(R.styleable.MarkdownTheme_codeTextColor, 0);
      code_background_color = a.getColor(R.styleable.MarkdownTheme_codeBackgroundColor, 0);
      link_color = a.getColor(R.styleable.MarkdownTheme_linkColor, 0);
      h_under_line_color = a.getColor(R.styleable.MarkdownTheme_underlineColor, 0);
    }

    a.recycle();
  }


  @Override
  public SpannableStringBuilder em(CharSequence charSequence) {
    SpannableStringBuilder builder = SpannableStringBuilder.valueOf(charSequence);
    FontSpan fontSpan = new FontSpan(scale_normal, Typeface.BOLD, h1_text_color);
    builder.setSpan(fontSpan, 0, charSequence.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    return builder;
  }


  @Override
  public SpannableStringBuilder italic(CharSequence charSequence) {
    SpannableStringBuilder builder = SpannableStringBuilder.valueOf(charSequence);
    FontSpan fontSpan = new FontSpan(scale_normal, Typeface.ITALIC, h1_text_color);
    builder.setSpan(fontSpan, 0, charSequence.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    return builder;
  }


  @Override
  public SpannableStringBuilder h1(CharSequence charSequence) {
    return h(charSequence, scale_h1, h1_text_color);
  }


  @Override
  public SpannableStringBuilder h2(CharSequence charSequence) {
    return h(charSequence, scale_h2, h1_text_color);
  }


  @Override
  public SpannableStringBuilder h3(CharSequence charSequence) {
    return h(charSequence, scale_h3, h1_text_color);
  }


  @Override
  public SpannableStringBuilder ul(CharSequence charSequence) {
    SpannableStringBuilder spannableStringBuilder = SpannableStringBuilder.valueOf(charSequence);
    BulletSpan bulletSpan = new MarkDownBulletSpan(h1_text_color, 0);
    spannableStringBuilder.setSpan(bulletSpan, 0, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    return spannableStringBuilder;
  }


  @Override
  public SpannableStringBuilder ol(CharSequence charSequence, int index) {
    SpannableStringBuilder spannableStringBuilder = SpannableStringBuilder.valueOf(charSequence);
    BulletSpan bulletSpan = new MarkDownBulletSpan(h1_text_color, index);
    spannableStringBuilder.setSpan(bulletSpan, 0, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    return spannableStringBuilder;
  }


  @Override
  public SpannableStringBuilder link(CharSequence title, String link, String hint) {
    SpannableStringBuilder builder = SpannableStringBuilder.valueOf(title);
    LinkSpan linkSpan = new LinkSpan(link, link_color);
    UnderlineSpan underlineSpan = new UnderlineSpan();
    builder.setSpan(linkSpan, 0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    builder.setSpan(underlineSpan, 0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    return builder;
  }


  @Override
  public SpannableStringBuilder image(CharSequence title, String url, final String hint) {
    // if (title == null || title.length() == 0) {
    //   title = "$";
    // }
    // SpannableStringBuilder builder = SpannableStringBuilder.valueOf(title);
    // Drawable drawable = null;
    // if (imageGetter != null) {
    //   drawable = imageGetter.getDrawable(url);
    // }
    // if (drawable == null) {
    //   drawable = new ColorDrawable(Color.TRANSPARENT);
    //   //            builder.delete(0, builder.length());
    //   //            return builder;
    // }
    // ImageSpan imageSpan = new ImageSpan(drawable, url);
    // builder.setSpan(imageSpan, 0, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    // builder.append("\n");
    //
    // SpannableStringBuilder builder2 = SpannableStringBuilder.valueOf(title);
    // FontSpan fontSpan = new FontSpan(scale_normal, Typeface.NORMAL, quota_text_color);
    // builder2.setSpan(fontSpan, 0, builder2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    // builder.append(builder2);
    //
    // if (!TextUtils.isEmpty(hint)) {
    //   builder.setSpan(new LongPressClickableSpan() {
    //     @Override
    //     public void onLongPress(View view) {
    //       Toast.makeText(view.getContext(), hint, Toast.LENGTH_SHORT).show();
    //     }
    //
    //
    //     @Override
    //     public void onClick(View view) {
    //     }
    //   }, 0, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    // }

    return SpannableStringBuilder.valueOf("");
  }


  @SuppressWarnings("WeakerAccess")
  protected SpannableStringBuilder h(CharSequence charSequence, float s, int color) {
    SpannableStringBuilder spannableStringBuilder = SpannableStringBuilder.valueOf(charSequence);
    FontSpan fontSpan = new FontSpan(s, Typeface.BOLD, color);
    spannableStringBuilder.setSpan(fontSpan, 0, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    return spannableStringBuilder;
  }

}
