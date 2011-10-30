package com.blogspot.tonyatkins.archetype.activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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

import com.blogspot.tonyatkins.archetype.R;
import com.blogspot.tonyatkins.archetype.exception.SampleExceptionHandler;

public class ExceptionCatcherActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exception_catcher);
		
		Button closeButton = (Button) findViewById(R.id.exception_catcher_close_button);
		closeButton.setOnClickListener(new ActivityQuitListener(this));

		File crashDir = this.getDir(".crashes", Activity.MODE_WORLD_READABLE);
		File[] crashInstances = crashDir.listFiles();
		long newestTime = 0;
		File newestCrashDir = null;
		
		for (File crashInstanceDir : crashInstances) {
			if (crashInstanceDir.lastModified() > newestTime) {
				newestTime = crashInstanceDir.lastModified();
				newestCrashDir = crashInstanceDir;
			}
		}
		
		if (newestCrashDir == null ) {
			Log.w(this.getClass().getName(), "No crash data found, exiting crash catcher activity.");
			finish();
		}
		else {
			File stackTraceFile = new File(newestCrashDir.getAbsolutePath() + "/" + SampleExceptionHandler.STACKTRACE_FILENAME);
			FileReader reader;
			try {
				reader = new FileReader(stackTraceFile);
				BufferedReader br = new BufferedReader(reader);
				StringBuffer output = new StringBuffer();
				String line;
				while ((line = br.readLine()) != null) {
					output.append(line + "\n");
				}
				
				TextView view = (TextView) findViewById(R.id.exception_catcher_exception_body);
				view.setText(output.toString());
			} catch (Exception e) {
				Log.e(getClass().getName(),"Error reading stacktrace file",e);
			}

			File screenshotFile = new File(newestCrashDir.getAbsolutePath() + "/" + SampleExceptionHandler.SCREENSHOT_FILENAME);
			Bitmap bitmap = BitmapFactory.decodeFile(screenshotFile.getAbsolutePath());
			ImageView imageView = (ImageView) findViewById(R.id.exception_catcher_thumbnail_view);
			if (bitmap == null) {
				Log.w(getClass().getName(), "Couldn't load bitmap data from screenshot file " + screenshotFile.getAbsolutePath());
				imageView.setVisibility(View.INVISIBLE);
			}
			else {
				imageView.setImageBitmap(bitmap);
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
