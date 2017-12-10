package cn.dkm.gamehelper.main;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.ab.activity.AbActivity;
import com.ab.http.AbHttpListener;
import com.ab.http.AbHttpResponseListener;
import com.ab.http.AbHttpUtil;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.view.titlebar.AbTitleBar;

import java.util.List;

import cn.dkm.gamehelper.R;
import cn.dkm.gamehelper.listener.FileResponseListener;
import cn.dkm.gamehelper.model.Article;
import cn.dkm.gamehelper.web.NetworkWeb;

public class TestActivity extends AbActivity {


    AbTitleBar mAbTitleBar;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.activity_test);

        mAbTitleBar = this.getTitleBar();
        mAbTitleBar.setTitleText(R.string.app_name);
        /*mAbTitleBar.setLogo(R.mipmap.ic_launcher);*/
        mAbTitleBar.setTitleBarBackgroundColor(R.color.colorPrimary);
        /*mAbTitleBar.setTitleTextMargin(10,0,0,0);
        mAbTitleBar.setLogoLine(R.drawable.line);*/

        imageView = findViewById(R.id.image);

        AbHttpUtil abHttpUtil = AbHttpUtil.getInstance(getApplicationContext());



        String url = "https://gss0.baidu.com/-Po3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/30adcbef76094b3607bbed4da4cc7cd98d109d61.jpg";


        Log.d("=====","start");
        abHttpUtil.get(url, new FileResponseListener(this,getApplicationContext(),imageView));


        NetworkWeb networkWeb = NetworkWeb.newInstance(this);

        networkWeb.findLogList(null, new AbHttpListener() {
            @Override
            public void onFailure(String s) {

            }

            @Override
            public void onSuccess(List<?> list) {

                List<Article> articles = (List<Article>) list;
                for (Article article: articles){
                    Log.d("article" , article.getTitle());
                }

            }
        });



        /*AbHttpUtil abHttpUtil = AbHttpUtil.getInstance(this);

        abHttpUtil.get("www.baidu.com", new AbHttpResponseListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {

            }
        });*/
    }


    public void set(Bitmap bitmap){
        imageView.setImageBitmap(bitmap);
    }
}
