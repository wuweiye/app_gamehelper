package cn.dkm.gamehelper.gameInfo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.http.AbHttpListener;
import com.ab.http.AbRequestParams;
import com.ab.util.AbJsonUtil;
import com.ab.view.titlebar.AbTitleBar;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.dkm.gamehelper.R;
import cn.dkm.gamehelper.model.params.BaseListResult;
import cn.dkm.gamehelper.model.params.GameLibrary;
import cn.dkm.gamehelper.web.NetworkWeb;
import cn.dkm.gamehelper.web.UrlConstant;
import cn.dkm.gamehelper.web.params.GameDetailParams;
import cn.dkm.gamehelper.web.result.GameDetailResult;

public class GameDetailActivity extends AbActivity {

    @BindView(R.id.tv_title)
    TextView mTvTitle;

    @BindView(R.id.iv_game_icon)
    ImageView mIvGameIcon;

    @BindView(R.id.iv_icon)
    ImageView mIvIcon;

    @BindView(R.id.tv_content)
    TextView mTvContent;

    @BindView(R.id.tv_num)
    TextView mTvNum;

    @BindView(R.id.tv_time)
    TextView mTvTime;

    @BindView(R.id.tv_assent)
    TextView mTvAssent;


    private AbTitleBar mAbTitleBar = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.activity_game_detail);
        ButterKnife.bind(this);
        mAbTitleBar = this.getTitleBar();
        mAbTitleBar.setVisibility(View.GONE);


        initDate();





    }

    private void initDate() {

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String gid = bundle.getString("gif");




        AbRequestParams params = new AbRequestParams();
        params.put("gid",gid);

        NetworkWeb networkWeb = NetworkWeb.newInstance(this);

        networkWeb.urlPost(params, UrlConstant.GAMES_DETAIL_URL, new AbHttpListener(){

            @Override
            public void onFailure(String s) {

            }

            @Override
            public void onSuccess(String content) {
                Log.d("onSuccess", "onSuccess: " + content);
                GameDetailParams gameDetailParams = (GameDetailParams) AbJsonUtil.fromJson(content,GameDetailParams.class);

                Log.d("onSuccess", "onSuccess: " + gameDetailParams.getDevelopStore());
            }
        });

    }
}
