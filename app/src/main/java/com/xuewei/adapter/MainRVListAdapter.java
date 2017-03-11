package com.xuewei.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xuewei.R;
import com.xuewei.activity.XWEffectActivity;
import com.xuewei.db.dao.GroupXueWeiDao;
import com.xuewei.entity.GroupXueWei;
import com.xuewei.utils.SVGUtils;

import java.util.List;

/**
 * 
 * Created by Administrator on 2017/3/5.
 */

public class MainRVListAdapter extends RecyclerView.Adapter<MainRVListAdapter.MyViewHolder> {
	private Context			mContext;
	private List<GroupXueWei> mGroupXueWeiList;
	private GroupXueWei groupXueWei;
	private GroupXueWeiDao groupXueWeiDao;

	public MainRVListAdapter(Context mContext, List<GroupXueWei> datas){
		this.mContext = mContext;
		this.mGroupXueWeiList = datas;
		groupXueWeiDao = GroupXueWeiDao.getInstance();
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.main_rvlist_item, parent, false));
		return holder;
	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, final int position) {
		groupXueWei = mGroupXueWeiList.get(position);
		if(groupXueWei.getCollection()){
			SVGUtils.getInstance().changeSVGColor(holder.collectionIcon,R.drawable.ic_collection,R.color.collection);
		}else{
			SVGUtils.getInstance().changeSVGColor(holder.collectionIcon,R.drawable.ic_collection,R.color.unCollection);
		}
		holder.collectionIcon.setOnClickListener(new View.OnClickListener() {
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
		holder.showDetail.setOnClickListener(new View.OnClickListener() {
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
		holder.previewImg.setImageURI(uri);
		holder.title.setText(groupXueWei.getTitle());
		String subTitle = groupXueWei.getSubTitle();
		if(TextUtils.isEmpty(subTitle)){
			holder.subTitle.setVisibility(View.GONE);
		}else{
			holder.subTitle.setText(subTitle);
		}
		String content = groupXueWei.getContent();
		if(!TextUtils.isEmpty(content)){
			holder.content.setText(content.replace("\\n","\n").replace("\\t","\t"));
		}

	}

	@Override
	public int getItemCount() {
		return mGroupXueWeiList.size();
	}

	class MyViewHolder extends RecyclerView.ViewHolder {

		SimpleDraweeView previewImg;
		Button showDetail;
		ImageButton collectionIcon;
		TextView title;
		TextView subTitle;
		TextView content;

		public MyViewHolder(View view){
			super(view);
			previewImg = (SimpleDraweeView) view.findViewById(R.id.sdv_preview_img);
			showDetail = (Button) view.findViewById(R.id.btn_showDetail);
			collectionIcon  = (ImageButton) view.findViewById(R.id.ib_collection);
			title = (TextView)view.findViewById(R.id.titleTV);
			subTitle = (TextView)view.findViewById(R.id.subTitleTV);
			content = (TextView)view.findViewById(R.id.contentTV);
		}
	}

}