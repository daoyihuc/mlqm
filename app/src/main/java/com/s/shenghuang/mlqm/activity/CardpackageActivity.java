package com.s.shenghuang.mlqm.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.s.shenghuang.mlqm.R;
import com.s.shenghuang.mlqm.adpter.DynamicsAdpter;
import com.s.shenghuang.mlqm.fragment.ExpiredFragment;
import com.s.shenghuang.mlqm.fragment.UnusedFragment;
import com.s.shenghuang.mlqm.fragment.UsedFragment;
import com.s.shenghuang.mlqm.utils.MacUtils;
import com.s.shenghuang.mlqm.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:"道翼(yanwen)"
 * @params:"卡包"
 * @data:20-8-23 下午4:47
 * @email:1966287146@qq.com
 */
public class CardpackageActivity extends BaseTitleActivity{


    private View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> list_fragment;
    private DynamicsAdpter pageAdapter;
    private int curtaion;

    public static void start(Activity activity){
        Intent intent=new Intent();
        intent.setClass(activity,CardpackageActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init_childview();
    }

    @Override
    public void initViews() {
        super.initViews();
        view=getLayoutInflater().inflate(R.layout.activity_cardpackage,relativeLayout);
    }

    @Override
    public void initDatas() {
        super.initDatas();

        list_fragment=new ArrayList<>();

        list_fragment.add(new UnusedFragment());
        list_fragment.add(new UsedFragment());
        list_fragment.add(new ExpiredFragment());

    }

    @Override
    public void init_childview() {
        super.init_childview();

        tabLayout =view.findViewById(R.id.tab_layout);
        viewPager=view.findViewById(R.id.view_pager);
        setTabLayout();
        setViewPager();
    }

    @Override
    protected void setTitlabar() {
        super.setTitlabar();
        titlabar.setCenterTitle("优惠卷");
        titlabar.setLeftTitle("返回");
        titlabar.setLeftColor(0xff000000);
        titlabar.setLeftFontSize(18);
        titlabar.setLeftPadding(0,0,0,0);
        titlabar.setLeftMargin(MacUtils.dpto(10),0,0,0);
        titlabar.setRightMargin(0,0,MacUtils.dpto(10),0);
        titlabar.setLeftDrawable(R.drawable.ic_navigate_before_black_24dp,0x4d000000);
    }

    private void setTabLayout(){

        String []mTitles = {"未使用","已经使用", "已过期"};



        for(int i=0;i< mTitles.length; i++) {

            tabLayout.addTab(tabLayout.newTab());
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if(tab !=null) {

                View view = tab.getCustomView();
                if(view == null){
                    tab.setCustomView(R.layout.view_tab_text);
                }
                view =tab.getCustomView();
                if(view !=null) {
                    TextView tabTv =view.findViewById(R.id.tab_text);
                    tabTv.setText(mTitles[i]);

//                    if(i ==0){
//                        tabTv.setTextAppearance(getActivity(), R.style.tablayout_select_tab);
//                    } else{
//                        tabTv.setTextAppearance(getActivity(), R.style.tablayout_normal_tab);
//                    }
                }
            }
        }
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                curtaion=tab.getPosition();
//                viewPager.setCurrentItem(tab.getPosition());

                View view = tab.getCustomView();
                if (null == view) {
                    tab.setCustomView(R.layout.view_tab_text);
                }
                view = tab.getCustomView();
                TextView textView = view.findViewById(R.id.tab_text);
                if(curtaion !=viewPager.getCurrentItem()) {
                    viewPager.setCurrentItem(curtaion);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                View view = tab.getCustomView();
                if (null == view) {
                    tab.setCustomView(R.layout.view_tab_text);
                }
                view = tab.getCustomView();
                TextView textView =view.findViewById(R.id.tab_text);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }


    private void setViewPager(){

        pageAdapter=new DynamicsAdpter(getSupportFragmentManager(),list_fragment);
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                curtaion=position;
            }

            @Override
            public void onPageSelected(int position) { }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }

}
