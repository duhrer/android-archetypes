package com.blogspot.tonyatkins.archetype.activity;

import com.blogspot.tonyatkins.archetype.R;

import android.app.Activity;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class ButtonGridActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.buttongrid);
		
		GridView gridView = (GridView) findViewById(R.id.buttonGridView);
		gridView.setAdapter(new ButtonListAdapter());
	}

	private class ButtonListAdapter implements ListAdapter {
		private String[] buttonLabels = { "One", "Two", "Three","Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"};

		private int[] buttonResources = { 	android.R.drawable.btn_minus,
											android.R.drawable.btn_plus,
											android.R.drawable.btn_radio,
											android.R.drawable.btn_star,
											android.R.drawable.btn_star_big_on,
											android.R.drawable.gallery_thumb,
											android.R.drawable.sym_def_app_icon,
											android.R.drawable.sym_action_chat,
											android.R.drawable.sym_action_call,
											android.R.drawable.sym_action_email};

		private int[] buttonColors = { 		Color.BLUE,
											Color.RED,
											Color.GREEN,
											Color.CYAN,
											Color.MAGENTA,
											Color.YELLOW,
											Color.WHITE,
											Color.GRAY,
											Color.LTGRAY,
											Color.TRANSPARENT};
		
		@Override
		public int getCount() {
			return 10;
		}

		@Override
		public Object getItem(int position) {
			return null;
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
			LayoutInflater li = getLayoutInflater();
			View view = li.inflate(R.layout.buttongrid_template, null);
			view.setMinimumHeight(view.getMeasuredWidth());
			view.invalidate();
			
			TextView label = (TextView) view.findViewById(R.id.buttonTemplateLabel);
			label.setText(buttonLabels[position]);
			
			ImageView image = (ImageView) view.findViewById(R.id.buttonTemplateImage);
			image.setImageResource(buttonResources[position]);
			
			Button button = (Button) view.findViewById(R.id.buttonTemplateButton);
			button.getBackground().setColorFilter(buttonColors[position],PorterDuff.Mode.MULTIPLY);

			return view;
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
		public boolean isEmpty() { return false; }

		@Override
		public void registerDataSetObserver(DataSetObserver observer) {}

		@Override
		public void unregisterDataSetObserver(DataSetObserver observer) {}

		@Override
		public boolean areAllItemsEnabled() { return true;}

		@Override
		public boolean isEnabled(int position) { return true;}
	}
}
