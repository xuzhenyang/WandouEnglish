package com.idlepilot.android.wandouenglish.view;

import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.idlepilot.android.wandouenglish.R;
import com.idlepilot.android.wandouenglish.controller.MyNewsAdapter;
import com.idlepilot.android.wandouenglish.controller.NewsService;
import com.idlepilot.android.wandouenglish.model.News;

import java.io.File;
import java.util.List;

public class NewsActivity extends TitleActivity
{

    private Button mSearchPageButton;
    private Button mNewWordsButton;
    private Button mTestButton;
    public static TextView mResultText;

    protected static final int SUCCESS_GET_CONTACT = 0;
    private ListView mListView;
    private MyNewsAdapter mAdapter;
    private File cache;

    private Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            if (msg.what == SUCCESS_GET_CONTACT)
            {
                List<News> newss = (List<News>) msg.obj;
                mAdapter = new MyNewsAdapter(getApplicationContext(), newss, cache);
                mListView.setAdapter(mAdapter);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        setTitle("新闻");

        mListView = (ListView) findViewById(R.id.news_list);

        //创建缓存目录，系统一运行就得创建缓存目录的，
        cache = new File(Environment.getExternalStorageDirectory(), "cache");

        if (!cache.exists())
        {
            cache.mkdirs();
        }

        //获取数据，主UI线程是不能做耗时操作的，所以启动子线程来做
        new Thread()
        {
            public void run()
            {

                NewsService service = new NewsService();
                List<News> newss = null;
                try
                {
                    newss = service.getNewsAll();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                //子线程通过Message对象封装信息，并且用初始化好的，
                //Handler对象的sendMessage()方法把数据发送到主线程中，从而达到更新UI主线程的目的
                Message msg = new Message();
                msg.what = SUCCESS_GET_CONTACT;
                msg.obj = newss;
                mHandler.sendMessage(msg);
            }
        }.start();

/*
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3)
            {
                //获得选中项的HashMap对象
                //HashMap<String, String> map = (HashMap<String, String>) mListView.getItemAtPosition(arg2);
                String url = NewsService.listNews.get(position).getContentUrl();

                Intent i = new Intent(NewsActivity.this, MainActivity.class);
                i.putExtra("url", url);
                startActivity(i);

                MainActivity.this.finish();
            }

        });*/


    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        //清空缓存
        File[] files = cache.listFiles();
        for (File file : files)
        {
            file.delete();
        }
        cache.delete();
    }

    public void onBackPressed()
    {
        //注释掉这行,back键不退出activity

        Intent i = new Intent(NewsActivity.this, MainActivity.class);
        startActivity(i);
        super.onBackPressed();
    }
}
