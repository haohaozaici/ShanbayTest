package io.github.haohaozaici.shanbaytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.haohaozaici.shanbaytest.markdown.MarkDown;
import io.github.haohaozaici.shanbaytest.markdown.parser.ImageLine;
import io.github.haohaozaici.shanbaytest.markdown.parser.Line;
import io.github.haohaozaici.shanbaytest.markdown.viewbinder.ImageLineViewBinder;
import io.github.haohaozaici.shanbaytest.markdown.viewbinder.LineViewBinder;
import java.util.List;
import me.drakeet.multitype.MultiTypeAdapter;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.markdown_text_container) RecyclerView mRecyclerView;



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    MultiTypeAdapter adapter = new MultiTypeAdapter();
    adapter.register(Line.class, new LineViewBinder());
    adapter.register(ImageLine.class, new ImageLineViewBinder());
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    mRecyclerView.setAdapter(adapter);

    List<Line> lineList = MarkDown.fromMarkdown(getResources().openRawResource(R.raw.source), this);

    if (lineList != null) {
      adapter.setItems(lineList);
      adapter.notifyDataSetChanged();
    }
  }

}
