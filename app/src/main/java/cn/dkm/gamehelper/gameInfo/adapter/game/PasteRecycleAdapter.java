package cn.dkm.gamehelper.gameInfo.adapter.game;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.dkm.gamehelper.R;
import cn.dkm.gamehelper.model.params.Assess;
import cn.dkm.gamehelper.model.params.Paste;
import cn.dkm.gamehelper.view.RatingBar;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dkm on 2018/4/18 0018.
 */

public class PasteRecycleAdapter extends RecyclerView.Adapter<PasteRecycleAdapter.ViewHolder> {


    private List<Paste> list;

    private Context mContext;

    public PasteRecycleAdapter(Context context, List<Paste> list){
        this.list = list;
        this.mContext = context;
    }

    @Override
    public PasteRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {



        View view = View.inflate(mContext, R.layout.recycle_paste_item,null);
        final PasteRecycleAdapter.ViewHolder holder = new PasteRecycleAdapter.ViewHolder(view) ;

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
    public void onBindViewHolder(PasteRecycleAdapter.ViewHolder holder, int position) {

        Paste paste = list.get(position);
       holder.mName.setText(paste.getAuthor());
       holder.mTitle.setText(paste.getTitle());
       holder.mContent.setText(Html.fromHtml(paste.getContent()));
       holder.mTime.setText(paste.getCreateTime());

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
        private CircleImageView mIcon;
        private TextView mName;
        private TextView mTitle;
        private TextView mContent;
        private TextView mTime;

        public ViewHolder(View view) {

            super(view);
            this.view = view;

            mIcon = view.findViewById(R.id.iv_user_icon);
            mName = view.findViewById(R.id.tv_name);
            mContent = view.findViewById(R.id.tv_content);
            mTitle = view.findViewById(R.id.tv_title);
            mTime = view.findViewById(R.id.tv_time);

        }
    }
}
