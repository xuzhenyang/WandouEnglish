package com.idlepilot.android.wandouenglish.controller;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;

import com.idlepilot.android.wandouenglish.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 宸 on 2015/11/18.
 */
public class NewsService {

    public static List<News> listNews=null;
    /*
     * 从服务器上获取数据
     */
    public List<News> getNewsAll() throws Exception {

      List<News> news = null;
          String Parth = " http://115.28.220.95:1234";
        URL url = new URL(Parth);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(3000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream is = conn.getInputStream();
            // 这里获取数据直接放在XmlPullParser里面解析
            news = getPersons(is);
            return news;
        } else {
            return null;
        }
       /* File file = new File(Environment.getExternalStorageDirectory(),
                "list.xml");
        FileInputStream in = new FileInputStream(file);


        news = xmlParser(in);
        return news;*/
    }

    // 这里并没有下载图片下来，而是把图片的地址保存下来了
    /*private List<News> xmlParser(InputStream is) throws Exception {
        List<News> Newss = null;
        News News = null;
        XmlPullParserFactory pullParserFactory = XmlPullParserFactory.newInstance();

        //获取XmlPullParser的实例
        XmlPullParser  parser = pullParserFactory.newPullParser();

        // 设置需要解析的XML数据

        parser.setInput(is, "UTF-8");
        int eventType = parser.getEventType();
        while (eventType  != XmlPullParser.END_DOCUMENT) {
            String nodeName = parser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (nodeName.equalsIgnoreCase("Newss")) {
                        Newss = new ArrayList<News>();
                        Log.i("1", "456");
                    } else if (nodeName.equalsIgnoreCase("News")) {
                        News = new News();
                        Log.i("2", "456");
                        News.setId(Integer.valueOf(parser.getAttributeValue(0)));
                    } else if (nodeName.equalsIgnoreCase("title")) {
                        News.setTitle(parser.nextText());

                        Log.i("3", News.getTitle());
                    } else if (nodeName.equalsIgnoreCase("image")) {
                        News.setImage(parser.getAttributeValue(0));

                        Log.i("4", "456");
                    }else if (nodeName.equalsIgnoreCase("info")) {
                        News.setInfo(parser.nextText());
                        Log.i("5", News.getInfo());
                    }
                    break;

                case XmlPullParser.END_TAG:
                    if (nodeName.equalsIgnoreCase("News")) {

                        Log.i("6", "456");
                        Newss.add(News);
                    }
                    break;
            }
            eventType = parser.next();
        }

        return Newss;
    }*/


    public  List<News> getPersons(InputStream is) {
        List<News> list = new ArrayList<News>();

        String jsonString = inputStreamToString(is);


        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray results = jsonObject.getJSONArray("news");
            for (int i = 0; i < results.length(); i++) {
                News News = new News();
                JSONObject result = results.getJSONObject(i);
                News.setTitle(result.getString("title"));
                News.setInfo(result.getString("intro"));
                News.setDate(result.getString("date"));
                News.setContentUrl(result.getString("contentUrl"));
                Log.i("5455",result.getString("date"));
                News.setImage(result.getString("image"));
                list.add(News);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        listNews = list;
        return list;
    }


    private String inputStreamToString(InputStream is)
    {
        String s = "";
        String line = "";

        // Wrap a BufferedReader around the InputStream
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        // Read response until the end
        try
        {
            while ((line = rd.readLine()) != null)
            {
                s += line;
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        // Return full string
        return s;
    }



    /*
     * 从网络上获取图片，如果图片在本地存在的话就直接拿，如果不存在再去服务器上下载图片
     * 这里的path是图片的地址
     */
    public Uri getImageURI(String path, File cache) throws Exception {
        String name = MD5.getMD5(path) + path.substring(path.lastIndexOf("."));
        File file = new File(cache, name);
        // 如果图片存在本地缓存目录，则不去服务器下载
        if (file.exists()) {
            return Uri.fromFile(file);//Uri.fromFile(path)这个方法能得到文件的URI
        } else {
            // 从网络上获取图片
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            if (conn.getResponseCode() == 200) {

                InputStream is = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                is.close();
                fos.close();
                // 返回一个URI对象
                return Uri.fromFile(file);
            }
        }
        return null;
    }
}
class MD5 {

    public static String getMD5(String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(content.getBytes());
            return getHashString(digest);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getHashString(MessageDigest digest) {
        StringBuilder builder = new StringBuilder();
        for (byte b : digest.digest()) {
            builder.append(Integer.toHexString((b >> 4) & 0xf));
            builder.append(Integer.toHexString(b & 0xf));
        }
        return builder.toString();
    }
}