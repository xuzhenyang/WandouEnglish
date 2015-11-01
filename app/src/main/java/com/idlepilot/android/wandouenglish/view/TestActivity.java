package com.idlepilot.android.wandouenglish.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.daimajia.swipe.SwipeLayout;
import com.idlepilot.android.wandouenglish.R;

public class TestActivity extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        SwipeLayout swipeLayout = (SwipeLayout) findViewById(R.id.sample1);

//set show mode.
        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

//add drag edge.(If the BottomView has 'layout_gravity' attribute, this line is unnecessary)
        swipeLayout.addDrag(SwipeLayout.DragEdge.Top, findViewById(R.id.bottom_wrapper));

        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener()
        {
            @Override
            public void onClose(SwipeLayout layout)
            {
                //when the SurfaceView totally cover the BottomView.
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset)
            {
                //you are swiping.
            }

            @Override
            public void onStartOpen(SwipeLayout layout)
            {

            }

            @Override
            public void onOpen(SwipeLayout layout)
            {
                //when the BottomView totally show.
            }

            @Override
            public void onStartClose(SwipeLayout layout)
            {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel)
            {
                //when user's hand released.
            }
        });
    }
}
