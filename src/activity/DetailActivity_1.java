package activity;

import java.util.ArrayList;

import util.AllUrl;
 


import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.app.R;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.nostra13.universalimageloader.core.ImageLoader;

import domain.Deatail_1_list;
import domain.Detail_1;
import domain.Deatail_1_list.Results4;

import adapter.Activity_detail_1_adapter;
 
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
 
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
 
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
 
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import application.MySingle;
import application.Myapplication;

public class DetailActivity_1 extends BaseActivity implements OnClickListener {
	 
	public RadioButton radio1;
	public RadioButton radio2;
	public RadioButton radio3;
	public RadioButton radio4;
	public ImageView radio4_image;
	public Bitmap bitmap1;
	public Bitmap bitmap2;
	public Bitmap bitmap3;
	public boolean flag=true;
	public TextView activityname_text;   //顶部的跑马灯
	
	public PullToRefreshGridView detail_pull_gradview;
	
	public View header;
	public TextView detai_1_header_text;   //中间的文字介绍
	public ImageView detai_1_header_image;//顶部图片
	public Activity_detail_1_adapter detail_1_adapter;
    public RequestQueue queue; 
    public Gson gson=new Gson();  
	private StringRequest request1;      //网络请求
	private StringRequest request2;      //网络请求
	public String account;
	// public String detail_imageurl; 
	public Context context;
	public Detail_1 detail_1 ;
    public String ActiveName;    //顶部跑马灯文字
	 
	 public Deatail_1_list detail_1_list;
	 public ArrayList<Results4>  detail_results_list;
	
	private Handler handler=new Handler();
	 
	 public String GiveUrl;    
	 
	 public String MorenUrl;   //默认
	 public String ZuixinUrl;   //最新	 
	 public String RexiaoUrl;   //热销
	 public String DijiaUrl;    //低价
	 public String GaojiaUrl;    //高价 
	 
	 public ImageView back_image;
	 public ImageButton share_image;
	 @Override
    
	public  void onCreate(Bundle savedInstanceState) {
    	 
     	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_1);
        context=this;
        
      //检查网络
   	 checkNet();
        
        activityname_text=(TextView) findViewById(R.id.activityname_text);
        back_image=(ImageView) findViewById(R.id.back_image);
        
        share_image=(ImageButton) findViewById(R.id.share_image);
        
        //gridview
        detail_pull_gradview=(PullToRefreshGridView) findViewById(R.id.detail_pull_gradview);
        detail_pull_gradview.setMode(Mode.PULL_FROM_START); //仅仅支持下拉刷新 
        
               	        	        
       
    
       
       //传递过来的account 
       account= getIntent().getStringExtra("account"); 
        
       //五个url
       MorenUrl=AllUrl.URL_Base +"/p.ashx?m=getProductList&id="+account+"&sort=0&pageNum=1&pageSize=20";
       ZuixinUrl=AllUrl.URL_Base +"/p.ashx?m=getProductList&id="+account+"&sort=1&pageNum=1&pageSize=20";
       RexiaoUrl=AllUrl.URL_Base +"/p.ashx?m=getProductList&id="+account+"&sort=2&pageNum=1&pageSize=20";
       DijiaUrl=AllUrl.URL_Base +"/p.ashx?m=getProductList&id="+account+"&sort=3&pageNum=1&pageSize=20";
       GaojiaUrl=AllUrl.URL_Base +"/p.ashx?m=getProductList&id="+account+"&sort=4&pageNum=1&pageSize=20";
       
       //只建立一个队列对象，避免浪费资源
       queue=MySingle.getInstance(context).getRequestQueue();
       
       getDataFromServer1(MorenUrl);
    
