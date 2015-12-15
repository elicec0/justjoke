package com.special.ResideMenuDemo;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.List;





import com.elicec.adapter.MyPagerAdapter;
import com.elicec.adapter.NewListAdapter;
import com.elicec.maincode.MySqlLiteDb;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.special.ResideMenu.ResideMenu;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class ProfileFragment extends Fragment implements OnClickListener {
	
	private List<View> views;
	private View mFragmentView;
	private ViewPager mViewPage;
	private int offset; // 间隔
	private int cursorWidth; // 游标的长度
	private int originalIndex = 0;
	private Animation animation = null;
	private ImageView cursor = null;
	private PullToRefreshListView flip_humor;
	private PullToRefreshListView flip_feeling;
	private PullToRefreshListView flip_color;
	private PullToRefreshListView flip_truth;
	private NewListAdapter[] newAdapter;
	private ResideMenu resideMenu;
	private MySqlLiteDb sqlDb;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	mFragmentView=inflater.inflate(R.layout.profile, container, false);
    	sqlDb=new MySqlLiteDb(getActivity());
    	MenuActivity m=(MenuActivity)(getActivity());
    	resideMenu=m.getResideMenu();
    	TextView tvIgnore=(TextView) mFragmentView.findViewById(R.id.ignore_view);
    	resideMenu.addIgnoredView(tvIgnore);
    	
    	views=new ArrayList<View>();
    	views.add(inflater.inflate(R.layout.flipview_humor,null));
    	views.add(inflater.inflate(R.layout.flipview_feeling,null));
    	views.add(inflater.inflate(R.layout.flipview_color,null));
    	views.add(inflater.inflate(R.layout.flipview_truth,null));
    	
    	((TextView) mFragmentView.findViewById(R.id.tvTag1)).setOnClickListener(this);
		((TextView) mFragmentView.findViewById(R.id.tvTag2)).setOnClickListener(this);
		((TextView) mFragmentView.findViewById(R.id.tvTag3)).setOnClickListener(this);
		((TextView) mFragmentView.findViewById(R.id.tvTag4)).setOnClickListener(this);
		
    	mViewPage=(ViewPager) mFragmentView.findViewById(R.id.vpViewPager1);
    	mViewPage.setAdapter(new MyPagerAdapter(views));
    	mViewPage.setOnPageChangeListener(new MyOnPageChangeListener());
    	
    	
    	initCursor(views.size());
    	
    	MyPagerAdapter pagerAdapter=(MyPagerAdapter) mViewPage.getAdapter();
    	View v1_humor=pagerAdapter.getItemAtPosition(0);
    	View v2_feeling=pagerAdapter.getItemAtPosition(1);
    	View v3_color=pagerAdapter.getItemAtPosition(2);
    	View v4_truth=pagerAdapter.getItemAtPosition(3);
    	
    	 flip_humor = (PullToRefreshListView) v1_humor
 				.findViewById(R.id.flip_humor);
 		flip_feeling = (PullToRefreshListView) v2_feeling
 				.findViewById(R.id.flip_feeling);
 		flip_color = (PullToRefreshListView) v3_color
 				.findViewById(R.id.flip_color);
 		flip_truth = (PullToRefreshListView) v4_truth
 				.findViewById(R.id.flip_truth);
 		
 		newAdapter=new NewListAdapter[]{
 				 new NewListAdapter(getActivity(), sqlDb.getContentByStyles(0).get(0)),
 		 		 new NewListAdapter(getActivity(), sqlDb.getContentByStyles(1).get(0)),
 		 		 new NewListAdapter(getActivity(), sqlDb.getContentByStyles(2).get(0)),
 		 		 new NewListAdapter(getActivity(), sqlDb.getContentByStyles(3).get(0)),
 		};
 		
 		
 		
 		initPullToRefreshListView(flip_humor, newAdapter[0]);
 		initPullToRefreshListView(flip_feeling, newAdapter[1]);
 		initPullToRefreshListView(flip_color, newAdapter[2]);
 		initPullToRefreshListView(flip_truth, newAdapter[3]);
        return mFragmentView;
    }
