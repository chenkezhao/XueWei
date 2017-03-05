package com.xuewei.utils;

import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;

/**
 *
 * Created by Administrator on 2017/1/12.
 */

public class AnimationUtils {

	/**
	 * fab按钮动画旋转
	 */
	private void fabAnimationRotate(Runnable endAction, final FloatingActionButton	fab) {
        ViewCompat.animate(fab).rotation(360f).setDuration(1000).setInterpolator(new FastOutSlowInInterpolator()).withEndAction(endAction).setListener(new ViewPropertyAnimatorListener() {
			@Override
			public void onAnimationStart(View view) {
                //清除view保留的动画记录
                fab.clearAnimation();
                fab.setRotation(0f);
			}

			@Override
			public void onAnimationEnd(View view) {

			}

			@Override
			public void onAnimationCancel(View view) {

			}
		}).start();

	}

	/**
	 * fab按钮动画变小及旋转
	 */
	private void fabAnimationToSmall(Runnable endAction, final FloatingActionButton	fab) {
        ViewCompat.animate(fab).scaleX(0).scaleY(0).rotation(360f).setDuration(2000).setInterpolator(new FastOutSlowInInterpolator()).withEndAction(endAction).setListener(new ViewPropertyAnimatorListener() {
			@Override
			public void onAnimationStart(View view) {
                //清除view保留的动画记录
                fab.clearAnimation();
                fab.setRotation(0f);
			}

			@Override
			public void onAnimationEnd(View view) {

			}

			@Override
			public void onAnimationCancel(View view) {

			}
		}).start();

	}

}
