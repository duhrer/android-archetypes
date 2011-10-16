package com.blogspot.tonyatkins.archetype.activity;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.blogspot.tonyatkins.archetype.R;
import com.blogspot.tonyatkins.archetype.exception.SampleExceptionHandler;

public class StartupActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.startup);
        
        // Wire up the exception handling for the pitcher/catcher example
        UncaughtExceptionHandler handler = new SampleExceptionHandler(this, ExceptionCatcherActivity.class);
		Thread.setDefaultUncaughtExceptionHandler(handler);

        // Wire up the list of activities to its adapter 
        ListView activityListView = (ListView) findViewById(R.id.activityListView);
        
        activityListView.setAdapter(new ActivityListAdapter(this));
    }
    
    private class ActivityListAdapter implements ListAdapter {
    	private final Activity activity;
    	
    	private final List<Activity> activityClasses = new ArrayList<Activity>();
    	
		public ActivityListAdapter(Activity activity) {
			this.activity = activity;
			
			activityClasses.add(new ImageMaskActivity());
			activityClasses.add(new ButtonGridActivity());
			activityClasses.add(new TouchEventActivity());
			activityClasses.add(new ExceptionPitcherActivity());
		}

		@Override
		public int getCount() {
			return activityClasses.size();
		}

		@Override
		public Object getItem(int position) {
			return activityClasses.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public int getItemViewType(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Activity childActivity = (Activity) getItem(position);
			
			Button button = new Button(activity);
			
			if (childActivity.getTitle() != null && childActivity.getTitle().length() > 0) {
				button.setText(childActivity.getTitle());
			}
			else {
				button.setText(childActivity.getClass().getSimpleName());
			}
			button.setOnClickListener(new StartActivityListener(activity, childActivity.getClass()));
			
			return button;
		}

		@Override
		public int getViewTypeCount() {
			return 1;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isEmpty() {
			return false;
		}

		@Override
		public void registerDataSetObserver(DataSetObserver observer) {
		}

		@Override
		public void unregisterDataSetObserver(DataSetObserver observer) {
		}

		@Override
		public boolean areAllItemsEnabled() {
			return true;
		}

		@Override
		public boolean isEnabled(int position) {
			return true;
		}
    }
    
	private class StartActivityListener implements OnClickListener {
    	private Activity activity;
    	private Class startClass;

		public StartActivityListener(Activity activity, Class startClass) {
			super();
			this.activity = activity;
			this.startClass = startClass;
		}

		@Override
		public void onClick(View view) {
        	Intent mainIntent = new Intent(activity, startClass);
        	startActivity(mainIntent);
		}
    }
}