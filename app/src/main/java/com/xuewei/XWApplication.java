package com.xuewei;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import org.xutils.x;

import java.io.File;

/**
 *
 * Created by Administrator on 2017/3/5.
 */

public class XWApplication extends Application {
    private static XWApplication instance;

    public static XWApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = XWApplication.this;
        x.Ext.init(this);
//		x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public int getScreenHeight() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public int getScreenWidth() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    public View getRootView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }

    /***
     * 获取用户数据文件夹
     *
     * @return
     */
    public File getUserHomePath() {
        String dataPath = getSdCardPath() + File.separator+ getPackageName() +  File.separator;
        File dataPathFile = new File(dataPath);
        if (!dataPathFile.exists()) {
            dataPathFile.mkdirs();
        }
        return dataPathFile;
    }


    /**
     * 判断sd卡是否存在
     *
     * @return
     */
    public boolean isExistSdCard() {
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED); //
        return sdCardExist;
    }
    /**
     * 获取SD卡路径
     *
     * @return
     */
    public String getSdCardPath() {
        File sdDir = null;
        if (isExistSdCard()) {
            sdDir = Environment.getExternalStorageDirectory();// 获取根目录
            return sdDir.toString();
        } else {
            return null;
        }
    }

    public void exit() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
