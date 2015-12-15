package com.elicec.maincode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.special.ResideMenuDemo.R;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MySqlLiteDb {
	
	private static final String DATABASE_NAME = "joke.db";
	  private static final String DATABASE_PATH = "/data/data/com.special.ResideMenuDemo/databases";
	  private static final int DATABASE_VERSION = 0;
	  public static final String TAG = "tag_joke";
	  private static String outFileName = "/data/data/com.special.ResideMenuDemo/databases/joke.db";
	  private Context context;
	  private SQLiteDatabase database;
	  private int[] cursorPosition={0,0,0,0};
	  public MySqlLiteDb(Context paramContext)
	  {
		  this.context = paramContext;
			
			File file = new File(outFileName);
			if (file.exists()) {
				database = SQLiteDatabase.openOrCreateDatabase(outFileName, null);
				if (database.getVersion() != DATABASE_VERSION) {
					database.close();
					file.delete();	
				}
			}
			try {
				buildDatabase();
			} catch (Exception e) {
				e.printStackTrace();
			}
	  }

	  private void buildDatabase() throws Exception{
			InputStream myInput = context.getResources().openRawResource(R.raw.joke);
			File file = new File(outFileName);
			
			File dir = new File(DATABASE_PATH);
			if (!dir.exists()) {
				if (!dir.mkdir()) {
					throw new Exception("创建文件夹失败");
				}
			}
			
			if (!file.exists()) {			
				try {
					OutputStream myOutput = new FileOutputStream(outFileName);
					
					byte[] buffer = new byte[1024];
			    	int length;
			    	while ((length = myInput.read(buffer))>0){
			    		myOutput.write(buffer, 0, length);
			    	}
			    	myOutput.close();
			    	myInput.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			}
		}

	  private int update(String paramString1, String paramString2)
	  {
	    return 0;
	  }

	  public void close()
	  {
	    this.database.close();
	  }

	  public String getJokeContent()
	  {
	    Cursor localCursor = select();
	    Random localRandom = new Random();
	    boolean bool = true;
	    int i = localCursor.getCount();
	    if (!localCursor.moveToPosition(localRandom.nextInt(i)))
	      bool = localCursor.moveToFirst();
	    Log.i("tag_joke", "the number of joke is" + i);
	    if (bool)
	      return localCursor.getString(localCursor.getColumnIndex("CONTENT"));
	    return "error";
	  }

	  public long insert(String paramString1, String paramString2)
	  {
	    this.database = SQLiteDatabase.openOrCreateDatabase(outFileName, null);
	    ContentValues localContentValues = new ContentValues();
	    localContentValues.put("CONTENT", paramString1);
	    localContentValues.put("STYLES", paramString2);
	    return this.database.insert("myjoke", null, localContentValues);
	  }

	  public Cursor select()
	  {
	    this.database = SQLiteDatabase.openOrCreateDatabase(outFileName, null);
	    return this.database.rawQuery("select * from myjoke", null);
	  }
	  public Cursor[] selectByStyles(){
		 
		  this.database = SQLiteDatabase.openOrCreateDatabase(outFileName, null);
		  
		  Cursor[] c = new Cursor[]{
				  database.rawQuery("select * from myjoke where  STYLES='幽你一默'", null),
				  database.rawQuery("select * from myjoke where  STYLES='情感专区'", null),
				  database.rawQuery("select * from myjoke where  STYLES='色彩缤纷'", null),
				  database.rawQuery("select * from myjoke where  STYLES='人生哲理'", null),
		  };
//		  c[0]=database.rawQuery("select * from myjoke where  STYLES='幽你一默'", null);
//		  c[1]=database.rawQuery("select * from myjoke where  STYLES='情感专区'", null);
//		  c[2]=database.rawQuery("select * from myjoke where  STYLES='色彩缤纷'", null);
//		  c[3]=database.rawQuery("select * from myjoke where  STYLES='人生哲理'", null);
		  return c;
	  }
	  public ArrayList<List<HashMap<String ,String>>> getContentByStyles(int styles){
		  ArrayList<List<HashMap<String ,String>>> arrayList=new ArrayList<List<HashMap<String,String>>>();
		  //Cursor[]c=selectByStyles();
		  //database = SQLiteDatabase.openOrCreateDatabase(outFileName, null);
		  switch(styles){
		  case 0:{
			  Log.i(TAG, "cursorPosition幽默"+cursorPosition[0]);
			  String sql0="select * from myjoke where  STYLES='幽你一默' " +
				  		"order by CONTENT DESC limit 10 offset "+cursorPosition[0];
			  Cursor c0=database.rawQuery(sql0, null);
			  List<HashMap<String, String>> l0=new ArrayList<HashMap<String,String>>();
			  for(c0.moveToFirst();!c0.isAfterLast();c0.moveToNext()){
				  HashMap<String, String> hm=new HashMap<String, String>();
				  String temp=c0.getString(c0.getColumnIndex("CONTENT")); 
				  hm.put("content", temp);
				  l0.add(hm);
			  }
			  c0.close();
			  cursorPosition[0]+=10;
			  arrayList.add(l0);
			  break;
		  }
		  case 1:{
			  Log.i(TAG, "cursorPosition情感"+cursorPosition[1]);
			  String sql1="select * from myjoke where  STYLES='情感专区' " +
					  	"order by CONTENT DESC limit 10 offset "+cursorPosition[1];
			  Cursor c1=database.rawQuery(sql1, null);
			  List<HashMap<String, String>> l0=new ArrayList<HashMap<String,String>>();
			  for(c1.moveToFirst();!c1.isAfterLast();c1.moveToNext()){
				  HashMap<String, String> hm=new HashMap<String, String>();
				  String temp=c1.getString(c1.getColumnIndex("CONTENT")); 
				  hm.put("content", temp);
				  l0.add(hm);
			  }
			  c1.close();
			  cursorPosition[1]+=10;
			  arrayList.add(l0);
			  break;
			  
		  }
		  case 2:{
			  Log.i(TAG, "cursorPosition色彩"+cursorPosition[2]);
			  String sql2="select * from myjoke where  STYLES='色彩缤纷' " +
					  	"order by CONTENT DESC limit 10 offset "+cursorPosition[2];
			  Cursor c2=database.rawQuery(sql2, null);
			  List<HashMap<String, String>> l0=new ArrayList<HashMap<String,String>>();
			  for(c2.moveToFirst();!c2.isAfterLast();c2.moveToNext()){
				  HashMap<String, String> hm=new HashMap<String, String>();
				  String temp=c2.getString(c2.getColumnIndex("CONTENT")); 
				  hm.put("content", temp);
				  l0.add(hm);
			  }
			  c2.close();
			  cursorPosition[2]+=10;
			  arrayList.add(l0);
			  break;
		  }
		  case 3:{
			  String sql3="select * from myjoke where  STYLES='人生哲理' " +
					  	"order by CONTENT DESC limit 10 offset "+cursorPosition[3];
			  Cursor c3=database.rawQuery(sql3, null);
			  List<HashMap<String, String>> l0=new ArrayList<HashMap<String,String>>();
			  for(c3.moveToFirst();!c3.isAfterLast();c3.moveToNext()){
				  HashMap<String, String> hm=new HashMap<String, String>();
				  String temp=c3.getString(c3.getColumnIndex("CONTENT")); 
				  hm.put("content", temp);
				  l0.add(hm);
			  }
			  c3.close();
			  cursorPosition[3]+=10;
			  arrayList.add(l0);
			  break;
		  }
		  }
		  return arrayList;
	  }
}
