package cn.dkm.gamehelper.gameInfo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.dkm.gamehelper.R;
import cn.dkm.gamehelper.model.Article;

/**
 * Created by dkm on 2018/1/22 0022.
 */

public class UserGamesRecycleAdapter extends RecyclerView.Adapter<UserGamesRecycleAdapter.ViewHolder> {


    private List<Article> list;

    private Context mContext;


    public UserGamesRecycleAdapter(Context context, List<Article> list){
        this.list = list;
        this.mContext = context;
    }

    @Override
    public UserGamesRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(mContext, R.layout.games_item_user,null);
        final UserGamesRecycleAdapter.ViewHolder holder = new UserGamesRecycleAdapter.ViewHolder(view) ;

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();

                Log.d("UserGamesRecycleAdapter", "-----");
            }
        });


        return holder;
    }

    @Override
    public void onBindViewHolder(UserGamesRecycleAdapter.ViewHolder holder, int position) {

        //填充数据
        Article article = list.get(position);
        Glide.with(mContext).load(article.getImageUrl()).into(holder.mIVIcon);
        holder.mTVTitle.setText(article.getTagName());
        holder.mTVContent.setText(article.getTitle());
        holder.mTVNum.setText(article.getUrl());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private View view;
        private ImageView mIVIcon;
        private TextView mTVTitle;
        private TextView mTVContent;
        private TextView mTVNum;

        public ViewHolder(View view) {

            super(view);
            this.view = view;

            mIVIcon = view.findViewById(R.id.iv_icon);
            mTVTitle = view.findViewById(R.id.tv_title);
            mTVContent = view.findViewById(R.id.tv_content);
            mTVNum = view.findViewById(R.id.tv_num);

        }
    }
}
