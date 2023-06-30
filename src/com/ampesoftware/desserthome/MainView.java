package com.ampesoftware.desserthome;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MainView extends Activity {
	TextView txtname;
	TextView txtrecipe;
	CheckBox chk;
    ImageView imageview;
    ImageButton imgplus;
    ImageButton imgmines;
	dbhelper dbh=new dbhelper(this);
	String sharestr;
	int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_view);
		
		txtname=(TextView)findViewById(R.id.txtname);
		txtrecipe=(TextView)findViewById(R.id.txtrecipe);
		txtname.setTextSize(MainApplicationMenu.fontsize);
		txtrecipe.setTextSize(MainApplicationMenu.fontsize);
		imgplus=(ImageButton)findViewById(R.id.btnplus);
		imgmines=(ImageButton)findViewById(R.id.btnmines);
		chk=(CheckBox)findViewById(R.id.chkfav);
		
		String imagename;

		txtrecipe.setMovementMethod(new ScrollingMovementMethod());
		txtrecipe.setTextSize(MainApplicationMenu.fontsize);
		txtname.setTextSize(MainApplicationMenu.fontsize);
		Bundle b=getIntent().getExtras();
		position=b.getInt("Pos");
		int favorite;
		String str;
		int res;
		imageview= (ImageView)findViewById(R.id.imgview);
		dbh.openDataBase();
		txtname.setText(dbh.getdatabyid(position,1));
		str=dbh.getdatabyid(position,2);
		txtrecipe.setText(str);
		sharestr="";
		sharestr=dbh.getdatabyid(position,1)+"\n"+str;
		favorite=Integer.parseInt(dbh.getdatabyid(position,4));
		
        imagename = "f"+position;
        res = getResources().getIdentifier(imagename, "drawable", this.getPackageName());        
        imageview.setImageResource(res);

				if(favorite==0){
					chk.setChecked(false);
				}else{
					chk.setChecked(true);
				}
				dbh.close();
				switch (MainApplicationMenu.whichpage) {
				case 1:
					getActionBar().setTitle("دسرهای سنتی");
					break;
				case 2:
					getActionBar().setTitle("کیک ها");
					break;
				case 3:
					getActionBar().setTitle("دسرهای مدرن");
					break;
				case 4:
					getActionBar().setTitle("حلواها");
					break;
				case 5:
					getActionBar().setTitle("دونات ها");
					break;

				default:
					break;
				}
				
		
		imgplus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(MainApplicationMenu.fontsize<40){
					MainApplicationMenu.fontsize+=1;
				txtrecipe.setTextSize(MainApplicationMenu.fontsize);
				txtname.setTextSize(MainApplicationMenu.fontsize);
				}
			}
		});
		
		imgmines.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(MainApplicationMenu.fontsize>8){
					MainApplicationMenu.fontsize-=1;
				txtrecipe.setTextSize(MainApplicationMenu.fontsize);
				txtname.setTextSize(MainApplicationMenu.fontsize);
				}
			}
		});
		
		chk.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
						if(chk.isChecked()){
							dbh.openDataBase();
							dbh.favset(1,position);
							dbh.close();
						}if(!chk.isChecked()){
							dbh.openDataBase();
							dbh.favset(0,position);
							dbh.close();
						}
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_view, menu);
		return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
        // action with ID action_refresh was selected
    	case R.id.action_sharerecepie:
 	        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
 	        sharingIntent.setType("text/plain");
 	        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "خانه دسر");
 	        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, sharestr);
 	        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.hello_world)));
 	        break;
        case R.id.action_mainviewback:
    		onBackPressed();			
            break;
          default:
          break;
        }

    	return super.onOptionsItemSelected(item);
    }
	@Override
	public void onBackPressed() {
		Bundle b=getIntent().getExtras();
		String ac=b.getString("Activityname");
		if(ac.equals("listitem")){
			Intent intf =new Intent(MainView.this,MainItemsList.class);
			startActivity(intf);
			finish();
		}else{
			Intent intf =new Intent(MainView.this,MainFavItemsList.class);
			startActivity(intf);
			finish();
		}


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
