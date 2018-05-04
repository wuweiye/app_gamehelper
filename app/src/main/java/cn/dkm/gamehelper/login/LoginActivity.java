package cn.dkm.gamehelper.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import cn.dkm.gamehelper.utils.SPUtil;
import cn.dkm.gamehelper.web.NetworkWeb;
import cn.dkm.gamehelper.web.UrlConstant;


public class LoginActivity extends AppCompatActivity {


    TextView mRegister;


    TextView mForgetPwd;

    @BindView(R.id.et_phone)
    EditText mPhone;

    @BindView(R.id.et_pwd)
    EditText mPwd;

    @BindView(R.id.bt_login)
    Button mLogin;

    ImageView mIvQuit;
    Unbinder bind;

    String sessionid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        initView();
        initListener();


    }



    private void initView() {

        mLogin = findViewById(R.id.bt_login);
        mPhone = findViewById(R.id.et_phone);
        mPwd = findViewById(R.id.et_pwd);
        mIvQuit = findViewById(R.id.iv_quit);


        String phone =  SPUtil.getString(getApplicationContext(), "phone", "15670698550");
        String pwd =  SPUtil.getString(getApplicationContext(), "pwd", "15670698550");


        mPhone.setText(phone);
        mPwd.setText(pwd);

    }

    private void initListener() {

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        mIvQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }



    public void login(){

        final String phone = mPhone.getText().toString();
        final String pwd = mPwd.getText().toString();

        if(!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(pwd)){


            AbRequestParams params = new AbRequestParams();

            params.put("name", phone);
            params.put("pwd", pwd);

            NetworkWeb networkWeb = NetworkWeb.newInstance(getApplicationContext());
            networkWeb.findQueryList(params, UrlConstant.UrlType.LOGIN, new AbHttpListener() {
                @Override
                public void onFailure(String s) {

                   loginError();

                    Toast.makeText(getApplicationContext(), "网络失败,请检测网络！", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(List<?> list) {
                    List<Login> loginList = (List<Login>) list;
                    processData(loginList.get(0));
                }

                @Override
                public void onSuccess(String content) {
                    super.onSuccess(content);
                }
            });


        }

    }

    private void loginError() {
        SPUtil.putString(getApplicationContext(),"status","loginError");
        SPUtil.putString(getApplicationContext(),"userId","");
        SPUtil.putString(getApplicationContext(),"time","");
        SPUtil.putString(getApplicationContext(),"key","");
        SPUtil.putString(getApplicationContext(),"userName","");
    }

    private void processData(Login login) {



        if(login.getResultCode() == 0){

            SPUtil.putString(getApplicationContext(),"status","login");
            SPUtil.putString(getApplicationContext(),"userId",login.getUserId());
            SPUtil.putString(getApplicationContext(),"time",login.getTime());
            SPUtil.putString(getApplicationContext(),"key",login.getKey());
            SPUtil.putString(getApplicationContext(),"userName",login.getUserName());

            Intent intent = new Intent();
            intent.putExtra("result", "success");
            setResult(UrlConstant.LOGIN_SUCCESS, intent);
            finish();

        }else {

            SPUtil.putString(getApplicationContext(),"status","loginError");
            SPUtil.putString(getApplicationContext(),"userId","");
            SPUtil.putString(getApplicationContext(),"time","");
            SPUtil.putString(getApplicationContext(),"key","");
            SPUtil.putString(getApplicationContext(),"userName","");
            Toast.makeText(getApplicationContext(),"登陆失败,失败原因：" + login.getResultMessage(), Toast.LENGTH_SHORT).show();
        }




    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
