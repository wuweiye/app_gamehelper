package cn.dkm.gamehelper;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.activity.AbActivity;
import com.ab.db.storage.AbSqliteStorage;
import com.ab.db.storage.AbSqliteStorageListener;
import com.ab.db.storage.AbStorageQuery;
import com.ab.util.AbLogUtil;
import com.ab.util.AbToastUtil;
import com.ab.view.slidingmenu.SlidingMenu;
import com.ab.view.titlebar.AbTitleBar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.dkm.gamehelper.gameInfo.fragment.FirstFragment;
import cn.dkm.gamehelper.gameInfo.fragment.MainFragment;
import cn.dkm.gamehelper.gameInfo.fragment.UserFragment;
import cn.dkm.gamehelper.gameInfo.fragment.game.AssessViewFragment;
import cn.dkm.gamehelper.gameInfo.fragment.game.DetailViewFragment;
import cn.dkm.gamehelper.gameInfo.fragment.game.ForumsViewFragment;
import cn.dkm.gamehelper.gameInfo.holder.UserHolder;
import cn.dkm.gamehelper.user.dao.UserDao;
import cn.dkm.gamehelper.gameInfo.fragment.GamesFragment;
import cn.dkm.gamehelper.gameInfo.fragment.MessageFragment;
import cn.dkm.gamehelper.gameInfo.fragment.MoodNotesFragment;
import cn.dkm.gamehelper.login.LoginActivity;
import cn.dkm.gamehelper.main.MainContentFragment;
import cn.dkm.gamehelper.main.MainMenuFragment;
import cn.dkm.gamehelper.model.User;
import cn.dkm.gamehelper.model.params.GameLibrary;
import cn.dkm.gamehelper.utils.CastUtils;
import cn.dkm.gamehelper.utils.SPUtil;
import cn.dkm.gamehelper.web.UrlConstant;
import cn.dkm.gamehelper.web.params.GameDetailParams;
import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AbActivity implements View.OnClickListener{


    private MainHandler handler;
    private SlidingMenu menu;
    private AbTitleBar mAbTitleBar = null;
    private MainMenuFragment mMainMenuFragment = null;
    private MainContentFragment mMainContentFragment = null;
    private ArrayList fragments;
    private Fragment tempFragemnt;


    private UserHolder userHolder;





    // 数据库操作类
    public AbSqliteStorage mAbSqliteStorage = null;
    public UserDao mUserDao = null;

    public final int LOGIN_CODE = 0;
    public final int FRIEND_CODE = 1;
    public final int CHAT_CODE = 2;
    private Boolean isExit = false;
    private RadioGroup mRadioGroup;

    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.activity_main);
        handler = new MainHandler(this);

        initMenu();
        initView();
        initFragment();
        initRadio();


    }

    private void initMenu() {

        mMainContentFragment = new MainContentFragment();
        mMainMenuFragment = new MainMenuFragment();
        // SlidingMenu的配置
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        //menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

        // menu视图的Fragment添加
        menu.setMenu(R.layout.sliding_menu_menu);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.menu_frame, mMainMenuFragment).commit();

    }

    private void initView() {

        userHolder = new UserHolder();

        userHolder.title = findViewById(R.id.tv_title);
        userHolder.icon = findViewById(R.id.civ_icon);
        userHolder.itemFirst = findViewById(R.id.iv_item_first);
        userHolder.itemSecond = findViewById(R.id.iv_item_second);
        mRadioGroup = findViewById(R.id.rg_main);


        userHolder.icon.setOnClickListener(this);


    }

    private void initFragment() {

        fragments = new ArrayList<>();
        fragments.add(new MainFragment());
        fragments.add(new GamesFragment());
        //fragments.add(new MoodNotesFragment());
        fragments.add(new ForumsViewFragment(new GameDetailParams()));
        fragments.add(new MessageFragment());
        fragments.add(new UserFragment());
    }

    private void initRadio() {


        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                switch (checkedId){
                    case R.id.rb_home:
                        position = 0;
                        break;
                    case R.id.rb_type:
                        position = 1;
                        break;
                    case R.id.rb_community:
                        position = 2;
                        break;
                    case R.id.rb_cart:
                        position = 3;
                        break;
                    case R.id.rb_user:
                        position = 4;
                        break;
                }

                Log.d("====",position +"------");

                if(userHolder != null){

                    if(position == 0){
                        userHolder.title.setText("首页");
                    }else if(position == 1){

                        userHolder.title.setText("游戏列表");
                    }else if(position == 2){

                        userHolder.title.setText("游戏");
                    }else if (position == 3){

                        userHolder.title.setText("--2--");
                    }else {
                        userHolder.title.setText("个人中心");
                    }
                }
                //根据位置取不同的Fragment
                Fragment baseFragment = getFragment(position);
                switchFragment(tempFragemnt, baseFragment);
            }
        });
        mRadioGroup.check(R.id.rb_home);

    }

    private void switchFragment(Fragment fromFragment, Fragment nextFragment) {

        if (tempFragemnt != nextFragment) {
            tempFragemnt = nextFragment;
            if (nextFragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //判断nextFragment是否添加
                if (!nextFragment.isAdded()) {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    //添加Fragment
                    transaction.add(R.id.content_frame, nextFragment).commit();
                } else {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.show(nextFragment).commit();
                }


            }
        }
    }


    private Fragment getFragment(int position) {

        if (fragments != null && fragments.size() > 0) {
            Fragment baseFragment = (Fragment) fragments.get(position);
            return baseFragment;
        }
        return null;


    }

    /**
     * 描述：返回.
     */
    @Override
    public void onBackPressed() {
        if (menu.isMenuShowing()) {
            menu.showContent();
        } else {
            if (mMainContentFragment.canBack()) {
                if (isExit == false) {
                    isExit = true;
                    AbToastUtil.showToast(MainActivity.this,"再按一次退出程序");
                    new Handler().postDelayed(new Runnable(){

                        @Override
                        public void run() {
                            isExit = false;
                        }

                    }, 2000);
                } else {
                    super.onBackPressed();
                }
            }

        }
    }

    /**
     * 登录
     */
    private void checkLogin(User user) {

        // 查询本地数据
        AbStorageQuery mAbStorageQuery = new AbStorageQuery();
        mAbStorageQuery.equals("user_name", user.getUserName());
        mAbStorageQuery.equals("password", user.getPassword());
        mAbStorageQuery.equals("is_login_user", true);
        mAbSqliteStorage.findData(mAbStorageQuery, mUserDao,
                new AbSqliteStorageListener.AbDataSelectListener() {

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {
                        AbToastUtil.showToast(MainActivity.this,errorMessage);
                    }

                    @Override
                    public void onSuccess(List<?> paramList) {
                        if (paramList != null && paramList.size() > 0) {
                            //登录IM
                           /* loginIMTask((User) paramList.get(0));*/
                        }else{
                            AbToastUtil.showToast(MainActivity.this,"IM信息缺失");
                        }
                    }

                });
    }


    /**
     * 描述：侧边栏刷新
     */
    public void updateMenu() {
        mMainMenuFragment.initMenu();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);
        AbLogUtil.d(this, requestCode + "--" + resultCode);

        switch (resultCode){
            case UrlConstant.LOGIN_SUCCESS:

                updateMenu();

                break;
        }

        if (resultCode != RESULT_OK) {
            return;
        }

        //刷新


        /*switch (requestCode) {
            case LOGIN_CODE :
                //登录成功后启动IM服务
                *//*startIMService();*//*
                break;
            case CHAT_CODE :
                //进入会话窗口
                String userName = intent.getStringExtra("USERNAME");
                toChat(userName);
                break;
            case FRIEND_CODE :
                //登录成功后启动IM服务
                *//*startIMService();*//*
                //进入联系人
                toContact();
                break;
        }*/

    }


    /**
     * 描述：显示这个fragment
     */
    public void showFragment(Fragment fragment) {
        // 主视图的Fragment添加
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment).commit();
        if (menu.isMenuShowing()) {
            menu.showContent();
        }
    }


    private void initTitleRightLayout() {

        /*mAbTitleBar.clearRightView();
        View rightViewMore = mInflater.inflate(R.layout.more_btn, null);
        View rightViewApp = mInflater.inflate(R.layout.app_btn, null);
        mAbTitleBar.addRightView(rightViewApp);
        mAbTitleBar.addRightView(rightViewMore);
        Button about =  rightViewMore.findViewById(R.id.moreBtn);
        Button appBtn =  rightViewApp.findViewById(R.id.appBtn);

        appBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               *//* // 应用游戏
                showApp();*//*
               Toast.makeText(getApplicationContext(),"one",Toast.LENGTH_SHORT).show();
            }
        });

        about.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               *//* Intent intent = new Intent(MainActivity.this,
                        AboutActivity.class);
                startActivity(intent);*//*
                Toast.makeText(getApplicationContext(),"two",Toast.LENGTH_SHORT).show();
            }

        });*/
    }

    private void showApp() {
        Toast.makeText(getApplicationContext(),"showApp",Toast.LENGTH_SHORT).show();
    }

    public void toLogin(int requestCode){
        Intent loginIntent = new Intent(this,LoginActivity.class);
        startActivityForResult(loginIntent,requestCode);
    }

    public void toChat(String userName){
        //进入会话窗口
        AbLogUtil.d(this, "--进入会话窗口--");
       /* Intent chatIntent = new Intent(MainActivity.this,ChatActivity.class);
        chatIntent.putExtra("USERNAME", userName);
        startActivity(chatIntent);*/
    }


    public void toContact(){
        //进入联系人
        Toast.makeText(getApplicationContext(),"进入联系人",Toast.LENGTH_LONG);
    }





    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.civ_icon:

                /*Toast.makeText(this, "头像", Toast.LENGTH_SHORT).show();*/
                menu.showMenu();
                break;
        }
    }


    public class MainHandler extends Handler {

        private WeakReference<MainActivity> mActivity;

        public MainHandler(MainActivity activity){

            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){


                case 1 :
                    Bundle bundle =  msg.getData();
                    List<GameLibrary> libraries = CastUtils.cost(bundle.getCharSequenceArrayList("libraries"));
                    int count = libraries.size();

                    Toast.makeText(getApplicationContext(),"list:"+count,Toast.LENGTH_SHORT).show();

                    break;
                case 1000:


                    break;

            }

            super.handleMessage(msg);
        }
    }


    public MainHandler getHandler(){
        return handler;
    }

    public UserHolder getUserHolder(){
        return userHolder;
    }


}
