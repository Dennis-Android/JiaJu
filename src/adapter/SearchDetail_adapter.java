package adapter;
 
import com.app.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import activity.GridView_DetailActivity;
import activity.Search_DetailActivity;
 
import android.content.Context;
 
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
import application.Myapplication;

public class SearchDetail_adapter extends BaseAdapter {
	 
	public Context context;
	 
	public SearchDetail_adapter(Context context) {
		 
		this.context=context;
	}
	
	@Override
	public int getCount() {
		 
		return Search_DetailActivity.nlist.size();
	}

	@Override
	public Object getItem(int position) {
		 
		return Search_DetailActivity.nlist.get(position);
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
        
	    if(Search_DetailActivity.nlist.get(position).SkuNo!=null){
	    	ImageLoader.getInstance().displayImage("http://www.jumeimiao.com/UsersData/"+Search_DetailActivity.nlist.get(position).Account+"/"+Search_DetailActivity.nlist.get(position).SkuNo+"/5.jpg", holder.fenlei_pull_listview_item_image,Myapplication.option);
	    }else{
	    	holder.fenlei_pull_listview_item_image.setImageBitmap(null);
	    }
		
		
	 	
		holder.fenlei_pull_listview_item_nameText.setText(Search_DetailActivity.nlist.get(position).PName);
		holder.fenlei_pull_listview_item_newprice.setText("￥"+Search_DetailActivity.nlist.get(position).SellPrice);
		holder.fenlei_pull_listview_item_oldprice.setText("￥"+Search_DetailActivity.nlist.get(position).MarketPrice);
		holder.fenlei_pull_listview_item_DiscountDese.setVisibility(View.GONE); 
		
		 if(Search_DetailActivity.nlist.get(position).Stock==""||Search_DetailActivity.nlist.get(position).Stock=="0"){
			 
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
