package com.base.view.tabpage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author : Tanker
 * @email :zhoukai@zto.cn
 * @date : 2018/10/30
 * @describe : TODO
 */
public class TabPageAdapter extends FragmentPagerAdapter {

    private List<String> listTitle;
    private List<Fragment> fragmentList;

    public TabPageAdapter(FragmentManager fm, List<String> listTitle, List<Fragment> fragmentList) {
        super(fm);
        this.listTitle = listTitle;
        this.fragmentList = fragmentList;
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listTitle.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

}