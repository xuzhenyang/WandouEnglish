package com.idlepilot.android.wandouenglish.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.idlepilot.android.wandouenglish.model.Word;

/**
 * Created by xuzywozz on 2015/10/21.
 */
public class AsyncSearch extends AsyncTask<Object, Void, String> implements OnQueryComplete
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
    protected String doInBackground(Object... params)
    {
        Log.i(TAG, "doInBackground");
        //tmp存储传入的单词
        String tmp, url;
        tmp = ((String) params[0]).trim();
        Log.i(TAG, tmp);
        if (tmp.isEmpty())
            return mStringBuilder.append("请输入需要翻译的内容！\n").toString();
        WordManager wordManager = new WordManager((Context) params[1], "dict");
        Log.i(TAG, "isWordExist: " + wordManager.isWordExist(tmp));
/*        if (wordManager.isWordExist(tmp))
        {
            word = wordManager.getWordFromDict(tmp);
        }
        else*/
        {
            word = wordManager.getWordFromInternet(tmp);
            //这里false并没有什么卵用
//            wordManager.insertWordToDict(word, false);
        }
        word.printInfo();
        Log.i(TAG, mStringBuilder.toString());

        return mStringBuilder.toString();
    }

    @Override
    protected void onProgressUpdate(Void... values)
    {
        super.onProgressUpdate(values);
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

    }
}
