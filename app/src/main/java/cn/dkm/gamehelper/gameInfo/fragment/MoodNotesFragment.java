package cn.dkm.gamehelper.gameInfo.fragment;


import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import cn.dkm.gamehelper.base.BaseFragment;


/**
 * Created by Administrator on 2017/2/16.
 */

public class MoodNotesFragment extends BaseFragment {
    private static final String TAG = MoodNotesFragment.class.getSimpleName();
     private  TextView textView ;
    @Override
    public View initView() {
       /* View view = View.inflate(mContext, R.layout.fragment_acrticle, null);
        textView = (TextView) view.findViewById(R.id.action_bar_title);
        rvAcrticle = (RecyclerView) view.findViewById(R.id.rv_acrticle);
        initListener();

        return view;*/
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
       /* super.initDate();
        //textView.setText("MoodNotes Fragment");

        //联网请求主页的数据
        getDataFromNet();*/
        super.initDate();
        textView.setText("MoodNotes Fragment");
    }

    private void getDataFromNet() {

        /*String url = Constants.MOONNOTE_URL;
        OkHttpUtils
                .get()
                .url(url)
                //.addParams("","")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG,"MoonNotes请求失败=="+e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG,"MoonNotes请求成功=="+response);
                        //解析数据
                        processData(response);
                    }


                });*/

    }

    private void processData(String response) {


        /*ResultMoonNote moonNote = JSON.parseObject(response,ResultMoonNote.class);
        moonNotes = moonNote.getMoonNotes();

        if(moonNotes != null){
            //有数据
            //设置适配器
            adapter = new MoonNoteAdapter(mContext,moonNotes);
            rvAcrticle.setAdapter(adapter);
            rvAcrticle.setLayoutManager(new GridLayoutManager(mContext,1));
        }else{
            //无数据
            Log.e(TAG,"MoonNotes无数据---------==");
        }

        for(ResultMoonNote.MoonNotesBean moonNote1:moonNotes){
            Log.e(TAG,"MoonNotes解析成功==="+moonNote1.getMnDate()+"-----"+moonNote1.getMnContent());
        }*/

    }


}
