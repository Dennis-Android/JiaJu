package adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.PingLunData;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;
 

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.app.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import domain.Find.Results8;
 
import util.AllUrl;
import view.CircleImageView;
 
import activity.DetailActivity_2;
import activity.LoginActivity;
import activity.PingLunActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.sax.StartElementListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
 
import application.MySingle;
import application.Myapplication;

public class FindAdapter extends BaseAdapter   {

	public Bitmap bitma;
	public Bitmap bitma1;
	public Bitmap bitma2;       
	
	public Context context;
	public ViewHolder holder;
	
	public static  Map<Integer, Boolean> map;   //保存选中按钮状态的map
  
	public String	BaskOrderId;
	public List<PingLunData>  pinglunlist ;
	
    public RequestQueue queue;
	
    public SharedPreferences sp;
    
    public String url1;
    public String url2;
    
    public String  UserId;
    
	public StringRequest request;
	 
	public PingLunData pinglundata ;
	
	public ArrayList<Results8> resultlist;
	public FindAdapter (Context context, ArrayList<Results8> resultlist){
		this.context=context;
		this.resultlist=resultlist;
		sp=context.getSharedPreferences("LoginData",context.MODE_PRIVATE);
		queue=MySingle.getInstance(context).getRequestQueue();
		init();   //初始化
	    
	}
	 
	
	
	
	private void init() {
		map=new HashMap<Integer, Boolean>();
		for(int i=0;i<resultlist.size();i++){
			map.put(i, false);
		}
		
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
			convertView=View.inflate(context, R.layout.find_pull_listview_item, null);
 holder.fenlei_pull_listview_item_photo=(CircleImageView) convertView.findViewById(R.id.fenlei_pull_listview_item_photo);	
 holder.fenlei_pull_listview_item_NickName=(TextView) convertView.findViewById(R.id.fenlei_pull_listview_item_NickName);				
 holder.fenlei_pull_listview_item_ImageUrl=(ImageView) convertView.findViewById(R.id.fenlei_pull_listview_item_ImageUrl);				
 holder.fenlei_pull_listview_item_Content=(TextView) convertView.findViewById(R.id.fenlei_pull_listview_item_Content);				
 holder.zan=(ImageView) convertView.findViewById(R.id.zan);				
 holder.fenlei_pull_listview_item_SomePraiseCount=(TextView) convertView.findViewById(R.id.fenlei_pull_listview_item_SomePraiseCount);	
 holder.fenlei_pull_listview_item_ReviewCount=(TextView) convertView.findViewById(R.id.fenlei_pull_listview_item_ReviewCount);			
 holder.pinglun=(ImageView) convertView.findViewById(R.id.pinglun);			
 holder.fenxiang=(ImageView) convertView.findViewById(R.id.fenxiang);			
  
		
		convertView.setTag(holder);
		
	 
		}else{
			holder=(ViewHolder) convertView.getTag(); 
  	     }
	
	 //头像 
		if( resultlist.get(position).Photo!=""){
			ImageLoader.getInstance().displayImage(resultlist.get(position).Photo, holder.fenlei_pull_listview_item_photo, Myapplication.option);
		}else{
			
			 bitma=BitmapFactory.decodeResource(context.getResources(),R.drawable.tab_fifth_1);
			 holder.fenlei_pull_listview_item_photo.setImageBitmap(bitma);	
		}
		 
		
      holder.fenlei_pull_listview_item_NickName.setText( resultlist.get(position).NickName);	
		
      ImageLoader.getInstance().displayImage( resultlist.get(position).ImageUrl, holder.fenlei_pull_listview_item_ImageUrl, Myapplication.option);	
		
     holder.fenlei_pull_listview_item_Content.setText( resultlist.get(position).Content);	
		
     holder.fenlei_pull_listview_item_SomePraiseCount.setText(String.valueOf( resultlist.get(position).SomePraiseCount)+"赞");		
     holder.fenlei_pull_listview_item_ReviewCount.setText(String.valueOf( resultlist.get(position).ReviewCount));			

  
 	
     bitma2=BitmapFactory.decodeResource(context.getResources(),R.drawable.zan2);
 	 bitma1=BitmapFactory.decodeResource(context.getResources(),R.drawable.zan1);
    
 	 UserId=sp.getString("Id", null);
  
