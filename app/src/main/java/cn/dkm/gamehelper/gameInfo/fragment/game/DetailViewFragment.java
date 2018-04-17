package cn.dkm.gamehelper.gameInfo.fragment.game;


import android.annotation.SuppressLint;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youth.banner.Banner;
import java.util.ArrayList;
import java.util.List;

import cn.dkm.gamehelper.R;
import cn.dkm.gamehelper.base.BaseFragment;
import cn.dkm.gamehelper.gameInfo.adapter.DetailViewAdapter;
import cn.dkm.gamehelper.gameInfo.adapter.game.ViewPagerAdapter;
import cn.dkm.gamehelper.web.UrlConstant;
import cn.dkm.gamehelper.web.params.GameDetailParams;


/**
 * Created by Administrator on 2017/2/16.
 */

@SuppressLint("ValidFragment")
public class DetailViewFragment extends BaseFragment {
    private static final String TAG = DetailViewFragment.class.getSimpleName();


    private List<ImageView> imageViews;
    private ViewPager mViewPager;
    private TwinklingRefreshLayout refreshLayout;
    private RecyclerView mRecycleLabel;
    private TextView mTvShowAll;
    private TextView mTvInfoDetail;
    private TextView mTvInfoContent;

    protected ImageLoader imageLoader;
    GameDetailParams mGameDetailParams;


    public DetailViewFragment(GameDetailParams gameDetailParams){

        this.mGameDetailParams = gameDetailParams;
    }

    @Override
    public View initView() {


        View view = View.inflate(mContext, R.layout.view_detail,null);

        mViewPager = view.findViewById(R.id.view_pager);
        refreshLayout = view.findViewById(R.id.srl_refresh);
        mRecycleLabel = view.findViewById(R.id.recycle_label);
        mTvShowAll = view.findViewById(R.id.tv_show_all);
        mTvInfoContent = view.findViewById(R.id.tv_info_content);

        mTvInfoDetail = view.findViewById(R.id.tv_info_detail);
        refreshLayout.setPureScrollModeOn();


        initListener();
        return view;

    }

    private void initListener() {

        mTvShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text =  mTvShowAll.getText().toString();
                if(text.equals("收起")){
                    mTvShowAll.setText("显示全部");
                    mTvInfoContent.setMaxLines(3);
                }else {
                    mTvShowAll.setText("收起");
                    mTvInfoContent.setMaxLines(100);
                }

            }
        });

    }

    @Override
    public void initDate() {

        super.initDate();

        List<String> list = new ArrayList<>();

        Log.d(TAG, "initDate: "  + mGameDetailParams.getUrlPaths());
        if(! mGameDetailParams.getUrlPaths().equals("none") ){
            String[] urlPaths = mGameDetailParams.getUrlPaths().split("\\|");

            for(String urlPath : urlPaths){

                list.add(UrlConstant.BASE + urlPath);
            }
        }else {
            list.add("http://169.254.187.73:8866//game/image/show/20180413183240.jpg");
        }



        ViewPagerAdapter adapter=new ViewPagerAdapter(mContext,list);
        mViewPager.setAdapter(adapter);

        initLabel();

        mTvInfoContent.setText(mGameDetailParams.getDesc());
        mTvInfoDetail.setText(mGameDetailParams.getDesc());
        /*super.initDate();
        //textView.setText("activity Fragment")
        Log.e(TAG, "主页数据被初始化了");
        //联网请求主页的数据
        getDataFromNet();*/
    }

    private void initLabel() {

        List<String> strings = new ArrayList<>();

        for(int i = 0;i < mGameDetailParams.getLabels().size(); i++){

            strings.add(mGameDetailParams.getLabels().get(i));

        }

        int count = mGameDetailParams.getLabels().size();

        if(count != 0){
            mRecycleLabel.setLayoutManager(new GridLayoutManager(mContext, mGameDetailParams.getLabels().size()));
            mRecycleLabel.setAdapter(new DetailViewAdapter(mContext, strings));
        }

    }


    private void getDataFromNet() {


    }

    private void processData(String response) {


    }


}
