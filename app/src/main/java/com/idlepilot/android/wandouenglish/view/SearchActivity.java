package com.idlepilot.android.wandouenglish.view;

import android.app.Activity;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.idlepilot.android.wandouenglish.R;
import com.idlepilot.android.wandouenglish.controller.AsyncSearch;
import com.idlepilot.android.wandouenglish.controller.OnQueryComplete;
import com.idlepilot.android.wandouenglish.controller.SentenceListAdapter;
import com.idlepilot.android.wandouenglish.controller.WordManager;
import com.idlepilot.android.wandouenglish.model.Word;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchActivity extends Activity implements OnQueryComplete, View.OnClickListener
{
    private static final String TAG = "SearchActivity";

    private ImageButton mSearchButton;
    private EditText mSearchEdt;
    private TextView mTvWord;
    private TextView mPhonogramEng;
    private TextView mPhonogramUsa;
    private TextView mInterpret;
    private ListView mLvSentence;
    private AsyncSearch task = null;

    private WordManager wm;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mSearchEdt = (EditText) findViewById(R.id.edtTxt_word);
        mSearchButton = (ImageButton) findViewById(R.id.imgBtn_search);
        mTvWord = (TextView) findViewById(R.id.tv_word);
        mPhonogramEng = (TextView) findViewById(R.id.tv_phonogram_eng);
        mPhonogramUsa = (TextView) findViewById(R.id.tv_phonogram_usa);
        mInterpret = (TextView) findViewById(R.id.tv_interpret);
        mLvSentence = (ListView) findViewById(R.id.lv_sentence);

        mSearchButton.setOnClickListener(this);
    }

    @Override
    public void forResult(Word word)
    {
        ArrayList localArrayList = new ArrayList();
        for (int i = 0; i < word.getOrigList().size(); i++)
        {
            HashMap localHashMap = new HashMap();
            localHashMap.put("sentence", word.getOrigList().get(i) + "\n" + word.getTransList().get(i));
            Log.i(TAG, word.getOrigList().get(i));
            Log.i(TAG, word.getTransList().get(i));
            localArrayList.add(localHashMap);
        }

        Log.i(TAG, "test");
//        Log.i(TAG, localArrayList.get(2).);

        mTvWord.setText(word.getWord());
        mPhonogramEng.setText(word.getPsE());
        mPhonogramUsa.setText(word.getPsA());
        mInterpret.setText(word.getInterpret());
        SentenceListAdapter adapter = new SentenceListAdapter(this, R.layout.sentence_list_item, localArrayList, new String[]{"sentence"}, new int[]{R.id.tv_sentence_list_item});
        mLvSentence.setAdapter(adapter);
    }

    @Override
    public void onClick(View v)
    {
        task = new AsyncSearch(this);
        task.execute(mSearchEdt.getText().toString());
        wm = new WordManager(this, "dict");
        Log.i(TAG, "isWordExist: " + wm.isWordExist(mSearchEdt.getText().toString()));
    }

}
