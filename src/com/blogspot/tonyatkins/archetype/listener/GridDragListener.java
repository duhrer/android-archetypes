package com.blogspot.tonyatkins.archetype.listener;

import java.util.Iterator;
import java.util.TreeSet;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.widget.GridView;

import com.blogspot.tonyatkins.archetype.ButtonListAdapter;
import com.blogspot.tonyatkins.archetype.SimulatedButton;

public class GridDragListener implements OnDragListener {
	private final SimulatedButton button;
	private final TreeSet<SimulatedButton> buttons;
	private final Activity activity;
	private final GridView gridView;
	private float xPos = 0;
	
	public GridDragListener(SimulatedButton button, TreeSet<SimulatedButton> buttons, Activity activity, GridView gridView) {
		this.button = button;
		this.buttons = buttons;
		this.activity = activity;
		this.gridView = gridView;
	}

	@Override
	public boolean onDrag(View view, DragEvent event) {
		final int action = event.getAction();

		SimulatedButton draggedButton = (SimulatedButton) event.getLocalState();

		
		switch (action) {
			case DragEvent.ACTION_DRAG_ENDED:
				// Someone let go of a dragged view.  Reset my background
				view.getBackground().setColorFilter(button.getColor(),Mode.MULTIPLY);
				view.invalidate();
				break;
			case DragEvent.ACTION_DRAG_ENTERED:
				// A view has entered my air space.

				// If the button isn't "me", change the background
				if (draggedButton.getId() != button.getId()) {
					view.getBackground().setColorFilter(Color.GREEN,Mode.MULTIPLY);
					view.invalidate();
				}
				
				break;
			case DragEvent.ACTION_DRAG_EXITED:
				// A view has left my air space
				view.getBackground().setColorFilter(Color.RED,Mode.MULTIPLY);
				view.invalidate();
				break;
			case DragEvent.ACTION_DRAG_LOCATION:
				// Used for awareness of stuff that's being dragged
				xPos = event.getX();
				break;
			case DragEvent.ACTION_DRAG_STARTED:
				// Someone has picked a view up
				view.getBackground().setColorFilter(Color.RED,Mode.MULTIPLY);
				view.invalidate();
				break;
			case DragEvent.ACTION_DROP:
				// Someone has dropped a view on me.
				
				// Only reorder if I was dropped on a different button
				if (draggedButton.getId() != button.getId()) {
					// Go through the list of buttons and determine the new order.  basically, we need to set our new order, increment anything higher, and fill in the hole we left
					int droppedSortOrder = button.getSortOrder();
					int newDraggedSortOrder = xPos > (view.getWidth()/2) ? droppedSortOrder + 1 : droppedSortOrder -1;
					draggedButton.setSortOrder(newDraggedSortOrder);
					
					Iterator<SimulatedButton> iterator = buttons.iterator();
					while (iterator.hasNext()) {
						SimulatedButton sortButton = iterator.next();
						if (sortButton.getId() != draggedButton.getId()) {
							if (sortButton.getSortOrder() <= newDraggedSortOrder) {
								sortButton.setSortOrder(sortButton.getSortOrder() - 1);
							}
							else {
								sortButton.setSortOrder(sortButton.getSortOrder() + 1);
							}
						}
					}
					
					TreeSet<SimulatedButton> updatedButtons = new TreeSet<SimulatedButton>(buttons);
					gridView.setAdapter(new ButtonListAdapter(activity,gridView,updatedButtons));
				}

				break;
		}
		
		return true;
	}
}
