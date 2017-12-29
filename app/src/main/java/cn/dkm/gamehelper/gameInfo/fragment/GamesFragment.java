package cn.dkm.gamehelper.gameInfo.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbHttpListener;
import com.ab.http.AbRequestParams;
import com.ab.util.AbJsonUtil;

import java.util.List;

import cn.dkm.gamehelper.MainActivity;
import cn.dkm.gamehelper.R;
import cn.dkm.gamehelper.gameInfo.activity.GameDetailActivity;
import cn.dkm.gamehelper.gameInfo.adapter.GamesFragmentAdapter;
import cn.dkm.gamehelper.base.BaseFragment;
import cn.dkm.gamehelper.gameInfo.listener.OnItemClickListener;
import cn.dkm.gamehelper.model.params.BaseListResult;
import cn.dkm.gamehelper.model.params.GameLibrary;
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

                processData(libraries);


            }

            @Override
            public void onSuccess(String content) {
                Log.d(TAG, "start: String content");
                BaseListResult baseListResult = (BaseListResult) AbJsonUtil.fromJson(content,BaseListResult.class);
                List<GameLibrary> list = baseListResult.getRows();
                Log.d(TAG, "onSuccess: "+ list.get(0));
                processData(list);
            }
        });

    }

    private void processData(final List<GameLibrary> libraries) {

        if(libraries != null){

            adapter = new GamesFragmentAdapter(mContext,libraries);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new GridLayoutManager(mContext,1));

            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, GameDetailActivity.class);
                    Log.d(TAG, "gid parent :" +libraries.get(position).getGid() +" name"+libraries.get(position).getName());
                    intent.putExtra("gid",libraries.get(position).getGid());
                    startActivity(intent);
                }
            });


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
