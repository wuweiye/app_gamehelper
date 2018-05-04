package cn.dkm.gamehelper.gameInfo.adapter.game;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.List;

import cn.dkm.gamehelper.R;
import cn.dkm.gamehelper.model.params.Assess;
import cn.dkm.gamehelper.view.RatingBar;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dkm on 2018/4/18 0018.
 */

public class AssessRecycleAdapter extends RecyclerView.Adapter<AssessRecycleAdapter.ViewHolder> {


    private List<Assess> list;

    private Context mContext;

    public AssessRecycleAdapter(Context context, List<Assess> list){
        this.list = list;
        this.mContext = context;
    }

    @Override
    public AssessRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {



        View view = View.inflate(mContext, R.layout.recycle_asseaa_item,null);
        final AssessRecycleAdapter.ViewHolder holder = new AssessRecycleAdapter.ViewHolder(view) ;

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();

                Log.d("UserGamesRecycleAdapter", "-----" + position);
            }
        });


        return holder;
    }

    @Override
    public void onBindViewHolder(AssessRecycleAdapter.ViewHolder holder, int position) {

        Assess assess = list.get(position);
        /*Glide.with(mContext).load(assess.getImageUrl()).into(holder.mIVIcon);*/
        holder.mTVName.setText(assess.getUserName());
        holder.mTVContent.setText(assess.getContent());
        holder.mStarAssess.setStar(3);
        holder.mStarAssess.setClickable(false);
    }

    @Override
    public int getItemCount() {
        if(list == null){
            return 0;
        }
        return list.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder{

        private View view;
        private CircleImageView mIVIcon;
        private RatingBar mStarAssess;
        private TextView mTVName;
        private TextView mTVContent;

        public ViewHolder(View view) {

            super(view);
            this.view = view;

            mIVIcon = view.findViewById(R.id.iv_user_icon);
            mTVName = view.findViewById(R.id.tv_name);
            mStarAssess = view.findViewById(R.id.rb_assess);
            mTVContent = view.findViewById(R.id.tv_content);

        }
    }
}
