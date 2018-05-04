package cn.dkm.gamehelper.gameInfo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import cn.dkm.gamehelper.R;

/**
 * Created by Administrator on 2018/1/18.
 */

public class FirstRecycleAdapter extends RecyclerView.Adapter<FirstRecycleAdapter.ViewHolder> {

    private List<String> list;

    private Context context;

    public FirstRecycleAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public FirstRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(context, R.layout.first_recycle_item,null);
        final FirstRecycleAdapter.ViewHolder holder = new FirstRecycleAdapter.ViewHolder(view);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Log.d("holder","-------------------");
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(FirstRecycleAdapter.ViewHolder holder, int position) {

        /*String s = list.get(position);*/
        holder.mName.setText("测试");
        holder.mIcon.setImageResource(R.drawable.star);


    }

    @Override
    public int getItemCount() {
        return 10;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView mName;
        private ImageView mIcon;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            mName = view.findViewById(R.id.tv_name);
            mIcon = view.findViewById(R.id.iv_icon);
        }
    }
}
