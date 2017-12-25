package cn.dkm.gamehelper;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.List;

import cn.dkm.gamehelper.global.Constant;
import cn.dkm.gamehelper.model.params.GameLibrary;
import cn.dkm.gamehelper.utils.CastUtils;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/12/25.
 */

public class MyHandler extends Handler {

    private WeakReference<Activity> mActivity;



    public MyHandler(Activity activity){

        mActivity = new WeakReference<Activity>(activity);
    }

    @Override
    public void handleMessage(Message msg) {

        switch (msg.what){
            case 1 :
                Bundle bundle =  msg.getData();
                List<GameLibrary> libraries = CastUtils.cost(bundle.getCharSequenceArrayList("libraries"));
                int count = libraries.size();
                Log.d(TAG, "handleMessage: ok --------------------" + count);
                Toast.makeText(mActivity.get(),"list:"+count,Toast.LENGTH_SHORT).show();

                break;
        }

        super.handleMessage(msg);
    }
}
