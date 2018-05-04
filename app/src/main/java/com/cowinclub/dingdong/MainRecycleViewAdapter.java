package com.cowinclub.dingdong;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cowinclub.dingdong.bean.TestBean;

import java.util.List;

public class MainRecycleViewAdapter extends RecyclerView.Adapter<MainRecycleViewAdapter.ViewHolder> {

    private List<TestBean.Data> mDataList;
    private Context mContext;

    public MainRecycleViewAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_main_rcv, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    public void updateData(List<TestBean.Data> dataList) {
        if (dataList != null) {
            mDataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView logo_iv;
        public TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);

        }
    }
}
