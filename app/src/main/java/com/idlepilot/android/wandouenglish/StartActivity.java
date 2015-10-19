package com.idlepilot.android.wandouenglish;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class StartActivity extends AppCompatActivity
{

    private Button mStartButton;
    private TextView mVersionView;
//    private static final String TAG = "StartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        showVersion();

        mStartButton = (Button) findViewById(R.id.start_button);
        mStartButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(StartActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    //显示手机型号、SDK版本、系统版本、软件版本
    private void showVersion()
    {
        mVersionView = (TextView) findViewById(R.id.version_view);
        mVersionView.setText(getHandSetInfo());
    }

    private String getHandSetInfo()
    {
        String handSetInfo = "手机型号:" + android.os.Build.MODEL +
                "\nSDK版本:" + android.os.Build.VERSION.SDK +
                "\n系统版本:" + android.os.Build.VERSION.RELEASE +
                "\n软件版本:" + getAppVersionName(StartActivity.this);
        return handSetInfo;
    }

    //获取当前软件版本号
    private String getAppVersionName(Context context)
    {
        String versionName = "";
        try
        {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo("com.idlepilot.android.wandouenglish", 0);
            versionName = packageInfo.versionName;
            if (TextUtils.isEmpty(versionName))
            {
                return "";
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return versionName;
    }
}
