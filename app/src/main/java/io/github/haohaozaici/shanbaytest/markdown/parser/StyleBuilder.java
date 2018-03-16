package io.github.haohaozaici.shanbaytest.markdown.parser;

import android.text.SpannableStringBuilder;

/**
 * Created by zhou on 16-6-28.
 * markdown各种样式的构建器
 */
public interface StyleBuilder {

  SpannableStringBuilder em(CharSequence charSequence);

  SpannableStringBuilder italic(CharSequence charSequence);

  SpannableStringBuilder link(CharSequence title, String link, String hint);

  SpannableStringBuilder image(CharSequence title, String url, String hint);

  SpannableStringBuilder h1(CharSequence charSequence);

  SpannableStringBuilder h2(CharSequence charSequence);

  SpannableStringBuilder h3(CharSequence charSequence);

  SpannableStringBuilder ul(CharSequence charSequence);

  SpannableStringBuilder ol(CharSequence charSequence, int index);

}
