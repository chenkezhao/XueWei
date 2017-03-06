package com.xuewei.activity;

import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.xuewei.R;
import com.xuewei.facebook.zoomable.DoubleTapGestureListener;
import com.xuewei.facebook.zoomable.ZoomableDraweeView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
@ContentView(R.layout.activity_xwdetail_picture)
public class XWDetailPictureActivity extends BaseActivity {

	@ViewInject(R.id.zoomableView)
	private ZoomableDraweeView	zoomableDraweeView;
	private boolean				mAllowSwipingWhileZoomed	= true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		zoomableDraweeView.setAllowTouchInterceptionWhileZoomed(mAllowSwipingWhileZoomed);
		// needed for double tap to zoom
		zoomableDraweeView.setIsLongpressEnabled(false);
		zoomableDraweeView.setTapListener(new DoubleTapGestureListener(zoomableDraweeView));
		DraweeController controller = Fresco.newDraweeControllerBuilder().setUri("res:///" + R.mipmap.test).build();
		zoomableDraweeView.setController(controller);
	}

	public void setAllowSwipingWhileZoomed(boolean allowSwipingWhileZoomed) {
		mAllowSwipingWhileZoomed = allowSwipingWhileZoomed;
	}

	public boolean allowsSwipingWhileZoomed() {
		return mAllowSwipingWhileZoomed;
	}

	public void toggleAllowSwipingWhileZoomed() {
		mAllowSwipingWhileZoomed = !mAllowSwipingWhileZoomed;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		zoomableDraweeView.setController(null);
	}
}
