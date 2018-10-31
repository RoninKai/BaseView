package com.base.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.base.view.fragment.Fragment1;
import com.base.view.fragment.Fragment2;
import com.base.view.fragment.Fragment3;
import com.base.view.fragment.Fragment4;
import com.base.view.fragment.Fragment5;
import com.base.view.fragment.Fragment6;
import com.base.view.fragment.Fragment7;
import com.base.view.fragment.Fragment8;
import com.base.view.tabpage.TabPageView;
import com.base.view.test.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabPageView tpvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tpvTest = findViewById(R.id.tpv_test);

        final List<String> titleList = new ArrayList<>();
        titleList.add("标题一");
        titleList.add("标题二");
        titleList.add("标题三");
        titleList.add("标题四");
        titleList.add("标题五");
        titleList.add("标题六");
        titleList.add("标题七");
        titleList.add("标题八");

        List<Fragment> fragmentList = new ArrayList<>();

        fragmentList.add(new Fragment1());
        fragmentList.add(new Fragment2());
        fragmentList.add(new Fragment3());
        fragmentList.add(new Fragment4());
        fragmentList.add(new Fragment5());
        fragmentList.add(new Fragment6());
        fragmentList.add(new Fragment7());
        fragmentList.add(new Fragment8());

        tpvTest.setPageAdapter(getSupportFragmentManager(), titleList, fragmentList)
                .setCustomViewInterface(new TabPageView.TabLayoutItemTabCustomViewCreatCallBack() {
                    @Override
                    public void onCustomView(View tabItemView, int index) {
                        ((TextView) tabItemView.findViewById(R.id.tv_tab_name)).setText(titleList.get(index));
                    }
                })
                .addCustomViewOnTabSelectedListener(new TabPageView.OnTabSelectedListener() {
                    @Override
                    public void onTabChageStatus(boolean check, TabLayout.Tab tab, View tabView, int position) {
                        ((TextView) tabView.findViewById(R.id.tv_tab_name)).setText(check ? "选中" : titleList.get(position));
                    }
                })
                .setCurrentItem(1);
    }

}