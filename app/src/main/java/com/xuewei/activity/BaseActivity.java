package com.xuewei.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.xuewei.XWApplication;
import com.xuewei.utils.MessageUtils;

//import net.youmi.android.normal.video.VideoAdManager;

import org.xutils.x;

/**
 *
 * Created by Administrator on 2017/1/8.
 */

public class BaseActivity extends AppCompatActivity/*BaseSkinActivity*/ {

    final private int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 689;
    protected static final String TAG = "xuewei";
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        x.view().inject(this);
        //启动时检查权限
        if(android.os.Build.VERSION.SDK_INT>=23){
            //checkPermission();
        }
    }

    public void setTitle(String title){
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(title);
        }
    }
    public void setSubTitle(String subTitle){
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle(subTitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @TargetApi(23)
    private void checkPermission(){
        int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                MessageUtils.getInstance().showAlertDialog(this, "系统提示", "受权将用于初始化相关数据！", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
                    }
                });
                return;
            }
            requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    //queryTradeHistory(mCode,mDate);
                    MessageUtils.getInstance().showSnackbar(XWApplication.getInstance().getRootView(BaseActivity.this),"受权成功，尽情享用！");
                } else {
                    // Permission Denied
                    MessageUtils.getInstance().closeProgressDialog();
//                    XWApplication.getInstance().exit();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getName()); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getName()); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }

    private String getName(){
        return getClass().getSimpleName();
    }

    /**
     * 检查广告配置
     */
    public void checkAdSettings() {
//        boolean result = VideoAdManager.getInstance(mContext).checkVideoAdConfig();
//        MessageUtils.getInstance().showShortToast("配置 %s", result ? "正确" : "不正确，请对照文档检查是否存在遗漏");
    }

}
