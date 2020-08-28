package com.s.shenghuang.mlqm.adpter;

import android.os.Parcelable;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

/**
 * @author 道义（daoyi）
 * @version 1.0
 * @date 2020-04-20
 * @params 动态适配器
 **/
public class DynamicsAdpter extends FragmentStatePagerAdapter {

    private List<Fragment> list;
    FragmentManager fs;

    public DynamicsAdpter(FragmentManager fm, List<Fragment> lists){
        super(fm);
        this.fs=fm;
        this.list=lists;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(list!=null&&list.size()>=0){
            return list.get(position);
        }
        return null;

    }

    @Override
    public int getCount() {
        if(list!=null&&list.size()>=0){
            return list.size();
        }
        return 0;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
//    @Override
//    public int getItemPosition(Object object) {
//        return POSITION_NONE;
//    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        if(position==0){
//            NearbyMasterFragment fragment = (NearbyMasterFragment) super.instantiateItem(container, position);
////            fragment.refresh();
//            return fragment;
//        }else if(position==1){
//            NearbyUserFragment fragment1 = (NearbyUserFragment) super.instantiateItem(container, position);
//            return fragment1;
//        }else if(position==2){
//            NearbyDynamicFrament fragment2 = (NearbyDynamicFrament) super.instantiateItem(container, position);
//            return fragment2;
//        }else if(position==3){
//            NearbyBusinessFragment fragment3 = (NearbyBusinessFragment) super.instantiateItem(container, position);
//            return fragment3;
//        }else{
//
//        }

        return super.instantiateItem(container,position);
    }
    private void removeALlFragments(){
        FragmentTransaction transaction = fs.beginTransaction();
        for (int i=0; i<list.size(); i++){
            Fragment fg = list.get(i);
            transaction.remove(fg);
        }
        transaction.commit();
        list.clear();
    }

    @Override
    public void notifyDataSetChanged() {
//        removeALlFragments();
        super.notifyDataSetChanged();
    }
}