 	 if(map.get(position)==true){
    	 holder.zan.setImageBitmap(bitma2);
          
    	 holder.fenlei_pull_listview_item_SomePraiseCount.setText(String.valueOf( resultlist.get(position).SomePraiseCount+1)+"赞");
    	 
 	 }else{
 		 holder.zan.setImageBitmap(bitma1); 
  	 }
     
     	
      //点赞
     holder.zan.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			 int v1=v.getId();
			if(v1==holder.zan.getId()){
				 if(UserId==null){
			 		context.startActivity(new Intent(context,LoginActivity.class));}
				     
				 else{
		 		 if(map.get(position)==false){
					map.put(position, true);
					//点赞扬
					 BaskOrderId=resultlist.get(position).Id;
  			         url1=AllUrl.URL_Base+"/u.ashx?m=Zan&BaskOrderId="+BaskOrderId+"&UserId="+UserId; 
				 	 url2=AllUrl.URL_Base+"/u.ashx?m=CancelZan&BaskOrderId="+BaskOrderId+"&UserId="+UserId;
	                 holder.zan.setImageBitmap(bitma2);
	                 getZanData(url1);
 
                             
              
				}else{
					map.put(position, false);
 		 	       
					holder.zan.setImageBitmap(bitma1);
					
		 			getZanData(url2);
					 
                    }
			 	notifyDataSetChanged();
			}
 		 
		}

		}
	  });
    
    
       holder.pinglun.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			 
			Intent intent=new Intent(context,PingLunActivity.class);
            intent.putExtra("Id", resultlist.get(position).Id);
			context.startActivity(intent);
		}
  	 
 	  });
     
       //点击图片
       holder.fenlei_pull_listview_item_ImageUrl.setOnClickListener(new OnClickListener() {

   		@Override
   		public void onClick(View v) {
   			 
   			Intent intent=new Intent(context,DetailActivity_2.class);
            intent.putExtra("ActivityShare", resultlist.get(position).ActivityShare);
   			context.startActivity(intent);
   		
   		}
     	 
    	  });
     
     
      
       holder.fenxiang.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			showShare();
 		}
	});
     
 
	
 
      return convertView;
	}
 
	
	
	
	static class ViewHolder{
		public CircleImageView  fenlei_pull_listview_item_photo;  //头像
		public TextView fenlei_pull_listview_item_NickName;       //用户名 
		public ImageView fenlei_pull_listview_item_ImageUrl;     // <!--中间图片 -->
		public TextView fenlei_pull_listview_item_Content;         // <!--内容  -->
		public ImageView zan;                                      // <!--赞  -->   
		public TextView fenlei_pull_listview_item_SomePraiseCount; //  <!--赞数 -->
       	public TextView fenlei_pull_listview_item_ReviewCount;     // <!--评论数 -->
 	    public ImageView pinglun;                                  //评论图片
		public ImageView fenxiang;                                 //分享图片
	
	
	}


	 
        private void getZanData(String url) {
		 request=new StringRequest(url, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				 
				
			}
		}, null);
			
		 queue.add(request);
          
        }
 
 
        
        //分享
   	 private void showShare() {
   		 ShareSDK.initSDK(context);
   		 OnekeyShare oks = new OnekeyShare();
   		 //关闭sso授权
   		 oks.disableSSOWhenAuthorize(); 

   		// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
   		 //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
   		 // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
   		 oks.setTitle(context.getString(R.string.ssdk_oks_share));
   		 // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
   		 oks.setTitleUrl("http://sharesdk.cn");
   		 // text是分享文本，所有平台都需要这个字段
   		 oks.setText("我是分享文本");
   		 // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
   		 oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
   		 // url仅在微信（包括好友和朋友圈）中使用
   		 oks.setUrl("http://sharesdk.cn");
   		 // comment是我对这条分享的评论，仅在人人网和QQ空间使用
   		 oks.setComment("我是测试评论文本");
   		 // site是分享此内容的网站名称，仅在QQ空间使用
   		 oks.setSite(context.getString(R.string.app_name));
   		 // siteUrl是分享此内容的网站地址，仅在QQ空间使用
   		 oks.setSiteUrl("http://sharesdk.cn");

   		// 启动分享GUI
   		 oks.show(context);
   		 }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
		
	}
	
	
	
 
	
	
	
 
