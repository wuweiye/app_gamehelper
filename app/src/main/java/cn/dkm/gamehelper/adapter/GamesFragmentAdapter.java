package cn.dkm.gamehelper.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.dkm.gamehelper.R;
import cn.dkm.gamehelper.model.params.GameLibrary;

/**
 * Created by Administrator on 2017/12/26.
 */

public class GamesFragmentAdapter extends RecyclerView.Adapter<GamesFragmentAdapter.GamesHolder> {

    private final Context mContext;
    private List<GameLibrary> libraries ;

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
    public void onBindViewHolder(GamesHolder holder, int position) {

        GameLibrary library = libraries.get(position);
        holder.tv_title.setText(library.getName());
        holder.tv_content.setText(library.getContent());
        holder.tv_time.setText(library.getGId());

    }

    @Override
    public int getItemCount() {
        return libraries.size();
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext,"content "+libraries.get(getLayoutPosition()).getContent(),Toast.LENGTH_SHORT).show();


                }
            });


        }
    }
}
