package cn.dkm.gamehelper.gameInfo.fragment.game;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbHttpListener;
import com.ab.http.AbRequestParams;
import com.ab.util.AbJsonUtil;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cn.dkm.gamehelper.R;
import cn.dkm.gamehelper.base.BaseFragment;
import cn.dkm.gamehelper.gameInfo.activity.AssessActivity;
import cn.dkm.gamehelper.gameInfo.adapter.DetailViewAdapter;
import cn.dkm.gamehelper.gameInfo.adapter.game.AssessRecycleAdapter;
import cn.dkm.gamehelper.gameInfo.adapter.game.ViewPagerAdapter;
import cn.dkm.gamehelper.model.params.Assess;
import cn.dkm.gamehelper.utils.SPUtil;
import cn.dkm.gamehelper.web.NetworkWeb;
import cn.dkm.gamehelper.web.UrlConstant;
import cn.dkm.gamehelper.web.params.GameDetailParams;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.ab.network.toolbox.VolleyLog.TAG;
import static com.ab.network.toolbox.VolleyLog.v;


/**
 * Created by Administrator on 2017/2/16.
 */

@SuppressLint("ValidFragment")
public class AssessViewFragment extends BaseFragment {



    private TwinklingRefreshLayout refreshLayout;
    private TextView mGameAssessScore;
    private TextView mUserName;
    private CircleImageView mUserPhotoIcon;
    private RatingBar mRatingBar;
    private ProgressBar mProgressBarOneStar;
    private ProgressBar mProgressBarTwoStar;
    private ProgressBar mProgressBarThereStar;
    private ProgressBar mProgressBarFourStar;
    private ProgressBar mProgressBarFiveStar;
    private RecyclerView mRecycleAssess;
    private AssessRecycleAdapter adapter;
    private LinearLayout mNoAssess;
    private LinearLayout mAssess;

    private CircleImageView mUserPhotoIconTwo;
    private TextView mUpdate;
    private TextView mUserNameTwo;
    private TextView mContent;
    private cn.dkm.gamehelper.view.RatingBar mRatingBarTwo;


    GameDetailParams mGameDetailParams;


    public AssessViewFragment(GameDetailParams gameDetailParams){

        this.mGameDetailParams = gameDetailParams;
    }




    @Override
    public View initView() {


        View view = View.inflate(mContext, R.layout.view_assess,null);

        refreshLayout = view.findViewById(R.id.srl_refresh);
        mGameAssessScore = view.findViewById(R.id.tv_gameAssessScore);
        mUserName = view.findViewById(R.id.tv_user_name);
        mUserPhotoIcon = view.findViewById(R.id.user_photo_icon);
        mRatingBar = view.findViewById(R.id.ratingBarId);
        mProgressBarOneStar = view.findViewById(R.id.pb_progress_bar_1);
        mProgressBarTwoStar = view.findViewById(R.id.pb_progress_bar_2);
        mProgressBarThereStar = view.findViewById(R.id.pb_progress_bar_3);
        mProgressBarFourStar = view.findViewById(R.id.pb_progress_bar_4);
        mProgressBarFiveStar = view.findViewById(R.id.pb_progress_bar_5);
        mRecycleAssess = view.findViewById(R.id.recycle_assess);
        mNoAssess = view.findViewById(R.id.noAssess);
        mAssess = view.findViewById(R.id.assess);

        mUserPhotoIconTwo = view.findViewById(R.id.iv_user_icon);
        mUpdate = view.findViewById(R.id.tv_update);
        mUserNameTwo = view.findViewById(R.id.tv_name);
        mContent = view.findViewById(R.id.tv_content);
        mRatingBarTwo = view.findViewById(R.id.rb_assess);



        initListener();
        return view;

    }

    private void initListener() {

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                Log.d(TAG, "onRatingChanged: " + mGameDetailParams.getId());
                Intent intent = new Intent(getContext(), AssessActivity.class);
                intent.putExtra("star", rating);
                intent.putExtra("gid", mGameDetailParams.getId() + "");
                intent.putExtra("assessId","none");
                startActivityForResult(intent,1002);
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult: ---------------------------" + resultCode);
        switch (resultCode){
            case UrlConstant.ASSESS_SUCCESS:

                Log.d(TAG, "onActivityResult: ---------------------------");
                initDate();
                break;
        }
    }

    @Override
    public void initDate() {

        super.initDate();

        refreshLayout.setPureScrollModeOn();


        if(mGameDetailParams.getTotalAll() != 0){

            mProgressBarOneStar.setProgress(mGameDetailParams.getOneStarNum()/ mGameDetailParams.getTotalAll() * 100);
            mProgressBarTwoStar.setProgress(mGameDetailParams.getTwoStarNum()/ mGameDetailParams.getTotalAll() * 100);
            mProgressBarThereStar.setProgress(mGameDetailParams.getThereStarNum()/ mGameDetailParams.getTotalAll() * 100);
            mProgressBarFourStar.setProgress(mGameDetailParams.getFourStarNum()/ mGameDetailParams.getTotalAll() * 100);
            mProgressBarFiveStar.setProgress(mGameDetailParams.getFiveStarNum()/ mGameDetailParams.getTotalAll() * 100);
        }

        if(mGameDetailParams.getAssessId() != null){

            mAssess.setVisibility(View.VISIBLE);
            mNoAssess.setVisibility(View.GONE);
            mUserNameTwo.setText(SPUtil.getString(getContext(),"userName","未登陆"));
            mContent.setText(mGameDetailParams.getAssessContent());
            mRatingBarTwo.setStar(mGameDetailParams.getStar());
            mRatingBarTwo.setClickable(false);


        }else {
            mAssess.setVisibility(View.GONE);
            mNoAssess.setVisibility(View.VISIBLE);
            mUserName.setText(SPUtil.getString(getContext(),"userName","未登陆"));
        }


        mGameAssessScore.setText(mGameDetailParams.getAccessScore());

        AbRequestParams params = new AbRequestParams();
        params.put("gid",mGameDetailParams.getId() +"");
        params.put("page","1");
        params.put("rows","10");

        NetworkWeb networkWeb = NetworkWeb.newInstance(getContext());

        networkWeb.findQueryList(params, UrlConstant.UrlType.ASSESS, new AbHttpListener(){

            @Override
            public void onFailure(String errorMessage) {

                Toast.makeText(getContext(),"连接网络失败 错误信息"+ errorMessage ,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSuccess(List<?> list) {
                List<Assess> loginList = (List<Assess>) list;
                processData(loginList);
            }
        });
    }

    private void processData(List<Assess> loginList) {

        adapter = new AssessRecycleAdapter(getContext(), loginList);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        mRecycleAssess.setLayoutManager(gridLayoutManager);
        mRecycleAssess.setAdapter(adapter);

    }


}
