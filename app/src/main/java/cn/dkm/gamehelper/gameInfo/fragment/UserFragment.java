package cn.dkm.gamehelper.gameInfo.fragment;


import android.graphics.Color;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ab.util.AbFileUtil;
import com.ab.util.AbJsonUtil;


import java.util.ArrayList;
import java.util.List;

import cn.dkm.gamehelper.R;
import cn.dkm.gamehelper.base.BaseFragment;
import cn.dkm.gamehelper.gameInfo.adapter.UserGamesRecycleAdapter;
import cn.dkm.gamehelper.model.Article;
import cn.dkm.gamehelper.model.ArticleListResult;


/**
 * Created by Administrator on 2017/2/16.
 */

public class UserFragment extends BaseFragment implements View.OnClickListener {


    private TextView mCollGame;

    private TextView mFollowForum;

    private RecyclerView mRVCollGame;

    private RecyclerView mRVFollowForum;

    private List<Article> first;
    private List<Article> second;
    private UserGamesRecycleAdapter firstAdapter;
    private UserGamesRecycleAdapter secondAdapter;





    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_user, null);

        mCollGame = view.findViewById(R.id.tv_coll_game);
        mFollowForum = view.findViewById(R.id.tv_follow_forum);

        mRVCollGame = view.findViewById(R.id.rv_coll_game);
        mRVFollowForum = view.findViewById(R.id.rv_follow_forum);

        mRVFollowForum.setVisibility(View.GONE);

        mCollGame.setOnClickListener(this);
        mFollowForum.setOnClickListener(this);
        mCollGame.setTextColor(Color.BLUE);


        return view;
    }


    @Override
    public void initDate() {

        mUserHolder.title.setText("个人中心");

        getDataFromNet();
        if(first != null && first.size() > 0){

            Log.d("--------", first.size() +"====");
            firstAdapter = new UserGamesRecycleAdapter(getContext(), first);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
            mRVCollGame.setLayoutManager(gridLayoutManager);
            mRVCollGame.setAdapter(firstAdapter);

        }

        if(second != null && second.size() > 0){

            Log.d("--------", second.size() +"====");
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
            secondAdapter = new UserGamesRecycleAdapter(getContext(), second);
            mRVFollowForum.setLayoutManager(gridLayoutManager);
            mRVFollowForum.setAdapter(secondAdapter);
        }

    }

    private void getDataFromNet() {


        final String result = AbFileUtil.readAssetsByName(mContext, "article_list.json","UTF-8");


        ArticleListResult articleListResult = (ArticleListResult) AbJsonUtil.fromJson(result,ArticleListResult.class);

        List<Article> articles = articleListResult.getItems();

        first = articles;

        second = new ArrayList<>();

        second.addAll(articles);
        second.addAll(articles);




    }



    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_coll_game:

                mCollGame.setTextColor(Color.BLUE);
                mFollowForum.setTextColor(getResources().getColor(R.color.gray_light));
                mRVCollGame.setVisibility(View.VISIBLE);
                mRVFollowForum.setVisibility(View.GONE);
                break;
            case R.id.tv_follow_forum:

                mCollGame.setTextColor(getResources().getColor(R.color.gray_light));
                mFollowForum.setTextColor(Color.BLUE);
                mRVFollowForum.setVisibility(View.VISIBLE);
                mRVCollGame.setVisibility(View.GONE);
                break;

        }
    }
}
