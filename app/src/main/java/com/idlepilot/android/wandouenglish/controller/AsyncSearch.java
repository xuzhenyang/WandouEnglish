package com.idlepilot.android.wandouenglish.controller;

import android.os.AsyncTask;
import android.util.Log;

import com.idlepilot.android.wandouenglish.model.Word;

/**
 * Created by xuzywozz on 2015/10/21.
 */
public class AsyncSearch extends AsyncTask<String, Void, String> implements OnQueryComplete
{
    private static final String TAG = "AsyncSearch";

    private OnQueryComplete mOnQueryComplete;

    private StringBuilder mStringBuilder = new StringBuilder();

    private Word word = null;

    public AsyncSearch(OnQueryComplete queryComplete)
    {
        this.mOnQueryComplete = queryComplete;
    }

/*
    //代理模式？？？
    public interface OnQueryComplete
    {
        public void forResult(String result);
    }*/

    @Override
    protected String doInBackground(String... strings)
    {
        Log.i(TAG, "doInBackground");
        //tmp存储传入的单词
        String tmp, url;
        tmp = strings[0].trim();
        Log.i(TAG, tmp);
        if (tmp.isEmpty())
            return mStringBuilder.append("请输入需要翻译的内容！\n").toString();
        WordManager wordManager = new WordManager();
        word = wordManager.getWordFromInternet(tmp);
        word.printInfo();
        Log.i(TAG, mStringBuilder.toString());

        return mStringBuilder.toString();
    }

    @Override
    protected void onPostExecute(String s)
    {
        Log.i(TAG, "onPostExecute" + mStringBuilder.toString());
        mOnQueryComplete.forResult(word);
    }

    /*private void parseJSONAndUpdate(String s) throws JSONException
    {
        JSONObject mainObject = new JSONObject(s);
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
        }
        catch (JSONException e)
        {
//                this.cancel(true);
//                new AsyncSearch(mOnQueryComplete, true, false).execute(st);
        }
        JSONArray symbols = data.getJSONArray("symbols");
        JSONObject symbolsData = symbols.getJSONObject(0);
        String ph_am = symbolsData.getString("ph_am");
        String ph_en = symbolsData.getString("ph_en");
        if (ph_am.length() > 0)
        {
            mStringBuilder.append("美音:[" + ph_am + "]");
            if (ph_en.length() > 0)
                mStringBuilder.append("  英音:[" + ph_en + "]\n\n");
        }
        else if (ph_en.length() > 0) mStringBuilder.append("英音:[" + ph_en + "]\n\n");
        JSONArray parts = symbolsData.getJSONArray("parts");
        for (int i = 0; i < parts.length(); i++)
        {
            JSONObject part = parts.getJSONObject(i);
            mStringBuilder.append("词性: " + part.getString("part") + '\n');
            Log.i(TAG, "after" + mStringBuilder.toString());
            JSONArray means = part.getJSONArray("means");
            for (int j = 0; j < means.length(); j++)
            {
                mStringBuilder.append((j + 1) + ":" + means.getString(j) + '\n');
            }
            if (i == parts.length() - 1) break;
            mStringBuilder.append('\n');
        }

    }*/

    @Override
    public void forResult(Word word)
    {
        mOnQueryComplete.forResult(word);
    }
}
