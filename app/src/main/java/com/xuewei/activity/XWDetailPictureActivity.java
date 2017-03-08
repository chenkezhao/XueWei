package com.xuewei.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.xuewei.R;
import com.xuewei.facebook.zoomable.DefaultZoomableController;
import com.xuewei.facebook.zoomable.DoubleTapGestureListener;
import com.xuewei.facebook.zoomable.ZoomableDraweeView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
@ContentView(R.layout.activity_xwdetail_picture)
public class XWDetailPictureActivity extends BaseActivity {

	@ViewInject(R.id.toolbar)
	private Toolbar toolbar;
	@ViewInject(R.id.zoomableView)
	private ZoomableDraweeView	zoomableDraweeView;
	private boolean				mAllowSwipingWhileZoomed	= true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setSupportActionBar(toolbar);
		if(getSupportActionBar()!=null){
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setTitle("高清图解");
		}
		initView();
	}

	private void initView() {
		zoomableDraweeView.setAllowTouchInterceptionWhileZoomed(mAllowSwipingWhileZoomed);
		// needed for double tap to zoom
		zoomableDraweeView.setIsLongpressEnabled(false);
		DoubleTapGestureListener doubleTapGestureListener = new DoubleTapGestureListener(zoomableDraweeView);
		doubleTapGestureListener.setMinScaleFactor(1.0f);
		doubleTapGestureListener.setMaxScaleFactor(7f);
		zoomableDraweeView.setTapListener(doubleTapGestureListener);


		DraweeController controller = Fresco.newDraweeControllerBuilder().setUri("res:///" + R.mipmap.p1).build();
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
