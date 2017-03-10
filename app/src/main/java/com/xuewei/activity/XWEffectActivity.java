package com.xuewei.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.xuewei.R;
import com.xuewei.adapter.EffectRecyclerAdapter;
import com.xuewei.db.MyDatabase;
import com.xuewei.entity.GroupXueWei;
import com.xuewei.entity.XueWeiEffect;

import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_xwdetail_picture)
public class XWEffectActivity extends BaseActivity {

	@ViewInject(R.id.toolbar)
	private Toolbar toolbar;
	@ViewInject(R.id.rv_Effect)
	private RecyclerView effects;
	@ViewInject(R.id.fab_lookHDMap)
	private FloatingActionButton lookHDMap;
	private List<XueWeiEffect> xueWeiEffectList;
	private GroupXueWei group;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setSupportActionBar(toolbar);
		setTitle("穴位作用|效果");
		if (savedInstanceState == null) {
			group  = (GroupXueWei) getIntent().getSerializableExtra(GroupXueWei.GROUPXUEWEI);
			try {
				if(xueWeiEffectList==null){
					xueWeiEffectList  = new ArrayList<XueWeiEffect>();
				}
				xueWeiEffectList.addAll(group.getXueWeiEffectList(MyDatabase.getInstance().getDb()));
				if(xueWeiEffectList.size()==0){
					Intent intent =new Intent(XWEffectActivity.this, HDMapActivity.class);
					intent.putExtra(GroupXueWei.GROUPXUEWEI,group);
					startActivity(intent);
					finish();
				}
			} catch (DbException e) {
				e.printStackTrace();
			}
		}
		initView();
	}

	private void initView() {
		//布局管理器
		effects.setLayoutManager(new LinearLayoutManager(this));//线性布局，类似ListView
		//适配器
		effects.setAdapter(new EffectRecyclerAdapter(this,xueWeiEffectList));
		//线性宫格显示，瀑布流
		effects.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
	}

	@Event(R.id.fab_lookHDMap)
	private void onLookHDMap(View view){
		Intent intent =new Intent(XWEffectActivity.this, HDMapActivity.class);
		intent.putExtra(GroupXueWei.GROUPXUEWEI,group);
		startActivity(intent);
	}

}
