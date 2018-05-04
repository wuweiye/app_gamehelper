package cn.dkm.gamehelper.gameInfo.fragment;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gongwen.marqueen.SimpleMF;
import com.gongwen.marqueen.SimpleMarqueeView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.dkm.gamehelper.R;
import cn.dkm.gamehelper.base.BaseFragment;
import cn.dkm.gamehelper.base.BaseFragmentTwo;
import cn.dkm.gamehelper.gameInfo.adapter.FirstRecycleAdapter;
import cn.dkm.gamehelper.view.MyScrollView;

/**
 * Created by Administrator on 2018/1/17.
 */

@SuppressWarnings("all")
public class FirstFragment extends BaseFragmentTwo {

    private TwinklingRefreshLayout mRefreshLayout;

    private MyScrollView mScrollView;

    private Banner mBanner;

    private RecyclerView mRecyclerView;

    private RelativeLayout mSearch;

    private View mCourseFirst, mCourseSecond, mCourseThird;

    private SimpleMarqueeView mSimpleMarqueeView;
    private FirstRecycleAdapter mFirstRecycleAdapter;


    @Override
    public View initView() {

        View view = View.inflate(getContext(), R.layout.homepage_fragment, null);

        mRefreshLayout = view.findViewById(R.id.trl_refresh);
        mScrollView = view.findViewById(R.id.sv_scroll);
        mBanner = view.findViewById(R.id.banner);
        mRecyclerView = view.findViewById(R.id.rv_item);
        mSearch = view.findViewById(R.id.rl_search);
        mCourseFirst = view.findViewById(R.id.course_first);
        mCourseSecond = view.findViewById(R.id.course_second);
        mCourseThird = view.findViewById(R.id.course_third);
        mSimpleMarqueeView = view.findViewById(R.id.smv_marqueeView);






        return view;
    }

