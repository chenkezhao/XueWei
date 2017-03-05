package com.xuewei.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {

	private static final String GET_DATA_SOURCE = "GET_DATA_SOURCE";

	public static SharedPreferences.Editor getWriteableSharedPreferences(Context ctx) {
		SharedPreferences.Editor editor = ctx.getSharedPreferences("global", Context.MODE_PRIVATE).edit();
		return editor;
	}
	public static SharedPreferences getReadableSharedPreferences(Context ctx) {
		SharedPreferences editor = ctx.getSharedPreferences("global", Context.MODE_PRIVATE);
		return editor;
	}
	public static void putString(Context ctx, String key, String value) {
		SharedPreferences.Editor editor = getWriteableSharedPreferences(ctx);
		editor.putString(key, value);
		editor.commit();
	}
	public static String getString(Context ctx, String key, String defValue) {
		SharedPreferences pre = getReadableSharedPreferences(ctx);
		return pre.getString(key, defValue);
	}
	public static void putInt(Context ctx, String key, int value) {
		SharedPreferences.Editor editor = getWriteableSharedPreferences(ctx);
		editor.putInt(key, value);
		editor.commit();
	}
	public static int getInt(Context ctx, String key, int defValue) {
		SharedPreferences pre = getReadableSharedPreferences(ctx);
		return pre.getInt(key, defValue);
	}


	/**
	 * 获取配置数据来源，sina新浪、ifeng凤凰
	 *
	 * @param ctx
	 * @return
	 */
	public static String getDataSource(Context ctx) {
		SharedPreferences pre = getReadableSharedPreferences(ctx);
		return pre.getString(GET_DATA_SOURCE, "");
	}

	/**
	 * 配置数据来源
	 * @param ctx
	 * @param source sina、ifeng
     */
	public static void setDataSource(Context ctx, String source) {
		SharedPreferences.Editor editor = getWriteableSharedPreferences(ctx);
		editor.putString(GET_DATA_SOURCE, source);
		editor.commit();
	}
}
