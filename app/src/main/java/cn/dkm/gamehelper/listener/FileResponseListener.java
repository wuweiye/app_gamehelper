package cn.dkm.gamehelper.listener;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.ab.activity.AbActivity;
import com.ab.http.AbFileHttpResponseListener;
import com.ab.util.AbFileUtil;
import com.ab.view.progress.AbHorizontalProgressBar;

import java.io.File;

/**
 * Created by Administrator on 2017/12/10 0010.
 */

public class FileResponseListener extends AbFileHttpResponseListener {


    private int max=100;

    private Context context;
    ImageView view;
    private AbActivity activity;

    private AbHorizontalProgressBar progressBar;

    public FileResponseListener(AbActivity activity, Context context, ImageView view){

        this.activity=activity;
        this.context=context;
        this.view = view;
    }

    @Override
    public void onSuccess(int statusCode, File file) {
        Bitmap bitmap= AbFileUtil.getBitmapFromSD(file);
   /*     ImageView view=new ImageView(context);*/
        view.setImageBitmap(bitmap);


        Log.d("=========","onSuccess" + statusCode +"---"+file.getName());

    }

    @Override
    public void onStart() {

        Log.d("========","onStart:");
    }

    @Override
    public void onProgress(int bytesWritten, int totalSize) {

        Log.d("========","onProgress:" +bytesWritten + "---" +totalSize);


    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onFailure(int statusCode, String content, Throwable error) {

        Log.d("=========","onFailure" + statusCode +"---"+error.toString());

    }
}
