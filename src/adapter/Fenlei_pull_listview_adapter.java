package adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import util.AllUrl;



import com.app.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import domain.Shouye_list.Results3;

import activity.GridView_DetailActivity;
import android.app.Service;
import android.content.Context;
 
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import application.Myapplication;

public class Fenlei_pull_listview_adapter extends BaseAdapter {
	 
	public Context context;
	 
	public Fenlei_pull_listview_adapter(Context context) {
		 
		this.context=context;
	}
	
	@Override
	public int getCount() {
		 
		return GridView_DetailActivity.mlist.size();
	}

	@Override
	public Object getItem(int position) {
		 
		return GridView_DetailActivity.mlist.get(position);
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
			convertView=View.inflate(context, R.layout.fenlei_pull_listview_item, null);
	 holder.fenlei_pull_listview_item_image=(ImageView) convertView.findViewById(R.id.fenlei_pull_listview_item_image);
	 holder.fenlei_pull_listview_item_nameText=(TextView) convertView.findViewById(R.id.fenlei_pull_listview_item_nameText);		
	 holder.fenlei_pull_listview_item_newprice=(TextView) convertView.findViewById(R.id.fenlei_pull_listview_item_newprice);		
	 holder.fenlei_pull_listview_item_oldprice=(TextView) convertView.findViewById(R.id.fenlei_pull_listview_item_oldprice);
	 holder.fenlei_pull_listview_item_DiscountDese=(TextView) convertView.findViewById(R.id.fenlei_pull_listview_item_DiscountDese);
	 holder.fenlei_pull_listview_item_qiangguang=(ImageView) convertView.findViewById(R.id.fenlei_pull_listview_item_qiangguang); 
	 
	 convertView.setTag(holder);
	 
		}else{holder=(ViewHolder) convertView.getTag();}
        
	 
		ImageLoader.getInstance().displayImage("http://www.jumeimiao.com/UsersData/"+GridView_DetailActivity.mlist.get(position).Account+"/"+GridView_DetailActivity.mlist.get(position).SkuNo+"/5.jpg", holder.fenlei_pull_listview_item_image,Myapplication.option);
		holder.fenlei_pull_listview_item_nameText.setText(GridView_DetailActivity.mlist.get(position).PName);
		holder.fenlei_pull_listview_item_newprice.setText("￥"+GridView_DetailActivity.mlist.get(position).SellPrice);
		holder.fenlei_pull_listview_item_oldprice.setText("￥"+GridView_DetailActivity.mlist.get(position).MarketPrice);
		holder.fenlei_pull_listview_item_DiscountDese.setText(GridView_DetailActivity.mlist.get(position).DiscountDes+"折");
		
		if(GridView_DetailActivity.mlist.get(position).Stock==0){
			 
			holder.fenlei_pull_listview_item_qiangguang.setVisibility(View.VISIBLE);
		}
		
	   
		
		return convertView;
	}

	static class ViewHolder{
		public ImageView fenlei_pull_listview_item_image;    
		public TextView fenlei_pull_listview_item_nameText;         //名称
		public TextView fenlei_pull_listview_item_newprice;         //新价
		public TextView fenlei_pull_listview_item_oldprice;         //原价
		public TextView fenlei_pull_listview_item_DiscountDese;     //折扣
		public ImageView fenlei_pull_listview_item_qiangguang;
		
		
	}
	
	
}
