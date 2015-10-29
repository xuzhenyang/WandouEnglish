package com.idlepilot.android.wandouenglish.view;

import android.os.Bundle;

import com.idlepilot.android.wandouenglish.R;
import com.timqi.sectorprogressview.SectorProgressView;

public class OverviewActivity extends TitleActivity
{

    private SectorProgressView spv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        setTitle("概览");
        spv = (SectorProgressView) findViewById(R.id.spv);

    }

}