       //监听刷新
       detail_pull_gradview.setOnRefreshListener(new OnRefreshListener<GridView>() {

		@Override
		public void onRefresh(PullToRefreshBase<GridView> refreshView) {
			getDataFromServer1(MorenUrl);
			
			handler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
	 	        
			    radio4.setChecked(false);
			    radio1.setChecked(true); //回到默认位置
			    detail_pull_gradview.onRefreshComplete(); //收起下拉刷新	
				}
			}, 2000);
			
			
		}
	});
  
       //监听点击，跳到详情页2
       detail_pull_gradview.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
		 
			   Intent intent=new Intent(DetailActivity_1.this,DetailActivity_2.class);
			   intent.putExtra("Id", detail_results_list.get(position).Id);
			   intent.putExtra("Pid",  detail_results_list.get(position).Pid);
			   intent.putExtra("PName",  detail_results_list.get(position).PName);
			   intent.putExtra("Account",  account);
			   startActivity(intent);
			   
		}
	});
       
       
       
       
        
        //四个RadioButton 
        radio1=(RadioButton) findViewById(R.id.radio1);
        radio2=(RadioButton) findViewById(R.id.radio2);
        radio3=(RadioButton) findViewById(R.id.radio3);
        radio4=(RadioButton) findViewById(R.id.radio4);
   
        radio1.setOnClickListener(this);
        radio2.setOnClickListener(this);
        radio3.setOnClickListener(this);
        radio4.setOnClickListener(this);
       
        back_image.setOnClickListener(this);  //返回键
        share_image.setOnClickListener(this);  //分享键
        
        //默认选中
        radio1.setChecked(true); 
        
        
       radio4_image=(ImageView) findViewById(R.id.radio4_image);
       bitmap1=BitmapFactory.decodeResource(getResources(), R.drawable.goods_1); 
       bitmap2=BitmapFactory.decodeResource(getResources(), R.drawable.goods_2); 
       bitmap3=BitmapFactory.decodeResource(getResources(), R.drawable.goods_3); 
       
       radio4.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			
			
			if(radio4.isChecked()){
				if(flag==true){
					radio4_image.setImageBitmap(bitmap3);	
				    flag=false;
				    getDataFromServer1(GaojiaUrl);  //高价
				}else{
					radio4_image.setImageBitmap(bitmap2);
					 flag=true;
					 getDataFromServer1(DijiaUrl);  //低价价
				}
				
				
			   	
			   
			    
			
			} 
		    
			
		
		}
	});
       
       
       radio4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			 if(isChecked){
				 radio1.setChecked(false); 
				 radio2.setChecked(false);
				 radio3.setChecked(false);  
				 radio4_image.setImageBitmap(bitmap3);
				 
			 } else{
				 radio1.setChecked(true); 
				 radio2.setChecked(true);
				 radio3.setChecked(true); 
				 radio4_image.setImageBitmap(bitmap1);
			 } 
	
			 
	 	}
	}); 
       
       
       
      
	    
	 }
	
	
	
	public void getDataFromServer1(String GiveUrl ){
   	 //第一个请求
	     	 
		 request1=new StringRequest(AllUrl.URL_Base +"/p.ashx?m=activedetail&activeId="+account, new Listener<String>() {
	    	 
				@Override
				public void onResponse(String response) {					 					 
					PraseWithGson1 (response);									   
				}			
			}, new ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {					 
					System.out.println("网络不同1");					 				
				}
			 });	    
	       
	    
		 //第二个请求
	    	 request2=new StringRequest(GiveUrl,new Listener<String>() {
	    	 
				@Override
				public void onResponse(String response) {					 					 
					PraseWithGson2 (response);									   
				}			
			 }, new ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {					 
					System.out.println("网络不同1");					 				
				}
			 });	    
	    	 
	    	 queue.add(request1);
	    	 queue.add(request2);
	}
	
	
	//第一个请求解析
	public void PraseWithGson1 (String response) {
		 detail_1 =gson.fromJson(response, Detail_1.class);
	     ActiveName  =detail_1.Results.ActiveName; 	     
         activityname_text.setText(ActiveName);
         
         
	}		
	
	    //第二个请求解析
		public void PraseWithGson2 (String response) {
		    detail_1_list=gson.fromJson(response, Deatail_1_list.class);
			  
	        detail_results_list= detail_1_list.Results;                                                              
	        
	        detail_1_adapter=new Activity_detail_1_adapter(detail_results_list,context);    
	        detail_pull_gradview.setAdapter(detail_1_adapter);
	        detail_1_adapter.notifyDataSetChanged();
			 
		}		
       
		
	
		
	 	
   		
		
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.radio1:
			radio4.setChecked(false);
			radio1.setChecked(true);
		    getDataFromServer1(MorenUrl);
			
			break;
		case R.id.radio2:
			radio4.setChecked(false);
			radio2.setChecked(true);
			getDataFromServer1(ZuixinUrl);
			
			break;
		case R.id.radio3:
			radio4.setChecked(false);
			radio3.setChecked(true);
			getDataFromServer1(RexiaoUrl); 
			
			break;
		 
		case R.id.back_image:
			
			onBackPressed();   //返回效果
    			 			
			break;
		 //分享
         case R.id.share_image:
			
        	 showShare();
             
        	 break;
		
		
		
		} 
		
		
		
	}	




      //分享
	 private void showShare() {
		 ShareSDK.initSDK(this);
		 OnekeyShare oks = new OnekeyShare();
		 //关闭sso授权
		 oks.disableSSOWhenAuthorize(); 

		// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
		 //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
		 // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		 oks.setTitle(getString(R.string.ssdk_oks_share));
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
		 oks.setSite(getString(R.string.app_name));
		 // siteUrl是分享此内容的网站地址，仅在QQ空间使用
		 oks.setSiteUrl("http://sharesdk.cn");

		// 启动分享GUI
		 oks.show(this);
		 }
 
 



}
