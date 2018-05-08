package cn.dkm.gamehelper.gameInfo.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.activity.AbActivity;
import com.ab.http.AbHttpListener;
import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.util.AbJsonUtil;
import com.ab.view.titlebar.AbTitleBar;
import com.bumptech.glide.Glide;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;

import cn.dkm.gamehelper.R;
import cn.dkm.gamehelper.gameInfo.fragment.game.AssessViewFragment;
import cn.dkm.gamehelper.gameInfo.fragment.game.DetailViewFragment;
import cn.dkm.gamehelper.listener.FileResponseListener;
import cn.dkm.gamehelper.web.NetworkWeb;
import cn.dkm.gamehelper.web.UrlConstant;
import cn.dkm.gamehelper.web.params.GameDetailParams;

public class GameDetailActivity extends AbActivity {
    private final static String TAG = "GameDetailActivity";

    private TwinklingRefreshLayout refreshLayout;

    private TextView mTvTitle;
    private TextView mTvContent;

    private TextView mTvNum;

    private TextView mTvTime;

    private TextView mTvAssent;

    private ImageView mIvGameIcon;

    private ImageView mIvIcon;

    private View itemView;

    private View radioView;

    private View mGameBar;

    private ImageView mQuit;

    private TextView mGameName;

    private AbTitleBar mAbTitleBar = null;

    private ArrayList fragments;

    private Fragment tempFragment;

    private RadioGroup mRadioGroup;




    private int position = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.activity_game_detail);
       
        mAbTitleBar = this.getTitleBar();
        mAbTitleBar.setVisibility(View.GONE);

        initView();
        initDate();
        initListener();


    }

    private void initFragment(GameDetailParams gameDetailParams) {

        fragments = new ArrayList<>();
        fragments.add(new DetailViewFragment(gameDetailParams));
        fragments.add(new AssessViewFragment(gameDetailParams));
        fragments.add(new DetailViewFragment(gameDetailParams));

    }


    private void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mTvAssent = findViewById(R.id.tv_assent);
        mTvTime = findViewById(R.id.tv_time);
        mTvNum = findViewById(R.id.tv_num);
        mTvContent = findViewById(R.id.tv_content);
        mIvGameIcon = findViewById(R.id.iv_game_icon);
        itemView = findViewById(R.id.l_item);
        mIvIcon = itemView .findViewById(R.id.iv_icon);
        refreshLayout = findViewById(R.id.srl_refresh);
        radioView = findViewById(R.id.l_radio);

        mGameBar = findViewById(R.id.game_bar);
        mQuit = mGameBar.findViewById(R.id.civ_icon);
        mGameName = mGameBar.findViewById(R.id.tv_name);

        mRadioGroup = radioView.findViewById(R.id.rg_main);

        refreshLayout.setPureScrollModeOn();

    }

    private void initDate() {

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String gid = bundle.getString("gid");
        Log.d(TAG, "initDate: "  +gid);
        String name = bundle.getString("name");
        String logoUrl = bundle.getString("logoUrl");
        mTvTitle.setText(name);
        mGameName.setText(name);
        Glide.with(getApplicationContext()).load(UrlConstant.BASE + logoUrl).error(R.drawable.bl_icon).into(mIvIcon);
        refreshTask(gid);

    }


    private void refreshTask(String gid) {

        AbRequestParams params = new AbRequestParams();
        params.put("gid",gid);

        NetworkWeb networkWeb = NetworkWeb.newInstance(this);

        networkWeb.urlPost(params, UrlConstant.GAMES_DETAIL_URL, new AbHttpListener(){

            @Override
            public void onFailure(String errorMessage) {

                Toast.makeText(getApplicationContext(),"连接网络失败 错误信息"+ errorMessage ,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSuccess(String content) {
                Log.d(TAG, "onSuccess: "  + content);

                GameDetailParams gameDetailParams = (GameDetailParams) AbJsonUtil.fromJson(content,GameDetailParams.class);
                Log.d(TAG, "onSuccess: "  + gameDetailParams.getId()  +" status" + gameDetailParams.getStatus());

                freshenView(gameDetailParams);
            }
        });
    }

    private void initListener() {

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                switch (checkedId){
                    case R.id.rb_detail:
                        position = 0;
                        break;
                    case R.id.rb_assess:
                        position = 1;
                        break;
                    case R.id.rb_community:
                        position = 2;
                        break;

                }


                Fragment baseFragment = getFragment(position);

                switchFragment(tempFragment, baseFragment);

            }
        });

        mQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void switchFragment(Fragment fromFragment, Fragment nextFragment) {

        if (tempFragment != nextFragment) {
            tempFragment = nextFragment;
            if (nextFragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //判断nextFragment是否添加
                if (!nextFragment.isAdded()) {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    //添加Fragment
                    transaction.add(R.id.content_frame2, nextFragment).commit();
                } else {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.show(nextFragment).commit();
                }


            }
        }
    }

    private Fragment getFragment(int position) {

        if (fragments != null && fragments.size() > 0) {
            Fragment baseFragment = (Fragment) fragments.get(position);
            return baseFragment;
        }
        return null;


    }

    private void freshenView(GameDetailParams gameDetailParams) {



        mTvNum.setText(gameDetailParams.getFiveStarNum() + "人关注");
        mTvContent.setText("游戏厂商:" + gameDetailParams.getDevelopStore());
        mTvAssent.setText(gameDetailParams.getAccessScore());
        String label = "";
        if(gameDetailParams.getLabels() != null){
            for(int i = 0;i < gameDetailParams.getLabels().size(); i++){
                label += gameDetailParams.getLabels().get(i) +"    ";
            }
        }

        mTvTime.setText(label);

        initFragment(gameDetailParams);
        mRadioGroup.check(R.id.rb_detail);

        AbHttpUtil abHttpUtil = AbHttpUtil.getInstance(getApplicationContext());
        Log.d(TAG, "start image ");
        abHttpUtil.get(UrlConstant.BASE + gameDetailParams.getUrlPath(), new FileResponseListener(this,getApplicationContext(),mIvGameIcon));


    }
}
