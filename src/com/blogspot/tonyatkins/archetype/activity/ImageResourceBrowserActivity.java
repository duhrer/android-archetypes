package com.blogspot.tonyatkins.archetype.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListAdapter;

import com.blogspot.tonyatkins.archetype.R;

public class ImageResourceBrowserActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.image_resource_browser);
		
		GridView gridView = (GridView) findViewById(R.id.imageResourceBrowserGridView);
		gridView.setAdapter(new ImageResourceAdapter(this));
	}

	private class ImageResourceAdapter implements ListAdapter {
		private List<Integer> resources = new ArrayList<Integer>();
		private List<Integer> forbidden = new ArrayList<Integer>();
		private int[] forbiddenArray = new int[] {0,1,4,5,6,18,19,20,21,22,24,25,26,27,28,98,101,108,109,112,113,134,135,139,140};
		private final Context context;
		public ImageResourceAdapter(Context context) {
			this.context = context;
			for (int x : forbiddenArray) { forbidden.add(x); }
			
			for (int a=0; a<144; a++) {
				if (!forbidden.contains(a)) resources.add(17301504 + a);
			}
		}
		
		
		@Override
		public int getCount() {
			return resources.size();
		}

		@Override
		public Object getItem(int position) {
			return resources.toArray()[position];
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
			ImageView view = new ImageView(context);
			view.setMinimumHeight(200);
			view.setMaxHeight(200);
			view.setScaleType(ScaleType.CENTER_INSIDE);
			view.setImageResource((Integer) getItem(position));
			view.setBackgroundResource(R.drawable.darkgrayoutline);
			return view;
		}

		@Override
		public int getViewTypeCount() {
			return 1;
		}

		@Override
		public boolean hasStableIds() {
			return true;
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
}
