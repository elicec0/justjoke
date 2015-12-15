package com.special.ResideMenuDemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.elicec.maincode.MySqlLiteDb;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

public class MenuActivity extends FragmentActivity implements View.OnClickListener{

    private ResideMenu resideMenu;
    private MenuActivity mContext;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemProfile;
    private ResideMenuItem itemCalendar;
    private ResideMenuItem itemSettings;
	private ResideMenuItem itemSearch;
	private TextView titleTextView;
	private MySqlLiteDb mSqlLiteDb;
	private HomeFragment mHomeFragment;
	private ProfileFragment mProfileFragment;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.titleTextView = ((TextView)findViewById(R.id.title_bar));
        mContext = this;
        mSqlLiteDb = new MySqlLiteDb(this);
        setUpMenu();
        if( savedInstanceState == null ){
        	this.titleTextView.setText("每日一笑");
            if(mHomeFragment==null){
            	mHomeFragment= HomeFragment.getInstance();
            	changeFragment(mHomeFragment);
            }else{
            	 changeFragment(mHomeFragment);
            }
        }
        	
           
    }

    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip. 
        resideMenu.setScaleValue(0.6f);
        
        this.itemHome = new ResideMenuItem(this, R.drawable.icon_home, "每日一笑");
        this.itemProfile = new ResideMenuItem(this, R.drawable.icon_profile, "全部笑话");
        this.itemSearch = new ResideMenuItem(this, R.drawable.search, "搜索");
        this.itemCalendar = new ResideMenuItem(this, R.drawable.start, "收藏");
        this.itemSettings = new ResideMenuItem(this, R.drawable.icon_settings, "留言板");
        
        itemHome.setOnClickListener(this);
        itemProfile.setOnClickListener(this);
        itemCalendar.setOnClickListener(this);
        itemSettings.setOnClickListener(this);

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemCalendar, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemSearch, ResideMenu.DIRECTION_LEFT);

        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        findViewById(R.id.title_bar_right_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {
//    	FragmentManager fragmentManager=getSupportFragmentManager();
//    	FragmentTransaction transaction = fragmentManager.beginTransaction(); 
    	

        if (view == itemHome){
        	this.titleTextView.setText("每日一笑");
        	if(mHomeFragment==null){
        		mHomeFragment=HomeFragment.getInstance();
        		changeFragment(mHomeFragment);
        		//transaction.add(R.id.main_fragment, mHomeFragment);
        	}else{
        		changeFragment(mHomeFragment);
        	}
            
        }else if (view == itemProfile){
        	if(mProfileFragment==null){
        		mProfileFragment=new ProfileFragment();
        		titleTextView.setText("分类笑话");
        		changeFragment(mProfileFragment);
        	}else{
        		changeFragment(mProfileFragment);	
        	}
            
        }else if (view == itemCalendar){
            changeFragment(new CalendarFragment());
        }else if (view == itemSettings){
            changeFragment(new SettingsFragment());
        }

        resideMenu.closeMenu();
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
            Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
            Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };

    private void changeFragment(Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    // What good method is to access resideMenu
    public ResideMenu getResideMenu(){
        return resideMenu;
    }
}
