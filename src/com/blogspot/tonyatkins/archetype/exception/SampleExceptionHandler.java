package com.blogspot.tonyatkins.archetype.exception;

import java.io.ByteArrayOutputStream;
import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SampleExceptionHandler implements UncaughtExceptionHandler {
	public static final String EXCEPTION_BUNDLE_KEY = "throwable";
	public static final String SCREENSHOT_BUNDLE_KEY = "screenshot";
	
	private Activity parentActivity;
	private Class exceptionHandlingActivityClass;
	
	/**
	 * @param parentActivity The root-level activity for this application.
	 * @param exceptionHandlingActivity The activity that will handle the exception (it will be passed the exception in a bundle).
	 */
	public SampleExceptionHandler(Activity parentActivity, Class exceptionHandlingActivityClass) {
		super();
		this.parentActivity = parentActivity;
		this.exceptionHandlingActivityClass = exceptionHandlingActivityClass;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		Log.e(this.getClass().getName(), "Caught exception, preparing to handle internally", ex);
		
		// Pass on the data we need to save the ship into the next loop.
		Intent exceptionHandlingIntent = new Intent(parentActivity, exceptionHandlingActivityClass);
		Bundle bundle = new Bundle();
		bundle.putSerializable(EXCEPTION_BUNDLE_KEY, ex);

		View contentView = parentActivity.findViewById(android.R.id.content);
		if (contentView == null) {
			Log.e(this.getClass().getName(),"Can't find content view to take screen shot.");
		}
		else {
			View v1 = contentView.getRootView();
			if (v1 == null) {
				Log.e(this.getClass().getName(),"Can't find root view to take screen shot.");
			}
			else {
				v1.setDrawingCacheEnabled(true);
				Bitmap bm = v1.getDrawingCache();
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				bm.compress(CompressFormat.PNG, 100, output);
				
				bundle.putByteArray(SCREENSHOT_BUNDLE_KEY, output.toByteArray());
			}
		}
		
		exceptionHandlingIntent.putExtras(bundle);
    	PendingIntent pendingIntent = PendingIntent.getActivity(parentActivity.getApplication().getBaseContext(), 0, exceptionHandlingIntent, exceptionHandlingIntent.getFlags());

    	AlarmManager mgr = (AlarmManager) parentActivity.getSystemService(Context.ALARM_SERVICE);
    	mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 2000, pendingIntent);
    	System.exit(2);
    
//    	parentActivity.startActivity(exceptionHandlingIntent);
	}
}
