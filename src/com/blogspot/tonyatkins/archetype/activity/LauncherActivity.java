package com.blogspot.tonyatkins.archetype.activity;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
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
import com.blogspot.tonyatkins.recorder.Constants;
import com.blogspot.tonyatkins.recorder.activity.RecordSoundActivity;

public class LauncherActivity extends Activity {
	private static final int CROP_REQUEST        = 23;
	private static final int CAMERA_REQUEST      = 1234;
	private static final int MICROPHONE_REQUEST  = 2345;
	private static final int VOICE_INPUT_REQUEST = 3456;
	private static final int FILE_PICKER_REQUEST = 4567;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.launcher);
		
		ImageButton cameraButton = (ImageButton) findViewById(R.id.launcherCameraButton);
		cameraButton.setOnClickListener(new ActivityLaunchListener(android.provider.MediaStore.ACTION_IMAGE_CAPTURE, CAMERA_REQUEST));
		
		ImageButton filePickerButton = (ImageButton) findViewById(R.id.launcherFilePickerButton);
		filePickerButton.setOnClickListener(new ActivityLaunchListener(com.blogspot.tonyatkins.picker.Constants.ACTION_PICK_FILE, FILE_PICKER_REQUEST));
		
		ImageButton recordButton = (ImageButton) findViewById(R.id.launcherMicrophoneButton);
		Bundle recordingBundle = new Bundle();
		recordingBundle.putString(RecordSoundActivity.OUTPUT_DIR_KEY, Constants.SOUND_DIRECTORY);
		recordingBundle.putString(RecordSoundActivity.FILE_NAME_KEY, "long-jacket");
		recordButton.setOnClickListener(new ActivityLaunchListener(android.provider.MediaStore.Audio.Media.RECORD_SOUND_ACTION, MICROPHONE_REQUEST,recordingBundle));
		
		ImageButton voiceInputButton = (ImageButton) findViewById(R.id.launcherVoiceInputButton);
		voiceInputButton.setOnClickListener(new ActivityLaunchListener(RecognizerIntent.ACTION_RECOGNIZE_SPEECH, VOICE_INPUT_REQUEST));
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
			case FILE_PICKER_REQUEST:
				if (data != null) {
					Uri fileUri = data.getData();
					if (fileUri != null) {
						File file = new File(fileUri.getPath());
						if (file.exists()) {
							Toast.makeText(this, "Activity returned a URI that points to a file named '" + file.getName() + "' which is " + file.length() + " bytes in size.", Toast.LENGTH_LONG).show();
						}
						else {
							Toast.makeText(this, "Activity returned a URI that points to a file named '" + file.getName() + "' which doesn't exist.", Toast.LENGTH_LONG).show();
						}
					}
					else {
						Toast.makeText(this, "No file URI returned.", Toast.LENGTH_LONG).show();
					}
				}
				else {
					Toast.makeText(this, "Error getting file input or no data returned.", Toast.LENGTH_LONG).show();
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
		private Bundle bundle;
		
		public ActivityLaunchListener(String action, int requestCode) {
			this.action = action;
			this.requestCode = requestCode;
		}
		
		public ActivityLaunchListener(String action, int requestCode, Bundle bundle) {
			this.action = action;
			this.requestCode = requestCode;
			this.bundle = bundle;
		}

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(action);
			if (bundle != null) {
				intent.putExtras(bundle);
			}
			startActivityForResult(intent, requestCode);
		}
	}
}
