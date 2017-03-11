package com.xuewei.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuewei.R;
import com.xuewei.entity.XueWeiEffect;

import java.util.List;

/**
 *
 * Created by Administrator on 2017/3/10.
 */

public class EffectRecyclerAdapter  extends RecyclerView.Adapter<EffectRecyclerAdapter.MyiewAdapter>{

    private Context mContext;
    private List<XueWeiEffect> xueWeiEffectList;

    public EffectRecyclerAdapter(Context context,List<XueWeiEffect> list){
        this.mContext = context;
        this.xueWeiEffectList = list;
    }

    @Override
    public MyiewAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyiewAdapter(mContext, LayoutInflater.from(mContext).inflate(R.layout.effect_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(MyiewAdapter holder, int position) {
        XueWeiEffect e = xueWeiEffectList.get(position);
        holder.title.setText(e.getXueWei());
        String content =e.getEffect();
        if(!TextUtils.isEmpty(content)){
            holder.content.setText(content.replace("\\n","\n").replace("\\t","\t"));
        }
    }

    @Override
    public int getItemCount() {
        return xueWeiEffectList == null ? 0 : xueWeiEffectList.size();
    }


    public static class MyiewAdapter extends RecyclerView.ViewHolder{
        TextView title;
        TextView content;

        public MyiewAdapter(Context mContext,View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.titleTV);
            content = (TextView) itemView.findViewById(R.id.contentTV);
        }
    }
}