package com.idlepilot.android.wandouenglish.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by xuzywozz on 2015/10/20.
 */
public class NetOperator
{

//    public static final String urlPre = "http://dict-co.iciba.com/api/dictionary.php?w=";
//    public static final String urlLow = "&key=B3CF1E57148A7E7AA82FC64C2719EEAD";

    public static final String urlPre = "http://115.28.220.95:4321/";

    public static InputStream getInputStreamByUrl(String urlStr)
    {
        InputStream tempInput = null;
        URL url = null;
        HttpURLConnection connection = null;
        //设置超时时间
        try
        {
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();     //别忘了强制类型转换
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(10000);
            tempInput = connection.getInputStream();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return tempInput;
    }

    private byte[] getUrlBytes(String urlSpec) throws IOException
    {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try
        {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
            {
                return null;
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0)
            {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        }
        finally
        {
            connection.disconnect();
        }
    }

    public String getUrl(String urlSpec) throws IOException
    {
        return new String(getUrlBytes(urlSpec));
    }
}