//    
//    public ArrayList<HashMap<String, String>> getSimulationNews(int n) {
//		ArrayList<HashMap<String, String>> ret = new ArrayList<HashMap<String, String>>();
//		HashMap<String, String> hm;
//		for (int i = 0; i < n; i++) {
//			hm = new HashMap<String, String>();
//			if (i % 2 == 0) {
//				hm.put("uri",
//						"http://images.china.cn/attachement/jpg/site1000/20131029/001fd04cfc4813d9af0118.jpg");
//			} else {
//				hm.put("uri",
//						"http://photocdn.sohu.com/20131101/Img389373139.jpg");
//			}
//			//hm.put("title", "国内成品油价两连跌几成定局");
//			hm.put("content", "国内成品油今日迎调价窗口，机构预计每升降价0.1元。");
//			//hm.put("review", i + "跟帖");
//			ret.add(hm);
//		}
//		return ret;
//	}
    
    public void initPullToRefreshListView(PullToRefreshListView rtflv,
			NewListAdapter adapter) {
		rtflv.setMode(Mode.PULL_FROM_END);
		rtflv.setOnRefreshListener(new MyOnRefreshListener2(rtflv));
		rtflv.setAdapter(adapter);

		
	}
    
    class MyOnRefreshListener2 implements OnRefreshListener2<ListView> {

		private PullToRefreshListView mPtflv;

		public MyOnRefreshListener2(PullToRefreshListView ptflv) {
			this.mPtflv = ptflv;
		}

		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			// 下拉刷新
			String label = DateUtils.formatDateTime(ProfileFragment.this.getActivity(),
					System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
							| DateUtils.FORMAT_SHOW_DATE
							| DateUtils.FORMAT_ABBREV_ALL);

			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
			//new GetNewsTask(mPtflv).execute();
//			newAdapter.addNews(getSimulationNews(10));
//			newAdapter.notifyDataSetChanged();
			//mPtflv.onRefreshComplete();
			

		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			// 上拉加载
			//new GetNewsTask(mPtflv).execute();
//			newAdapter.addNews(getSimulationNews(10));
//			newAdapter.notifyDataSetChanged();
			new GetMoreJokes(mPtflv).execute();
			//mPtflv.onRefreshComplete();
		}

	}
    
    public void initCursor(int tagNum) {
		cursorWidth = BitmapFactory.decodeResource(getResources(),
				R.drawable.cursor).getWidth();
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		offset = ((dm.widthPixels / tagNum) - cursorWidth) / 2;

		cursor = (ImageView) mFragmentView.findViewById(R.id.ivCursor);
		Matrix matrix = new Matrix();
		matrix.setTranslate(offset, 0);
		cursor.setImageMatrix(matrix);
	}
    
    class MyOnPageChangeListener implements OnPageChangeListener{

    	@Override
    	public void onPageScrollStateChanged(int arg0) {
    		// TODO Auto-generated method stub
    		
    	}

    	@Override
    	public void onPageScrolled(int arg0, float arg1, int arg2) {
    		// TODO Auto-generated method stub
    		
    	}

    	@Override
    	public void onPageSelected(int arg0) {
    		// TODO Auto-generated method stub
    		int one = 2 * offset + cursorWidth;
    		int two = one * 2;

    		switch (originalIndex) {
			case 0:
				if (arg0 == 1) {
					animation = new TranslateAnimation(0, one, 0, 0);
				}
				if (arg0 == 2) {
					animation = new TranslateAnimation(0, two, 0, 0);
				}
				if (arg0 == 3) {
					animation = new TranslateAnimation(0, two+one, 0, 0);
				}
				break;
			case 1:
				if (arg0 == 0) {
					animation = new TranslateAnimation(one, 0, 0, 0);
				}
				if (arg0 == 2) {
					animation = new TranslateAnimation(one, two, 0, 0);
				}
				if (arg0 == 3) {
					animation = new TranslateAnimation(one, two+one, 0, 0);
				}
				break;
			case 2:
				if (arg0 == 1) {
					animation = new TranslateAnimation(two, one, 0, 0);
				}
				if (arg0 == 0) {
					animation = new TranslateAnimation(two, 0, 0, 0);
				}
				if (arg0 == 3) {
					animation = new TranslateAnimation(two, two+one, 0, 0);
				}
				break;
			case 3:
				if (arg0 == 1) {
					animation = new TranslateAnimation(two+one, one, 0, 0);
				}
				if (arg0 == 0) {
					animation = new TranslateAnimation(two+one, 0, 0, 0);
				}
				if (arg0 == 2) {
					animation = new TranslateAnimation(two+one, two, 0, 0);
				}
				break;
			}
    		animation.setFillAfter(true);
    		animation.setDuration(300);
    		cursor.startAnimation(animation);

    		originalIndex = arg0;
    		
    	}
    	 
     }

    
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tvTag1:
			mViewPage.setCurrentItem(0);
			break;
		case R.id.tvTag2:
			mViewPage.setCurrentItem(1);
			break;
		case R.id.tvTag3:
			mViewPage.setCurrentItem(2);
			break;
		case R.id.tvTag4:
			mViewPage.setCurrentItem(3);
			break;
		
		}
		
	}
	class GetMoreJokes extends AsyncTask<String, Void, Integer> {
		
		private PullToRefreshListView mPtrlv;
		private ArrayList<List<HashMap<String ,String>>> list;
		public GetMoreJokes(PullToRefreshListView ptrlv){
			
			this.mPtrlv=ptrlv;
			
			
		}

		@Override
		protected Integer doInBackground(String... params) {
			// TODO Auto-generated method stub
			switch(mPtrlv.getId()){
			case R.id.flip_humor:{list=sqlDb.getContentByStyles(0);break;}
			case R.id.flip_feeling:{list=sqlDb.getContentByStyles(1);break;}
			case R.id.flip_color:{list=sqlDb.getContentByStyles(2);break;}
			case R.id.flip_truth:{list=sqlDb.getContentByStyles(3);break;}
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			switch(mPtrlv.getId()){
			case R.id.flip_humor:{
				if(list.get(0).size()==0)mPtrlv.getLoadingLayoutProxy().setLastUpdatedLabel("没有更多数据了");
				newAdapter[0].addNews(list.get(0));
				newAdapter[0].notifyDataSetChanged();
				break;
			}
			case R.id.flip_feeling:{
				if(list.get(0).size()==0)mPtrlv.getLoadingLayoutProxy().setLastUpdatedLabel("没有更多数据了");
				newAdapter[1].addNews(list.get(0));
				newAdapter[1].notifyDataSetChanged();
				break;
			}
			case R.id.flip_color:{
				if(list.get(0).size()==0)mPtrlv.getLoadingLayoutProxy().setLastUpdatedLabel("没有更多数据了");
				newAdapter[2].addNews(list.get(0));
				newAdapter[2].notifyDataSetChanged();
				break;
			}
			case R.id.flip_truth:{
				if(list.get(0).size()==0)mPtrlv.getLoadingLayoutProxy().setLastUpdatedLabel("没有更多数据了");
				newAdapter[3].addNews(list.get(0));
				newAdapter[3].notifyDataSetChanged();
				break;
			}
				
			}
			
			
			mPtrlv.onRefreshComplete();
		}
		

	}

}
