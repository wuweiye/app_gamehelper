package cn.dkm.gamehelper.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.ab.http.AbHttpListener;
import com.ab.http.AbRequestParams;
import com.ab.view.pullview.AbPullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import cn.dkm.gamehelper.MainActivity;
import cn.dkm.gamehelper.MyHandler;
import cn.dkm.gamehelper.R;
import cn.dkm.gamehelper.adapter.GamesFragmentAdapter;
import cn.dkm.gamehelper.base.BaseFragment;
import cn.dkm.gamehelper.model.params.GameLibrary;
import cn.dkm.gamehelper.utils.CastUtils;
import cn.dkm.gamehelper.web.NetworkWeb;
import cn.dkm.gamehelper.web.UrlConstant;


/**
 * Created by Administrator on 2017/2/16.
 */

public class GamesFragment extends BaseFragment {
    private static final String TAG = GamesFragment.class.getSimpleName();
     private  TextView textView ;
     private Handler mHandler;
     //private AbPullToRefreshView mAbPullView;

     private RecyclerView recyclerView;
     private GamesFragmentAdapter adapter;

    @SuppressWarnings("unchecked")
    @Override
    public View initView() {

       View view = View.inflate(mContext,R.layout.fragment_games,null);
       recyclerView = view.findViewById(R.id.rv_games);
      /* mAbPullView = view.findViewById(R.id.mPullView);*/

        initListener();
        return view;

    }

    private void initListener() {

/*

        mAbPullView.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(AbPullToRefreshView abPullToRefreshView) {

                Log.d(TAG, "onHeaderRefresh: ------------------------------");
                getDataFromNet();
            }
        });
*/

    }

    @Override
    public void initDate() {

        super.initDate();
   /*     mAbPullView.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));*/
        //textView.setText("activity Fragment");
        /*super.initDate();
        //textView.setText("activity Fragment");
        Log.e(TAG, "主页数据被初始化了");
        //联网请求主页的数据
        getDataFromNet();*/

        getDataFromNet();

    }

    private void getDataFromNet() {


        AbRequestParams params = new AbRequestParams();

        NetworkWeb networkWeb = NetworkWeb.newInstance(getContext());
        params.put("status","valid");
        params.put("page","1");
        params.put("rows","10");
        networkWeb.findQueryList( params, UrlConstant.GAMES,new AbHttpListener() {
            @Override
            public void onFailure(String content) {

            }

            @Override
            public void onSuccess(List<?> list) {

                List<GameLibrary> libraries = (List<GameLibrary>) list;

                Log.d(TAG, "start: ");
                Log.d(TAG, "onSuccess: "+libraries.get(0).getContent());
                processData(libraries);

                /*Message message = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putCharSequenceArrayList("libraries",(ArrayList) libraries);
                message.setData(bundle);
                message.what = 1;
                mHandler.handleMessage(message);*/

            }
        });

    }

    private void processData(List<GameLibrary> libraries) {

        if(libraries != null){

            for (GameLibrary library : libraries){
                Log.d(TAG, "processData: li"+ library.getName());
            }
            adapter = new GamesFragmentAdapter(mContext,libraries);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new GridLayoutManager(mContext,1));

        }



    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity  mainActivity = (MainActivity) context;
        this.mHandler = mainActivity.getHandler();
        Log.d(TAG, "onAttach: mHandler 初始化ok");
    }





}
