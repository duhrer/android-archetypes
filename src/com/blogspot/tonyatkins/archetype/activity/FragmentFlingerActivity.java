package com.blogspot.tonyatkins.archetype.activity;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.blogspot.tonyatkins.archetype.R;
import com.blogspot.tonyatkins.archetype.fragments.SampleFragment;

public class FragmentFlingerActivity extends FragmentActivity {
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_flinger);
		
		ViewPager pager = (ViewPager) findViewById(R.id.fragmentFlingerViewPager);
		FragmentManager fragmentManager = getSupportFragmentManager();
		pager.setAdapter(new FragmentFlingerPagerAdapter(fragmentManager));
	}
	
	private class FragmentFlingerPagerAdapter extends FragmentPagerAdapter {
		private List<SampleFragment> fragments = new ArrayList<SampleFragment>();
		
		public FragmentFlingerPagerAdapter(android.support.v4.app.FragmentManager fm) {
			super(fm);
			
			fragments.add(new SampleFragment("foo"));
			fragments.add(new SampleFragment("bar"));
			fragments.add(new SampleFragment("baz"));
		}

		@Override
		public int getCount() {
			return fragments.size();
		}

		@Override
		public Fragment getItem(int arg0) {
			return fragments.get(arg0);
		}
	}
}
