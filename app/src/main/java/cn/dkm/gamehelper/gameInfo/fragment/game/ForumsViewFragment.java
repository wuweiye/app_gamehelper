package cn.dkm.gamehelper.gameInfo.fragment.game;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbHttpListener;
import com.ab.http.AbRequestParams;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.List;

import cn.dkm.gamehelper.R;
import cn.dkm.gamehelper.base.BaseFragment;
import cn.dkm.gamehelper.gameInfo.activity.AssessActivity;
import cn.dkm.gamehelper.gameInfo.adapter.game.AssessRecycleAdapter;
import cn.dkm.gamehelper.gameInfo.adapter.game.PasteRecycleAdapter;
import cn.dkm.gamehelper.model.params.Assess;
import cn.dkm.gamehelper.model.params.Paste;
import cn.dkm.gamehelper.utils.SPUtil;
import cn.dkm.gamehelper.web.NetworkWeb;
import cn.dkm.gamehelper.web.UrlConstant;
import cn.dkm.gamehelper.web.params.GameDetailParams;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.ab.network.toolbox.VolleyLog.TAG;


@SuppressLint("ValidFragment")
public class ForumsViewFragment extends BaseFragment {


    private RecyclerView mForums;

    PasteRecycleAdapter adapter;

    GameDetailParams mGameDetailParams;


    public ForumsViewFragment(GameDetailParams gameDetailParams){

        this.mGameDetailParams = gameDetailParams;
    }


    @Override
    public View initView() {


        View view = View.inflate(mContext, R.layout.view_forums,null);
        mForums = view.findViewById(R.id.recycle_forums);
        initListener();
        return view;

    }

    private void initListener() {



    }



    @Override
    public void initDate() {

        super.initDate();

        AbRequestParams params = new AbRequestParams();
        //params.put("gid",mGameDetailParams.getId() +"");
        params.put("page","1");
        params.put("rows","10");

        NetworkWeb networkWeb = NetworkWeb.newInstance(getContext());

        networkWeb.findQueryList(params, UrlConstant.UrlType.PASTE, new AbHttpListener(){

            @Override
            public void onFailure(String errorMessage) {

                Toast.makeText(getContext(),"连接网络失败 错误信息"+ errorMessage ,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSuccess(List<?> list) {
                List<Paste> pastes = (List<Paste>) list;
                processData(pastes);
            }
        });
    }

    private void processData(List<Paste> pastes) {


        adapter = new PasteRecycleAdapter(getContext(), pastes);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        mForums.setLayoutManager(gridLayoutManager);
        mForums.setAdapter(adapter);





    }


}
