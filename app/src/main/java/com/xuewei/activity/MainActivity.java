package com.xuewei.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xuewei.R;
import com.xuewei.adapter.DividerItemDecoration;
import com.xuewei.adapter.MainRVListAdapter;
import com.xuewei.db.dao.GroupXueWeiDao;
import com.xuewei.db.dao.XueWeiEffectDao;
import com.xuewei.entity.GroupXueWei;
import com.xuewei.entity.XueWeiEffect;
import com.xuewei.utils.MessageUtils;
import com.xuewei.utils.SharedPreferencesUtils;
import com.xuewei.utils.StringUtils;
import com.xuewei.utils.XmlReadUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import sw.ls.ps.AdManager;
import sw.ls.ps.normal.common.ErrorCode;
import sw.ls.ps.normal.spot.SpotListener;
import sw.ls.ps.normal.spot.SpotManager;
import sw.ls.ps.onlineconfig.OnlineConfigCallBack;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {


	@ViewInject(R.id.drawer_layout)
	private DrawerLayout			navDrawer;
	@ViewInject(R.id.toolbar)
	private Toolbar					mToolbar;
	@ViewInject(R.id.nav_view)
	private NavigationView			mNavigationView;
	@ViewInject(R.id.ctl_toolbar)
	private CollapsingToolbarLayout	ctlToolbar;
	@ViewInject(R.id.rv_list)
	private RecyclerView			mRecyclerView;
	@ViewInject(R.id.appBarLayout)
	private AppBarLayout			mAppBarLayout;
	@ViewInject(R.id.tv_about)
	private TextView				about;

	private GroupXueWeiDao			groupXueWeiDao;
	private XueWeiEffectDao			xueWeiEffectDao;
	private List<GroupXueWei>		mGroupXueWeiList;
	private MainRVListAdapter		mainRVListAdapter;


	private void startAD(String defaultValue){
		// 方法二： 异步调用（可在任意线程中调用）
		AdManager.getInstance(this).asyncGetOnlineConfig("adShowType", new MyOnlineConfigCallBack(defaultValue));
	}

	class MyOnlineConfigCallBack implements OnlineConfigCallBack{

		private String defaultValue;
		public MyOnlineConfigCallBack(){
			super();

		}
		public MyOnlineConfigCallBack(String defaultValue){
			super();
			this.defaultValue = defaultValue;
		}
		@Override
		public void onGetOnlineConfigSuccessful(String key, String value) {
			// TODO Auto-generated method stub
			// 获取在线参数成功
			if(StringUtils.isBlank(value)){
				value = "onCreate";
			}
			if(defaultValue.equals(value)){
				// 设置轮播插屏广告
				try {
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							setupSlideableSpotAd();
						}
					}, 5000);
				} catch (Exception e) {
					// MessageUtils.getInstance().showLongToast(e.toString());
				}
			}

		}

		@Override
		public void onGetOnlineConfigFailed(String s) {
			if(defaultValue.equals("onCreate")){
				// 设置轮播插屏广告
				try {
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							setupSlideableSpotAd();
						}
					}, 5000);
				} catch (Exception e) {
					// MessageUtils.getInstance().showLongToast(e.toString());
				}
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startAD("onCreate");
		setSupportActionBar(mToolbar);
		mToolbar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
				if (layoutManager instanceof LinearLayoutManager) {
					LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
					linearManager.scrollToPositionWithOffset(0, 0);
					linearManager.setStackFromEnd(true);
				}
			}
		});
		mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
					case R.id.menu_search:
						break;
					case R.id.menu_layout:
						int viewType = SharedPreferencesUtils.getListViewType(MainActivity.this);
						SharedPreferencesUtils.setListViewType(MainActivity.this, viewType == 0 ? "2" : "0");
						if (mGroupXueWeiList != null) {
							mainRVListAdapter = new MainRVListAdapter(MainActivity.this, mGroupXueWeiList);
							mRecyclerView.setAdapter(mainRVListAdapter);
							if (SharedPreferencesUtils.getListViewType(MainActivity.this) == 0) {
								mRecyclerView.removeItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL_LIST));
							} else {
								mRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL_LIST));
							}
						}
						break;
					default:
						break;
				}
				return false;
			}
		});
		if (getSupportActionBar() != null) {
			// Enable the Up button，返回按钮
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, navDrawer, mToolbar, R.string.drawer_open, R.string.drawer_close);
		navDrawer.addDrawerListener(toggle);
		toggle.syncState();
		mNavigationView.setNavigationItemSelectedListener(this);
		initData();
		initView();

	}

	@Override
	protected void onResume() {
		super.onResume();
		startAD("onResume");
	}

	private void initView() {
		about.setText(Html.fromHtml("<div style=\"padding: 24px;color: #FFFFFF\">\n" + "Copyright © &nbsp;2017&nbsp;陈科肇 All rights reserved.<br>\n" + "联系方式：<font class=\"email\">310771881@qq.com</font>\n" + "</div>"));
		// 初始化appBarLayout
		mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
			@Override
			public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
				if (verticalOffset == 0) {// 展开状态
					// mAppBarLayout.setExpanded(false);
				} else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {// 关闭状态
				} else {// 其它
				}
			}
		});

		// 设置标题
		ctlToolbar.setTitle("十二经络|奇经八脉");
		ctlToolbar.setTitleEnabled(true);
		ctlToolbar.setExpandedTitleColor(getResources().getColor(R.color.colorAccent));// 设置还没收缩时状态下字体颜色
		ctlToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.colorTextInPrimaryDark));// 设置收缩后Toolbar上字体的颜色

		if (SharedPreferencesUtils.getListViewType(MainActivity.this) == 0) {
			mRecyclerView.removeItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
		} else {
			mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
		}

		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(linearLayoutManager);
		mainRVListAdapter = new MainRVListAdapter(this, mGroupXueWeiList);
		mRecyclerView.setAdapter(mainRVListAdapter);
		mainRVListAdapter.updateData();
	}

	private void initData() {
		groupXueWeiDao = GroupXueWeiDao.getInstance();
		xueWeiEffectDao = XueWeiEffectDao.getInstance();
		if (groupXueWeiDao.getAllCount() == 0 || xueWeiEffectDao.getAllCount() == 0) {
			MessageUtils.getInstance().showProgressDialog(MainActivity.this, "首次启动", "数据初始化中...");
			groupXueWeiDao.clearTable();
			xueWeiEffectDao.clearTable();
			List<GroupXueWei> groupXueWeiList = XmlReadUtils.getInstance().fromXMLByGroup().getGroupXueWeiList();
			List<XueWeiEffect> xueWeiEffectList = XmlReadUtils.getInstance().fromXMLByEffect().getXueWeiEffectList();
			int size = xueWeiEffectList.size();
			int groupid = 1;
			for (int i = 0; i < size; i++) {
				XueWeiEffect e = xueWeiEffectList.get(i);
				e.setSeq(i + 1);
				if (e.getGroupid() == -1) {
					groupid = 2;
					continue;
				}
				e.setGroupid(groupid);
			}
			groupXueWeiDao.insert(groupXueWeiList);
			xueWeiEffectDao.insert(xueWeiEffectList);
			MessageUtils.getInstance().closeProgressDialog();
		}
		if (mGroupXueWeiList == null) {
			mGroupXueWeiList = new ArrayList<GroupXueWei>();
		}
		mGroupXueWeiList.addAll(groupXueWeiDao.getAll());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		SupportMenuItem searchItem = (SupportMenuItem) menu.findItem(R.id.menu_search);
		SearchView searchView = (SearchView) searchItem.getActionView();
		searchView.setQueryHint("搜索主治功效");
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				if (TextUtils.isEmpty(newText)) {
					newText = "";
				}
				mGroupXueWeiList.clear();
				mGroupXueWeiList.addAll(groupXueWeiDao.getLikeBy("content", newText));
				mainRVListAdapter.notifyDataSetChanged();
				return false;
			}
		});
		return super.onCreateOptionsMenu(menu);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		switch (item.getItemId()) {
			case R.id.nav_home:
				mGroupXueWeiList.clear();
				mGroupXueWeiList.addAll(groupXueWeiDao.getAll());
				mainRVListAdapter.updateData();
				break;
			case R.id.nav_collection:
				mGroupXueWeiList.clear();
				mGroupXueWeiList.addAll(groupXueWeiDao.getBy("collection", true));
				mainRVListAdapter.updateData();
				break;
			case R.id.nav_share:
				MessageUtils.getInstance().showAlertDialog(MainActivity.this, "溫馨提示", "你可以通过QQ分享该应用，步骤：QQ好友-聊天页面-文件-应用!");
				break;
			case R.id.nav_about:
				Toast.makeText(MainActivity.this, "关于", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
		}
		navDrawer.closeDrawer(GravityCompat.START);
		return true;
	}

	/**
	 * 设置轮播插屏广告
	 */
	private void setupSlideableSpotAd() {
		// 设置插屏图片类型，默认竖图
		// // 横图
		// SpotManager.getInstance(mContext).setImageType(SpotManager
		// .IMAGE_TYPE_HORIZONTAL);
		// 竖图
		SpotManager.getInstance(mContext).setImageType(SpotManager.IMAGE_TYPE_VERTICAL);

		// 设置动画类型，默认高级动画
		// // 无动画
		// SpotManager.getInstance(mContext).setAnimationType(SpotManager
		// .ANIMATION_TYPE_NONE);
		// // 简单动画
		// SpotManager.getInstance(mContext).setAnimationType(SpotManager
		// .ANIMATION_TYPE_SIMPLE);
		// 高级动画
		SpotManager.getInstance(mContext).setAnimationType(SpotManager.ANIMATION_TYPE_ADVANCED);

		// 展示轮播插屏广告
		SpotManager.getInstance(mContext).showSlideableSpot(mContext, new SpotListener() {

			@Override
			public void onShowSuccess() {
				//MessageUtils.getInstance().logInfo("轮播插屏展示成功");
			}

			@Override
			public void onShowFailed(int errorCode) {
				//MessageUtils.getInstance().logError("轮播插屏展示失败");
				switch (errorCode) {
					case ErrorCode.NON_NETWORK:
						//MessageUtils.getInstance().showShortToast("网络异常");
						break;
					case ErrorCode.NON_AD:
						//MessageUtils.getInstance().showShortToast("暂无轮播插屏广告");
						break;
					case ErrorCode.RESOURCE_NOT_READY:
						//MessageUtils.getInstance().showShortToast("轮播插屏资源还没准备好");
						break;
					case ErrorCode.SHOW_INTERVAL_LIMITED:
						//MessageUtils.getInstance().showShortToast("请勿频繁展示");
						break;
					case ErrorCode.WIDGET_NOT_IN_VISIBILITY_STATE:
						//MessageUtils.getInstance().showShortToast("请设置插屏为可见状态");
						break;
					default:
						//MessageUtils.getInstance().showShortToast("请稍后再试");
						break;
				}
			}

			@Override
			public void onSpotClosed() {
				//MessageUtils.getInstance().logDebug("轮播插屏被关闭");
			}

			@Override
			public void onSpotClicked(boolean isWebPage) {
				//MessageUtils.getInstance().logDebug("轮播插屏被点击");
				//MessageUtils.getInstance().logInfo("是否是网页广告？%s", isWebPage ? "是" : "不是");
			}
		});
	}

	@Override
	public void onBackPressed() {
		if (navDrawer.isDrawerOpen(GravityCompat.START)) {
			navDrawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
		// 点击后退关闭轮播插屏广告
		if (SpotManager.getInstance(mContext).isSlideableSpotShowing()) {
			SpotManager.getInstance(mContext).hideSlideableSpot();
		} else {
			super.onBackPressed();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 轮播插屏广告
		SpotManager.getInstance(mContext).onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
		// 轮播插屏广告
		SpotManager.getInstance(mContext).onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 轮播插屏广告
		SpotManager.getInstance(mContext).onDestroy();

		// 插屏广告（包括普通插屏广告、轮播插屏广告、原生插屏广告）
		SpotManager.getInstance(mContext).onAppExit();
	}

}
