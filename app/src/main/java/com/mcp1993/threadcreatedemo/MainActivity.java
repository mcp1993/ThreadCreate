package com.mcp1993.threadcreatedemo;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 子线程创建子线程demo
 */
public class MainActivity extends AppCompatActivity {
    private Thread notifyingThread;
    private Thread secondThread;
    private TextView tv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_show = (TextView) findViewById(R.id.tv_show);
        //创建第一个子线程
        notifyingThread = new Thread(null, mTask, "子线程1");
        notifyingThread.start();
    }

    private Runnable mTask = new Runnable() {
        public void run() {
            //第二个子线程
              secondThread = new Thread(null,secondRunable,"子线程2");
              secondThread.start();
        }
    };

    private Runnable secondRunable = new Runnable() {
        @Override
        public void run() {
            Looper.prepare();
            Handler handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what){
                        case 1:
                            Toast.makeText(MainActivity.this,"吐司",Toast.LENGTH_SHORT).show();
                           //更新视图发广播或者Handler
//                            mHandler.sendEmptyMessageDelayed(0,2000);
                            mHandler.sendEmptyMessage(0);
                            break;
                    }
                }
            };
            handler.sendEmptyMessageDelayed(1,2000);
            Looper.loop();
        }
    };

    //更新视图的handler
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    tv_show.setText("dsfasdf");
                    break;
                default:
                    break;
            }
        }

    };
}
