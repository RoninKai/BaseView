package com.base.view.tabpage;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.base.view.R;

import java.util.List;

/**
 * @author : Tanker
 * @email :zhoukai@zto.cn
 * @date : 2018/10/30
 * @describe : TabLayout + ViewPage 组合控件
 */
public class TabPageView extends LinearLayout {

    private Context mContext;
    private TabLayout tabLayout;
    private NoScrollViewPager viewPager;
    /**
     * TabLayout显示位置
     * TOP：顶部
     * BOTTOM：底部
     */
    private int tabPosition;
    /**
     * ViewPage缓存
     */
    private int offscreenPageLimit;
    /**
     * 指示器颜色
     */
    private int tabPageIndicatorColor;
    /**
     * 指示器高度
     */
    private int tabPageIndicatorHeight;
    /**
     * tab栏字体颜色
     */
    private int tabPageTextColor;
    /**
     * tab栏字体被选颜色
     */
    private int tabSelectedTextColor;
    private int tabMode;
    private int tabGravity;
    /**
     * tab栏背景颜色
     */
    private int tabBackground;
    private boolean viewPageCanScroll;
    private int customViewId;
    private boolean customViewCreated;
    private boolean overScrollModeShow;

    public TabPageView(Context context) {
        this(context, null);
    }

