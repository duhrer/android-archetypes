package com.blogspot.tonyatkins.archetype.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import com.blogspot.tonyatkins.archetype.ButtonListAdapter;
import com.blogspot.tonyatkins.archetype.R;

public class ButtonGridActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.buttongrid);
		
		GridView gridView = (GridView) findViewById(R.id.buttonGridView);
		gridView.setAdapter(new ButtonListAdapter(this,gridView));
	}
}
