package com.xuewei.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xuewei.R;
import com.xuewei.activity.XWDetailPictureActivity;

import java.util.List;

/**
 * 
 * Created by Administrator on 2017/3/5.
 */

public class MainRVListAdapter extends RecyclerView.Adapter<MainRVListAdapter.MyViewHolder> {
	private Context			mContext;
	private List<String>	mDatas;

	public MainRVListAdapter(Context mContext, List<String> datas){
		this.mContext = mContext;
		this.mDatas = datas;
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.main_rvlist_item, parent, false));
		return holder;
	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, int position) {
		Uri uri = Uri.parse("res:///"+R.mipmap.test);
		holder.previewImg.setImageURI(uri);

		holder.showDetail.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mContext.startActivity(new Intent(mContext, XWDetailPictureActivity.class));
			}
		});
	}

	@Override
	public int getItemCount() {
		return mDatas.size();
	}

	class MyViewHolder extends RecyclerView.ViewHolder {

		SimpleDraweeView previewImg;
		Button showDetail;
		ImageButton collectionIcon;

		public MyViewHolder(View view){
			super(view);
			previewImg = (SimpleDraweeView) view.findViewById(R.id.sdv_preview_img);
			showDetail = (Button) view.findViewById(R.id.btn_showDetail);
			collectionIcon  = (ImageButton) view.findViewById(R.id.ib_collection);
		}
	}

}