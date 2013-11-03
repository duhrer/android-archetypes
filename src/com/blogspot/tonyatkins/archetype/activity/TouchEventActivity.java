package com.blogspot.tonyatkins.archetype.activity;

import com.blogspot.tonyatkins.archetype.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.MotionEvent.PointerCoords;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

public class TouchEventActivity extends Activity {
    protected View root;

    protected ImageView rArrow;
    protected ImageView lArrow;
    protected ImageView uArrow;
    protected ImageView dArrow;
    protected ImageView spinCw;
    protected ImageView spinCcw;
    protected ImageView pinchIn;
    protected ImageView pinchOut;
    protected TextView touchCount;
    protected TextView diffX;
    protected TextView diffY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.touchevent);

        // hold on to each of the indicators for future use
        root = findViewById(R.id.te_layout);
        rArrow = (ImageView) findViewById(R.id.te_arrow_r);
        lArrow = (ImageView) findViewById(R.id.te_arrow_l);
        uArrow = (ImageView) findViewById(R.id.te_arrow_u);
        dArrow = (ImageView) findViewById(R.id.te_arrow_d);
        spinCw = (ImageView) findViewById(R.id.te_spin_cw);
        spinCcw = (ImageView) findViewById(R.id.te_spin_ccw);
        pinchIn = (ImageView) findViewById(R.id.te_pinch_in);
        pinchOut = (ImageView) findViewById(R.id.te_pinch_out);
        touchCount = (TextView) findViewById(R.id.te_touch_count);
        diffX = (TextView) findViewById(R.id.te_touch_diff_x);
        diffY = (TextView) findViewById(R.id.te_touch_diff_y);

        // wire up a touch event listener of our own devising that flips the indicators when events happen
        root.setOnTouchListener(new DashboardTouchListener());
    }

    private class DashboardTouchListener implements OnTouchListener {
        private float lastX = 0;
        private float lastY = 0;
        private float startLengthX = 0F;
        private float startLengthY = 0F;
        private float startLength = 0F;
        private double startAngle = 0F;

        private PointerCoords firstFingerCoords = new PointerCoords();
        private PointerCoords startFirstFingerCoords = new PointerCoords();
        private PointerCoords secondFingerCoords = new PointerCoords();
        private PointerCoords startSecondFingerCoords = new PointerCoords();

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            touchCount.setText(String.valueOf(event.getPointerCount()));

            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_POINTER_DOWN:
                case MotionEvent.ACTION_DOWN:
                    if (event.getPointerCount() == 1) {
                        uArrow.setImageResource(R.drawable.arrow_u_on);
                        dArrow.setImageResource(R.drawable.arrow_d_on);
                        lArrow.setImageResource(R.drawable.arrow_l_on);
                        rArrow.setImageResource(R.drawable.arrow_r_on);

                        lastX = event.getX();
                        lastY = event.getY();
                    }
                    else if (event.getPointerCount() == 2) {
                        event.getPointerCoords(0, firstFingerCoords);
                        event.getPointerCoords(1, secondFingerCoords);
                        startFirstFingerCoords = firstFingerCoords;
                        startSecondFingerCoords = secondFingerCoords;

                        startLengthX = Math.abs(startFirstFingerCoords.x - startSecondFingerCoords.x);
                        startLengthY = Math.abs(startFirstFingerCoords.y - startSecondFingerCoords.y);
                        startLength = (float) Math.sqrt((startLengthX * startLengthX) + (startLengthY * startLengthY));
                        startAngle = (Math.toDegrees(Math.atan2(startSecondFingerCoords.y - startFirstFingerCoords.y, startFirstFingerCoords.x - startSecondFingerCoords.x))) % 360;
                    }

                    break;

                case MotionEvent.ACTION_MOVE:
                    if (event.getPointerCount() == 1) {
                        if (lastY == event.getY()) {
                            // do nothing
                        } else if (lastY < event.getY()) {
                            uArrow.setImageResource(R.drawable.arrow_u_off);
                            dArrow.setImageResource(R.drawable.arrow_d_on);
                        } else {
                            uArrow.setImageResource(R.drawable.arrow_u_on);
                            dArrow.setImageResource(R.drawable.arrow_d_off);
                        }

                        if (lastX == event.getX()) {
                            // do nothing
                        } else if (lastX < event.getX()) {
                            lArrow.setImageResource(R.drawable.arrow_l_off);
                            rArrow.setImageResource(R.drawable.arrow_r_on);
                        } else {
                            lArrow.setImageResource(R.drawable.arrow_l_on);
                            rArrow.setImageResource(R.drawable.arrow_r_off);
                        }

                        lastX = event.getX();
                        lastY = event.getY();

                    } else if (event.getPointerCount() == 2) {
                        event.getPointerCoords(0, firstFingerCoords);
                        event.getPointerCoords(1, secondFingerCoords);

                        float firstDiffX = firstFingerCoords.x - startFirstFingerCoords.x;
                        float firstDiffY = firstFingerCoords.y - startFirstFingerCoords.y;
                        float secondDiffX = secondFingerCoords.x - startSecondFingerCoords.x;
                        float secondDiffY = secondFingerCoords.y - startSecondFingerCoords.y;
                        float avgDiffX = (firstDiffX + secondDiffX) / 2;
                        float avgDiffY = (firstDiffY + secondDiffY) / 2;
                        double endAngle = (Math.toDegrees(Math.atan2(startSecondFingerCoords.y - startFirstFingerCoords.y, startFirstFingerCoords.x - startSecondFingerCoords.x))) % 360;

                        diffX.setText(String.valueOf(avgDiffX));
                        diffY.setText(String.valueOf(avgDiffY));

                        float endLengthX = Math.abs(firstFingerCoords.x - secondFingerCoords.x);
                        float endLengthY = Math.abs(firstFingerCoords.y - secondFingerCoords.y);

                        float endLength = (float) Math.sqrt((endLengthX * endLengthX) + (endLengthY * endLengthY));

                        // Inward pinch
                        if (endLength < startLength) {
                            pinchIn.setImageResource(R.drawable.pinch_in_on);
                            pinchOut.setImageResource(R.drawable.pinch_out_off);
                        }
                        // Outward pinch
                        else if (endLength > startLength) {
                            pinchIn.setImageResource(R.drawable.pinch_in_off);
                            pinchOut.setImageResource(R.drawable.pinch_out_on);
                        } else {
                            pinchIn.setImageResource(R.drawable.pinch_in_off);
                            pinchOut.setImageResource(R.drawable.pinch_out_off);
                        }

                        // For this demo, we keep the normal movement as well.
                        if (avgDiffY == 0) {
                            uArrow.setImageResource(R.drawable.arrow_u_off);
                            dArrow.setImageResource(R.drawable.arrow_d_off);
                        }
                        if (avgDiffY < 0) {
                            uArrow.setImageResource(R.drawable.arrow_u_off);
                            dArrow.setImageResource(R.drawable.arrow_d_on);
                        } else {
                            uArrow.setImageResource(R.drawable.arrow_u_on);
                            dArrow.setImageResource(R.drawable.arrow_d_off);
                        }

                        if (avgDiffX == 0) {
                            lArrow.setImageResource(R.drawable.arrow_l_off);
                            rArrow.setImageResource(R.drawable.arrow_r_off);
                        } else if (avgDiffX > 0) {
                            lArrow.setImageResource(R.drawable.arrow_l_off);
                            rArrow.setImageResource(R.drawable.arrow_r_on);
                        } else {
                            lArrow.setImageResource(R.drawable.arrow_l_on);
                            rArrow.setImageResource(R.drawable.arrow_r_off);
                        }

                        if (startAngle < endAngle) {
                            spinCcw.setImageResource(R.drawable.spin_ccw_on);
                            spinCw.setImageResource(R.drawable.spin_cw_off);
                        } else if (startAngle > endAngle) {
                            spinCcw.setImageResource(R.drawable.spin_ccw_off);
                            spinCw.setImageResource(R.drawable.spin_cw_on);
                        } else {
                            spinCcw.setImageResource(R.drawable.spin_ccw_off);
                            spinCw.setImageResource(R.drawable.spin_cw_off);
                        }
                    }
                    break;

                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    pinchIn.setImageResource(R.drawable.pinch_in_off);
                    pinchOut.setImageResource(R.drawable.pinch_out_off);
                    uArrow.setImageResource(R.drawable.arrow_u_off);
                    dArrow.setImageResource(R.drawable.arrow_d_off);
                    lArrow.setImageResource(R.drawable.arrow_l_off);
                    rArrow.setImageResource(R.drawable.arrow_r_off);
                    spinCcw.setImageResource(R.drawable.spin_ccw_off);
                    spinCw.setImageResource(R.drawable.spin_cw_off);

                    touchCount.setText(String.valueOf(event.getPointerCount() - 1));

                    startLengthX = 0F;
                    startLengthY = 0F;
                    startLength = 0F;
                    startAngle = 0;

                    break;
            }

            return true;
        }

    }

}
