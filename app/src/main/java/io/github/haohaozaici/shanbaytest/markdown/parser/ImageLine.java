package io.github.haohaozaici.shanbaytest.markdown.parser;

/**
 * Created by haoyuan on 2018/3/14.
 */

public class ImageLine extends Line {

  private String link;
  private String title;


  public ImageLine(String source) {
    super(source);
  }


  public static ImageLine toImageLine(Line line, String url, String title) {
    ImageLine imageLine = (ImageLine) new Line(line.getSource());
    imageLine.link = url;
    imageLine.title = title;
    return imageLine;
  }


  public String getLink() {
    return link;
  }


  public void setLink(String link) {
    this.link = link;
  }


  public String getTitle() {
    return title;
  }


  public void setTitle(String title) {
    this.title = title;
  }
}
