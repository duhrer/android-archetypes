package com.blogspot.tonyatkins.archetype.activity;

import java.lang.Thread.UncaughtExceptionHandler;

import com.blogspot.tonyatkins.archetype.R;
import com.blogspot.tonyatkins.archetype.exception.SampleExceptionHandler;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ExceptionPitcherActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.exception_pitcher);
		
		Button exceptionButton = (Button) findViewById(R.id.exception_pitcher_exception_button);
		exceptionButton.setOnClickListener(new ExceptionPitcherListener());
	}
	
	private class ExceptionPitcherListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			int foo = 0;
			
			// this will throw a java.lang.ArithmeticException
			int bar = 1 / foo;
		}
	}
}
