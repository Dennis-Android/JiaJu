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
import domain.Shouye_list;
import domain.Deatail_1_list.Results4;

import adapter.Activity_detail_1_adapter;
import adapter.Fenlei_pull_listview_adapter;
 
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
 
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
 
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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import application.MySingle;
import application.Myapplication;

public class GridView_DetailActivity extends BaseActivity implements OnClickListener {
	 
	
	 public TextView activityname_text_1;   //顶部名称
	 public String CategoryName;    //顶部名称
	 public ImageButton back_image_1;
	 public String Id;
	
	 public RadioButton gridview_tab1;   //默认
	 public RadioButton gridview_tab2;   //价格
	 public RadioButton gridview_tab3;   //折扣
	 
	 public Boolean flag=true;
	 
	 public ImageView gridview_tab_image1; //价格图片
	 public ImageView gridview_tab_image2; //价格图片
	  
	 public PullToRefreshListView fenlei_pull_listview; //listview
	 
	 public RequestQueue queue;//网络请求
	 public StringRequest request;
	 public Gson gson=new Gson();
	 public static Deatail_1_list deatail_1_list;
	 public static ArrayList<Results4>  mlist=new ArrayList<Results4>();  
	 
	 public Context context;
	 
	 public int a=0;  //标记 ,下拉刷新刷哪个
	 
	 public Fenlei_pull_listview_adapter adapter;
	 
	 private Handler handler=new Handler(); 
	 
	 
	 private ImageButton share_image_3;
	 
	 
	 
	 public String url1;              //网址
	 public String url2;  
	 public String url3;  
	 public String url4;  
	 public String url5;  
	 
	 public Bitmap bitmap1;
	 public Bitmap bitmap2;
	 public Bitmap bitmap3;
	 @Override    
	 public  void onCreate(Bundle savedInstanceState) {
    	 
     	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview_detail);
        
        //检查网络
		 checkNet();
        
        
        context=this;
        activityname_text_1=(TextView) findViewById(R.id.activityname_text_1);
        back_image_1=(ImageButton) findViewById(R.id.back_image_1);
        
        fenlei_pull_listview=(PullToRefreshListView) findViewById(R.id.fenlei_pull_listview);
        
        gridview_tab1=(RadioButton) findViewById(R.id.gridview_tab1);
        gridview_tab2=(RadioButton) findViewById(R.id.gridview_tab2);
        gridview_tab3=(RadioButton) findViewById(R.id.gridview_tab3);
        gridview_tab_image1=(ImageView) findViewById(R.id.gridview_tab_image1);
        gridview_tab_image2=(ImageView) findViewById(R.id.gridview_tab_image2);
        
         share_image_3=(ImageButton) findViewById(R.id.share_image_3);
         share_image_3.setOnClickListener(this);
         
         back_image_1.setOnClickListener(this);
        
        gridview_tab1.setOnClickListener(this);
        gridview_tab2.setOnClickListener(this);
        gridview_tab3.setOnClickListener(this); 
        
        //价格图片
         bitmap1=BitmapFactory.decodeResource(getResources(), R.drawable.goods_1);
         bitmap2=BitmapFactory.decodeResource(getResources(), R.drawable.goods_2);
         bitmap3=BitmapFactory.decodeResource(getResources(), R.drawable.goods_3);
        
         //获取传递的数据
        CategoryName=getIntent().getStringExtra("CategoryName");
        Id=getIntent().getStringExtra("Id");
        //赋值
        
        
        activityname_text_1.setText(CategoryName);
        
        gridview_tab1.setChecked(true);
        //选中变色
       
       
        queue=MySingle.getInstance(context).getRequestQueue();
        
        //几个网址
        url1=AllUrl.URL_Base+"/p.ashx?m=categoryProduct&categoryId="+Id+"&sortCol=&sortType=&pageSize=20";
        url2=AllUrl.URL_Base+"/p.ashx?m=categoryProduct&categoryId="+Id+"&sortCol=Price&sortType=asc&pageSize=20";
        url3=AllUrl.URL_Base+"/p.ashx?m=categoryProduct&categoryId="+Id+"&sortCol=Price&sortType=desc&pageSize=20";
        url4=AllUrl.URL_Base+"/p.ashx?m=categoryProduct&categoryId="+Id+"&sortCol=DiscountDes&sortType=asc&pageSize=20";
        url5=AllUrl.URL_Base+"/p.ashx?m=categoryProduct&categoryId="+Id+"&sortCol=DiscountDes&sortType=desc&pageSize=20";
        
        
        getDatafromServer(url1);
       
