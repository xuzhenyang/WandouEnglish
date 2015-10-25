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


    @Override
    public void forResult(Word word)
    {
        mOnQueryComplete.forResult(word);
    }
}
