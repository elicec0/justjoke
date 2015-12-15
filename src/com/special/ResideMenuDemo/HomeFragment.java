package com.special.ResideMenuDemo;

import android.os.Bundle;


import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elicec.maincode.MySqlLiteDb;
import com.elicec.maincode.ShakeListener;
import com.special.ResideMenu.ResideMenu;


public class HomeFragment extends Fragment {

    private View parentView;
    private ResideMenu resideMenu;
	private TextView jokeContent;
	private  MySqlLiteDb mDataBase;
	private ImageView img_shake;
	private Animation shake;
	private ShakeListener shakeListener=null;
	private Animation text_in;
	private Vibrator vibrator;
	private RelativeLayout btn_favourites;
	private RelativeLayout btn_copy;
	private RelativeLayout btn_share;
	private static HomeFragment instance=null;
	
	public static HomeFragment getInstance(){
		if(instance==null){
            synchronized(HomeFragment.class){
                if(instance==null){
                    instance=new HomeFragment();
                }
            }
        }
        return instance;
	}

   

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		shakeListener.stop();
		super.onPause();
	}



	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		shakeListener.start();
	}



	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.shakeListener = new ShakeListener(getActivity());
		this.shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shakeoneshake);
		this.text_in = AnimationUtils.loadAnimation(getActivity(), R.anim.flip_vertical_in);
        this.shake.setFillAfter(true);
		this.shakeListener.setOnShakeListener(new ShakeListener.OnShakeListener()
        {
          public void onShake()
          {
        	  String jokeConent=mDataBase.getJokeContent();
        	// Log.i(MySqlLiteDb.TAG,"jokeCoent:"+HomeFragment.this.getId());
        	HomeFragment.this.shakeListener.stop();
            HomeFragment.this.shake.setFillAfter(true);
            HomeFragment.this.img_shake.startAnimation(HomeFragment.this.shake);
            HomeFragment.this.vibrator.vibrate(new long[] { 300, 200, 300, 200 }, -1);
            HomeFragment.this.jokeContent.setText(jokeConent);
            HomeFragment.this.jokeContent.startAnimation(HomeFragment.this.text_in);
            
            new Handler().postDelayed(new Runnable()
            {
              public void run()
              {
                HomeFragment.this.vibrator.cancel();
                HomeFragment.this.shakeListener.start();
              }
            }
            , 1500);
          }
        });
		
	}



	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.home_laugheveryday, container, false);
        Log.i(MySqlLiteDb.TAG,"onCreateView");
        this.mDataBase = new MySqlLiteDb(getActivity());
        this.vibrator = ((Vibrator)getActivity().getApplication().getSystemService("vibrator"));
        setUpViews();
        return parentView;
    }

    private void setUpViews() {
        MenuActivity parentActivity = (MenuActivity) getActivity();
        resideMenu = parentActivity.getResideMenu();
        
        jokeContent = ((TextView)this.parentView.findViewById(R.id.joke_content));
        jokeContent.setText(this.mDataBase.getJokeContent());
        img_shake = ((ImageView)this.parentView.findViewById(R.id.shake));
        this.img_shake.startAnimation(this.shake);
        
        
        this.btn_favourites = ((RelativeLayout)this.parentView.findViewById(R.id.joke_btn_favorites));
        this.btn_copy = ((RelativeLayout)this.parentView.findViewById(R.id.joke_btn_copy));
        this.btn_share = ((RelativeLayout)this.parentView.findViewById(R.id.joke_btn_share));

        this.btn_favourites.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            paramView.setBackgroundResource(R.drawable.bg_list_item_btn);
          }
        });
        this.btn_copy.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            paramView.setBackgroundResource(R.drawable.bg_list_item_btn);
          }
        });
        this.btn_share.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            paramView.setBackgroundResource(R.drawable.bg_list_item_btn);
          }
        });
    }

}
