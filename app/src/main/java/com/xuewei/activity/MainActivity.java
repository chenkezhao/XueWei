package com.xuewei.activity;

import android.os.Bundle;
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
import com.xuewei.adapter.MainRVListAdapter;
import com.xuewei.db.MyDatabase;
import com.xuewei.db.dao.GroupXueWeiDao;
import com.xuewei.db.dao.XueWeiEffectDao;
import com.xuewei.entity.GroupXueWei;
import com.xuewei.entity.XueWeiEffect;
import com.xuewei.utils.MessageUtils;
import com.xuewei.utils.XmlReadUtils;

import net.youmi.android.normal.banner.BannerManager;
import net.youmi.android.normal.spot.SpotManager;
import net.youmi.android.normal.video.VideoAdManager;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @ViewInject(R.id.drawer_layout)
    private DrawerLayout navDrawer;
    @ViewInject(R.id.toolbar)
    private Toolbar mToolbar;
    @ViewInject(R.id.nav_view)
    private NavigationView mNavigationView;
    @ViewInject(R.id.ctl_toolbar)
    private CollapsingToolbarLayout ctlToolbar;
    @ViewInject(R.id.rv_list)
    private RecyclerView mRecyclerView;
    @ViewInject(R.id.appBarLayout)
    private AppBarLayout mAppBarLayout;
    @ViewInject(R.id.tv_about)
    private TextView about;

    private GroupXueWeiDao groupXueWeiDao;
    private XueWeiEffectDao xueWeiEffectDao;
    private List<GroupXueWei> mGroupXueWeiList;
    private MainRVListAdapter mainRVListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        //广告
        // 预加载数据
        preloadData();
        //checkAdSettings();
    }


    private void initView(){
        about.setText(Html.fromHtml("<div style=\"padding: 24px;color: #FFFFFF\">\n" +
                "Copyright © &nbsp;2017&nbsp;陈科肇 All rights reserved.<br>\n" +
                "联系方式：<font class=\"email\">310771881@qq.com</font>\n" +
                "</div>"));
        //初始化appBarLayout
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(verticalOffset==0){//展开状态
                    //mAppBarLayout.setExpanded(false);
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {//关闭状态
                } else {//其它
                }
            }
        });

        //设置标题
        ctlToolbar.setTitle("十二经络|奇经八脉");
        ctlToolbar.setTitleEnabled(true);
        ctlToolbar.setExpandedTitleColor(getResources().getColor(R.color.colorAccent));//设置还没收缩时状态下字体颜色
        ctlToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.colorTextInPrimaryDark));//设置收缩后Toolbar上字体的颜色


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mainRVListAdapter =new MainRVListAdapter(this,mGroupXueWeiList);
        mRecyclerView.setAdapter(mainRVListAdapter);
    }

    private void initData(){
        groupXueWeiDao = GroupXueWeiDao.getInstance();
        xueWeiEffectDao = XueWeiEffectDao.getInstance();
        if(groupXueWeiDao.getAllCount()==0 || xueWeiEffectDao.getAllCount()==0){
            MessageUtils.getInstance().showProgressDialog(MainActivity.this,"首次启动", "数据初始化中...");
            groupXueWeiDao.clearTable();
            xueWeiEffectDao.clearTable();
            List<GroupXueWei> groupXueWeiList = XmlReadUtils.getInstance().fromXMLByGroup().getGroupXueWeiList();
            List<XueWeiEffect> xueWeiEffectList = XmlReadUtils.getInstance().fromXMLByEffect().getXueWeiEffectList();
            int size = xueWeiEffectList.size();
            int groupid = 1;
            for(int i=0;i<size;i++){
                XueWeiEffect e = xueWeiEffectList.get(i);
                e.setSeq(i+1);
                if(e.getGroupid()==-1){
                    groupid = 2;
                    continue;
                }
                e.setGroupid(groupid);
            }
            groupXueWeiDao.insert(groupXueWeiList);
            xueWeiEffectDao.insert(xueWeiEffectList);
            MessageUtils.getInstance().closeProgressDialog();
        }
        if(mGroupXueWeiList==null){
            mGroupXueWeiList = new ArrayList<GroupXueWei>();
        }
        mGroupXueWeiList.addAll(groupXueWeiDao.getAll());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        SupportMenuItem searchItem = (SupportMenuItem)menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("搜索主治功效");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(TextUtils.isEmpty(newText)){
                    newText = "";
                }
                mGroupXueWeiList.clear();
                mGroupXueWeiList.addAll(groupXueWeiDao.getLikeBy("content",newText));
                mainRVListAdapter.notifyDataSetChanged();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (navDrawer.isDrawerOpen(GravityCompat.START)) {
            navDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()){
            case R.id.nav_home:
                mGroupXueWeiList.clear();
                mGroupXueWeiList.addAll(groupXueWeiDao.getAll());
                mainRVListAdapter.notifyDataSetChanged();
                break;
            case R.id.nav_collection:
                mGroupXueWeiList.clear();
                mGroupXueWeiList.addAll(groupXueWeiDao.getBy("collection",true));
                mainRVListAdapter.notifyDataSetChanged();
                break;
            case R.id.nav_share:
                MessageUtils.getInstance().showAlertDialog(MainActivity.this,"溫馨提示","你可以通过QQ分享该应用，步骤：QQ好友-聊天页面-文件-应用!");
                break;
            case R.id.nav_about:
                Toast.makeText(MainActivity.this,"关于",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        navDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyDatabase.getInstance().closeDatabase();


        // 展示广告条窗口的 onDestroy() 回调方法中调用
        BannerManager.getInstance(mContext).onDestroy();
        // 退出应用时调用，用于释放资源
        // 如果无法保证应用主界面的 onDestroy() 方法被执行到，请移动以下接口到应用的退出逻辑里面调用
        // 插屏广告（包括普通插屏广告、轮播插屏广告、原生插屏广告）
        SpotManager.getInstance(mContext).onAppExit();
        // 视频广告（包括普通视频广告、原生视频广告）
        VideoAdManager.getInstance(mContext).onAppExit();

        // 原生视频广告
        VideoAdManager.getInstance(mContext).onDestroy();
    }



    //-----------------------必须调用以下全部生命周期方法-------------------------------

    @Override
    protected void onStart() {
        super.onStart();
        // 原生视频广告
        VideoAdManager.getInstance(mContext).onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 原生视频广告
        VideoAdManager.getInstance(mContext).onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 原生视频广告
        VideoAdManager.getInstance(mContext).onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 原生视频广告
        VideoAdManager.getInstance(mContext).onStop();
    }


    //-----------------------必须调用以上全部生命周期方法-------------------------------


    /**
     * 预加载数据
     */
    private void preloadData() {
        // 设置服务器回调 userId，一定要在请求广告之前设置，否则无效
        VideoAdManager.getInstance(mContext).setUserId("xuewei689");
        // 请求视频广告
        VideoAdManager.getInstance(mContext).requestVideoAd(mContext);
    }
}
