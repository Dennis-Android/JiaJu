package adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import util.AllUrl;



import com.app.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import domain.Shouye_list.Results3;

import android.app.Service;
import android.content.Context;
 
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import application.Myapplication;

public class Shouye_adapter extends BaseAdapter {
	public ArrayList<Results3> shouye_arraylist;
	public Context context;
	public long days ; //还有几天截至
	public Shouye_adapter(Context context, ArrayList<Results3> shouye_arraylist) {
		this.shouye_arraylist=shouye_arraylist;
		this.context=context;
	}

	@Override
	public int getCount() {
		 
		return shouye_arraylist.size();
	}

	@Override
	public Object getItem(int position) {
		 
		return shouye_arraylist.get(position);
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
			convertView=View.inflate(context, R.layout.shouye_list_item, null);
	 holder.shouye_list_item_image=(ImageView) convertView.findViewById(R.id.shouye_list_item_image);
	 holder.shouye_list_item_text1=(TextView) convertView.findViewById(R.id.shouye_list_item_text1);		
	 holder.shouye_list_item_text2=(TextView) convertView.findViewById(R.id.shouye_list_item_text2);		
	 holder.shouye_list_item_text3=(TextView) convertView.findViewById(R.id.shouye_list_item_text3);
	 holder.shouye_list_item_text4=(TextView) convertView.findViewById(R.id.shouye_list_item_text4);
	   convertView.setTag(holder);
	 
		}else{holder=(ViewHolder) convertView.getTag();}
       
		//计算还有多少天截至
				SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				String ToDay=format.format(System.currentTimeMillis());  			
			    String EdnDay= shouye_arraylist.get(position).EndTime;
				
				try {
					Date date1=format.parse(ToDay);
					Date date2=format.parse(EdnDay);		
					days = (date2.getTime()-date1.getTime()) / (1000 * 60 * 60 * 24);		    		 
				} catch (ParseException e) {			 
					e.printStackTrace();
				}
		
		ImageLoader.getInstance().displayImage(AllUrl.URL_GBase+shouye_arraylist.get(position).ActivityPic, holder.shouye_list_item_image,Myapplication.option);
		holder.shouye_list_item_text1.setText(shouye_arraylist.get(position).DesGuide);
		holder.shouye_list_item_text2.setText(shouye_arraylist.get(position).DiscountDes);
		holder.shouye_list_item_text3.setText(shouye_arraylist.get(position).ActiveName);
		holder.shouye_list_item_text4.setText("仅剩下"+String.valueOf(days)+"天");
		
		
		
		
		
		return convertView;
	}

	static class ViewHolder{
		public ImageView shouye_list_item_image;
		public TextView shouye_list_item_text1;
		public TextView shouye_list_item_text2;
		public TextView shouye_list_item_text3;
		public TextView shouye_list_item_text4;
		
		
		
	}
	
	
}
