package com.cowinclub.dingdong;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cowinclub.dingdong.bean.TestBean;
import com.cowinclub.dingdong.mdpulltorefresh.MyLog;
import com.cowinclub.dingdong.network.simple;
import com.cowinclub.dingdong.network.subcribe.ProgressSubscribe;
import com.cowinclub.dingdong.network.subcribe.onSuccessListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.main_recycleview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new MainRVDividerDecoration(this));
        MainRecycleViewAdapter adapter = new MainRecycleViewAdapter();




        simple.getSampleData(1, 1).subscribe(new ProgressSubscribe<>(this, new onSuccessListener<TestBean>() {
            @Override
            public void onSeccess(TestBean testBean) {
                if (testBean != null) {
                    MyLog.i("**********************数据输出");
                    MyLog.i("**********************数据数量:"+testBean.getData().size()+"个");
                }else {
                    MyLog.i("**********************没有数据输出");
                }
            }

            @Override
            public void onFailed(int code, String message) {

            }
        }));
    }


    public void getData(){

    }

}
