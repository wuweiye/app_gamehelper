package cn.dkm.gamehelper.main;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import de.hdodenhof.circleimageview.CircleImageView;

import static android.widget.ExpandableListView.*;


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

        mMenuListView.setOnGroupClickListener(new OnGroupClickListener() {

            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });

        /*mMenuListView.setOnChildClickListener(new OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (mOnChangeViewListener != null) {
                    mOnChangeViewListener.onChangeView(groupPosition, childPosition);
                }
                return true;
            }
        });*/

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

                Toast.makeText(mActivity,"退出登陆",Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initView(View view) {

        mMenuListView =  view.findViewById(R.id.menu_list);

        mNameText = view.findViewById(R.id.user_name);
        mUserPhoto =  view.findViewById(R.id.user_photo);
        mUserPoint =  view.findViewById(R.id.user_point);
        sunshineView =  view.findViewById(R.id.sunshineView);
        loginLayout =  view.findViewById(R.id.login_layout);

        quitLogin =  view.findViewById(R.id.cacheClearBtn);
        refreshLayout = view.findViewById(R.id.refreshLayout);
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


        AbMenuItem m3 = new AbMenuItem();
        m3.setIconId(R.drawable.share);
        m3.setText("程序案例");
        mChild1.add(m3);

        AbMenuItem m1 = new AbMenuItem();
        m1.setIconId(R.drawable.square);
        m1.setText("我的消息");
        mChild1.add(m1);



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


       setOnChangeViewListener(new OnChangeViewListener() {
           @Override
           public void onChangeView(int groupPosition, int childPosition) {
               if (groupPosition == 0) {
                   if (childPosition == 0) {
                       Toast.makeText(getContext(),"联系人",Toast.LENGTH_LONG).show();

                   } else if (childPosition == 1) {
                       // 我的消息
                       Toast.makeText(getContext(),"我的消息",Toast.LENGTH_LONG).show();

                   } else if (childPosition == 2) {
                       // 程序案例
                       Toast.makeText(getContext(),"程序案例",Toast.LENGTH_LONG).show();

                   } else if (childPosition == 3) {
                       // 应用游戏
                       Toast.makeText(getContext(),"应用游戏",Toast.LENGTH_LONG).show();

                   }
               }else if (groupPosition == 1) {
                   if (childPosition == 0) {
                       // 选项、赞助作者
                       Toast.makeText(getContext(),"赞助作者",Toast.LENGTH_LONG).show();

                   } else if (childPosition == 1) {

                       Toast.makeText(getContext(),"推荐",Toast.LENGTH_LONG).show();
                   } else if (childPosition == 2) {
                       if (mUser != null) {
                           AbDialogUtil.showAlertDialog(mActivity, "注销",
                                   "确定要注销该用户吗?",
                                   new AbAlertDialogFragment.AbDialogOnClickListener() {

                                       @Override
                                       public void onPositiveClick() {
                                           // 注销
                                           application.clearLoginParams();
                                           initMenu();

                                       }

                                       @Override
                                       public void onNegativeClick() {
                                           // TODO Auto-generated method stub

                                       }

                                   });

                       } else {
                           // 关于
                           Toast.makeText(getContext(),"关于",Toast.LENGTH_LONG).show();

                       }
                   } else if (childPosition == 3) {
                       if (application.mUser != null) {
                           Toast.makeText(getContext(),"关于",Toast.LENGTH_LONG).show();

                       } else {

                       }
                   }
               }
           }
       });
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
