package com.elicec.adapter;

import java.util.HashMap;

import java.util.List;

import com.special.ResideMenuDemo.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NewListAdapter extends BaseAdapter {

	
	
	static class ViewHolder {
		
		
		TextView tvContent;
		
	}
	
	private Context context;
	private List<HashMap<String, String>> news;

	public NewListAdapter(Context context,List<HashMap<String, String>> news) {
		this.context = context;
		this.news = news;
		
	}
	
	@Override
	public int getCount() {
		return news.size();
	}

	@Override
	public HashMap<String,String> getItem(int position) {
		return news.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		if(convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.joke_list_item, null);
			holder = new ViewHolder();
			
			
			holder.tvContent = (TextView) convertView.findViewById(R.id.joke_content);
			
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		//holder.tvTitle.setText(getItem(position).get("title"));
		holder.tvContent.setText(getItem(position).get("content"));
		//holder.tvReview.setText(getItem(position).get("review"));
		
		return convertView;
	}
	
	public void addNews(List<HashMap<String, String>> addNews) {
		for(HashMap<String, String> hm:addNews) {
			news.add(hm);
		}
	}

}
