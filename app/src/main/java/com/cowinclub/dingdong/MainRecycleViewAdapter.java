package com.cowinclub.dingdong;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cowinclub.dingdong.bean.TestBean;
import com.cowinclub.dingdong.lru.recyclelru.LruRecycleData;

public class MainRecycleViewAdapter extends RecyclerView.Adapter<MainRecycleViewAdapter.ViewHolder> {

    private Context mContext;
    private LruRecycleData mRecycleData;

    public MainRecycleViewAdapter(Context context, LruRecycleData recycleData) {
        this.mContext = context;
        this.mRecycleData = recycleData;
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
        mRecycleData.loadDataToCache(position);
        TestBean.Data bean = mRecycleData.get(position);
        if (bean == null) return;
        holder.textView.setText(bean.text);
        Glide.with(mContext)
                .load(bean.profile_image)
                .thumbnail((float) 0.1)
                .into(holder.logo_iv);
    }

    @Override
    public int getItemCount() {
        return mRecycleData == null ? 0 : mRecycleData.getItemCount();
    }


    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        mRecycleData.onViewAttachedToWindow(holder.getLayoutPosition());
        super.onViewAttachedToWindow(holder);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView logo_iv;
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.logo_iv = itemView.findViewById(R.id.logo_iv);
            this.textView = itemView.findViewById(R.id.content);
        }
    }
}
