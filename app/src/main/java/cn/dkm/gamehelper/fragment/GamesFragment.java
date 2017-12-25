package cn.dkm.gamehelper.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.ab.http.AbHttpListener;
import com.ab.http.AbRequestParams;

import java.util.ArrayList;
import java.util.List;

import cn.dkm.gamehelper.MainActivity;
import cn.dkm.gamehelper.MyHandler;
import cn.dkm.gamehelper.R;
import cn.dkm.gamehelper.base.BaseFragment;
import cn.dkm.gamehelper.model.params.GameLibrary;
import cn.dkm.gamehelper.utils.CastUtils;
import cn.dkm.gamehelper.web.NetworkWeb;
import cn.dkm.gamehelper.web.UrlConstant;


/**
 * Created by Administrator on 2017/2/16.
 */

public class GamesFragment extends BaseFragment {
    private static final String TAG = GamesFragment.class.getSimpleName();
     private  TextView textView ;
     private Handler mHandler;


    @SuppressWarnings("unchecked")
    @Override
    public View initView() {

        textView = new TextView(mContext);
        textView.setTextSize(30);
        textView.setTextColor(Color.BLUE);
        textView.setGravity(Gravity.CENTER);

        return textView;

    }

    private void initListener() {

        //暂时无监听时间

    }

    @Override
    public void initDate() {

        super.initDate();
        textView.setText("activity Fragment");
        /*super.initDate();
        //textView.setText("activity Fragment");
        Log.e(TAG, "主页数据被初始化了");
        //联网请求主页的数据
        getDataFromNet();*/

        getDataFromNet();

    }

    private void getDataFromNet() {


        AbRequestParams params = new AbRequestParams();

        NetworkWeb networkWeb = NetworkWeb.newInstance(getContext());
        params.put("status","valid");
        params.put("page","1");
        params.put("rows","10");
        networkWeb.findQueryList( params, UrlConstant.GAMES,new AbHttpListener() {
            @Override
            public void onFailure(String content) {

            }

            @Override
            public void onSuccess(List<?> list) {

                List<GameLibrary> libraries = CastUtils.cost( list);
                /*Message message = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putCharSequenceArrayList("libraries",(ArrayList) libraries);
                message.setData(bundle);
                message.what = 1;
                mHandler.handleMessage(message);*/



            }
        });

     /*  String url = Constants.HOME_URL;
        OkHttpUtils
                .get()
                .url(url)
                //.addParams("","")
                .build()
                .execute(new StringCallback() {



                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG,"首页请求失败=="+e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG,"首页请求成功=="+response);
                        //解析数据
                        processData(response);
                    }


                });
*/
    }

    private void processData(String response) {

       /* ResultBeanData resultBeanData = JSON.parseObject(response,ResultBeanData.class);
        resultBean = resultBeanData.getAcrticles();


        if(resultBean != null){
            //有数据
            //设置适配器
                                                                                                                                                      adapter = new AcrticleAdapter(mContext,resultBean);
            //adapter = new AcrticleFragmentAdapter(mContext,resultBean);
            rvAcrticle.setAdapter(adapter);
            rvAcrticle.setLayoutManager(new GridLayoutManager(mContext,1));
        }else{
            //无数据
            Log.e(TAG,"无数据---------==");
        }

        for(ResultBeanData.AcrticlesBean acrticle:resultBean){
            Log.e(TAG,"解析成功=="+acrticle.getTitle()+"-----"+acrticle.getJointime());
        }*/


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity  mainActivity = (MainActivity) context;
        this.mHandler = mainActivity.getHandler();
        Log.d(TAG, "onAttach: mHandler 初始化ok");
    }





}
