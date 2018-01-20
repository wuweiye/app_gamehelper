package cn.dkm.gamehelper.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbHttpListener;
import com.ab.http.AbRequestParams;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.dkm.gamehelper.R;
import cn.dkm.gamehelper.model.params.GameLibrary;
import cn.dkm.gamehelper.model.params.Login;
import cn.dkm.gamehelper.web.NetworkWeb;
import cn.dkm.gamehelper.web.UrlConstant;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.tv_register)
    TextView mRegister;

    @BindView(R.id.tv_pwd)
    TextView mForgetPwd;

    @BindView(R.id.et_phone)
    EditText mPhone;

    @BindView(R.id.et_pwd)
    EditText mPwd;

    @BindView(R.id.bt_login)
    Button mLogin;

    Unbinder bind;

    String sessionid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bind = ButterKnife.bind(this);

        initView();
        initListener();


    }



    private void initView() {

        SharedPreferences sp = getSharedPreferences("phone",MODE_PRIVATE);

        String phone = sp.getString("phone","");
        String pwd = sp.getString("pwd", "");

        mPhone.setText(phone);
        mPwd.setText(pwd);
    }

    private void initListener() {


    }



    @OnClick(R.id.bt_login)
    public void login(){

        final String phone = mPhone.getText().toString();
        final String pwd = mPwd.getText().toString();



        if(!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(pwd)){


            AbRequestParams params = new AbRequestParams();

            params.put("name", phone);
            params.put("pwd", pwd);

            NetworkWeb networkWeb = NetworkWeb.newInstance(getApplicationContext());
            networkWeb.findQueryList(params, UrlConstant.LOGIN, new AbHttpListener() {
                @Override
                public void onFailure(String s) {

                    Toast.makeText(getApplicationContext(), "网络失败,请检测网络！", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(List<?> list) {
                    List<Login> loginList = (List<Login>) list;
                    processData(loginList);
                }

                @Override
                public void onSuccess(String content) {
                    super.onSuccess(content);
                }
            });


        }

    }

    private void processData(List<Login> list) {



    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
