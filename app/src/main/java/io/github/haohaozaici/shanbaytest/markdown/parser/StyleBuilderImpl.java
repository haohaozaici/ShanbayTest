package io.github.haohaozaici.shanbaytest.markdown.parser;

import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.text.style.UnderlineSpan;
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
  private static final float scale_normal = 1;

  private final int h1_text_color;
  private final int link_color;


  public StyleBuilderImpl() {

    h1_text_color = 0xdf000000;
    link_color = 0xFFFF4081;

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
