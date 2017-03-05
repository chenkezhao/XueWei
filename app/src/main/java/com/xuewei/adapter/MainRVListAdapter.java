package com.xuewei.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuewei.R;

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
		holder.tv.setText(mDatas.get(position));
	}

	@Override
	public int getItemCount() {
		return mDatas.size();
	}

	class MyViewHolder extends RecyclerView.ViewHolder {

		TextView tv;

		public MyViewHolder(View view){
			super(view);
			tv = (TextView) view.findViewById(R.id.id_num);
		}
	}

}