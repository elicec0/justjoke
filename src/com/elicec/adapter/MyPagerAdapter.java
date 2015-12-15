package com.elicec.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class MyPagerAdapter extends PagerAdapter {
	private List<View> mViews;
	
	public MyPagerAdapter(List<View> views){
		this.mViews=views;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mViews.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}

	public View getItemAtPosition(int position) {
		// TODO Auto-generated method stub
		return mViews.get(position);
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
		super.destroyItem(container, position, object);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		((ViewPager) container).removeView(mViews.get(position));
		
	}

	@Override
	public Object instantiateItem(View container, int position) {
		// TODO Auto-generated method stub
		((ViewPager)container).addView(mViews.get(position));
		return mViews.get(position);
	}

}
