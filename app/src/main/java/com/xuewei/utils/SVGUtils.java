package com.xuewei.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

import com.xuewei.XWApplication;

/**
 *
 * Created by Administrator on 2017/3/11.
 */

public class SVGUtils {

    public static SVGUtils svgUtils;
    private Context mContext;
    private SVGUtils(){
        mContext = XWApplication.getInstance();
    }
    public static SVGUtils getInstance(){
        if(svgUtils==null){
            svgUtils = new SVGUtils();
        }
        return svgUtils;
    }


    /**
     * 改变SVG图片着色
     * @param imageView
     * @param iconResId svg资源id
     * @param color 期望的着色
     */
    public void changeSVGColor(ImageView imageView,int iconResId,int color){
        Drawable drawable = ContextCompat.getDrawable(mContext, iconResId);
        imageView.setImageDrawable(drawable);
        Drawable.ConstantState state = drawable.getConstantState();
        Drawable drawable1 = DrawableCompat.wrap(state == null ? drawable : state.newDrawable()).mutate();
        drawable1.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        DrawableCompat.setTint(drawable1, ContextCompat.getColor(mContext, color));
        imageView.setImageDrawable(drawable1);
    }
}
