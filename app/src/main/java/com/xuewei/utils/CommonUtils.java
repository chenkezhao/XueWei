package com.xuewei.utils;
import android.widget.Toast;
import com.xuewei.XWApplication;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 通用工具类 Created by Administrator on 2017/1/7.
 */

public class CommonUtils {

	public static String dateToStringFormat(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.format(sdf.parse(date));
		} catch (Exception ex) {
			ex.printStackTrace();
			Toast.makeText(XWApplication.getInstance(), "异常：" + ex.toString(), Toast.LENGTH_LONG).show();
		}
		return sdf.format(new Date());
	}

	public static String dateToStringFormat(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.format(date);
		} catch (Exception ex) {
			ex.printStackTrace();
			Toast.makeText(XWApplication.getInstance(), "异常：" + ex.toString(), Toast.LENGTH_LONG).show();
		}
		return sdf.format(new Date());
	}

	public static Date dateToFormat(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(sdf.format(date));
		} catch (Exception ex) {
			ex.printStackTrace();
			Toast.makeText(XWApplication.getInstance(), "异常：" + ex.toString(), Toast.LENGTH_LONG).show();
		}
		return null;
	}

	public static Date dateToFormat(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(date);
		} catch (Exception ex) {
			ex.printStackTrace();
			Toast.makeText(XWApplication.getInstance(), "异常：" + ex.toString(), Toast.LENGTH_LONG).show();
		}
		return null;
	}

	public static Date dateToFormat(String date, String templet) {
		SimpleDateFormat sdf = new SimpleDateFormat(templet);
		try {
			return sdf.parse(date);
		} catch (Exception ex) {
			ex.printStackTrace();
			Toast.makeText(XWApplication.getInstance(), "异常：" + ex.toString(), Toast.LENGTH_LONG).show();
		}
		return null;
	}

	/**
	 * 判断指定日期是否为周未
	 * 
	 * @param date
	 * @return
	 */
	public static boolean weekendMethod(String date) {
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String Date = date; // 传入的date格式是yyyy-MM-dd
		Date bdate = null;
		try {
			bdate = format1.parse(Date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(bdate);
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			// System.out.println("是周末");
			return true;
		} else {
			// System.out.println("不是周末！");
			return false;
		}
	}
}
