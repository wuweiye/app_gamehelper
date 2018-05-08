package cn.dkm.gamehelper.gameInfo.fragment;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.ab.http.AbHttpListener;
import com.ab.http.AbRequestParams;
import com.ab.task.AbTask;
import com.ab.task.AbTaskItem;
import com.ab.task.AbTaskListener;
import com.ab.util.AbDialogUtil;

import java.util.List;

import cn.dkm.gamehelper.R;
import cn.dkm.gamehelper.base.BaseFragment;
import cn.dkm.gamehelper.gameInfo.activity.GameDetailActivity;
import cn.dkm.gamehelper.gameInfo.adapter.MainFragmentAdapter;
import cn.dkm.gamehelper.gameInfo.listener.OnItemClickListener;
import cn.dkm.gamehelper.model.params.RecommendGameLibrary;
import cn.dkm.gamehelper.view.MyScrollView;
import cn.dkm.gamehelper.web.NetworkWeb;
import cn.dkm.gamehelper.web.UrlConstant;


/**
 *
 * 首页
 * Created by Administrator on 2017/2/16.
 */

public class MainFragment extends BaseFragment {

    private static final String TAG = MainFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private MainFragmentAdapter adapter;
    private MyScrollView mScrollView;
    private RelativeLayout mSearch;

    //@SuppressWarnings("unchecked")
    @Override
    public View initView() {


        View view = View.inflate(mContext, R.layout.fragment_main, null);
        recyclerView = view.findViewById(R.id.rv_recycle);
        mScrollView = view.findViewById(R.id.sv_scroll);
        mSearch = view.findViewById(R.id.rl_search);
        setAbPullToRefreshView(view, false, false);

        return view;

    }


    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        Log.d(TAG, "----onAttach----");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "---onResume-----");
    }

    @Override
    public void initDate() {

        super.initDate();
        mUserHolder.title.setText("首页");
        mUserHolder.itemSecond.setImageResource(R.drawable.search);

        initListener();

        getDataFromNet();

    }




    private void initListener() {




    }


    private void getDataFromNet() {

        AbRequestParams params = new AbRequestParams();

        NetworkWeb networkWeb = NetworkWeb.newInstance(getContext());
        params.put("isRecommend", "1");
        params.put("page", "1");
        params.put("rows", "10");

        networkWeb.findQueryList(params, UrlConstant.UrlType.RECOMMEND_GAME, new AbHttpListener() {

            @Override
            public void onSuccess(List<?> list) {
                List<RecommendGameLibrary> libraries = (List<RecommendGameLibrary>) list;
                processData(libraries);
            }

            @Override
            public void onFailure(String s) {

            }
        });


    }

    private void processData(final List<RecommendGameLibrary> libraries) {

        if (libraries != null) {

            adapter = new MainFragmentAdapter(mContext, libraries);

            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 1));

            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                    Intent intent = new Intent();
                    intent.setClass(mContext, GameDetailActivity.class);
                    intent.putExtra("gid",libraries.get(position).getGid());
                    intent.putExtra("name",libraries.get(position).getName());
                    intent.putExtra("logoUrl", libraries.get(position).getLogoUrl());

                    startActivity(intent);

                }
            });

        }


    }


    public void refreshTask(final String flag) {
        AbTask mAbTask = new AbTask();
        final AbTaskItem item = new AbTaskItem();

        if (flag.equals(HEADER)) {
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
                }

                ;
            });
        } else {
            mAbPullToRefreshView.onFooterLoadFinish();
        }

        mAbTask.execute(item);
    }




}
