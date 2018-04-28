package com.cowinclub.dingdong.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.cowinclub.dingdong.R;

/**
 * Created by Sunzhigang on 2018-03-09.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
    }

    /**
     * 返回页面布局
     */
    protected abstract int getLayoutID();

    /**
     * 设置标题名称
     *
     * @param titleStringID 资源id
     */
    public void setTitle(int titleStringID) {
        setTitle(getString(titleStringID));
    }

    /**
     * 设置标题名称
     *
     * @param title 标题名称
     */
    protected void setTitle(String title) {
        Toolbar toolbar = findViewById(R.id.toolBar);
        toolbar.setVisibility(View.VISIBLE);
        TextView tv_left = toolbar.findViewById(R.id.tv_left);
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView tv_title = toolbar.findViewById(R.id.tv_title);
        tv_title.setText(title);
    }
}
