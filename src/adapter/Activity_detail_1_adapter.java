package adapter;

import java.util.ArrayList;


import com.app.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import domain.Deatail_1_list.Results4;
 

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import application.Myapplication;

public class Activity_detail_1_adapter extends BaseAdapter {
    
	public ArrayList<Results4> detail_results_list;
	public Context context;
	public Activity_detail_1_adapter(ArrayList<Results4> detail_results_list, Context context){
		this.detail_results_list=detail_results_list;
		this.context=context;
    }
	
	
	
	
	
	@Override
	public int getCount() {
		 
		return detail_results_list.size();
	}

	@Override
	public Object getItem(int position) {
		 
		return detail_results_list.get(position);
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
			convertView=View.inflate(context, R.layout.activity_detail_1_item, null);
			holder.detail_1_list_item_DiscountDes=(TextView) convertView.findViewById(R.id.detail_1_list_item_DiscountDes);
			holder.detail_1_list_item_Newprice=(TextView) convertView.findViewById(R.id.detail_1_list_item_Newprice);
			holder.detail_1_list_item_Oldprice=(TextView) convertView.findViewById(R.id.detail_1_list_item_Oldprice);
			holder.detail_1_list_item_PName=(TextView) convertView.findViewById(R.id.detail_1_list_item_PName);
		
			holder.detail_1_list_item_image=(ImageView) convertView.findViewById(R.id.detail_1_list_item_image);
			holder.detail_1_list_item_qiangguang=(ImageView) convertView.findViewById(R.id.detail_1_list_item_qiangguang);
		
			convertView.setTag(holder);
		}else{holder=(ViewHolder) convertView.getTag(); }
		holder.detail_1_list_item_DiscountDes.setText(detail_results_list.get(position).DiscountDes+"折");
		holder.detail_1_list_item_Newprice.setText("￥"+detail_results_list.get(position).SellPrice);
		holder.detail_1_list_item_Oldprice.setText("￥"+detail_results_list.get(position).MarketPrice);
		holder.detail_1_list_item_PName.setText(detail_results_list.get(position).PName);
		
		ImageLoader.getInstance().displayImage("http://www.jumeimiao.com/UsersData/"+detail_results_list.get(position).Account+"/"+detail_results_list.get(position).SkuNo+"/5.jpg"  , holder.detail_1_list_item_image, Myapplication.option);
		
		if(detail_results_list.get(position).Stock==0){
			 
			holder.detail_1_list_item_qiangguang.setVisibility(View.VISIBLE);
		}
		
		
		
		return convertView ;
	}
 
	
	
	 static class ViewHolder{
		public  ImageView  detail_1_list_item_image;
		public  ImageView  detail_1_list_item_qiangguang; 
		public  TextView detail_1_list_item_PName;
		 
		public  TextView detail_1_list_item_Newprice;
		public  TextView detail_1_list_item_Oldprice;
		public  TextView detail_1_list_item_DiscountDes;
	 
	 }
	
	
	
	
	
	
	
	
}
