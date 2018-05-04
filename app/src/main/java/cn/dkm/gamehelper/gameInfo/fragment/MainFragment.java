package cn.dkm.gamehelper.gameInfo.fragment;


import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ab.http.AbHttpListener;
import com.ab.http.AbRequestParams;
import com.ab.task.AbTask;
import com.ab.task.AbTaskItem;
import com.ab.task.AbTaskListener;
import com.ab.util.AbDialogUtil;
import java.util.ArrayList;
import java.util.List;

import cn.dkm.gamehelper.R;
import cn.dkm.gamehelper.base.BaseFragment;
import cn.dkm.gamehelper.gameInfo.activity.GameDetailActivity;
import cn.dkm.gamehelper.gameInfo.adapter.GamesFragmentAdapter;
import cn.dkm.gamehelper.gameInfo.adapter.MainFragmentAdapter;
import cn.dkm.gamehelper.gameInfo.listener.OnItemClickListener;
import cn.dkm.gamehelper.model.params.BaseListResult;
import cn.dkm.gamehelper.model.params.GameLibrary;
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
        initBanner();
        initListener();
        //getDataFromNet();

        List<String> strings = new ArrayList<>();
        strings.add("策略1");
        strings.add("策略2");
        strings.add("策略3");
        strings.add("策略4");
        strings.add("策略5");
        strings.add("策略6");
        strings.add("策略7");

        processData(strings);

    }

    private void initBanner() {







    }


    private void initListener() {


        /*mScrollView.setOnScrollListener(new MyScrollView.OnScrollListener() {
            @Override
            public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {

                Log.d("onScrollChanged","onScrollChanged");

                if (y <= 650){
                    if (y   >=300){
                        mSearch.setClickable(false);
                    }else {
                        mSearch.setClickable(true);
                    }
                    float v = 1 - (float) y / 650;

                    mSearch.setAlpha(v);
                }
            }
        });*/

    }


    private void getDataFromNet() {

        AbRequestParams params = new AbRequestParams();

        NetworkWeb networkWeb = NetworkWeb.newInstance(getContext());
        params.put("status", "valid");
        params.put("page", "1");
        params.put("rows", "10");


        networkWeb.findQueryList(params, UrlConstant.GAMES, new AbHttpListener() {
            @Override
            public void onFailure(String content) {

            }

            @Override
            public void onSuccess(List<?> list) {

                List<String> libraries = (List<String>) list;
                processData(libraries);


            }

            @Override
            public void onSuccess(String content) {

            }
        });

    }

    private void processData(final List<String> libraries) {

        if (libraries != null) {

            adapter = new MainFragmentAdapter(mContext, libraries);

            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 1));

            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                    Toast.makeText(mContext,"--11---",Toast.LENGTH_SHORT).show();

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
