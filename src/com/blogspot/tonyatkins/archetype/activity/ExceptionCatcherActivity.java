package com.blogspot.tonyatkins.archetype.activity;

import com.blogspot.tonyatkins.archetype.R;
import com.blogspot.tonyatkins.archetype.exception.SampleExceptionHandler;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ExceptionCatcherActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exception_catcher);
		
		Button closeButton = (Button) findViewById(R.id.exception_catcher_close_button);
		closeButton.setOnClickListener(new ActivityQuitListener(this));
		
		if (savedInstanceState == null) {
			Log.e(this.getClass().getName(), "No error data bundle received, I have nothing to display.");
		}
		else {
			Throwable t = (Throwable) savedInstanceState.getSerializable(SampleExceptionHandler.EXCEPTION_BUNDLE_KEY);
			if (t == null) {
				Log.w(this.getClass().getName(), "Throwable data was null in starting application bundle.");
			}
			else {
				TextView view = (TextView) findViewById(R.id.exception_catcher_exception_body);
				view.setText(t.getCause().toString());
			}
			
			byte[] byteArray = savedInstanceState.getByteArray(SampleExceptionHandler.SCREENSHOT_BUNDLE_KEY);
			if (byteArray.length > 0) {
				Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
				ImageView imageView = (ImageView) findViewById(R.id.exception_catcher_thumbnail_view);
				imageView.setImageBitmap(bitmap);
			}
			else {
				Log.i(this.getClass().getName(), "No screenshot data received in starting application bundle.");
			}
		}
		
	}
	
	private class ActivityQuitListener implements OnClickListener {
		Activity activity;
		public ActivityQuitListener(Activity activity) {
			this.activity = activity;
		}
		@Override
		public void onClick(View v) {
			activity.finish();
		}
	}
}
