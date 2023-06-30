package com.ampesoftware.desserthome;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class MainFavItemsList extends Activity {
	ListView lstfav;
	dbhelper dbh=new dbhelper(this);
	public static int[] recortnumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try{
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_fav_items_list);
		
		lstfav=(ListView)findViewById(R.id.favlist);
		refreshfavorite(MainApplicationMenu.whichpage);
						
		lstfav.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
							Intent inte =new Intent(MainFavItemsList.this,MainView.class);
							inte.putExtra("Pos", recortnumber[arg2]);
							inte.putExtra("Activityname", "Favorite");
							startActivity(inte);
							finish();
			}
		});
		}catch (Exception e) {
			onBackPressed();
		}
		


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_fav_items_list, menu);
		return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
        // action with ID action_refresh was selected
        case R.id.action_listfavback:
        	onBackPressed();
            break;
          default:
          break;
        }

    	return super.onOptionsItemSelected(item);
    }
	public void refreshfavorite(int grp){
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> listfav = new ArrayList<String>();
		ArrayList<String> listinx = new ArrayList<String>();
        MyCustomAdapter adapter = new MyCustomAdapter(list,listfav,listinx, this);
        dbh.openDataBase();
		int i=dbh.countfavitem(grp);
		recortnumber=new int[i];
		for(int j=0;j<i;j++){
			list.add(dbh.favitems(1, j, grp).toString());
			listfav.add(dbh.favitems(4, j, grp).toString());
			listinx.add(dbh.favitems(0, j, grp).toString());
			recortnumber[j]=Integer.parseInt(dbh.favitems(0, j, grp).toString());
    	}
		dbh.close();
		lstfav.setAdapter(adapter);
    }

@Override
public void onBackPressed() {
	Intent intf =new Intent(MainFavItemsList.this,MainItemsList.class);
	startActivity(intf);
	finish();
}


}
