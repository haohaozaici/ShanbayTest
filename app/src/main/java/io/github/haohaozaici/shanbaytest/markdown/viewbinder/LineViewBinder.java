package io.github.haohaozaici.shanbaytest.markdown.viewbinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.haohaozaici.shanbaytest.R;
import io.github.haohaozaici.shanbaytest.markdown.parser.Line;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by haoyuan on 2018/3/14.
 */
public class LineViewBinder extends ItemViewBinder<Line, LineViewBinder.ViewHolder> {

  @NonNull @Override
  protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    View root = inflater.inflate(R.layout.item_normal_line, parent, false);
    return new ViewHolder(root);
  }


  @Override
  protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Line line) {
    holder.text.setText(line.getStyle());

  }


  static class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_text) TextView text;


    ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);

    }
  }
}
