package cn.dkm.gamehelper.gameInfo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.activity.AbActivity;
import com.ab.http.AbHttpListener;
import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.util.AbJsonUtil;
import com.ab.view.titlebar.AbTitleBar;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.dkm.gamehelper.R;
import cn.dkm.gamehelper.global.Constant;
import cn.dkm.gamehelper.listener.FileResponseListener;
import cn.dkm.gamehelper.model.params.BaseListResult;
import cn.dkm.gamehelper.model.params.GameLibrary;
import cn.dkm.gamehelper.web.NetworkWeb;
import cn.dkm.gamehelper.web.UrlConstant;
import cn.dkm.gamehelper.web.params.GameDetailParams;
import cn.dkm.gamehelper.web.result.GameDetailResult;

public class GameDetailActivity extends AbActivity {
    private final static String TAG = "GameDetailActivity";

    TextView mTvTitle;
    TextView mTvContent;

    TextView mTvNum;

    TextView mTvTime;

    TextView mTvAssent;


    ImageView mIvGameIcon;

    @BindView(R.id.iv_icon)
    ImageView mIvIcon;


    private AbTitleBar mAbTitleBar = null;


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


    private void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mTvAssent = findViewById(R.id.tv_assent);
        mTvTime = findViewById(R.id.tv_time);
        mTvNum = findViewById(R.id.tv_num);
        mTvContent = findViewById(R.id.tv_content);
        mIvGameIcon = findViewById(R.id.iv_game_icon);

    }

    private void initDate() {






        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String gid = bundle.getString("gid");
        String name = bundle.getString("name");
        mTvTitle.setText(name);
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

                GameDetailParams gameDetailParams = (GameDetailParams) AbJsonUtil.fromJson(content,GameDetailParams.class);

                freshenView(gameDetailParams);
            }
        });
    }

    private void initListener() {

    }

    private void freshenView(GameDetailParams gameDetailParams) {

        mTvNum.setText(gameDetailParams.getFiveStarNum() + "人关注");

        mTvContent.setText("游戏厂商:" + gameDetailParams.getDevelopStore());
        mTvAssent.setText("100%");
        String label = "";
        for(int i = 0;i < gameDetailParams.getLabels().size(); i++){
            label += gameDetailParams.getLabels().get(i) +"    ";
        }
        mTvTime.setText(label);

        AbHttpUtil abHttpUtil = AbHttpUtil.getInstance(getApplicationContext());
        Log.d(TAG, "start image ");
        abHttpUtil.get(gameDetailParams.getUrlPath(), new FileResponseListener(this,getApplicationContext(),mIvGameIcon));
    }
}
