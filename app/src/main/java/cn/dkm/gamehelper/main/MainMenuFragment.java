package cn.dkm.gamehelper.main;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import java.util.ArrayList;

import cn.dkm.gamehelper.MainActivity;
import cn.dkm.gamehelper.R;
import cn.dkm.gamehelper.global.MyApplication;
import cn.dkm.gamehelper.model.User;


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
    private ImageView mUserPhoto;
    private ImageView sunshineView;
    private AbImageLoader mAbImageLoader = null;
    private RelativeLayout loginLayout = null;
    private User mUser = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mActivity = (MainActivity) this.getActivity();
        /*application = (MyApplication) mActivity.getApplication();*/

        View view = inflater.inflate(R.layout.main_menu, null);
        mMenuListView =  view.findViewById(R.id.menu_list);

        mNameText = view.findViewById(R.id.user_name);
        mUserPhoto =  view.findViewById(R.id.user_photo);
        mUserPoint =  view.findViewById(R.id.user_point);
        sunshineView =  view.findViewById(R.id.sunshineView);
        loginLayout =  view.findViewById(R.id.login_layout);
        Button cacheClearBtn =  view.findViewById(R.id.cacheClearBtn);


        cacheClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AbDialogUtil.showProgressDialog(mActivity,0, "正在清空缓存...");
                AbTask task = new AbTask();
                // 定义异步执行的对象
                final AbTaskItem item = new AbTaskItem();
                item.setListener(new AbTaskListener() {

                    @Override
                    public void update() {
                        AbDialogUtil.removeDialog(mActivity);
                        AbToastUtil.showToast(mActivity, "缓存已清空完成");
                    }

                    @Override
                    public void get() {
                        try {
                            AbFileUtil.clearDownloadFile();
                            AbImageCache.getInstance().clearBitmap();
                        } catch (Exception e) {
                            AbToastUtil.showToastInThread(mActivity,
                                    e.getMessage());
                        }
                    };
                });
                task.execute(item);
            }
        });


        mGroupName = new ArrayList<>();
        mChild1 = new ArrayList<>();
        mChild2 = new ArrayList<>();

        ArrayList<ArrayList<AbMenuItem>> mChilds = new ArrayList<>();
        mChilds.add(mChild1);
        mChilds.add(mChild2);

        mAdapter = new LeftMenuAdapter(mActivity, mGroupName, mChilds);
        mMenuListView.setAdapter(mAdapter);
        for (int i = 0; i < mGroupName.size(); i++) {
            mMenuListView.expandGroup(i);
        }

        mMenuListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });

        mMenuListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (mOnChangeViewListener != null) {
                    mOnChangeViewListener.onChangeView(groupPosition, childPosition);
                }
                return true;
            }
        });

        // 图片的下载
        mAbImageLoader = new AbImageLoader(mActivity);
        mAbImageLoader.setMaxWidth(150);
        mAbImageLoader.setMaxHeight(150);

        initMenu();

        AbAnimationUtil.playRotateAnimation(sunshineView, 2000, 5,
                Animation.RESTART);

        mAbImageLoader.setErrorImage(R.drawable.image_error);
        mAbImageLoader.setEmptyImage(R.drawable.image_empty);

        return view;

    }

    public void initMenu() {

        mGroupName.clear();
        mChild1.clear();
        mChild2.clear();

        mGroupName.add("常用");
        mGroupName.add("操作");

        AbMenuItem m0 = new AbMenuItem();
        m0.setIconId(R.drawable.square);
        m0.setText("联系人");
        mChild1.add(m0);

        AbMenuItem m1 = new AbMenuItem();
        m1.setIconId(R.drawable.square);
        m1.setText("我的消息");
        mChild1.add(m1);

        AbMenuItem m3 = new AbMenuItem();
        m3.setIconId(R.drawable.share);
        m3.setText("程序案例");
        mChild1.add(m3);

        AbMenuItem m4 = new AbMenuItem();
        m4.setIconId(R.drawable.app);
        m4.setText("应用游戏");
        mChild1.add(m4);

        AbMenuItem m5 = new AbMenuItem();
        m5.setIconId(R.drawable.set);
        m5.setText("支持我");
        mChild2.add(m5);

        AbMenuItem m6 = new AbMenuItem();
        m6.setIconId(R.drawable.recommend);
        m6.setText("推荐给好友");
        mChild2.add(m6);

        /*mUser = application.mUser;
        if (mUser != null) {
            AbMenuItem m7 = new AbMenuItem();
            m7.setIconId(R.drawable.quit);
            m7.setText("注销");
            mChild2.add(m7);
        }*/

        AbMenuItem m8 = new AbMenuItem();
        m8.setIconId(R.drawable.about);
        m8.setText("关于");
        mChild2.add(m8);
        mAdapter.notifyDataSetChanged();
        for (int i = 0; i < mGroupName.size(); i++) {
            mMenuListView.expandGroup(i);
        }

        if (mUser == null) {
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
            setNameText(mUser.getUserName());
            downSetPhoto(mUser.getHeadUrl());
            if ("MAN".equals(mUser.getSex())) {
                Drawable d = mActivity.getResources().getDrawable(
                        R.drawable.user_info_male);
                d.setBounds(0, 0, 26, 26);
                mNameText.setCompoundDrawables(null, null, d, null);
            } else if ("WOMAN".equals(mUser.getSex())) {
                Drawable d = mActivity.getResources().getDrawable(
                        R.drawable.user_info_female);
                d.setBounds(0, 0, 26, 26);
                mNameText.setCompoundDrawables(null, null, d, null);
            } else {
                mNameText.setCompoundDrawables(null, null, null, null);
            }

            setUserPoint(String.valueOf(mUser.getPoint()));
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
