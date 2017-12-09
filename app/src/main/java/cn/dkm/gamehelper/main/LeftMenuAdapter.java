package cn.dkm.gamehelper.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.model.AbMenuItem;

import java.util.ArrayList;

import cn.dkm.gamehelper.R;

/**
 * Created by Administrator on 2017/12/9 0009.
 */

class LeftMenuAdapter extends BaseExpandableListAdapter {

    private Context mContext = null;
    private ArrayList<String> mGroupName;
    private ArrayList<ArrayList<AbMenuItem>> mChilds;
    private LayoutInflater mInflater = null;

    public LeftMenuAdapter(Context context, ArrayList<String> groupName, ArrayList<ArrayList<AbMenuItem>> childs ) {
        mContext = context;
        mGroupName = groupName;
        mChilds = childs;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChilds.get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.main_menu_list_child, null);
            holder = new ViewHolder();
            holder.mChildIcon =  convertView.findViewById(R.id.desktop_list_child_icon);
            holder.mChildName =  convertView.findViewById(R.id.desktop_list_child_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AbMenuItem m = mChilds.get(groupPosition).get(childPosition);
        holder.mChildIcon.setImageResource(m.getIconId());
        holder.mChildName.setText(m.getText());
        return convertView;

    }


    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public int getGroupCount() {
        return mGroupName.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mChilds.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupName.get(groupPosition);
    }



    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.main_menu_list_group, null);
            holder = new ViewHolder();
            holder.mGroupName = (TextView) convertView.findViewById(R.id.desktop_list_group_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String name = mGroupName.get(groupPosition);
        holder.mGroupName.setText(name);
        return convertView;
    }



    private class ViewHolder {
        private TextView mGroupName;
        private ImageView mChildIcon;
        private TextView mChildName;
    }
}
