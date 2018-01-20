package cn.dkm.gamehelper.gameInfo.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ab.http.AbRequestParams;

import org.w3c.dom.Text;

import cn.dkm.gamehelper.R;
import cn.dkm.gamehelper.base.BaseFragment;
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



    @Override
    public View initView() {
       View view = View.inflate(getContext(), R.layout.fragment_user, null);

       mSetting = view.findViewById(R.id.iv_setting);
        mUserIcon = view.findViewById(R.id.civ_icon);
        mUserName = view.findViewById(R.id.tv_name);
        mLevelProgress = view.findViewById(R.id.my_progress_bar);
        mUserLevel = view.findViewById(R.id.tv_level);
        mUserLevelPro = view.findViewById(R.id.tv_level_pro);

        return view;
    }


    @Override
    public void initDate() {


        getDataFromNet();

    }

    private void getDataFromNet() {


        AbRequestParams params = new AbRequestParams();

        NetworkWeb networkWeb = NetworkWeb.newInstance(getContext());
        
    }
}
