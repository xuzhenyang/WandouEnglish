package com.idlepilot.android.wandouenglish.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.idlepilot.android.wandouenglish.R;
import com.idlepilot.android.wandouenglish.controller.SlideCutListView;
import com.idlepilot.android.wandouenglish.controller.WordManager;
import com.idlepilot.android.wandouenglish.model.Word;
import com.timqi.sectorprogressview.SectorProgressView;

import java.util.ArrayList;
import java.util.List;

public class StrangeWordlistActivity extends TitleActivity implements SlideCutListView.RemoveListener
{
    private SectorProgressView spv;
    private SlideCutListView slideCutListView;
    private ArrayAdapter<String> adapter;
    private List<String> wordNameList = new ArrayList<String>();
    private WordManager wm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        setTitle("生词");
        spv = (SectorProgressView) findViewById(R.id.spv);
        init();
    }

    private void init()
    {
        slideCutListView = (SlideCutListView) findViewById(R.id.slideCutListView);
        slideCutListView.setRemoveListener(this);

        wm = new WordManager(this, "dict");
        ArrayList<Word> strangeWordlist = wm.getStrangeWordlist();

        for (int i = 0; i < strangeWordlist.size(); i++)
        {
            wordNameList.add(strangeWordlist.get(i).getWord());
        }

        adapter = new ArrayAdapter<String>(this, R.layout.listview_item, R.id.list_item, wordNameList);
        slideCutListView.setAdapter(adapter);

        slideCutListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                Toast.makeText(StrangeWordlistActivity.this, wordNameList.get(position), Toast.LENGTH_SHORT).show();
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
