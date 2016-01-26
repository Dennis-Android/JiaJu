package adapter;

import java.util.ArrayList;


import com.app.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import domain.Fenlei.Results5;

import fragment.Fragment2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import application.Myapplication;

public class GridviewAdapter extends BaseAdapter {
	   public Context context;
	  
	  
	   public GridviewAdapter(Context context){
			this.context=context;
 	 
	    }
	 
	  
	  
	  
	 
	     
	   
	   
	   @Override
	public int getCount() {
		 
		return  Fragment2.Gridview_list.size();
	}

	@Override
	public Object getItem(int position) {
		 
		return Fragment2.Gridview_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		 
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			holder=new ViewHolder();	
		    convertView=View.inflate(context, R.layout.gridview_item, null);
			holder.grid_image=(ImageView) convertView.findViewById(R.id.grid_image);	
			holder.grid_text=(TextView) convertView.findViewById(R.id.grid_text);
   		  convertView.setTag(holder);
   	      }else{			
			holder=(ViewHolder) convertView.getTag();	 			
		  }
	
		 ImageLoader.getInstance().displayImage(Fragment2.Gridview_list.get(position).CategoryIcon, holder.grid_image, Myapplication.option);
		 holder.grid_text.setText(Fragment2.Gridview_list.get(position).CategoryName);
	 
		
		return convertView;
	}
       
    static class ViewHolder{
    	
    	public ImageView grid_image;
    	public TextView grid_text;
     
    	
    }
	
}
