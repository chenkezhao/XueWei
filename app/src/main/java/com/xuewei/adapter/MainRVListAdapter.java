package com.xuewei.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xuewei.R;
import com.xuewei.XWApplication;
import com.xuewei.activity.XWEffectActivity;
import com.xuewei.db.dao.GroupXueWeiDao;
import com.xuewei.entity.GroupXueWei;
import com.xuewei.utils.CommonUtils;
import com.xuewei.utils.MessageUtils;
import com.xuewei.utils.SVGUtils;

import net.youmi.android.normal.common.ErrorCode;
import net.youmi.android.normal.video.VideoAdListener;
import net.youmi.android.normal.video.VideoAdManager;
import net.youmi.android.normal.video.VideoAdSettings;
import net.youmi.android.normal.video.VideoInfoViewBuilder;

import java.util.List;
import java.util.Random;

/**
 * 
 * Created by Administrator on 2017/3/5.
 */

public class MainRVListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private Context			mContext;
	private List<GroupXueWei> mGroupXueWeiList;
	private GroupXueWei groupXueWei;
	private GroupXueWeiDao groupXueWeiDao;
	private int random[];

	public MainRVListAdapter(Context mContext, List<GroupXueWei> datas){
		this.mContext = mContext;
		this.mGroupXueWeiList = datas;
		groupXueWeiDao = GroupXueWeiDao.getInstance();
		random = CommonUtils.generateGroupRandom(5,datas.size()-1,1);
	}

	@Override
	public int getItemViewType(int position) {
		if(CommonUtils.isWifiConnected()){
			if(position == 1/* || position==random[0]*/ ){
				return 1;//广告
			}else{
				return 0;
			}
		}
		return 0;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if (viewType == 0) {
			return new ViewHolder1(LayoutInflater.from(mContext).inflate(R.layout.main_rvlist_item, parent, false));
		} else {
			return new ViewHolder2(LayoutInflater.from(mContext).inflate(R.layout.ad_native_video, parent, false));
		}
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
		if (holder instanceof ViewHolder1) {
			//正常
			ViewHolder1 holder1 = ((ViewHolder1)holder);
			groupXueWei = mGroupXueWeiList.get(position);
			if(groupXueWei.getCollection()){
				SVGUtils.getInstance().changeSVGColor(holder1.collectionIcon,R.drawable.ic_collection,R.color.collection);
			}else{
				SVGUtils.getInstance().changeSVGColor(holder1.collectionIcon,R.drawable.ic_collection,R.color.unCollection);
			}
			holder1.collectionIcon.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					groupXueWei.setCollection(!groupXueWei.getCollection());
					groupXueWeiDao.update(groupXueWei);
					if(groupXueWei.getCollection()){
						SVGUtils.getInstance().changeSVGColor((ImageView) v,R.drawable.ic_collection,R.color.collection);
					}else{
						SVGUtils.getInstance().changeSVGColor((ImageView) v,R.drawable.ic_collection,R.color.unCollection);
					}
				}
			});
			holder1.showDetail.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent =new Intent(mContext, XWEffectActivity.class);
					GroupXueWei group = mGroupXueWeiList.get(position);
					intent.putExtra(GroupXueWei.GROUPXUEWEI,group);
					mContext.startActivity(intent);
				}
			});
			String imgName  = groupXueWei.getUrl();
			imgName = imgName.substring(0,imgName.lastIndexOf("."));
			int resId = mContext.getResources().getIdentifier(imgName, "mipmap" , mContext.getPackageName());
			Uri uri = Uri.parse("res:///"+resId);
			holder1.previewImg.setImageURI(uri);
			holder1.title.setText(groupXueWei.getTitle());
			String subTitle = groupXueWei.getSubTitle();
			if(TextUtils.isEmpty(subTitle)){
				holder1.subTitle.setVisibility(View.GONE);
			}else{
				holder1.subTitle.setText(subTitle);
			}
			String content = groupXueWei.getContent();
			if(!TextUtils.isEmpty(content)){
				holder1.content.setText(content.replace("\\n","\n").replace("\\t","\t"));
				holder1.content.setVisibility(View.VISIBLE);
			}else{
				holder1.content.setText("");
				holder1.content.setVisibility(View.GONE);
			}
		} else if (holder instanceof ViewHolder2) {
			//广告段
		}

	}

	@Override
	public int getItemCount() {
		return mGroupXueWeiList.size();
	}

	class ViewHolder1 extends RecyclerView.ViewHolder {

		SimpleDraweeView previewImg;
		Button showDetail;
		ImageButton collectionIcon;
		TextView title;
		TextView subTitle;
		TextView content;

		public ViewHolder1(View view){
			super(view);
			previewImg = (SimpleDraweeView) view.findViewById(R.id.sdv_preview_img);
			showDetail = (Button) view.findViewById(R.id.btn_showDetail);
			collectionIcon  = (ImageButton) view.findViewById(R.id.ib_collection);
			title = (TextView)view.findViewById(R.id.titleTV);
			subTitle = (TextView)view.findViewById(R.id.subTitleTV);
			content = (TextView)view.findViewById(R.id.contentTV);
		}
	}


	static class ViewHolder2 extends RecyclerView.ViewHolder {

		/**
		 * 原生视频广告控件容器
		 */
		private RelativeLayout mNativeVideoAdLayout;

		/**
		 * 视频信息栏容器
		 */
		private RelativeLayout mVideoInfoLayout;
		private TextView loading;
		private CardView card;
		private Context mContext;

		public ViewHolder2(View view) {
			super(view);
			mContext = XWApplication.getInstance();
			// 原生视频广告控件容器
			mNativeVideoAdLayout = (RelativeLayout) view.findViewById(R.id.rl_native_video_ad);
			// 视频信息栏容器
			mVideoInfoLayout = (RelativeLayout) view.findViewById(R.id.rl_video_info);
			loading = (TextView) view.findViewById(R.id.tv_adVideo_loading);
			card = (CardView) view.findViewById(R.id.cv_native_video_layout);
			setupNativeVideoAd();
		}


		/**
		 * 设置原生视频广告
		 */
		private void setupNativeVideoAd() {
			// 设置视频广告
			final VideoAdSettings videoAdSettings = new VideoAdSettings();

			//		// 只需要调用一次，由于在主页窗口中已经调用了一次，所以此处无需调用
			//		VideoAdManager.getInstance().requestVideoAd(mContext);

			// 设置信息流视图，将图标，标题，描述，下载按钮对应的ID传入
			final VideoInfoViewBuilder videoInfoViewBuilder =
					VideoAdManager.getInstance(mContext).getVideoInfoViewBuilder(mContext)
							.setRootContainer(mVideoInfoLayout).bindAppIconView(R.id.info_iv_icon)
							.bindAppNameView(R.id.info_tv_title).bindAppDescriptionView(R.id.info_tv_description)
							.bindDownloadButton(R.id.info_btn_download);



			// 获取原生视频控件
			View nativeVideoAdView = VideoAdManager.getInstance(mContext)
					.getNativeVideoAdView(mContext, videoAdSettings, new VideoAdListener() {
						@Override
						public void onPlayStarted() {
							loading.setVisibility(View.GONE);
							// 由于多窗口模式下，屏幕较小，所以开始播放时先隐藏展示按钮
							if (Build.VERSION.SDK_INT >= 24) {
//								if (isInMultiWindowMode()) {
//									hideShowNativeVideoButton();
//								}
							}
							// 展示视频信息流视图
							showVideoInfoLayout();
						}

						@Override
						public void onPlayInterrupted() {
							loading.setText("play pause...");
							loading.setVisibility(View.VISIBLE);
							// 隐藏视频信息流视图
							hideVideoInfoLayout();
							// 释放资源
							if (videoInfoViewBuilder != null) {
								videoInfoViewBuilder.release();
							}
							// 移除原生视频控件
							if (mNativeVideoAdLayout != null) {
								mNativeVideoAdLayout.removeAllViews();
								mNativeVideoAdLayout.setVisibility(View.GONE);
							}
							card.setVisibility(View.GONE);
						}

						@Override
						public void onPlayFailed(int errorCode) {
							loading.setText("play failed...");
							loading.setVisibility(View.VISIBLE);
							switch (errorCode) {
								case ErrorCode.NON_NETWORK:
									MessageUtils.getInstance().showShortToast("网络异常");
									break;
								case ErrorCode.NON_AD:
//									MessageUtils.getInstance().showShortToast("原生视频暂无广告");
									break;
								case ErrorCode.RESOURCE_NOT_READY:
//									MessageUtils.getInstance().showShortToast("原生视频资源还没准备好");
									break;
								case ErrorCode.SHOW_INTERVAL_LIMITED:
//									MessageUtils.getInstance().showShortToast("请勿频繁展示");
									break;
								case ErrorCode.WIDGET_NOT_IN_VISIBILITY_STATE:
//									MessageUtils.getInstance().showShortToast("原生视频控件处在不可见状态");
									break;
								default:
//									MessageUtils.getInstance().logError("请稍后再试");
									break;
							}
						}

						@Override
						public void onPlayCompleted() {
							// 隐藏视频信息流视图
							hideVideoInfoLayout();
							// 释放资源
							if (videoInfoViewBuilder != null) {
								videoInfoViewBuilder.release();
							}
							// 移除原生视频控件
							if (mNativeVideoAdLayout != null) {
								mNativeVideoAdLayout.removeAllViews();
								mNativeVideoAdLayout.setVisibility(View.GONE);
							}
							card.setVisibility(View.GONE);
						}

					});
			if (mNativeVideoAdLayout != null) {
				final RelativeLayout.LayoutParams params =
						new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
								ViewGroup.LayoutParams.WRAP_CONTENT);
				if (nativeVideoAdView != null) {
					mNativeVideoAdLayout.removeAllViews();
					// 添加原生视频广告
					mNativeVideoAdLayout.addView(nativeVideoAdView, params);
					mNativeVideoAdLayout.setVisibility(View.VISIBLE);
				}
			}
		}

		/**
		 * 展示视频信息流视图
		 */
		private void showVideoInfoLayout() {
			if (mVideoInfoLayout != null && mVideoInfoLayout.getVisibility() != View.VISIBLE) {
				mVideoInfoLayout.setVisibility(View.VISIBLE);
			}
		}

		/**
		 * 隐藏视频信息流视图
		 */
		private void hideVideoInfoLayout() {
			if (mVideoInfoLayout != null && mVideoInfoLayout.getVisibility() != View.GONE) {
				mVideoInfoLayout.setVisibility(View.GONE);
			}
		}

	}




}