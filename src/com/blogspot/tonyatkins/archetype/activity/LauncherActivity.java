package com.blogspot.tonyatkins.archetype.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogspot.tonyatkins.archetype.R;

public class LauncherActivity extends Activity {
	private static final int CAMERA_REQUEST      = 1234;
	private static final int MICROPHONE_REQUEST  = 2345;
	private static final int VOICE_INPUT_REQUEST = 3456;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.launcher);
		
		ImageButton cameraButton = (ImageButton) findViewById(R.id.launcherCameraButton);
		cameraButton.setOnClickListener(new ActivityLaunchListener(android.provider.MediaStore.ACTION_IMAGE_CAPTURE, CAMERA_REQUEST,this));
		
		
		ImageButton imageButton = (ImageButton) findViewById(R.id.launcherMicrophoneButton);
		imageButton.setOnClickListener(new ActivityLaunchListener(android.provider.MediaStore.Audio.Media.RECORD_SOUND_ACTION, MICROPHONE_REQUEST,this));
		
		ImageButton voiceInputButton = (ImageButton) findViewById(R.id.launcherVoiceInputButton);
		voiceInputButton.setOnClickListener(new ActivityLaunchListener(RecognizerIntent.ACTION_RECOGNIZE_SPEECH, VOICE_INPUT_REQUEST,this));
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case CAMERA_REQUEST:
				Bundle extras = null;
				if (data != null ) extras = data.getExtras();
				if (extras != null) {
					Bitmap thumbnail = (Bitmap) extras.get("data");  
					ImageView imageView = new ImageView(this);
					imageView.setImageBitmap(thumbnail);
					Dialog dialog = new Dialog(this);
					dialog.setContentView(imageView);
					dialog.show();
				}
				else {
					Toast.makeText(this, "No data returned or camera failed to launch.", Toast.LENGTH_LONG).show();
				}
			
				break;
			case MICROPHONE_REQUEST:
				Uri audioUri = null;
				if (data != null) {
					audioUri = data.getData();
				}
				if (audioUri != null) {
					MediaPlayer player = new MediaPlayer();
					try {
						player.setDataSource(audioUri.getPath());
						player.prepare();
						player.start();
						Toast.makeText(this, player.getDuration()/1000d + " seconds of audio were returned.", Toast.LENGTH_LONG).show();
					} catch (Exception e) {
						Log.e(getLocalClassName(), "Error working with recorded sound", e);
					}
				}
				else {
					Toast.makeText(this, "No data returned or sound recorder failed to launch.", Toast.LENGTH_LONG).show();
				}
				break;
			case VOICE_INPUT_REQUEST:
				ArrayList<String> matches = null;
				if (data != null) {
					matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				}
				
				if (matches != null && matches.size() > 0) {
					Toast.makeText(this, "Voice input returned " +  matches.size() + " matches.", Toast.LENGTH_LONG).show();
				}
				else {
					Toast.makeText(this, "Error getting voice input or no data returned.", Toast.LENGTH_LONG).show();
				}
				
				break;
			default:
				Toast.makeText(this, "Returned from unknown request.", Toast.LENGTH_LONG).show();
				break;
		}
	}
	
	private class ActivityLaunchListener implements OnClickListener {
		private final String action;
		private final int requestCode;
		private final Context context;
		
		public ActivityLaunchListener(String action, int requestCode, Context context) {
			this.action = action;
			this.requestCode = requestCode;
			this.context = context;
		}

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(action);
			List<ResolveInfo> activities = getPackageManager().queryIntentActivities(intent,PackageManager.MATCH_DEFAULT_ONLY);
//			if (activities != null && activities.size() > 0) {
				startActivityForResult(intent, requestCode);
//			}
//			else {
//				Toast.makeText(context, "Unable to launch external activity as there's no application that can handle the request.", Toast.LENGTH_LONG).show();
//			}
		}
	}
	
	private class UnableToComplyListener implements OnClickListener {
		private final String message;
		private final Context context;
		
		
		public UnableToComplyListener(String message, Context context) {
			this.message = message;
			this.context = context;
		}


		@Override
		public void onClick(View v) {
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		}
	}
}