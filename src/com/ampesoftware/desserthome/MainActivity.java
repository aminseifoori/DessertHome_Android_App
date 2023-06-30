package com.ampesoftware.desserthome;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		final dbhelper myDbHelper = new dbhelper(this);
		Thread tr=new Thread(){
        	@Override
        	public void run(){
        		try{
        	        try {
        	        	myDbHelper.createDataBase();
        	        } catch (IOException ioe) {
        	        throw new Error("Unable to create database");
        	        }
        	        myDbHelper.openDataBase();
        	        myDbHelper.close();
        		}catch (SQLException e) {
        			 e.getStackTrace();
				}finally{
					Intent in=new Intent(MainActivity.this,MainApplicationMenu.class);
					startActivity(in);
					finish();
				}
        	}
        };
        tr.start();
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
        
    }
    @Override
    protected void onDestroy() {
    super.onDestroy();

    unbindDrawables(findViewById(R.id.RootView));
    System.gc();
    }

    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
        view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
            unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
        ((ViewGroup) view).removeAllViews();
        }
    }
    
}
