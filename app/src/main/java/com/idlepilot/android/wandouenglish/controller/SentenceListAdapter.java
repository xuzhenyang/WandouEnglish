package com.idlepilot.android.wandouenglish.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by xuzywozz on 2015/10/23.
 */
public class SentenceListAdapter extends BaseAdapter
{

    private Context context = null;
    private String[] from;
    private ArrayList<HashMap<String, Object>> list = null;
    private int resources;
    private int[] to;

    public SentenceListAdapter(Context paramContext, int paramInt, ArrayList<HashMap<String, Object>> paramArrayList, String[] paramArrayOfString, int[] paramArrayOfInt)
    {
        this.context = paramContext;
        this.resources = paramInt;
        this.list = paramArrayList;
        this.from = paramArrayOfString;
        this.to = paramArrayOfInt;
    }

    @Override
    public int getCount()
    {
        return this.list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        System.out.println(position);
        System.out.println(this.from[0]);
        View localView = LayoutInflater.from(this.context).inflate(this.resources, null);
        ((TextView) localView.findViewById(this.to[0])).setText((String) ((HashMap) this.list.get(position)).get(this.from[0]));
        return localView;
    }
}
