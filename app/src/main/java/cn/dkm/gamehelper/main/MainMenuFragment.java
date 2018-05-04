package cn.dkm.gamehelper.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.fragment.AbAlertDialogFragment;
import com.ab.image.AbImageCache;
import com.ab.image.AbImageLoader;
import com.ab.model.AbMenuItem;
import com.ab.task.AbTask;
import com.ab.task.AbTaskItem;
import com.ab.task.AbTaskListener;
import com.ab.util.AbAnimationUtil;
import com.ab.util.AbDialogUtil;
import com.ab.util.AbFileUtil;
import com.ab.util.AbToastUtil;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;

import cn.dkm.gamehelper.MainActivity;
import cn.dkm.gamehelper.R;
import cn.dkm.gamehelper.global.MyApplication;
import cn.dkm.gamehelper.model.User;
import cn.dkm.gamehelper.utils.SPUtil;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.widget.ExpandableListView.*;
import static com.ab.network.toolbox.VolleyLog.TAG;


/**
 * Created by Administrator on 2017/12/9 0009.
 */

public class MainMenuFragment extends Fragment{

    private MyApplication application;
    private MainActivity mActivity = null;
    private ExpandableListView mMenuListView;
    private ArrayList<String> mGroupName = null;
    private ArrayList<ArrayList<AbMenuItem>> mChilds = null;
    private ArrayList<AbMenuItem> mChild1 = null;
    private ArrayList<AbMenuItem> mChild2 = null;
    private LeftMenuAdapter mAdapter;
    private OnChangeViewListener mOnChangeViewListener;
    private TextView mNameText;
    private TextView mUserPoint;
    private CircleImageView mUserPhoto;
    private ImageView sunshineView;
    private AbImageLoader mAbImageLoader = null;
    private RelativeLayout loginLayout = null;


    private TwinklingRefreshLayout refreshLayout;

    private Button quitLogin;
    private User mUser = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mActivity = (MainActivity) this.getActivity();

        View view = inflater.inflate(R.layout.main_menu, null);

        initView(view);
        initListener();
        initData();


        return view;

    }

    private void initData() {

        refreshLayout.setPureScrollModeOn();



        // 图片的下载
        mAbImageLoader = new AbImageLoader(mActivity);
        mAbImageLoader.setMaxWidth(150);
        mAbImageLoader.setMaxHeight(150);

        initMenu();

        AbAnimationUtil.playRotateAnimation(sunshineView, 2000, 5,
                Animation.RESTART);

        mAbImageLoader.setErrorImage(R.drawable.image_error);
        mAbImageLoader.setEmptyImage(R.drawable.image_empty);
    }

    private void initListener() {

        quitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SPUtil.putString(getContext(),"status","loginError");
                SPUtil.putString(getContext(),"userId","");
                SPUtil.putString(getContext(),"time","");
                SPUtil.putString(getContext(),"key","");
                SPUtil.putString(getContext(),"userName","");
                initMenu();
                Toast.makeText(mActivity,"已经退出登陆",Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initView(View view) {

        //mMenuListView =  view.findViewById(R.id.menu_list);

        mNameText = view.findViewById(R.id.user_name);
        mUserPhoto =  view.findViewById(R.id.user_photo);
        mUserPoint =  view.findViewById(R.id.user_point);
        sunshineView =  view.findViewById(R.id.sunshineView);
        loginLayout =  view.findViewById(R.id.login_layout);

        quitLogin =  view.findViewById(R.id.cacheClearBtn);
        refreshLayout = view.findViewById(R.id.refreshLayout);


    }

    public void initMenu() {


        Log.d(TAG, "initMenu: -----------------------");
        String status = SPUtil.getString(getContext(),"status","loginError");
        if (!status.equals("login")) {
            setNameText("登录");
            setUserPhoto(R.drawable.photo01);
            setUserPoint("0");
            mNameText.setCompoundDrawables(null, null, null, null);
            loginLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (mUser == null) {
                        mActivity.toLogin(mActivity.LOGIN_CODE);
                    }
                }
            });
        }else {
            setNameText(SPUtil.getString(getContext(),"userName","未知"));
            setUserPhoto(R.drawable.photo01);
            loginLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {


                }
            });
        }



    }

    public interface OnChangeViewListener {
        public abstract void onChangeView(int groupPosition, int childPosition);
    }

    public void setOnChangeViewListener(OnChangeViewListener onChangeViewListener) {
        mOnChangeViewListener = onChangeViewListener;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    /**
     * 描述：用户名的设置
     *
     * @param mNameText
     */
    public void setNameText(String mNameText) {
        this.mNameText.setText(mNameText);
    }

    /**
     * 描述：设置用户阳光
     *
     * @param mPoint
     */
    public void setUserPoint(String mPoint) {
        this.mUserPoint.setText(mPoint);
        AbAnimationUtil.playRotateAnimation(sunshineView, 2000, 5,
                Animation.RESTART);
    }

    public void downSetPhoto(String mPhotoUrl) {
        // 缩放图片的下载
        mAbImageLoader.setEmptyImage(R.drawable.photo01);
        mAbImageLoader.setErrorImage(R.drawable.photo01_error);
        mAbImageLoader.display(mUserPhoto, mPhotoUrl);
    }

    /**
     * 描述：设置头像
     *
     * @param resId
     */
    public void setUserPhoto(int resId) {
        this.mUserPhoto.setImageResource(resId);
    }

}