    @Override
    public void initDate() {
        super.initDate();
        //banner
        SimpleMF <String> simpleMF = new SimpleMF<>(getActivity());

        List<String> list = new ArrayList<>();
        list.add("111111111111111111111111");
        list.add("2222222222222222222222222");
        list.add("33333333333333333333333");
        list.add("44444444444444444444444444");

        simpleMF.setData(list);

        mSimpleMarqueeView.setMarqueeFactory(simpleMF);
        mSimpleMarqueeView.startFlipping();


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),5);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mFirstRecycleAdapter = new FirstRecycleAdapter(null , getContext());
        mRecyclerView.setAdapter(mFirstRecycleAdapter);



        mRefreshLayout.setEnableLoadmore(false);
        mRefreshLayout.setDefaultHeader(SinaRefreshView.class.getName());



    }

    @Override
    public void onStop() {
        super.onStop();

        mSimpleMarqueeView.startFlipping();
    }

    @Override
    public void onResume() {
        super.onResume();

        initRecycle();
        initBanner();
        initCourse();


    }

    private void initRecycle() {
        initRecycleDate();
    }

    private void initRecycleDate() {

    }

    private void initCourse() {


        initCourseData(mCourseFirst,0);
        initCourseData(mCourseSecond,1);
        initCourseData(mCourseThird,2);

    }

    private void initCourseData(View view , int key){




        ViewHolder viewHolder = (ViewHolder) view.getTag();
        if(viewHolder == null){
            viewHolder = new ViewHolder();
            viewHolder.mCourseIcon = view.findViewById(R.id.iv_course_icon);
            viewHolder.mTitle = view.findViewById(R.id.tv_title);
            viewHolder.mMore = view.findViewById(R.id.iv_more);
            viewHolder.mCourseItemFirst = view.findViewById(R.id.course_item_first);
            viewHolder.mCourseItemSecond = view.findViewById(R.id.course_item_second);
            viewHolder.mCourseImageFirst = viewHolder.mCourseItemFirst.findViewById(R.id.iv_course_one);
            viewHolder.mCourseTitleFirst = viewHolder.mCourseItemFirst.findViewById(R.id.tv_title);
            viewHolder.mCourseLecturerFirst = viewHolder.mCourseItemFirst.findViewById(R.id.tv_lecturer);
            viewHolder.mCollectionCountFirst = viewHolder.mCourseItemFirst.findViewById(R.id.tv_collection_count);
            viewHolder.mFollowCountFirst = viewHolder.mCourseItemFirst.findViewById(R.id.tv_follow_count);

            viewHolder.mCourseImageSecond = viewHolder.mCourseItemSecond.findViewById(R.id.iv_course_one);
            viewHolder.mCourseTitleSecond = viewHolder.mCourseItemSecond.findViewById(R.id.tv_title);
            viewHolder.mCourseLecturerSecond = viewHolder.mCourseItemSecond.findViewById(R.id.tv_lecturer);
            viewHolder.mCollectionCountSecond = viewHolder.mCourseItemSecond.findViewById(R.id.tv_collection_count);
            viewHolder.mFollowCountSecond = viewHolder.mCourseItemSecond.findViewById(R.id.tv_follow_count);

            view.setTag(viewHolder);

            viewHolder.mTitle.setText("课堂" + key);
            viewHolder.mCourseTitleFirst.setText("one" +key);
            viewHolder.mCourseLecturerFirst.setText("讲师 A" +key);
            viewHolder.mCollectionCountFirst.setText("100");
            viewHolder.mFollowCountFirst.setText("0");

            Glide.with(getContext()).load(R.drawable.zixunliebiao_img).into(viewHolder.mCourseImageFirst);

            viewHolder.mCourseTitleSecond.setText("two" +key);
            viewHolder.mCourseLecturerSecond.setText("讲师 B" +key);
            viewHolder.mCollectionCountSecond.setText("100");
            viewHolder.mFollowCountSecond.setText("10");

            Glide.with(getContext()).load(R.drawable.zixunliebiao_img).into(viewHolder.mCourseImageSecond);


        }




    }




    private void initBanner() {


        List<String> list = new ArrayList<>();

        list.add( "http://img.zcool.cn/community/0166c756e1427432f875520f7cc838.jpg");
        list.add( "http://img.zcool.cn/community/0166c756e1427432f875520f7cc838.jpg");
        list.add( "http://img.zcool.cn/community/0166c756e1427432f875520f7cc838.jpg");
        list.add( "http://img.zcool.cn/community/0166c756e1427432f875520f7cc838.jpg");
       /* List<Integer> list = new ArrayList<>();
        list.add(R.drawable.shanping1);
        list.add(R.drawable.shanping2);
        list.add(R.drawable.shanping3);
        list.add(R.drawable.shanping4);*/
        mBanner.setImages(list)
                .setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {

                        Glide.with(context).load(path).into(imageView);
                    }
                })
                .start();


    }


    public void initListener() {

        Log.d("initListener","initListener");

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("setOnClickListener","search onclick");

            }
        });




        mScrollView.setOnScrollListener(new MyScrollView.OnScrollListener() {
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
        });




        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onPullingDown(TwinklingRefreshLayout refreshLayout, float fraction) {
                super.onPullingDown(refreshLayout, fraction);
                Log.d("onPullingDown","-------------------");
            }

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);

                Log.d("onRefresh","-------------------");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.finishRefreshing();
                    }
                },2000);
            }

            @Override
            public void onPullDownReleasing(TwinklingRefreshLayout refreshLayout, float fraction) {
                super.onPullDownReleasing(refreshLayout, fraction);
            }
        });

    }


    class ViewHolder{

        public TextView mTitle;
        public ImageView mCourseIcon;
        public ImageView mMore;
        public View mCourseItemFirst;
        public View mCourseItemSecond;
        public ImageView mCourseImageFirst;
        public TextView mCourseTitleFirst;
        public TextView mCourseLecturerFirst;
        public TextView mCollectionCountFirst;
        public TextView mFollowCountFirst;

        public ImageView mCourseImageSecond;
        public TextView mCourseTitleSecond;
        public TextView mCourseLecturerSecond;
        public TextView mCollectionCountSecond;
        public TextView mFollowCountSecond;


    }
}