        //适配器
	     adapter=new Fenlei_pull_listview_adapter(context);
        
        
	     
	     //下拉刷新
	     fenlei_pull_listview.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(final PullToRefreshBase<ListView> refreshView) {
				
				 handler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						switch(a){
						case 0:
							getDatafromServer(url1);
							break;
						case 1:
							getDatafromServer(url2);
							break;
						case 2:
							getDatafromServer(url3);
							break;
						case 3:
							getDatafromServer(url4);
							break;
						
						case 4:
							getDatafromServer(url5);
							break;
						} 
	 			       refreshView.onRefreshComplete();
					}
				}, 2000);
				
				
				
			}
		});
	     
	     //点击事件
	     fenlei_pull_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				 
				   Intent intent=new Intent(GridView_DetailActivity.this,DetailActivity_2.class);
				   intent.putExtra("Id", mlist.get(position-1).Id);
				   intent.putExtra("Pid",  mlist.get(position-1).Pid);
				   intent.putExtra("PName",  mlist.get(position-1).PName);
				   intent.putExtra("Account", mlist.get(position-1).ActiveId);
				   startActivity(intent);
			}
      });
	     
	     
        
        
		}



	private void getDatafromServer(String url) {
		 request=new StringRequest(url, new Listener<String>() {

			@Override
			public void onResponse(String response) {
			    GsonWith(response);
				
			}

			
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				 System.out.println("网络不通");
				
			}
		});
		 queue.add(request);
	}
      
	//解析
	 private void GsonWith(String response) {
       	 //捕获异常
		 try {
		 deatail_1_list=gson.fromJson(response, Deatail_1_list.class); 
		
		mlist= deatail_1_list.Results;
        fenlei_pull_listview.setAdapter(adapter);
 	 
		 } catch (Exception e) {
		  Toast.makeText(context, "暂时没商品",0).show();   
		}
		 
 	 
		 } 
		 
	 
	 

	@Override
	public void onClick(View v) {
		 switch( v.getId()){
		 case R.id.gridview_tab1:
			 
			 gridview_tab2.setChecked(false);
			 gridview_tab3.setChecked(false);
			 
			 gridview_tab_image1.setImageBitmap(bitmap1);
			 gridview_tab_image2.setImageBitmap(bitmap1);
			 
			 
			 a=0;
			 getDatafromServer(url1);
			 
			 break;
		 
		 case R.id.gridview_tab2:
			 
			 gridview_tab1.setChecked(false);
			 gridview_tab3.setChecked(false);
			 
			 gridview_tab_image2.setImageBitmap(bitmap1);
	  
			 if(flag==true){
			 gridview_tab_image1.setImageBitmap(bitmap3);
			 getDatafromServer(url2);
			 a=1;
			 flag=false;
			 }else{
				 gridview_tab_image1.setImageBitmap(bitmap2);
				 flag=true;	 
				 getDatafromServer(url3);
				 a=2;
			 }
			 break;
		 
		 case R.id.gridview_tab3:
			 
			 gridview_tab1.setChecked(false);
			 gridview_tab2.setChecked(false);			 
			 gridview_tab_image1.setImageBitmap(bitmap1);
			  
			 if(flag==true){
				 gridview_tab_image2.setImageBitmap(bitmap3);
				 getDatafromServer(url4);
				 a=3;
				 flag=false;
				 }else{
					 gridview_tab_image2.setImageBitmap(bitmap2);
					 getDatafromServer(url5);
					 flag=true;	 
					 a=4;	 
			  }
			 break;
		 
		 
		 case R.id.back_image_1:
			 onBackPressed();
			 break;
			  
		 
		 case R.id. share_image_3:
			
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
