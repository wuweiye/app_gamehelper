package cn.dkm.gamehelper.gameInfo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbHttpListener;
import com.ab.http.AbRequestParams;

import java.util.List;

import cn.dkm.gamehelper.R;
import cn.dkm.gamehelper.model.params.Assess;
import cn.dkm.gamehelper.utils.StringUtils;
import cn.dkm.gamehelper.view.RatingBar;
import cn.dkm.gamehelper.web.NetworkWeb;
import cn.dkm.gamehelper.web.UrlConstant;

public class AssessActivity extends AppCompatActivity {


    private RatingBar ratingBar;
    private TextView mSubmit;
    private EditText mEditor;
    private float star = 0f;
    private String gid;
    private String assessId = null;
    private ImageView mQuit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assess);

        initView();
        initData();
        initListener();
    }

    private void initListener() {
        ratingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
                star = ratingCount;
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = mEditor.getText().toString();
                if(StringUtils.isEmpty(content)){
                    Toast.makeText(getApplicationContext(),"请先填写你的意见",Toast.LENGTH_LONG).show();
                }else {
                    submit(content);
                }

            }
        });

        mQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }

    /**
     * 提交意见
     * @param content 意见
     */
    private void submit(String content) {

        AbRequestParams params = new AbRequestParams();

        params.put("id",assessId);
        params.put("gid",gid);
        params.put("content",content);
        params.put("star",star +"");

        NetworkWeb networkWeb = NetworkWeb.newInstance(this);

        networkWeb.findQueryResult(params, UrlConstant.UrlType.ASSESS_SUMBIT, new AbHttpListener(){

            @Override
            public void onFailure(String errorMessage) {

                Toast.makeText(getApplicationContext(),"连接网络失败 错误信息"+ errorMessage ,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSuccess(String content) {
                Intent intent = new Intent();
                setResult(UrlConstant.ASSESS_SUCCESS, intent);
                finish();
            }
        });



    }

    private void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        star = bundle.getFloat("star");
        gid = bundle.getString("gid");
        assessId = bundle.getString("assessId");
        if(assessId.equals("none")){
            assessId = "";
        }


        ratingBar.setStar(star);


    }

    private void initView() {
        ratingBar = findViewById(R.id.rb);
        mSubmit = findViewById(R.id.tv_submit);
        mEditor = findViewById(R.id.et_content);
        mQuit = findViewById(R.id.iv_quit);
        
    }
}
