package com.blogspot.tonyatkins.archetype.activity;

import com.blogspot.tonyatkins.archetype.Constants;
import com.blogspot.tonyatkins.archetype.R;
import com.blogspot.tonyatkins.archetype.service.ArchetypeService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ServiceControllerActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.service_controller);
		
		Button exitButton = (Button) findViewById(R.id.serviceControllerExitButton);
		exitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		Button startServiceButton = (Button) findViewById(R.id.serviceControllerStartServiceButton);
		startServiceButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(Constants.TAG, "Starting service from onClick in parent Activity...");
				startService(new Intent(ServiceControllerActivity.this,ArchetypeService.class));
				Log.i(Constants.TAG, "Finished starting service...");
			}
		});
		
		Button stopServiceButton = (Button) findViewById(R.id.serviceControllerStopServiceButton);
		stopServiceButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(Constants.TAG, "Stopping service from onClick in parent Activity...");
				
				Intent intent = new Intent(ServiceControllerActivity.this,ArchetypeService.class);
				intent.putExtra(ArchetypeService.STOP, true);
				startService(intent);
			}
		});
	}
}
