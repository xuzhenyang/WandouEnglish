package com.idlepilot.android.wandouenglish.controller;

import android.util.JsonReader;
import android.util.Log;

import com.idlepilot.android.wandouenglish.model.Word;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

/**
 * Created by xuzywozz on 2015/11/22.
 */
public class JsonUtils
{
    /*public static Word parseJson(String jsonData)
    {
        Word word = new Word();
        try
        {
            // 如果需要解析JSON数据，首要要生成一个JsonReader对象
            JsonReader reader = new JsonReader(new StringReader(jsonData));
            // 开始解析数组
            reader.beginArray();
            // 判断数组里面还有没下一个JSONObject对象
            while (reader.hasNext())
            {
                // 开始解析对象
                reader.beginObject();
                // 判断当前JSONObject对象里面还有没下一个键值对
                while (reader.hasNext())
                {
                    // 取出当前键值对的key
                    String tagName = reader.nextName();
                    // 取出key所对应的value
                    if (tagName.equals("keyword"))
                    {
                        word.setWord(reader.nextString());
                    } else if (tagName.equals("trans"))
                    {
                        word.setInterpret(reader.nextString());
                    } else if (tagName.equals("phonE"))
                    {
                        word.setPronE(reader.nextString());
                    } else if (tagName.equals("phonA"))
                    {
                        word.setPronA(reader.nextString());
                    }
                }
                // 结束解析对象
                reader.endObject();
            }
            // 结束解析数组
            reader.endArray();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return word;
    }*/

    public Word parseJson(InputStream is)
    {
        Word word = new Word();
        String jsonData = this.inputStreamToString(is);
        try
        {
            JSONObject jsonObject = new JSONObject(jsonData).getJSONObject("word");
            word.setWord(jsonObject.getString("keyword"));
            word.setInterpret(jsonObject.getString("trans"));
            word.setPsE(jsonObject.getString("phonE"));
            word.setPsA(jsonObject.getString("phonA"));
            Log.i("Json", "keyword:" + word.getWord() + "\ntrans:" + word.getInterpret() + "\nphonE:" + word.getPsE());
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        return word;
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


}
