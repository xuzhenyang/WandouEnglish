package com.idlepilot.android.wandouenglish.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.idlepilot.android.wandouenglish.R;
import com.idlepilot.android.wandouenglish.controller.AsyncSearch;
import com.idlepilot.android.wandouenglish.controller.DataBaseHelperDict;
import com.idlepilot.android.wandouenglish.controller.OnQueryComplete;
import com.idlepilot.android.wandouenglish.controller.SentenceListAdapter;
import com.idlepilot.android.wandouenglish.controller.WordManager;
import com.idlepilot.android.wandouenglish.model.Word;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchActivity extends TitleActivity implements OnQueryComplete, View.OnClickListener
{
    private static final String TAG = "SearchActivity";

    private ImageButton mSearchButton;
    private ImageButton mAddToWordlist;
    private EditText mSearchEdt;
    private TextView mTvWord;
    private TextView mPhonogramEng;
    private TextView mPhonogramUsa;
    private TextView mInterpret;
    private ListView mLvSentence;
    private AsyncSearch task = null;

    private WordManager wm;
    private Word mWord;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setTitle("查词");

        mSearchEdt = (EditText) findViewById(R.id.edtTxt_word);
        mSearchButton = (ImageButton) findViewById(R.id.imgBtn_search);
        mTvWord = (TextView) findViewById(R.id.tv_word);
        mPhonogramEng = (TextView) findViewById(R.id.tv_phonogram_eng);
        mPhonogramUsa = (TextView) findViewById(R.id.tv_phonogram_usa);
        mInterpret = (TextView) findViewById(R.id.tv_interpret);
        mLvSentence = (ListView) findViewById(R.id.lv_sentence);
        mAddToWordlist = (ImageButton) findViewById(R.id.imgBtn_add_to_wordlist);

        mSearchButton.setOnClickListener(this);

    }

    @Override
    public void forResult(Word word)
    {
        mWord = word;
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

        mAddToWordlist.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mWord.setIsStrange(0);
                wm.updateWordToDict(mWord);
                Toast.makeText(getApplicationContext(), "成功添加", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "是否生词:" + mWord.isStrange());
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        task = new AsyncSearch(this);
        //传入searchedWord以及当前Context
        Object params[] = {mSearchEdt.getText().toString(), this};
        task.execute(params);
        wm = new WordManager(this, "dict");

    }

}
