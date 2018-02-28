package com.kcufow.loadingview;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kcufow.loadview.LoadingView;

public class MainActivity extends AppCompatActivity {

    private LoadingView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        loadingView = new LoadingView(this) {
            @Override
            protected Object getLayoutRes() {
                return R.layout.activity_main;
            }

            @Override
            protected void loadData() {
               getData();

            }
        };
        setContentView(loadingView);
        initData();
    }

    private void initData() {
        handler.sendEmptyMessageDelayed(0,2000);


    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 0:
                    loadingView.setStatus(LoadingView.STATE_ERROR);


                    break;
                case 1:
                    loadingView.setStatus(LoadingView.STATE_SUCCESS);

                    break;
            }

            super.handleMessage(msg);
        }
    };


    public void getData() {
        handler.sendEmptyMessageDelayed(1,2000);
    }
}
