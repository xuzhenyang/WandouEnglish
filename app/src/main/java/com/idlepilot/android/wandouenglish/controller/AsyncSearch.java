package com.idlepilot.android.wandouenglish.controller;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by xuzywozz on 2015/10/21.
 */
public class AsyncSearch extends AsyncTask<String, Void, String>
{
    private static final String TAG = "AsyncSearch";

    private OnQueryComplete mOnQueryComplete;

    private boolean isTrans = false;
    private boolean isEnToZh = true;
    private StringBuilder mStringBuilder = new StringBuilder();

    //请到 “百度翻译api” 免费注册你的app key
    private String urlPre = "http://openapi.baidu.com/public/2.0/translate/dict/simple?client_id=jjswwEhSf5Qb44s1y7o4yTxA&q=";
    private String urlTranPre = "http://openapi.baidu.com/public/2.0/bmt/translate?client_id=jjswwEhSf5Qb44s1y7o4yTxA&q=";

    private String urlTranNextEnToZh = "&from=en&to=zh";
    private String urlTranNextZhToEn = "&from=zh&to=en";

    public AsyncSearch(OnQueryComplete queryComplete, boolean isSimpleMode, boolean isEnToZh)
    {
        Log.i(TAG, "AsyncSearch");
        this.mOnQueryComplete = queryComplete;
        this.isEnToZh = isEnToZh;
        if (isSimpleMode) isTrans = true;
    }

    //用于在SearchActivity接口回调
    public interface OnQueryComplete
    {
        public void forResult(String result);
    }

    @Override
    protected String doInBackground(String... strings)
    {
        Log.i(TAG, "doInBackground");
        //tmp存储传入的单词
        String tmp,url;
        tmp = strings[0].trim();
        Log.i(TAG, tmp);
        if (tmp.isEmpty())
            return mStringBuilder.append("请输入需要翻译的内容！\n").toString();
        try
        {
            tmp = URLEncoder.encode(strings[0].trim(), "utf-8");
            url = urlPre + tmp;
            url = url + urlTranNextEnToZh;
            Log.i(TAG, url);
            String result = new Fetcher().getUrl(url);
            if (isCancelled()) return null;
            parseJSONAndUpdate(result);
            Log.i(TAG, mStringBuilder.toString());
        } catch (UnsupportedEncodingException e)
        {
            mStringBuilder.append("不支持URL编码，请更换查询内容！");
        } catch (IOException e)
        {
            mStringBuilder.append("IO传输错误，请重试！");
        } catch (JSONException e)
        {
            mStringBuilder.delete(0, mStringBuilder.length());
            mStringBuilder.append("没有找到相关翻译！\n");
        }

        return mStringBuilder.toString();
    }

    @Override
    protected void onPostExecute(String s)
    {
//        super.onPostExecute(s);
        mOnQueryComplete.forResult(mStringBuilder.toString());
        mStringBuilder.delete(0, mStringBuilder.length());
    }

    private void parseJSONAndUpdate(String s) throws JSONException
    {
        JSONObject mainObject = new JSONObject(s);
        if (isTrans)
        {
            if (mainObject.has("error_code"))
            {
                mStringBuilder.append("没有找到相关翻译！\n");
                return;
            }
            mStringBuilder.append(mainObject.getJSONArray("trans_result")
                    .getJSONObject(0).getString("dst"));

        } else
        {
            int errorCode = mainObject.getInt("errno");
            if (errorCode != 0)
            {
                mStringBuilder.append("没有找到相关翻译！\n");
                return;
            }
            JSONObject data = null;
            try
            {
                data = mainObject.getJSONObject("data");
            } catch (JSONException e)
            {
//                this.cancel(true);
//                new AsyncSearch(mOnQueryComplete, true, false).execute(st);
            }
            JSONArray symbols = data.getJSONArray("symbols");
            JSONObject symbolsData = symbols.getJSONObject(0);
            if (isEnToZh)
            {
                String ph_am = symbolsData.getString("ph_am");
                String ph_en = symbolsData.getString("ph_en");
                if (ph_am.length() > 0)
                {
                    mStringBuilder.append("美音:[" + ph_am + "]");
                    if (ph_en.length() > 0)
                        mStringBuilder.append("  英音:[" + ph_en + "]\n\n");
                } else if (ph_en.length() > 0) mStringBuilder.append("英音:[" + ph_en + "]\n\n");
            }
            JSONArray parts = symbolsData.getJSONArray("parts");
            for (int i = 0; i < parts.length(); i++)
            {
                JSONObject part = parts.getJSONObject(i);
                if (isEnToZh) mStringBuilder.append("词性: " + part.getString("part") + '\n');
                JSONArray means = part.getJSONArray("means");
                for (int j = 0; j < means.length(); j++)
                {
                    mStringBuilder.append((j + 1) + ":" + means.getString(j) + '\n');
                }
                if (i == parts.length() - 1) break;
                mStringBuilder.append('\n');
            }

        }

    }
}
