package com.blogspot.tonyatkins.archetype;

import java.util.Set;
import java.util.TreeSet;

import android.app.Activity;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.blogspot.tonyatkins.archetype.listener.DragLongClickListener;
import com.blogspot.tonyatkins.archetype.listener.GridDragListener;

public class ButtonListAdapter implements ListAdapter {
	private String[] buttonLabels = { "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten" };

	private int[] buttonResources = { android.R.drawable.btn_minus, android.R.drawable.btn_plus, android.R.drawable.btn_radio, android.R.drawable.btn_star, android.R.drawable.btn_star_big_on, android.R.drawable.gallery_thumb, android.R.drawable.sym_def_app_icon, android.R.drawable.sym_action_chat, android.R.drawable.sym_action_call, android.R.drawable.sym_action_email };

	private int[] buttonColors = { Color.BLUE, Color.RED, Color.GREEN, Color.CYAN, Color.MAGENTA, Color.YELLOW, Color.WHITE, Color.GRAY, Color.LTGRAY, Color.TRANSPARENT };

	private final TreeSet<SimulatedButton> buttons = new TreeSet<SimulatedButton>();

	private final Activity activity;
	private final GridView gridView;

	public ButtonListAdapter(Activity activity, GridView gridView) {
		this.activity = activity;
		this.gridView = gridView;
		for (int a = 0; a < 10; a++)
		{
			buttons.add(new SimulatedButton(a, buttonLabels[a], buttonColors[a], buttonResources[a], a));
		}
	}
	
	public ButtonListAdapter(Activity activity, GridView gridView, TreeSet<SimulatedButton> buttons) {
		this.activity = activity;
		this.gridView = gridView;
		this.buttons.addAll(buttons);
	}

	@Override
	public int getCount() {
		return buttons.size();
	}

	@Override
	public Object getItem(int position) {
		return buttons.toArray()[position];
	}

	@Override
	public long getItemId(int position) {
		SimulatedButton button = (SimulatedButton) getItem(position);
		return button.getId();
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SimulatedButton simulatedButton = (SimulatedButton) getItem(position);

		LayoutInflater li = activity.getLayoutInflater();
		View view = li.inflate(R.layout.buttongrid_template, null);
		view.setMinimumHeight(view.getMeasuredWidth());
		view.measure(view.getMeasuredWidth(), view.getMeasuredWidth());
		view.invalidate();

		TextView label = (TextView) view.findViewById(R.id.buttonTemplateLabel);
		label.setText(simulatedButton.getLabel());

		ImageView image = (ImageView) view.findViewById(R.id.buttonTemplateImage);
		image.setImageResource(simulatedButton.getResource());

		Button button = (Button) view.findViewById(R.id.buttonTemplateButton);
		button.getBackground().setColorFilter(simulatedButton.getColor(), PorterDuff.Mode.MULTIPLY);
		button.setOnDragListener(new GridDragListener(simulatedButton, buttons, activity, gridView));
		button.setOnLongClickListener(new DragLongClickListener(simulatedButton));

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

	public Set<SimulatedButton> getButtons() {
		return buttons;
	}
}