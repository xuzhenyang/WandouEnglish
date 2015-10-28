package com.idlepilot.android.wandouenglish.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.idlepilot.android.wandouenglish.R;
import com.timqi.sectorprogressview.SectorProgressView;

public class LearnActivity extends AppCompatActivity
{

    private SectorProgressView spv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        spv = (SectorProgressView) findViewById(R.id.spv);

    }

}
