package com.idlepilot.android.wandouenglish.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.idlepilot.android.wandouenglish.R;
import com.idlepilot.android.wandouenglish.controller.AsyncSearch;
import com.idlepilot.android.wandouenglish.controller.OnQueryComplete;

public class SearchActivity extends Activity implements OnQueryComplete, View.OnClickListener
{
    private static final String TAG = "SearchActivity";

    private Button mSearchButton;
    private EditText mSearchEdt;
    private TextView mResultText;
    private AsyncSearch task = null;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mSearchEdt = (EditText) findViewById(R.id.search_edt);
        mSearchButton = (Button) findViewById(R.id.search_button);
        mResultText = (TextView) findViewById(R.id.result_text);

        mSearchButton.setOnClickListener(this);
    }

    @Override
    public void forResult(String result)
    {
        mResultText.setText(result);
    }

    @Override
    public void onClick(View v)
    {
        task = new AsyncSearch(this);
        task.execute(mSearchEdt.getText().toString());
        Log.i(TAG, mSearchEdt.getText().toString());
    }

}
