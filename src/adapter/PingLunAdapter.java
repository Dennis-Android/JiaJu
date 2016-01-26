package adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



import com.app.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import domain.Find.Results8;
import domain.PingLun.Results9;

import fragment.Fragment3;

import view.CircleImageView;
import activity.PingLunActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.sax.StartElementListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import application.Myapplication;

public class PingLunAdapter extends BaseAdapter   {

	public Bitmap bitma;
 
	public Context context;
	public ViewHolder holder;
	
 
	public ArrayList<Results9> resultlist;
	public PingLunAdapter (Context context, ArrayList<Results9> resultlist){
		this.context=context;
		this.resultlist=resultlist;
		 

	}
	 
	 



	@Override
	public int getCount() {
		 
		return  resultlist.size();
	}

	@Override
	public Object getItem(int position) {
		 
		return  resultlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		 
		return position;
	}

	@Override
	public View getView( final int position,   View convertView, ViewGroup parent) {
		 
		if(convertView==null){
			
			holder=new ViewHolder();
			convertView=View.inflate(context, R.layout.listview_comment_item, null);
 
 holder.listview_comment_item_photo=(CircleImageView) convertView.findViewById(R.id.listview_comment_item_photo);	
 holder.listview_comment_item_NickName=(TextView) convertView.findViewById(R.id.listview_comment_item_NickName);				
 
 holder.listview_comment_item_Content=(TextView) convertView.findViewById(R.id.listview_comment_item_Content);				
 holder.listview_comment_item_time=(TextView) convertView.findViewById(R.id.listview_comment_item_time);						
   
		convertView.setTag(holder);
		
	 
		}else{
			holder=(ViewHolder) convertView.getTag(); 
  	     }
	
	 //头像 
		if( resultlist.get(position).Photo!=""){
			ImageLoader.getInstance().displayImage(resultlist.get(position).Photo, holder.listview_comment_item_photo, Myapplication.option);
		}else{
			
			 bitma=BitmapFactory.decodeResource(context.getResources(),R.drawable.tab_fifth_1);
			 holder.listview_comment_item_photo.setImageBitmap(bitma);	
		}
		 
		
      holder.listview_comment_item_NickName.setText( resultlist.get(position).NickName);	
 
      holder.listview_comment_item_Content.setText( resultlist.get(position).Content);	
		
      holder.listview_comment_item_time.setText( resultlist.get(position).ReviewTime);	
	
 
      return convertView;
	}
 
	
	
	
	static class ViewHolder{
		public CircleImageView  listview_comment_item_photo;  //头像
		public TextView   listview_comment_item_NickName;       //用户名 
		 
		public TextView   listview_comment_item_Content;         // <!--内容  -->
		   
		public TextView   listview_comment_item_time;
	
	}



 
 
		
	}
	
	
	
 
	
	
	
 
