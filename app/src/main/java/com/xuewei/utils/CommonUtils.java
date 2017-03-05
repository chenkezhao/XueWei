package com.xuewei.utils;

import android.widget.Toast;

import com.jbp689.JBPApplication;
import com.jbp689.entity.KLine;
import com.jbp689.entity.TransactionDetail;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 通用工具类 Created by Administrator on 2017/1/7.
 */

public class CommonUtils {
    /**
     * 根据股票代码下载历史明细记录excel文件
     * @param url 下载地址
     * @param savePath 保存下载文件位置
     * @param code 股票代码
     */
	public static void downloadSinaTradehistoryFile(final String url, String savePath, final String code,final boolean isRed,final TransactionDetail td) {
		File file  = new File(savePath);
		String fileSavePath = file.getAbsolutePath();
		if(file.exists()){
			file.delete();
		}
		RequestParams requestParams = new RequestParams(url);
		requestParams.setSaveFilePath(fileSavePath);
		x.http().get(requestParams, new Callback.ProgressCallback<File>() {
			@Override
			public void onWaiting() {
			}

			@Override
			public void onStarted() {
			}

			@Override
			public void onLoading(long total, long current, boolean isDownloading) {
			}

			@Override
			public void onSuccess(File result) {
//				Toast.makeText(JBPApplication.getInstance(), "下载成功", Toast.LENGTH_SHORT).show();
				MessageUtils.getInstance().closeProgressDialog();
                //解析excel文件
//                KLine kLine = JExcelApiUtils.readSinaTradehistoryXls(result,new KLine(isRed),td);
				KLine kLine = readSinaTradehistoryTxt(result,new KLine(isRed),td);
				new VolleyUtils(JBPApplication.getInstance()).getCompanyInfo(kLine,td,td.getCode(),true);
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				ex.printStackTrace();
				Toast.makeText(JBPApplication.getInstance(), "下载失败，请检查网络和SD卡"+ex.toString(), Toast.LENGTH_LONG).show();
				MessageUtils.getInstance().closeProgressDialog();
                EventBus.getDefault().post(null);
			}

			@Override
			public void onCancelled(CancelledException cex) {
			}

			@Override
			public void onFinished() {
			}
		});
	}


	/**
	 * 根据股票代码获取从新浪下载的历史明细txt读取数据
	 * @param file 下载的文件
	 * @param kLine new KLine(isRed)
	 * @param td TransactionDetail对象
	 * @return
	 */
	public static KLine readSinaTradehistoryTxt(File file,KLine kLine,TransactionDetail td) {
		InputStreamReader reader = null;
		BufferedReader br = null;
		try {
			reader = new InputStreamReader(new FileInputStream(file),"gb2312"); // 建立一个输入流对象reader
			br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
			String line = "";
			line = br.readLine();
			long totalVolume = 0; // 总成交量
			long upVolume = 0; // K线上部分
			long middleVolume = 0; // K线中间部分
			long downVolume = 0; // K线下部分
			boolean isFirst = true;
			while (line != null) {
				if(isFirst){
					isFirst = false;
					continue;
				}
				line = br.readLine(); // 一次读入一行数据
				if(line==null){
					continue;
				}
				String []columns = line.split("\t");
				if(columns.length!=6){
					continue;
				}
				// 成交价（元）
				double price = Double.parseDouble(columns[1]);
				// 成交量（手）
				long volume = Long.parseLong(columns[3]);
				totalVolume += volume;
				if(kLine.isRed()){
					//阳线
					if (price <= td.getCurrentPrice() && price > td.getOpenPrice()) {
						// 中间
						middleVolume +=volume;
					}else if(price>td.getCurrentPrice()){
						//上
						upVolume +=volume;
					}else if(price<=td.getOpenPrice()){
						//下
						downVolume +=volume;
					}
				}else{
					if (price <= td.getOpenPrice() && price > td.getCurrentPrice()) {
						// 中间
						middleVolume +=volume;
					}else if(price>td.getOpenPrice()){
						//上
						upVolume +=volume;
					}else if(price<=td.getCurrentPrice()){
						//下
						downVolume +=volume;
					}
				}
			}
			kLine.setCode(td.getCode());
			kLine.setUpVolume(upVolume*100);
			kLine.setMiddleVolume(middleVolume*100);
			kLine.setDownVolume(downVolume*100);
			kLine.setTotalVolume(totalVolume*100);
			kLine.setName(td.getName());
			kLine.setDate(td.getDate());
			if(reader!=null){
				reader.close();
			}
			if(br!=null){
				br.close();
			}
			//删除下载的文件
			if (file.isFile() && file.exists()) {
				if(!file.delete()){
					Toast.makeText(JBPApplication.getInstance(), file.getName()+"文件删除失败！", Toast.LENGTH_LONG).show();
				}
			}
			return kLine;
		} catch (Exception ex) {
			ex.printStackTrace();
			Toast.makeText(JBPApplication.getInstance(), "异常："+ex.toString(), Toast.LENGTH_LONG).show();
		}finally {

		}
		return new KLine();
	}


	public static String dateToStringFormat(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try{
			return sdf.format(sdf.parse(date));
		}catch (Exception ex){
			ex.printStackTrace();
			Toast.makeText(JBPApplication.getInstance(), "异常："+ex.toString(), Toast.LENGTH_LONG).show();
		}
		return sdf.format(new Date());
	}
	public static String dateToStringFormat(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try{
			return sdf.format(date);
		}catch (Exception ex){
			ex.printStackTrace();
			Toast.makeText(JBPApplication.getInstance(), "异常："+ex.toString(), Toast.LENGTH_LONG).show();
		}
		return sdf.format(new Date());
	}
	public static Date dateToFormat(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try{
			return sdf.parse(sdf.format(date));
		}catch (Exception ex){
			ex.printStackTrace();
			Toast.makeText(JBPApplication.getInstance(), "异常："+ex.toString(), Toast.LENGTH_LONG).show();
		}
		return null;
	}
	public static Date dateToFormat(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try{
			return sdf.parse(date);
		}catch (Exception ex){
			ex.printStackTrace();
			Toast.makeText(JBPApplication.getInstance(), "异常："+ex.toString(), Toast.LENGTH_LONG).show();
		}
		return null;
	}
	public static Date dateToFormat(String date,String templet){
		SimpleDateFormat sdf = new SimpleDateFormat(templet);
		try{
			return sdf.parse(date);
		}catch (Exception ex){
			ex.printStackTrace();
			Toast.makeText(JBPApplication.getInstance(), "异常："+ex.toString(), Toast.LENGTH_LONG).show();
		}
		return null;
	}

	/**
	 * 判断指定日期是否为周未
	 * @param date
	 * @return
     */
	public static boolean weekendMethod(String date) {
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String Date = date;  //传入的date格式是yyyy-MM-dd
		Date bdate=null;
		try {
			bdate = format1.parse(Date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(bdate);
		if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY||cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
			//System.out.println("是周末");
			return true;
		}else{
			//System.out.println("不是周末！");
			return false;
		}
	}
}
