package io.github.haohaozaici.shanbaytest.markdown;

import android.content.Context;
import android.widget.TextView;
import io.github.haohaozaici.shanbaytest.markdown.parser.Line;
import io.github.haohaozaici.shanbaytest.markdown.parser.StyleBuilderImpl;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by zhou on 16-6-25.
 * Markdown解析器
 */
public class MarkDown {

  public static List<Line> fromMarkdown(InputStream inputStream, Context context) {
    MarkDownParser parser = new MarkDownParser(inputStream, new StyleBuilderImpl(context));
    try {
      return parser.parse();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

}
