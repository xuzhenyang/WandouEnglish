package com.idlepilot.android.wandouenglish.view;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.idlepilot.android.wandouenglish.R;
import com.idlepilot.android.wandouenglish.controller.Fetcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SearchActivity extends Activity
{
    private static final String TAG = "SearchActivity";

    private Button mSearchButton;
    private EditText mSearchEdt;
    private TextView mResultText;

    private boolean isTrans = false;
    private boolean isEnToZh = true;
    private StringBuilder mStringBuilder = new StringBuilder();

    //请到 “百度翻译api” 免费注册你的app key
    private String urlPre = "http://openapi.baidu.com/public/2.0/translate/dict/simple?client_id=jjswwEhSf5Qb44s1y7o4yTxA&q=";
    private String urlTranPre = "http://openapi.baidu.com/public/2.0/bmt/translate?client_id=jjswwEhSf5Qb44s1y7o4yTxA&q=";

    private String urlTranNextEnToZh = "&from=en&to=zh";
    private String urlTranNextZhToEn = "&from=zh&to=en";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mSearchEdt = (EditText) findViewById(R.id.search_edt);
        mSearchButton = (Button) findViewById(R.id.search_button);
        mResultText = (TextView) findViewById(R.id.result_text);

        mSearchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mStringBuilder.delete(0, mStringBuilder.length());
                new FetchItemsTask().execute(mSearchEdt.getText().toString());
                Log.i(TAG, "Builder:" + mStringBuilder.toString());
            }
        });
    }

    private void parseJSONAndUpdate(String s, String st) throws JSONException
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

    private class FetchItemsTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... strings)
            {
            String tmp, reuse;
            reuse = tmp = strings[0].trim();
            if (tmp.isEmpty())
                return mStringBuilder.append("请输入需要翻译的内容！\n").toString();
            try
            {
                if (!isTrans) isTrans = tmp.contains(" ");
                tmp = URLEncoder.encode(strings[0].trim(), "utf-8");
                if (isTrans) tmp = urlTranPre + tmp;
                else tmp = urlPre + tmp;
                if (isEnToZh) tmp = tmp + urlTranNextEnToZh;
                else tmp = tmp + urlTranNextZhToEn;
                Log.i(TAG, "URL:" + tmp);
                String result = new Fetcher().getUrl(tmp);
                Log.i(TAG, "result:" + result);
                tmp = result;
                if (isCancelled()) return null;
                parseJSONAndUpdate(tmp, reuse);
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
            super.onPostExecute(s);
            mResultText.setText(mStringBuilder.toString());
        }
    }

    /*private class FetchItemsTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids)
        {
            try
            {
                String result = new Fetcher().getUrl("http://openapi.baidu.com/public/2.0/translate/dict/simple?client_id=jjswwEhSf5Qb44s1y7o4yTxA&q=do&from=en&to=zh");
                Log.i(TAG, "Fetched contents of URL :\n" + result);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }

    }*/
}
