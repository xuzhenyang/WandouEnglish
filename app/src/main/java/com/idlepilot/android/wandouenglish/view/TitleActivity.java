package com.idlepilot.android.wandouenglish.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.idlepilot.android.wandouenglish.R;

public class TitleActivity extends Activity
{

    //private RelativeLayout mLayoutTitleBar;
    private TextView mTitleTextView;
    private FrameLayout mContentLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setupViews();   //加载 activity_title 布局 ，并获取标题及两侧按钮
    }


    private void setupViews()
    {
        super.setContentView(R.layout.activity_title);
        mTitleTextView = (TextView) findViewById(R.id.text_title);
        mContentLayout = (FrameLayout) findViewById(R.id.layout_content);
    }


    //设置标题内容
    @Override
    public void setTitle(int titleId)
    {
        mTitleTextView.setText(titleId);
    }

    //设置标题内容
    @Override
    public void setTitle(CharSequence title)
    {
        mTitleTextView.setText(title);
    }

    //设置标题文字颜色
    @Override
    public void setTitleColor(int textColor)
    {
        mTitleTextView.setTextColor(textColor);
    }


    //取出FrameLayout并调用父类removeAllViews()方法
    @Override
    public void setContentView(int layoutResID)
    {
        mContentLayout.removeAllViews();
        View.inflate(this, layoutResID, mContentLayout);
        onContentChanged();
    }

    @Override
    public void setContentView(View view)
    {
        mContentLayout.removeAllViews();
        mContentLayout.addView(view);
        onContentChanged();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#setContentView(android.view.View, android.view.ViewGroup.LayoutParams)
     */
    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params)
    {
        mContentLayout.removeAllViews();
        mContentLayout.addView(view, params);
        onContentChanged();
    }
}
