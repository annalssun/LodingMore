package com.cowinclub.dingdong;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cowinclub.dingdong.bean.TestBean;
import com.cowinclub.dingdong.lru.recyclelru.LruRecycleData;
import com.cowinclub.dingdong.lru.recyclelru.UIUpdateCallBack;
import com.cowinclub.dingdong.mdpulltorefresh.MDPullToRefresh;
import com.cowinclub.dingdong.mdpulltorefresh.MyLog;
import com.cowinclub.dingdong.mdpulltorefresh.OnLoadMoreListener;
import com.cowinclub.dingdong.mdpulltorefresh.OnRefreshListener;
import com.cowinclub.dingdong.network.simple;
import com.cowinclub.dingdong.network.subcribe.ProgressSubscribe;
import com.cowinclub.dingdong.network.subcribe.onSuccessListener;

public class MainActivity extends AppCompatActivity implements UIUpdateCallBack {

    private LinearLayoutManager mLayoutManager;
    private MainRecycleViewAdapter mRecycleViewAdapter;
    private LruRecycleData mLruRecycleData;

    private MDPullToRefresh mPullToRefresh;
    private int mPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLruRecycleData = new LruRecycleData(this, "main", this);

        RecyclerView recyclerView = findViewById(R.id.main_recycleview);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.addItemDecoration(new MainRVDividerDecoration(this));
        mRecycleViewAdapter = new MainRecycleViewAdapter(this, mLruRecycleData);
        recyclerView.setAdapter(mRecycleViewAdapter);

        mPullToRefresh = findViewById(R.id.refresh_view);
        mPullToRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void refreshListener() {
                mPage = 1;
                getData(1);
            }
        });

        mPullToRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                mPage++;
                getData(mPage);
            }
        });


        getData(mPage);

    }


    public void getData(final int page) {
        simple.getSampleData(1, page).subscribe(new ProgressSubscribe<>(this, new onSuccessListener<TestBean>() {
            @Override
            public void onSeccess(TestBean testBean) {
                if (testBean != null) {
                    MyLog.i("**********************数据输出");
                    MyLog.i("**********************数据数量:" + testBean.getData().size() + "个");
                    mLruRecycleData.saveDataToCache(testBean, page);
                   if (page ==1){
                       MyLog.i("**********************获取数据成功============================1");
                       mPullToRefresh.refreshSuccess();
                   }else {
                       mPullToRefresh.loadSuccess();
                   }

                } else {
                    MyLog.i("**********************没有数据输出");
                }
            }

            @Override
            public void onFailed(int code, String message) {

            }
        }));
    }

    @Override
    public int getFirstVisablePosition() {
        return mLayoutManager.findFirstVisibleItemPosition();
    }

    @Override
    public int getLastVisablePosition() {
        return mLayoutManager.findLastVisibleItemPosition();
    }

    @Override
    public void updataposition(final int position, final int itemCount) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mRecycleViewAdapter != null) {
                    if (position == 0) {
                        mRecycleViewAdapter.notifyDataSetChanged();
                    } else {
                        mRecycleViewAdapter.notifyItemRangeChanged(position, itemCount);
                    }

                }
            }
        });
    }
}
