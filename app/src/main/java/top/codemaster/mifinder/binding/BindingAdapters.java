package top.codemaster.mifinder.binding;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.databinding.BindingAdapter;
import top.codemaster.mifinder.R;
import top.codemaster.mifinder.data.Resource;

public class BindingAdapters {

    @BindingAdapter(value = {"imageUrl", "placeholder"}, requireAll = false)
    public static void setImageUrl(ImageView imageView, String url, Drawable placeHolder) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageDrawable(placeHolder);
        } else {
            Picasso.get().load(url).into(imageView);
        }
    }

    @BindingAdapter("resType")
    public static void setResType(TextView view, Resource.ResType type) {
        if (type != null) {
            Context context = view.getContext();
            String typeStr = "";
            switch (type) {
                case audio:
                    typeStr = context.getString(R.string.res_audio);
                    break;
                case image:
                    typeStr = context.getString(R.string.res_image);
                    break;
                case video:
                    typeStr = context.getString(R.string.res_video);
                    break;
            }
            view.setText(typeStr);
        } else {
            view.setText("");
        }
    }


    @BindingAdapter("isGone")
    public static void isGone(View view, boolean isGone) {
        if (isGone) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }


}
