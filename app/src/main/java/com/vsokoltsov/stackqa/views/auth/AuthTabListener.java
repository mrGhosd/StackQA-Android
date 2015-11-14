//package com.vsokoltsov.stackqa.views.auth;
//
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v7.app.ActionBar;
//
//import com.vsokoltsov.stackqa.R;
//
///**
// * Created by vsokoltsov on 14.11.15.
// */
//class AuthTabListener implements ActionBar.TabListener {
//    public Fragment fragment;
//
//    public AuthTabListener(Fragment fragment) {
//        this.fragment = fragment;
//    }
//
//
//    @Override
//    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
//
//    }
//
//    @Override
//    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
//        ft.replace(R.id.fragment_container, fragment);
//    }
//
//    @Override
//    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
//        ft.remove(fragment);
//    }
//}