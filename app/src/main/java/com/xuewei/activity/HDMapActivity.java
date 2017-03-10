package com.xuewei.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.xuewei.R;
import com.xuewei.entity.GroupXueWei;
import com.xuewei.facebook.zoomable.DoubleTapGestureListener;
import com.xuewei.facebook.zoomable.ZoomableDraweeView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_hdmap)
public class HDMapActivity extends BaseActivity {

    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;
    @ViewInject(R.id.zoomableView)
    private ZoomableDraweeView zoomableDraweeView;
    private boolean				mAllowSwipingWhileZoomed	= true;
    private GroupXueWei group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            group  = (GroupXueWei) getIntent().getSerializableExtra(GroupXueWei.GROUPXUEWEI);
        }
        setSupportActionBar(toolbar);
        setTitle("高清图解");
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
        String imgName  = group.getUrl();
        imgName = imgName.substring(0,imgName.lastIndexOf("."));
        int resId = getResources().getIdentifier(imgName, "mipmap" , getPackageName());
        Uri uri = Uri.parse("res:///"+resId);
        DraweeController controller = Fresco.newDraweeControllerBuilder().setUri(uri).build();
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
