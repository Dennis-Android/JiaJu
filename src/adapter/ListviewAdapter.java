package adapter;

import java.util.ArrayList;


import com.app.R;

import domain.Fenlei.Results5;

import fragment.Fragment2;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
                         
public class ListviewAdapter extends BaseAdapter {
    public Context context;
    public ArrayList<Results5> listview_list;
    
    
    public TextView name_text;
    
    
    
    public ListviewAdapter(Context context , ArrayList<Results5> listview_list){
		this.context=context;
		this.listview_list=listview_list;
		 
    }
	
	
	 
	
	
	@Override
	public int getCount() {
		 
		return listview_list.size();
	}

	@Override
	public Object getItem(int position) {
		 
		return listview_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		 
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 
		convertView=View.inflate(context, R.layout.listview_list_item, null); 
		name_text=(TextView) convertView.findViewById(R.id.name_text);
		name_text.setText(listview_list.get(position).CategoryName);
	 
		
	 	
	 if(position==Fragment2.xposition){
		 convertView.setBackgroundColor(Color.WHITE);
		 name_text.setTextColor(Color.RED);
	 }else{
		 name_text.setTextColor(Color.BLACK); 
		 convertView.setBackgroundColor(Color.parseColor("#FEFAEF"));
	 } 
		
		
		
		return convertView;
	}
   
 
	
	
}
