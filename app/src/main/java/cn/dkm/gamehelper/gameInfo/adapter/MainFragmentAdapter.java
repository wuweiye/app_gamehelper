package cn.dkm.gamehelper.gameInfo.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.dkm.gamehelper.R;
import cn.dkm.gamehelper.gameInfo.listener.OnItemClickListener;
import cn.dkm.gamehelper.model.params.GameLibrary;
import cn.dkm.gamehelper.model.params.RecommendGameLibrary;
import cn.dkm.gamehelper.web.UrlConstant;

/**
 * Created by Administrator on 2017/12/26.
 */

public class MainFragmentAdapter extends RecyclerView.Adapter<MainFragmentAdapter.MainHolder> {

    private final Context mContext;
    private List<RecommendGameLibrary> recommendGameLibraries;
    private OnItemClickListener mOnItemClickListener;//声明接口

    public MainFragmentAdapter(Context mContext, List<RecommendGameLibrary> recommendGameLibraries) {
        this.mContext = mContext;
        this.recommendGameLibraries = recommendGameLibraries;
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(mContext, R.layout.main_item,null);

        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(final MainHolder holder, int position) {



        RecommendGameLibrary library = recommendGameLibraries.get(position);
        Glide.with(mContext).load(UrlConstant.BASE + recommendGameLibraries.get(position).getRecommendImageUrl()).error(R.drawable.bg_bl).into(holder.background);
        holder.gameAssess.setText(library.getAssessCount() + "条评价");
        holder.gameRecommendContent.setText(library.getRecommendContent());
        holder.gameName.setText(library.getName());
        holder.gameAssessScore.setText(library.getAssess());

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
        return recommendGameLibraries.size();
    }


    class MainHolder extends RecyclerView.ViewHolder{

        private ImageView background;
        private TextView gameName;
        private TextView gameAssess;
        private TextView gameAssessScore;
        private TextView gameRecommendContent;

        public MainHolder(View itemView) {
            super(itemView);
            background =  itemView.findViewById(R.id.iv_background);
            gameName = itemView.findViewById(R.id.tv_name);
            gameAssess = itemView.findViewById(R.id.tv_assess_count);
            gameAssessScore = itemView.findViewById(R.id.tv_assess_star);
            gameRecommendContent = itemView.findViewById(R.id.tv_assess);



        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
