package com.example.doctorcommunication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * 프레임레이아웃 사용예제
 * http://croute.me/412
 *
 * @author croute
 * @since 2011.05.03
 */
public class FrameLayoutTest extends Activity
{
    private LinearLayout mLlRed;
    private LinearLayout mLlBlue;

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        Button bRed = (Button)findViewById(R.id.frame_layout_example_activity_b_visible_red);
        Button bBlue = (Button)findViewById(R.id.frame_layout_example_activity_b_visible_blue);

        mLlRed = (LinearLayout)findViewById(R.id.frame_layout_example_activity_ll_red);
        mLlBlue = (LinearLayout)findViewById(R.id.frame_layout_example_activity_ll_blue);

        bRed.setOnClickListener(v -> {
            mLlRed.setVisibility(LinearLayout.VISIBLE);
            mLlBlue.setVisibility(LinearLayout.INVISIBLE);
        });
        bBlue.setOnClickListener(v -> {
            mLlRed.setVisibility(LinearLayout.INVISIBLE);
            mLlBlue.setVisibility(LinearLayout.VISIBLE);
        });
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume()
    {
        super.onResume();

        mLlRed.setVisibility(LinearLayout.VISIBLE);
        mLlBlue.setVisibility(LinearLayout.INVISIBLE);
    }


}

