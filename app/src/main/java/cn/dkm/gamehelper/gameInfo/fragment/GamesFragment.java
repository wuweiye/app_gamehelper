package cn.dkm.gamehelper.gameInfo.fragment;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.ab.fragment.AbDialogFragment;
import com.ab.fragment.AbLoadDialogFragment;
import com.ab.http.AbHttpListener;
import com.ab.http.AbRequestParams;
import com.ab.task.AbTask;
import com.ab.task.AbTaskItem;
import com.ab.task.AbTaskListener;
import com.ab.util.AbDialogUtil;
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
    private AbLoadDialogFragment mDialogFragment = null;
    private RecyclerView recyclerView;
    private GamesFragmentAdapter adapter;

    @SuppressWarnings("unchecked")
    @Override
    public View initView() {

        mUserHolder.title.setText("游戏列表");
       View view = View.inflate(mContext,R.layout.fragment_games,null);
       recyclerView = view.findViewById(R.id.rv_games);
       setAbPullToRefreshView(view, true ,false);

       return view;

    }

    private void initListener() {

/*        mDialogFragment.setAbDialogOnLoadListener(new AbDialogFragment.AbDialogOnLoadListener() {

                    @Override
                    public void onLoad() {
                        // 费时操作
                    }

                });*/

    }

    @Override
    public void initDate() {

        super.initDate();

       /* //显示进度框
        mDialogFragment = AbDialogUtil.showLoadDialog(getContext(), R.drawable.ic_load, "查询中,请等一小会");
        */
        initListener();
        getDataFromNet();

    }

    private void getDataFromNet() {

        AbRequestParams params = new AbRequestParams();

        NetworkWeb networkWeb = NetworkWeb.newInstance(getContext());
        params.put("status","valid");
        params.put("page","1");
        params.put("rows","10");

        //TODO : 后期视情况优化
        /*connectNet(params, UrlConstant.GAMES);

        if(status){
            List<GameLibrary> libraries = (List<GameLibrary>) data;
            processData(libraries);
        }else {
            Log.d("-------=---", "error");
        }*/

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

                BaseListResult baseListResult = (BaseListResult) AbJsonUtil.fromJson(content,BaseListResult.class);
                List<GameLibrary> list = baseListResult.getRows();
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
                    intent.putExtra("gid",libraries.get(position).getGid());
                    intent.putExtra("name",libraries.get(position).getName());

                    startActivity(intent);
                }
            });

        }



    }


    public void refreshTask(final String flag){
        AbTask mAbTask = new AbTask();
        final AbTaskItem item = new AbTaskItem();

        if(flag.equals(HEADER)){
            item.setListener(new AbTaskListener() {
                @Override
                public void update() {
                    AbDialogUtil.removeDialog(getContext());
                    getDataFromNet();

                    mAbPullToRefreshView.onHeaderRefreshFinish();
                }

                @Override
                public void get() {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                };
            });
        }else {
            mAbPullToRefreshView.onFooterLoadFinish();
        }

        mAbTask.execute(item);
    }



/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity  mainActivity = (MainActivity) context;
        this.mHandler = mainActivity.getHandler();
        Log.d(TAG, "onAttach: mHandler 初始化ok");
    }

*/




}
