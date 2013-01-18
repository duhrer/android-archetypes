package com.blogspot.tonyatkins.archetype.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blogspot.tonyatkins.archetype.R;

public class SampleFragment extends Fragment {
	private final String message;

	public SampleFragment(String message) {
		super();
		this.message = message;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View parentView = inflater.inflate(R.layout.sample_fragment, container, false);
		TextView textView = (TextView) parentView.findViewById(R.id.sampleFragmentTextView);
		textView.setText(message);
		return parentView;
	}
}
