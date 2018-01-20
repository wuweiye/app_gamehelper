package cn.dkm.gamehelper.gameInfo.fragment;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ab.http.AbRequestParams;

import org.w3c.dom.Text;

import cn.dkm.gamehelper.R;
import cn.dkm.gamehelper.base.BaseFragment;
import cn.dkm.gamehelper.login.LoginActivity;
import cn.dkm.gamehelper.utils.SPUtil;
import cn.dkm.gamehelper.web.NetworkWeb;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Administrator on 2017/2/16.
 */

public class UserFragment extends BaseFragment {


    private ImageView mSetting;

    private CircleImageView mUserIcon;

    private TextView mUserName;

    private ProgressBar mLevelProgress;

    private TextView mUserLevel;

    private TextView mUserLevelPro;

    private RelativeLayout mLogin;

    private LinearLayout mNoLogin;

    private Button mButton;



    @Override
    public View initView() {
       View view = View.inflate(getContext(), R.layout.fragment_user, null);

        mSetting = view.findViewById(R.id.iv_setting);
        mUserIcon = view.findViewById(R.id.civ_icon);
        mUserName = view.findViewById(R.id.tv_name);
        mLevelProgress = view.findViewById(R.id.my_progress_bar);
        mUserLevel = view.findViewById(R.id.tv_level);
        mUserLevelPro = view.findViewById(R.id.tv_level_pro);
        mNoLogin = view.findViewById(R.id.ll_no_login);
        mLogin = view.findViewById(R.id.rl_login);
        mButton = view.findViewById(R.id.bt_login);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivityForResult(intent, Activity.RESULT_FIRST_USER);

            }
        });
        return view;
    }


    @Override
    public void initDate() {


       SPUtil.putString(getContext(), "status","");
        getDataFromNet();

    }

    private void getDataFromNet() {

        String status = SPUtil.getString(getContext(), "status","");
        if(status.equals("")){

            mNoLogin.setVisibility(View.VISIBLE);
            mLogin.setVisibility(View.GONE);


        }else {
            mNoLogin.setVisibility(View.GONE);
            mLogin.setVisibility(View.VISIBLE);
        }


        
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if(requestCode == 1 && resultCode == 1001){
            String result = data.getStringExtra("result");
            getDataFromNet();

            String key = SPUtil.getString(getContext(),"key","");


            Log.d("------------",key +"--" + result);
        }
    }
}
