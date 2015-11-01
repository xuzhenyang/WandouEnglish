package com.idlepilot.android.wandouenglish.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.idlepilot.android.wandouenglish.R;
import com.timqi.sectorprogressview.SectorProgressView;

public class OverviewActivity extends TitleActivity
{

    private SectorProgressView spv;
    private Button mContinueLearnButton;
    private Button mTestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        setTitle("概览");
        spv = (SectorProgressView) findViewById(R.id.spv);
        mContinueLearnButton = (Button)findViewById(R.id.continue_learn_button);
        mContinueLearnButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(OverviewActivity.this, LearnActivity.class);
                startActivity(i);
            }
        });

        mTestButton = (Button) findViewById(R.id.test_button);
        mTestButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(OverviewActivity.this, TestActivity.class);
                startActivity(i);
            }
        });

    }

}
