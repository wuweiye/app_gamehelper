package cn.dkm.gamehelper.gameInfo.fragment;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ab.http.AbHttpListener;
import com.ab.http.AbRequestParams;
import com.ab.task.AbTask;
import com.ab.task.AbTaskItem;
import com.ab.task.AbTaskListener;
import com.ab.util.AbDialogUtil;
import com.ab.util.AbJsonUtil;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

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
    private Banner banner;

    //@SuppressWarnings("unchecked")
    @Override
    public View initView() {


        View view = View.inflate(mContext, R.layout.fragment_main, null);
        recyclerView = view.findViewById(R.id.rv_recycle);
        banner = view.findViewById(R.id.banner);

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

        List<Integer> list = new ArrayList<>();

        list.add(R.drawable.banner);
        list.add(R.drawable.banner);
        list.add(R.drawable.banner);
        list.add(R.drawable.banner);

        banner.setImages(list).setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        }).start();





    }


    private void initListener() {



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
            /*
                BaseListResult baseListResult = (BaseListResult) AbJsonUtil.fromJson(content, BaseListResult.class);
                List<String> list = baseListResult.getRows();
                processData(list);*/
            }
        });

    }

    private void processData(final List<String> libraries) {

        if (libraries != null) {

            adapter = new MainFragmentAdapter(mContext, libraries);

            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));

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
