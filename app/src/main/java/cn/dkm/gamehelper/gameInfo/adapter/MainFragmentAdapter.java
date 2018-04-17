package cn.dkm.gamehelper.gameInfo.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.dkm.gamehelper.R;
import cn.dkm.gamehelper.gameInfo.listener.OnItemClickListener;
import cn.dkm.gamehelper.model.params.GameLibrary;

/**
 * Created by Administrator on 2017/12/26.
 */

public class MainFragmentAdapter extends RecyclerView.Adapter<MainFragmentAdapter.MainHolder> {

    private final Context mContext;
    private List<String> gameTypeNames ;
    private OnItemClickListener mOnItemClickListener;//声明接口

    public MainFragmentAdapter(Context mContext, List<String> gameTypeNames) {
        this.mContext = mContext;
        this.gameTypeNames = gameTypeNames;
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(mContext, R.layout.main_item,null);

        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(final MainHolder holder, int position) {

        String name = gameTypeNames.get(position);
        holder.tv_name.setText(name);

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
        return gameTypeNames.size();
    }


    class MainHolder extends RecyclerView.ViewHolder{

        private TextView tv_name;

        public MainHolder(View itemView) {
            super(itemView);
            tv_name =  itemView.findViewById(R.id.tv_name);

        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
