package cn.dkm.gamehelper.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ab.http.AbHttpListener;
import com.ab.http.AbRequestParams;
import com.ab.util.AbJsonUtil;
import com.ab.view.pullview.AbPullToRefreshView;

import java.util.List;

import cn.dkm.gamehelper.MainActivity;
import cn.dkm.gamehelper.R;
import cn.dkm.gamehelper.gameInfo.holder.UserHolder;
import cn.dkm.gamehelper.model.params.result.BaseListResult;
import cn.dkm.gamehelper.web.NetworkWeb;

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
    public MainActivity.MainHandler mHandler;

    public UserHolder mUserHolder;

    public List<?> data;
    public boolean status = false;


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
        setListener();
    }

    private void setListener() {


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

    //初始化数据
    private void setData() {
        if(isShowFooter){
            mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
        }


        if(isShowHeader){
            mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
        }

    }

    public void connectNet(AbRequestParams params, String url){

        NetworkWeb networkWeb = NetworkWeb.newInstance(getContext());
        networkWeb.findQueryList( params, url,new AbHttpListener() {
            @Override
            public void onFailure(String content) {

                status = false;
            }

            @Override
            public void onSuccess(List<?> list) {

                data = list;
                status = true;
            }

            @Override
            public void onSuccess(String content) {

                BaseListResult baseListResult = (BaseListResult) AbJsonUtil.fromJson(content,BaseListResult.class);
                data = baseListResult.getRows();
                status = true;
            }
        });

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {

            MainActivity  mainActivity = (MainActivity) context;
            this.mHandler = mainActivity.getHandler();
            this.mUserHolder = mainActivity.getUserHolder();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
