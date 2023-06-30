package com.ampesoftware.desserthome;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;

public class MainAboutActivity extends Activity {
	ImageButton btnsof;
	ImageButton btnos;
	ImageButton btnwebsite;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_about);
		btnsof=(ImageButton)findViewById(R.id.btnsof);
		btnos=(ImageButton)findViewById(R.id.btnosb);
		btnwebsite=(ImageButton)findViewById(R.id.btnwebsite);
		
		btnsof.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("bazaar://details?id=com.ampesoftware.secretoffitness"));
				startActivity(intent);				
			}
		});
		
		btnos.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("bazaar://collection?slug=by_author&aid=ami00254"));
				startActivity(intent);	
			}
		});
		
		btnwebsite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ampesoftware.com"));
				startActivity(browserIntent);				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_about, menu);
		return true;
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
        // action with ID action_refresh was selected
         case R.id.action_backabout:
        	  finish();
        	 break;
          default:
          break;
        }
    	return super.onOptionsItemSelected(item);
    }
	
	public void openlink() {
		String url = "http://www.ampesoftware.com";
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url)); 
		startActivity(i); 
		
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
