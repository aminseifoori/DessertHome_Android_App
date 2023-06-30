package com.ampesoftware.desserthome;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MyCustomAdapter extends BaseAdapter implements ListAdapter {

   	private ArrayList<String> list = new ArrayList<String>(); 
   	private ArrayList<String> listfav=new ArrayList<String>();
   	private ArrayList<String> listinx=new ArrayList<String>();
	private Context context; 



	public MyCustomAdapter(ArrayList<String> list, ArrayList<String> listfav,ArrayList<String> listinx,Context context) { 
	    this.list = list; 
	    this.listfav=listfav;
	    this.listinx=listinx;
	    this.context = context; 
	} 

	@Override
	public int getCount() { 
	    return list.size(); 
	} 

	@Override
	public Object getItem(int pos) { 
	    return list.get(pos); 
	} 

	public String getItemstring(int pos) { 
	    return list.get(pos).toString(); 
	}
	
	public String getfavsit(int pos){
		return listfav.get(pos).toString();
	}


	@Override
    public View getView(final int position, View convertView, ViewGroup parent) {
    View view = convertView;
    if (view == null) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
        view = inflater.inflate(R.layout.item_list_view, null);
     } 

    //Handle TextView and display string from your list
    TextView listItemText = (TextView)view.findViewById(R.id.txt_name); 
    listItemText.setText(list.get(position)); 
    
    final CheckBox chb=(CheckBox)view.findViewById(R.id.chkfav);
    
    if(Integer.parseInt(listfav.get(position).toString())==0){
    	chb.setChecked(false);
    }else{
    	chb.setChecked(true);
    }
    
    chb.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
				if(chb.isChecked()){
					dbhelper dbh = new dbhelper(context);
					dbh.openDataBase();
					dbh.favset(1,Integer.parseInt(listinx.get(position)));
					dbh.close();
				}if(!chb.isChecked()){
					dbhelper dbh = new dbhelper(context);
					dbh.openDataBase();
					dbh.favset(0,Integer.parseInt(listinx.get(position)));
					dbh.close();
				}
		}
	});
    chb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		public void onCheckedChanged(CompoundButton arg0, boolean arg1) {

		}
	});
    
    return view; 
}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	} 
}
