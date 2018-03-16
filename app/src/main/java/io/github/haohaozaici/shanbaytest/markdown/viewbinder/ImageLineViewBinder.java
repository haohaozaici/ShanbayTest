package io.github.haohaozaici.shanbaytest.markdown.viewbinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import io.github.haohaozaici.shanbaytest.GlideApp;
import io.github.haohaozaici.shanbaytest.R;
import io.github.haohaozaici.shanbaytest.markdown.parser.ImageLine;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by haoyuan on 2018/3/14.
 */
public class ImageLineViewBinder extends ItemViewBinder<ImageLine, ImageLineViewBinder.ViewHolder> {

  @NonNull @Override
  protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    View root = inflater.inflate(R.layout.item_img_line, parent, false);
    return new ViewHolder(root);
  }


  @Override
  protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ImageLine imageLine) {
    holder.img_des.setText(imageLine.getTitle());
    GlideApp.with(holder.img)
        .load(imageLine.getLink())
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .into(holder.img);
  }


  static class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_img) ImageView img;
    @BindView(R.id.item_img_description) TextView img_des;


    ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);

    }
  }
}
