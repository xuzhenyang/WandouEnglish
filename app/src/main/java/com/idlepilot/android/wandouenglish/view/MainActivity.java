package com.idlepilot.android.wandouenglish.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.idlepilot.android.wandouenglish.R;

public class MainActivity extends AppCompatActivity
{
    private Button mSearchPageButton;
    private Button mLearnPageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchPageButton = (Button) findViewById(R.id.search_page_button);
        mLearnPageButton = (Button) findViewById(R.id.learn_page_button);

        mSearchPageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(i);
            }
        });
        mLearnPageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(MainActivity.this, OverviewActivity.class);
                startActivity(i);
            }
        });
    }
}
