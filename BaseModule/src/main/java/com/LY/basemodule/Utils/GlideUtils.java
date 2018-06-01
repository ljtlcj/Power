package com.LY.basemodule.Utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * ====== 作者 ======
 * lcj yx
 * ====== 时间 ======
 * 2018-03-02.
 */

public class GlideUtils {
    /**
     * 加载网络图片
     *
     * @param url
     * @param imageView
     */
    public static void showImage(Context context, String url, ImageView imageView) {
        try {
            if (url != null) {
                Glide.with(context).load(url).thumbnail(0.1f).into(imageView);
            }
        } catch (Exception e) {
            //Gradle error:java.lang.IllegalArgumentException: You cannot start a load for a destroyed activity
            e.printStackTrace();
        }
    }
}
