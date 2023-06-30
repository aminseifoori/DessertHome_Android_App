package com.ampesoftware.desserthome;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

public class MainItemsList extends Activity {
	dbhelper dbh;
	ListView lst;
	ImageButton btnsearch;
	EditText txtsearch;
	int searchflag;
	public static int[] recortnumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_items_list);
		dbh=new dbhelper(this);
		lst=(ListView)findViewById(R.id.list1);
		lst.setItemsCanFocus(false);
		txtsearch=(EditText)findViewById(R.id.txtsearch);
		btnsearch=(ImageButton)findViewById(R.id.btnsearch);
		
		txtsearch.setTextSize(MainApplicationMenu.deffontsize);		
		searchflag=0;
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
		switch(MainApplicationMenu.whichpage)
		{
			case 1:
				getActionBar().setTitle("دسرهای سنتی");
				refresh(MainApplicationMenu.whichpage);
				break;
			case 2:
				getActionBar().setTitle("کیک ها");
				refresh(MainApplicationMenu.whichpage);
				break;
			case 3:
				getActionBar().setTitle("دسرهای مدرن");
				refresh(MainApplicationMenu.whichpage);
				break;
			case 4:
				getActionBar().setTitle("حلوا ها");
				refresh(MainApplicationMenu.whichpage);
				break;
			case 5:
				getActionBar().setTitle("دونات ها");
				refresh(MainApplicationMenu.whichpage);
				break;
		}
		
		
		lst.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
							Intent inte =new Intent(MainItemsList.this,MainView.class);
							inte.putExtra("Pos", recortnumber[arg2]);
							inte.putExtra("Activityname", "listitem");
							finish();
							startActivity(inte);
							
			}
		});
		
		
		txtsearch.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
				String str=txtsearch.getText().toString();
				if(str.equals("")){
					if(searchflag==1){
						searchflag=0;
						refresh(MainApplicationMenu.whichpage);
					}
					}else{
					searchflag=1;	
						refreshsearch(MainApplicationMenu.whichpage);
				}
				return false;
			}
		});
		
		btnsearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String str=txtsearch.getText().toString();
				if(str.equals("")){
					if(searchflag==1){
						searchflag=0;
						refresh(MainApplicationMenu.whichpage);
					}
					}else{
					searchflag=1;	
						refreshsearch(MainApplicationMenu.whichpage);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_items_list, menu);
		return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
        // action with ID action_refresh was selected
    	case R.id.action_favlist:
			Intent intf =new Intent(MainItemsList.this,MainFavItemsList.class);
			startActivity(intf);
    		finish();
        case R.id.action_listback:
        	 	finish();
            break;
          default:
          break;
        }

    	return super.onOptionsItemSelected(item);
    }
	

    public void refresh(int grp){
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> listfav = new ArrayList<String>();
		ArrayList<String> listinx = new ArrayList<String>();
        MyCustomAdapter adapter = new MyCustomAdapter(list,listfav,listinx, this);
        dbh.openDataBase();
		int i=dbh.countlistitems(grp);
		recortnumber=new int[i];
		for(int j=0;j<i;j++){
			list.add(dbh.listitems(1,j,grp).toString());
			listfav.add(dbh.listitems(4,j,grp).toString());
			listinx.add(dbh.listitems(0,j,grp).toString());
			recortnumber[j]=Integer.parseInt(dbh.listitems(0,j,grp).toString());
    	}
		dbh.close();
		lst.setAdapter(adapter);
    }
 
    public void refreshsearch(int grp){
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> listfav = new ArrayList<String>();
		ArrayList<String> listinx = new ArrayList<String>();
        MyCustomAdapter adapter = new MyCustomAdapter(list,listfav,listinx, this);
        String con=txtsearch.getText().toString();
        dbh.openDataBase();
		int i=dbh.countserachitems(con,grp);
		recortnumber=new int[i];
		for(int j=0;j<i;j++){
			list.add(dbh.searchitems(j, 1,con,grp).toString());
			listfav.add(dbh.searchitems(j, 4,con,grp).toString());
			listinx.add(dbh.searchitems(j, 0,con,grp).toString());
			recortnumber[j]=Integer.parseInt(dbh.searchitems(j, 0,con,grp).toString());
    	}
		dbh.close();
		lst.setAdapter(adapter);
    }
    
}
