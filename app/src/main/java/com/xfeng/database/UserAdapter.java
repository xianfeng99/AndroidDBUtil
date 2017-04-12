package com.xfeng.database;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * 推荐关注（订阅）和我的订阅管理
 * Created by lixianfeng on 2017/3/22.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.Holder> {

    private ArrayList<UserBean> mList;
    private Context mContext;

    public UserAdapter(Context context){
        mContext = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemview = LayoutInflater.from(mContext).inflate(R.layout.item_user_layout, null);
        return new Holder(itemview);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {

        UserBean user = getItem(position);
        holder.item_user_name.setText(user.getName());
        holder.item_user_age.setText(String.valueOf(user.getAge()));
        holder.item_user_city.setText(user.getCity());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public UserBean getItem(int position){
        return mList == null ? null : mList.get(position);
    }

    public void refresh(ArrayList<UserBean> list){
        mList = list;
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView item_user_name;
        TextView item_user_age;
        TextView item_user_city;

        public Holder(View itemView) {
            super(itemView);

            item_user_name = (TextView) itemView.findViewById(R.id.item_user_name);
            item_user_age = (TextView) itemView.findViewById(R.id.item_user_age);
            item_user_city = (TextView) itemView.findViewById(R.id.item_user_city);
        }
    }


}
