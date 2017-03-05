package com.xuewei.activity;

import android.graphics.Color;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.xuewei.R;
import com.xuewei.adapter.MainRVListAdapter;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, navDrawer, mToolbar, R.string.drawer_open, R.string.drawer_close);
        navDrawer.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
        initView();
    }

    private void initView(){
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
        ctlToolbar.setTitle("陈科肇");
        ctlToolbar.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        ctlToolbar.setCollapsedTitleTextColor(Color.GREEN);//设置收缩后Toolbar上字体的颜色


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(new MainRVListAdapter(this,initData()));

    }

    protected List<String> initData() {
        List<String> mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
        return mDatas;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        SupportMenuItem searchItem = (SupportMenuItem)menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
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
            case R.id.nav_setting:
                break;
            case R.id.nav_collection:
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_about:
                break;
            default:
                break;
        }
        navDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
