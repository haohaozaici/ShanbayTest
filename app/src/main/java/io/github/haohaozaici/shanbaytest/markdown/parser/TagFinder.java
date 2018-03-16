package io.github.haohaozaici.shanbaytest.markdown.parser;

/**
 * Created by zhou on 16-7-10.
 * TagFinder
 */
public interface TagFinder {

  /**
   * 检查对应tag是否存在
   *
   * @param tag Tag Id
   * @param line line
   * @return true：存在，false不存在
   */
  boolean find(int tag, Line line);

  /**
   * 检查对应tag是否存在
   *
   * @param tag Tag Id
   * @param line line
   * @return true：存在，false不存在
   */
  boolean find(int tag, String line);

}
