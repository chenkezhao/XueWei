package com.xuewei.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.xuewei.XWApplication;

/**
 *
 * Created by Administrator on 2017/1/7.
 */

public class MessageUtils {

    private static final String TAG = MessageUtils.class.getName();
    private ProgressDialog progressDialog;
    public static MessageUtils messageUtils;
    private MessageUtils(){
    }
    public static MessageUtils getInstance(){
        if(messageUtils==null){
            messageUtils = new MessageUtils();
        }
        return messageUtils;
    }

    public void showSnackbar(View view,String msg){
        closeProgressDialog();
        final Snackbar snackbar = Snackbar.make(view,msg,Snackbar.LENGTH_LONG);
        snackbar.show();
        snackbar.setAction("关闭", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
    }

    public void showProgressDialog(Context context,String title, String msg){
        if(title==null){
            title= "系统提示";
        }
        if (msg==null){
            msg="Loading...";
        }
        progressDialog = ProgressDialog.show(context,title, msg, true, false);
    }

    public void closeProgressDialog(){
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    public AlertDialog.Builder getAlertDialogBuilder(Context ctx){
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        return builder;
    }
    /**
     *
     * @param ctx
     * @param title
     * @param msg
     */
    public void showAlertDialog(Context ctx, String title, String msg) {
        AlertDialog dialog = getAlertDialogBuilder(ctx).setTitle(title).setMessage(msg)
                .setPositiveButton("确定", null).show();
    }


    /**
     *
     * @param ctx
     * @param title
     * @param msg
     */
    public void showAlertDialog(Context ctx, String title, String msg,
                                       DialogInterface.OnClickListener onClickListener) {
        AlertDialog dialog  =getAlertDialogBuilder(ctx).setTitle(title).setMessage(msg)
                .setPositiveButton("确定", onClickListener).show();
    }






    /**
     * 打印调试级别日志
     *
     * @param format
     * @param args
     */
    public void logDebug(String format, Object... args) {
        logMessage(Log.DEBUG, format, args);
    }

    /**
     * 打印信息级别日志
     *
     * @param format
     * @param args
     */
    public void logInfo(String format, Object... args) {
        logMessage(Log.INFO, format, args);
    }

    /**
     * 打印错误级别日志
     *
     * @param format
     * @param args
     */
    public void logError(String format, Object... args) {
        logMessage(Log.ERROR, format, args);
    }

    /**
     * 展示短时Toast
     *
     * @param format
     * @param args
     */
    public void showShortToast(String format, Object... args) {
        showToast(Toast.LENGTH_SHORT, format, args);
    }

    /**
     * 展示长时Toast
     *
     * @param format
     * @param args
     */
    public void showLongToast(String format, Object... args) {
        showToast(Toast.LENGTH_LONG, format, args);
    }

    /**
     * 打印日志
     *
     * @param level
     * @param format
     * @param args
     */
    private void logMessage(int level, String format, Object... args) {
        String formattedString = String.format(format, args);
        switch (level) {
            case Log.DEBUG:
                Log.d(TAG, formattedString);
                break;
            case Log.INFO:
                Log.i(TAG, formattedString);
                break;
            case Log.ERROR:
                Log.e(TAG, formattedString);
                break;
        }
    }

    /**
     * 展示Toast
     *
     * @param duration
     * @param format
     * @param args
     */
    private void showToast(int duration, String format, Object... args) {
        Toast.makeText(XWApplication.getInstance(), String.format(format, args), duration).show();
    }



}
