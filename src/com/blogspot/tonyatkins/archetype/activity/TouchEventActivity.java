package com.blogspot.tonyatkins.archetype.activity;

import com.blogspot.tonyatkins.archetype.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.touchevent);
		
		// hold on to each of the indicators for future use
		root = findViewById(R.id.te_layout);
		rArrow  = (ImageView) findViewById(R.id.te_arrow_r);
		lArrow  = (ImageView) findViewById(R.id.te_arrow_l);
		uArrow  = (ImageView) findViewById(R.id.te_arrow_u);
		dArrow  = (ImageView) findViewById(R.id.te_arrow_d);
		spinCw  = (ImageView) findViewById(R.id.te_spin_cw);
		spinCcw = (ImageView) findViewById(R.id.te_spin_ccw);
		pinchIn = (ImageView) findViewById(R.id.te_pinch_in);
		pinchOut = (ImageView) findViewById(R.id.te_pinch_out);
		
		// wire up a touch event listener of our own devising that flips the indicators when events happen
		root.setOnTouchListener(new DashboardTouchListener());
	}
	
	private class DashboardTouchListener implements OnTouchListener {
		private float lastX = 0;
		private float lastY = 0;
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			
			// single touch actions
			if (event.getPointerCount() <= 1) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					uArrow.setImageResource(R.drawable.arrow_u_on);
					dArrow.setImageResource(R.drawable.arrow_d_on);
					lArrow.setImageResource(R.drawable.arrow_l_on);
					rArrow.setImageResource(R.drawable.arrow_r_on);
					
					lastX = event.getX();
					lastY = event.getY();
					
					return true;
				}
				else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
					uArrow.setImageResource(R.drawable.arrow_u_off);
					dArrow.setImageResource(R.drawable.arrow_d_off);
					lArrow.setImageResource(R.drawable.arrow_l_off);
					rArrow.setImageResource(R.drawable.arrow_r_off);
					
					return true;
				}
				else if (event.getAction() == MotionEvent.ACTION_MOVE) {
					if (lastY == event.getY()) {
						// do nothing
					}
					else if (lastY < event.getY()) {
						uArrow.setImageResource(R.drawable.arrow_u_off);
						dArrow.setImageResource(R.drawable.arrow_d_on);
					}
					else {
						uArrow.setImageResource(R.drawable.arrow_u_on);
						dArrow.setImageResource(R.drawable.arrow_d_off);
					}
					
					if (lastX == event.getX()) {
						// do nothing
					}
					else if (lastX < event.getX()) {
						lArrow.setImageResource(R.drawable.arrow_l_off);
						rArrow.setImageResource(R.drawable.arrow_r_on);
					}
					else {
						lArrow.setImageResource(R.drawable.arrow_l_on);
						rArrow.setImageResource(R.drawable.arrow_r_off);
					}
					
					lastX = event.getX();
					lastY = event.getY();
					
					return true;
				}
			}
			else {
				// TODO: Implement multi-touch options
				
				// TODO: Implement pinch
				
				// TODO: Implement inward pinch
				
				// TODO: Implement outward pinch
				
				// TODO: Implement clockwise spin
				
				// TODO: Implement counter-clockwise spin
			}
			
			
			return false;
		}
		
	}
	
}
