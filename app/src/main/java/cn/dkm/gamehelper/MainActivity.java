package cn.dkm.gamehelper;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ab.activity.AbActivity;
import com.ab.db.storage.AbSqliteStorage;
import com.ab.db.storage.AbSqliteStorageListener;
import com.ab.db.storage.AbStorageQuery;
import com.ab.task.AbTask;
import com.ab.util.AbDialogUtil;
import com.ab.util.AbLogUtil;
import com.ab.util.AbToastUtil;
import com.ab.view.slidingmenu.SlidingMenu;
import com.ab.view.titlebar.AbTitleBar;

import java.util.List;

import cn.dkm.gamehelper.dao.UserDao;
import cn.dkm.gamehelper.global.MyApplication;
import cn.dkm.gamehelper.login.AboutActivity;
import cn.dkm.gamehelper.login.LoginActivity;
import cn.dkm.gamehelper.main.MainContentFragment;
import cn.dkm.gamehelper.main.MainMenuFragment;
import cn.dkm.gamehelper.model.User;


public class MainActivity extends AbActivity {

    private SlidingMenu menu;


    private MyApplication application;
    private AbTitleBar mAbTitleBar = null;
    private MainMenuFragment mMainMenuFragment = null;
    private MainContentFragment mMainContentFragment = null;

    // 数据库操作类
    public AbSqliteStorage mAbSqliteStorage = null;
    public UserDao mUserDao = null;

    public final int LOGIN_CODE = 0;
    public final int FRIEND_CODE = 1;
    public final int CHAT_CODE = 2;
    private Boolean isExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.activity_main);


        //application = (MyApplication) abApplication;

        mAbTitleBar = this.getTitleBar();

        mAbTitleBar.setTitleText(R.string.app_name);
        mAbTitleBar.setLogo(R.mipmap.ic_launcher);
        mAbTitleBar.setTitleBarBackgroundColor(R.color.colorPrimary);
        mAbTitleBar.setTitleTextMargin(10,0,0,0);
        mAbTitleBar.setLogoLine(R.drawable.line);

        mMainContentFragment = new MainContentFragment();
        // 主视图的Fragment添加
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, mMainContentFragment).commit();

        mMainMenuFragment = new MainMenuFragment();


        // SlidingMenu的配置
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

        // menu视图的Fragment添加
        menu.setMenu(R.layout.sliding_menu_menu);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.menu_frame, mMainMenuFragment).commit();

        mAbTitleBar.getLogoView().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (menu.isMenuShowing()) {
                    menu.showContent();
                } else {
                    menu.showMenu();
                }
            }
        });


        initTitleRightLayout();

        // 初始化AbSqliteStorage
        mAbSqliteStorage = AbSqliteStorage.getInstance(this);
        // 初始化数据库操作实现类
        mUserDao = new UserDao(this);

        /*if(application.mUser!=null){
            // 自动登录
            checkLogin(application.mUser);
        }*/

        /*msp = Zhao.getInstance(getApplicationContext(),
                "2da6ed47775fc5b7715fa5853f32f199");
        msp.setLa(getApplicationContext());
        msp.load(getApplicationContext());

        list = Kfb.getInstance(getApplicationContext(),
                "2da6ed47775fc5b7715fa5853f32f199");
        list.setThemeStyle(getApplicationContext(), 3);
        list.init(getApplicationContext());*/

        /*showChaping();*/


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

    /**
     * 描述：启动IM服务
     */
   /* public void startIMService(){
        Log.d("TAG", "----启动IM服务----");
        IMUtil.startIMService(this);
    }*/


    /**
     * 描述：关闭IM服务
     */
    /*public void stopIMService(){
        Log.d("TAG", "----关闭IM服务----");
        IMUtil.logoutIM();
        IMUtil.stopIMService(this);
    }*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent intent) {
        if (resultCode != RESULT_OK) {
            return;
        }

        //刷新
        updateMenu();

        switch (requestCode) {
            case LOGIN_CODE :
                //登录成功后启动IM服务
                /*startIMService();*/
                break;
            case CHAT_CODE :
                //进入会话窗口
                String userName = intent.getStringExtra("USERNAME");
                /*toChat(userName);*/
                break;
            case FRIEND_CODE :
                //登录成功后启动IM服务
                /*startIMService();*/
                //进入联系人
               /* toContact();*/
                break;
        }

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

    /**
     * 登录IM
     *
     * */
    /*public void loginIMTask(final User user){
        if(IMUtil.isLogin()){
            return;
        }
        AbDialogUtil.showProgressDialog(MainActivity.this,R.drawable.ic_load,"登录到IM");
        AbTask task = new AbTask();
        final AbTaskItem item = new AbTaskItem();
        item.setListener(new AbTaskObjectListener(){

            @Override
            public <T> void update(T entity) {
                AbDialogUtil.removeDialog(MainActivity.this);
                Log.d("TAG", "登录执行完成");
                int code = (Integer)entity;
                if(code == IMUtil.SUCCESS_CODE || code == IMUtil.LOGGED_CODE){
                    AbToastUtil.showToast(MainActivity.this,"IM登录成功");
                    //启动IM服务
                    startIMService();
                    //要跳转到哪里
                    toByIntent(getIntent());
                }else if(code == IMUtil.FAIL_CODE){
                    AbToastUtil.showToast(MainActivity.this,"IM登录失败");
                }

            }

            @SuppressWarnings("unchecked")
            @Override
            public Integer getObject() {
                int code = IMUtil.FAIL_CODE;
                try{
                    //设置用户名与密码
                    code = IMUtil.loginIM(user.getUserName(),user.getPassword());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return code;
            }

        });

        task.execute(item);
    }*/

    private void initTitleRightLayout() {

        mAbTitleBar.clearRightView();
        View rightViewMore = mInflater.inflate(R.layout.more_btn, null);
        View rightViewApp = mInflater.inflate(R.layout.app_btn, null);
        mAbTitleBar.addRightView(rightViewApp);
        mAbTitleBar.addRightView(rightViewMore);
        Button about =  rightViewMore.findViewById(R.id.moreBtn);
        Button appBtn =  rightViewApp.findViewById(R.id.appBtn);

        appBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 应用游戏
                showApp();
            }
        });

        about.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        AboutActivity.class);
                startActivity(intent);
            }

        });
    }

    private void showApp() {
        Toast.makeText(getApplicationContext(),"showApp",Toast.LENGTH_SHORT);
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
       /* Intent friendIntent = new Intent(MainActivity.this,
                ContacterActivity.class);
        startActivity(friendIntent);*/
    }

    @Override
    protected void onPause() {
        initTitleRightLayout();
        AbLogUtil.d(this, "--onPause--");
        //AbMonitorUtil.closeMonitor();
        super.onPause();
    }

    @Override
    protected void onResume() {
        AbLogUtil.d(this, "--onResume--");
        //如果debug模式被打开，显示监控
        //AbMonitorUtil.openMonitor(this);
        super.onResume();
    }

    @Override
    public void finish() {
        super.finish();

    }
}