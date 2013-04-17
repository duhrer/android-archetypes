package com.blogspot.tonyatkins.archetype.activity;

import com.blogspot.tonyatkins.archetype.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PreferenceSettingActivity extends Activity {
	public static final String COLUMNS_PREF = "columns";
	
	public static final String ORIENTATION_PREF = "orientation";
	
	public static final String DEV_OPTIONS_PREF = "enableDevOptions";
	public static final String FULL_SCREEN_PREF = "fullScreen";
	public static final String HIDE_TAB_CONTROLS_PREF = "hideTabControls";
	public static final String SCALE_TEXT_PREF = "scaleTextWidth";
	public static final String SWIPE_TAB_PREF = "swipeTabs";
	public static final String ALLOW_EDITING_PREF = "allowEditing";

	public static final String TTS_VOICE_PREF = "tts_voice";
	public static final String TTS_SAVE_PREF = "saveTTS";
	
	private static final String FS_CLASS = "com.blogspot.tonyatkins.freespeech.action.PREFS";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.preference_setting);
		
		Button oneColumnButton = (Button) findViewById(R.id.prefSetting1ColButton);
		Intent oneColumnIntent = new Intent(FS_CLASS).putExtra(COLUMNS_PREF, 1);
		oneColumnButton.setOnClickListener(new ActivityLaunchListener(oneColumnIntent) );
		
		Button threeColumnButton = (Button) findViewById(R.id.prefSetting3ColButton);
		Intent threeColumnIntent = new Intent(FS_CLASS).putExtra(COLUMNS_PREF, 3);
		threeColumnButton.setOnClickListener(new ActivityLaunchListener(threeColumnIntent) );
		
		// 
		
		/* 
		 * 		
		String[] booleanPrefKeys = 
			{	DEV_OPTIONS_PREF,
				FULL_SCREEN_PREF,
				HIDE_TAB_CONTROLS_PREF,
				SCALE_TEXT_PREF,
				SWIPE_TAB_PREF,
				ALLOW_EDITING_PREF,
				TTS_SAVE_PREF
			};
			
			ORIENTATION_PREF
			TTS_VOICE_PREF
			

		 */
	}
	
	private class ActivityLaunchListener implements OnClickListener {
		private final Intent intent;
		public ActivityLaunchListener(Intent intent) {
			this.intent = intent;
		}

		@Override
		public void onClick(View v) {
			startActivity(intent);
		}
	}
}
