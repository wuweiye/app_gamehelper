package cn.dkm.gamehelper.gameInfo.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.dkm.gamehelper.R;
import cn.dkm.gamehelper.gameInfo.activity.GameDetailActivity;
import cn.dkm.gamehelper.gameInfo.listener.OnItemClickListener;
import cn.dkm.gamehelper.model.params.GameLibrary;

/**
 * Created by Administrator on 2017/12/26.
 */

public class GamesFragmentAdapter extends RecyclerView.Adapter<GamesFragmentAdapter.GamesHolder> {

    private final Context mContext;
    private List<GameLibrary> libraries ;
    private OnItemClickListener mOnItemClickListener;//声明接口

    public GamesFragmentAdapter(Context mContext, List<GameLibrary> libraries) {
        this.mContext = mContext;
        this.libraries = libraries;
    }

    @Override
    public GamesHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(mContext, R.layout.games_item,null);

        return new GamesHolder(view);
    }

    @Override
    public void onBindViewHolder(final GamesHolder holder, int position) {

        GameLibrary library = libraries.get(position);
        holder.tv_title.setText(library.getName());
        //holder.tv_content.setText(library.getContent());

        holder.tv_content.setText("测试文字内容测试文字内容测试文字内容测试文字内容测试文字内容");
        holder.tv_time.setText(library.getUpdateTime());

        if(mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(view,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return libraries.size();
    }

    public String getGid(int position){
        return libraries.get(position).getGid();
    }


    class GamesHolder extends RecyclerView.ViewHolder{

        private ImageView iv_icon;
        private TextView tv_title;
        private TextView tv_content;
        private TextView tv_time;

        public GamesHolder(View itemView) {
            super(itemView);
            iv_icon =  itemView.findViewById(R.id.iv_icon);
            tv_title =  itemView.findViewById(R.id.tv_title);
            tv_content =  itemView.findViewById(R.id.tv_content);
            tv_time =  itemView.findViewById(R.id.tv_time);

        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
