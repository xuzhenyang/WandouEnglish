package com.idlepilot.android.wandouenglish.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.idlepilot.android.wandouenglish.R;
import com.idlepilot.android.wandouenglish.controller.SlideCutListView;
import com.timqi.sectorprogressview.SectorProgressView;

import java.util.ArrayList;
import java.util.List;

public class LearnActivity extends TitleActivity implements SlideCutListView.RemoveListener
{

    private SectorProgressView spv;
    private SlideCutListView slideCutListView;
    private ArrayAdapter<String> adapter;
    private List<String> dataSourceList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        setTitle("学习");
        spv = (SectorProgressView) findViewById(R.id.spv);
        init();
    }

    private void init()
    {
        slideCutListView = (SlideCutListView) findViewById(R.id.slideCutListView);
        slideCutListView.setRemoveListener(this);

        for (int i = 0; i < 20; i++)
        {
            dataSourceList.add("滑动删除" + i);
        }

        adapter = new ArrayAdapter<String>(this, R.layout.listview_item, R.id.list_item, dataSourceList);
        slideCutListView.setAdapter(adapter);

        slideCutListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                Toast.makeText(LearnActivity.this, dataSourceList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void removeItem(SlideCutListView.RemoveDirection direction, int position)
    {
        adapter.remove(adapter.getItem(position));
        switch (direction)
        {
            case RIGHT:
                Toast.makeText(this, "向右删除  " + position, Toast.LENGTH_SHORT).show();
                break;
            case LEFT:
                Toast.makeText(this, "向左删除  " + position, Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }
}
