package com.blogspot.tonyatkins.archetype.listener;

import com.blogspot.tonyatkins.archetype.SimulatedButton;

import android.content.ClipData;
import android.view.View;
import android.view.View.OnLongClickListener;

public class DragLongClickListener implements OnLongClickListener {
	private final SimulatedButton button;
	
	public DragLongClickListener(SimulatedButton button) {
		super();
		this.button = button;
	}

	@Override
	public boolean onLongClick(View v) {
		View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
		ClipData data = ClipData.newPlainText("id",String.valueOf(button.getId()));
		v.startDrag(data, shadowBuilder, button, 0);

		return true;
	}

}