    public TabPageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabPageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabPageView);
        tabPosition = typedArray.getInt(R.styleable.TabPageView_tabPage_tabPosition, 0);
        offscreenPageLimit = typedArray.getInt(R.styleable.TabPageView_tabPage_viewPageOffscreenLimit, 1);
        tabPageIndicatorColor = typedArray.getColor(R.styleable.TabPageView_tabPage_tabPageIndicatorColor, Color.RED);
        tabPageTextColor = typedArray.getColor(R.styleable.TabPageView_tabPage_tabPageTextColor, Color.GRAY);
        tabSelectedTextColor = typedArray.getColor(R.styleable.TabPageView_tabPage_tabSelectedTextColor, Color.BLACK);
        tabPageIndicatorHeight = typedArray.getDimensionPixelSize(R.styleable.TabPageView_tabPage_tabPageIndicatorHeight, 5);
        tabMode = typedArray.getInt(R.styleable.TabPageView_tabPage_tabMode, 1);
        tabGravity = typedArray.getInt(R.styleable.TabPageView_tabPage_tabGravity, 0);
        tabBackground = typedArray.getColor(R.styleable.TabPageView_tabPage_tabBackground, Color.GRAY);
        viewPageCanScroll = typedArray.getBoolean(R.styleable.TabPageView_tabPage_viewPageCanScroll, true);
        customViewId = typedArray.getResourceId(R.styleable.TabPageView_tabPage_tabCustomView, NO_ID);
        overScrollModeShow = typedArray.getBoolean(R.styleable.TabPageView_tabPage_overScrollModeShow, true);
        typedArray.recycle();
        initView();
    }

    private void initView() {
        setOrientation(LinearLayout.VERTICAL);
        addTabLayout();
        addViewPage();
        bindView();
    }

    private void addTabLayout() {
        tabLayout = new TabLayout(mContext);
        tabLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tabLayout.setSelectedTabIndicatorColor(tabPageIndicatorColor);
        tabLayout.setTabTextColors(tabPageTextColor, tabSelectedTextColor);
        tabLayout.setSelectedTabIndicatorHeight(tabPageIndicatorHeight);
        tabLayout.setTabMode(tabMode);
        tabLayout.setTabGravity(tabGravity);
        tabLayout.setBackgroundColor(tabBackground);
        tabLayout.setOverScrollMode(overScrollModeShow ? OVER_SCROLL_IF_CONTENT_SCROLLS : OVER_SCROLL_NEVER);
        addView(tabLayout);
    }

    private void addViewPage() {
        viewPager = new NoScrollViewPager(mContext);
        viewPager.setId(R.id.view_page_id);
        viewPager.setCanScroll(viewPageCanScroll);
        viewPager.setLayoutParams(tabPosition == 0 ? new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                : new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        if (offscreenPageLimit > 1) {
            viewPager.setOffscreenPageLimit(offscreenPageLimit);
        }
        viewPager.setOverScrollMode(overScrollModeShow ? OVER_SCROLL_IF_CONTENT_SCROLLS : OVER_SCROLL_NEVER);
        addView(viewPager, tabPosition == 1 ? 0 : 1);
    }

    /**
     * 设置TabLayout与ViewPager关联
     */
    private void bindView() {
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * 设置ViewPage适配器
     *
     * @param fm
     * @param listTitle
     * @param fragmentList
     * @return
     */
    public TabPageView setPageAdapter(FragmentManager fm, List<String> listTitle, List<Fragment> fragmentList) {
        setPageAdapter(new TabPageAdapter(fm, listTitle, fragmentList));
        return this;
    }

    /**
     * 设置自定义adapter
     *
     * @param pagerAdapter 自定义适配器
     * @return
     */
    public TabPageView setPageAdapter(PagerAdapter pagerAdapter) {
        viewPager.setAdapter(pagerAdapter);
        return this;
    }

    /**
     * 自定义布局
     * 使用自定义布局，则tabPageTextColor，tabSelectedTextColor属性失效
     *
     * @param tabCustomView
     */
    public TabPageView setCustomView(TabLayoutItemTabCustomView tabCustomView) {
        customViewCreated = true;
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab itemTab = tabLayout.getTabAt(i);
            if (itemTab != null) {
                itemTab.setCustomView(tabCustomView.onCustomView(itemTab, i));
            }
        }
        return this;
    }

    /**
     * 自定义布局
     * 使用自定义布局，则tabPageTextColor，tabSelectedTextColor属性失效
     *
     * @param customViewInterface
     * @return
     */
    public TabPageView setCustomViewInterface(TabLayoutItemTabCustomViewCreatCallBack customViewInterface) {
        if (customViewId > NO_ID) {
            customViewCreated = true;
            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                TabLayout.Tab itemTab = tabLayout.getTabAt(i);
                if (itemTab != null) {
                    View view = View.inflate(mContext, customViewId, null);
                    itemTab.setCustomView(view);
                    customViewInterface.onCustomView(view, i);
                }
            }
        }
        return this;
    }

    /**
     * 添加Tab选中事件
     *
     * @param onTabSelectedListener
     * @return
     */
    public TabPageView addOnTabSelectedListener(TabLayout.OnTabSelectedListener onTabSelectedListener) {
        tabLayout.addOnTabSelectedListener(onTabSelectedListener);
        return this;
    }

    /**
     * 添加Tab自定义View选中事件
     *
     * @param tabSelectedListener
     * @return
     */
    public TabPageView addCustomViewOnTabSelectedListener(final OnTabSelectedListener tabSelectedListener) {
        if(customViewId > NO_ID || customViewCreated) {
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    View view = tab.getCustomView();
                    int position = tab.getPosition();
                    tabSelectedListener.onTabChageStatus(true, tab, view, position);
                    tabSelectedListener.onTabSelected(tab, view, position);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    View view = tab.getCustomView();
                    int position = tab.getPosition();
                    tabSelectedListener.onTabChageStatus(false, tab, view, position);
                    tabSelectedListener.onTabSelected(tab, view, position);
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                    tabSelectedListener.onTabReselected(tab, tab.getCustomView(), tab.getPosition());
                }
            });
        }
        return this;
    }

    /**
     * 设置ViewPage缓存fragment个数
     *
     * @param limit
     * @return
     */
    public TabPageView setOffscreenPageLimit(int limit) {
        if (limit > 0 && this.offscreenPageLimit != limit) {
            viewPager.setOffscreenPageLimit(this.offscreenPageLimit = limit);
        }
        return this;
    }

    /**
     * 获取tab的自定义布局
     *
     * @param position
     * @return
     */
    public <T extends View> T getTabCustomView(int position){
       return (T) tabLayout.getTabAt(position).getCustomView();
    }

    /**
     * 设置ViewPage显示位置
     *
     * @param item
     * @return
     */
    public TabPageView setCurrentItem(int item) {
        viewPager.setCurrentItem(item);
        return this;
    }

    public interface TabLayoutItemTabCustomView {
        /**
         * 自定义Tablayout的Item布局
         *
         * @param itemTab
         * @param index
         */
        View onCustomView(TabLayout.Tab itemTab, int index);
    }

    public interface TabLayoutItemTabCustomViewCreatCallBack {
        /**
         * 自定义Tablayout的Item布局
         *
         * @param tabItemView
         * @param index
         */
        void onCustomView(View tabItemView, int index);
    }

    public abstract static class OnTabSelectedListener {

        /**
         * 选中状态发生改变
         *
         * @param check
         * @param tabView
         * @param position
         */
        public abstract void onTabChageStatus(boolean check, TabLayout.Tab tab, View tabView, int position);

        /**
         * 被选中
         *
         * @param tab
         * @param tabView
         * @param position
         */
        public void onTabSelected(TabLayout.Tab tab, View tabView, int position) {
        }

        /**
         * 未选中
         *
         * @param tab
         * @param tabView
         * @param position
         */
        public void onTabUnselected(TabLayout.Tab tab, View tabView, int position) {
        }

        /**
         * 再次被选中
         *
         * @param tab
         * @param tabView
         * @param position
         */
        public void onTabReselected(TabLayout.Tab tab, View tabView, int position) {
        }
    }

}