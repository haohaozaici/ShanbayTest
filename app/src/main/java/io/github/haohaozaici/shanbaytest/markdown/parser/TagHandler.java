package io.github.haohaozaici.shanbaytest.markdown.parser;

/**
 * Created by zhou on 16-7-10.
 * TagHandler
 */
public interface TagHandler extends TagFinder, QueueConsumer {

  boolean h(Line line);

  boolean h1(Line line);

  boolean h2(Line line);

  boolean h3(Line line);

  boolean ul(Line line);

  boolean ol(Line line);

  boolean em(Line line);

  boolean italic(Line line);

  boolean link(Line line);

  boolean image(Line line);

  boolean inline(Line line);

}
