package cn.dkm.gamehelper.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ab.view.pullview.AbPullToRefreshView;

import cn.dkm.gamehelper.R;

/**
 * Created by Administrator on 2017/2/16.
 */

public abstract class BaseFragment extends Fragment {

    private boolean isShowFooter = false;
    private boolean isShowHeader = false;
    public static final String HEADER = "header";
    public static final String FOOTER = "footer";

    public Context mContext;
    public AbPullToRefreshView mAbPullToRefreshView;
    public Handler mHandler;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }

    public abstract View initView();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDate();
    }


    public void initDate() {
    }

    public void setAbPullToRefreshView(View view, boolean isShowHeader, boolean isShowFooter){
        this.isShowFooter = isShowFooter;
        this.isShowHeader = isShowHeader;
        mAbPullToRefreshView = view.findViewById(R.id.mPullView);

        setData();
        setLinstener();
    }

    private void setLinstener() {


        //下拉刷新监听
        mAbPullToRefreshView.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(AbPullToRefreshView abPullToRefreshView) {

                refreshTask(HEADER);

            }
        });




        //上拉刷新监听
        mAbPullToRefreshView.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(AbPullToRefreshView abPullToRefreshView) {
                refreshTask(FOOTER);
            }
        });


    }

    protected  void refreshTask(String flag){

    }

    private void setData() {
        if(isShowFooter){
            mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
        }


        if(isShowHeader){
            mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
        }


    }
}
