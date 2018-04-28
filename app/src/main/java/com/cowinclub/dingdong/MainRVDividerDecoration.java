package com.cowinclub.dingdong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cowinclub.dingdong.mdpulltorefresh.Util;

public class MainRVDividerDecoration extends RecyclerView.ItemDecoration {
    private static final float DEFAULT_ITEM_DIVID_LENGTH = 8;
    private float mItemDivideLength;
    Context context;

    public MainRVDividerDecoration(Context context) {
        this(context, DEFAULT_ITEM_DIVID_LENGTH);
    }

    public MainRVDividerDecoration(Context context, float itemDivideLength) {
        this.context = context;
        mItemDivideLength = Util.dip2px(context, itemDivideLength);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int pos = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int itemCount = parent.getAdapter().getItemCount();

        if (pos == itemCount-1){
            outRect.set(0,0,0,0);
        }else {
            outRect.set(0,0,0, (int) mItemDivideLength);

        }

    }
}
