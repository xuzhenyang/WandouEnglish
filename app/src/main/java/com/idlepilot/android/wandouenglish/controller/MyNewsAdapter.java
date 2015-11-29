package com.idlepilot.android.wandouenglish.controller;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.idlepilot.android.wandouenglish.R;

import com.idlepilot.android.wandouenglish.model.News;

import java.io.File;
import java.util.List;

/**
 * Created by 宸 on 2015/11/18.
 */
public class MyNewsAdapter extends BaseAdapter
{

    protected static final int SUCCESS_GET_IMAGE = 0;
    private Context context;
    private List<News> news;
    private File cache;
    private LayoutInflater mInflater;

    // 自己定义的构造函数
    public MyNewsAdapter(Context context, List<News> news, File cache)
    {
        this.context = context;
        this.news = news;
        this.cache = cache;

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return news.size();
    }

    @Override
    public Object getItem(int position)
    {
        return news.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // 1获取item,再得到控件
        // 2 获取数据
        // 3绑定数据到item
        View view = null;
        if (convertView != null)
        {
            view = convertView;
        } else
        {
            view = mInflater.inflate(R.layout.news_list, null);
        }

        ImageView iv_header = (ImageView) view.findViewById(R.id.news_img);
        TextView tv_title = (TextView) view.findViewById(R.id.news_title);
        TextView tv_info = (TextView) view.findViewById(R.id.news_info);
        TextView tv_date = (TextView) view.findViewById(R.id.news_date);
        // Log.i("123", "1223");
        News newsList = news.get(position);

        // 异步的加载图片 (线程池 + Handler ) ---> AsyncTask
        asyncloadImage(iv_header, newsList.getImage());

        tv_title.setText(newsList.getTitle());
        tv_info.setText(newsList.getInfo());
        tv_date.setText(newsList.getDate());
        Log.i("123", tv_title.toString());
        //System.out.print(news.getTitle() + "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
        return view;
    }

    private void asyncloadImage(ImageView iv_header, String path)
    {
        // Log.i("123", "1123");
        NewsService service = new NewsService();
        AsyncImageTask task = new AsyncImageTask(service, iv_header);
        task.execute(path);
    }

    private final class AsyncImageTask extends AsyncTask<String, Integer, Uri>
    {

        private NewsService service;
        private ImageView iv_header;

        public AsyncImageTask(NewsService service, ImageView iv_header)
        {
            this.service = service;
            this.iv_header = iv_header;
        }

        // 后台运行的子线程子线程
        @Override
        protected Uri doInBackground(String... params)
        {
            try
            {
                return service.getImageURI(params[0], cache);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        // 这个放在在ui线程中执行
        @Override
        protected void onPostExecute(Uri result)
        {
            super.onPostExecute(result);
            // 完成图片的绑定
            if (iv_header != null && result != null)
            {
                iv_header.setImageURI(result);
            }
        }
    }

    /**
     * 采用普通方式异步的加载图片
     */
    /*private void asyncloadImage(final ImageView iv_header, final String path) {
        final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == SUCCESS_GET_IMAGE) {
                    Uri uri = (Uri) msg.obj;
                    if (iv_header != null && uri != null) {
                        iv_header.setImageURI(uri);
                    }

                }
            }
        };
        // 子线程，开启子线程去下载或者去缓存目录找图片，并且返回图片在缓存目录的地址
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                NewsService service = new NewsService();
                try {
                    //这个URI是图片下载到本地后的缓存目录中的URI
                    Uri uri = service.getImageURI(path, cache);
                    Message msg = new Message();
                    msg.what = SUCCESS_GET_IMAGE;
                    msg.obj = uri;
                    mHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
    }*/
}
