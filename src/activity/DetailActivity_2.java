package activity;

import util.AllUrl;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.R;
import com.google.gson.Gson;

import domain.Registe;
import domain.ShoppingCar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import application.MySingle;

@SuppressLint("SetJavaScriptEnabled")
public class DetailActivity_2 extends BaseActivity implements OnClickListener {
	public ImageButton back_image; 
	public ImageButton share_image2;
	
	
	public Button input_shopcar;   //加入购物车
	public Button shoppingcar_num; //要显示的数字
	public RelativeLayout toShoppingCar; //跳到购物车
	
	
	public RequestQueue queue;
	public Gson gson=new Gson();
	public String DeviceId;
	
	public int num;
	public SharedPreferences sp;
	public int i=1;
	public String UserId;
	
	public String  Id;
	public String  Pid;
	public String  PName;
	public String  ActivityId;
	public TextView detail_2_activityname_text;
	  
	public String  ActivityShare;   //发现页面穿过来的url
	
	@Override
      protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_detail_2);         
      
         Id= getIntent().getStringExtra("Id");
         Pid= getIntent().getStringExtra("Pid");
         PName=getIntent().getStringExtra("PName");
         ActivityId=getIntent().getStringExtra("Account");
         
         ActivityShare=getIntent().getStringExtra("ActivityShare");
        
         
         input_shopcar=(Button) findViewById(R.id.input_shopcar);
         shoppingcar_num=(Button)findViewById(R.id.shoppingcar_num);
         toShoppingCar=(RelativeLayout)findViewById(R.id.toShoppingCar);
         
         
         input_shopcar.setOnClickListener(this);
         toShoppingCar.setOnClickListener(this);
          
         sp=getSharedPreferences("LoginData", MODE_PRIVATE);
         
         UserId=sp.getString("Id", null);  //用户ID
         
        
       //检查网络
    	 checkNet();
         
         
         
         
         
         
         TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
         DeviceId=tm.getDeviceId();
         
         queue=MySingle.getInstance(this).getRequestQueue();
         
         getCarCount(); 
         
         //获取购物车数量
         
         
         
         //顶部名称跑马灯
         detail_2_activityname_text=(TextView) findViewById(R.id.detail_2_activityname_text);
         
         if(PName!=null){ detail_2_activityname_text.setText(PName);}
         
          
         share_image2=(ImageButton) findViewById(R.id.share_image2);  
         share_image2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showShare();
				
			}
		});
         
         
         
      back_image=(ImageButton) findViewById(R.id.back_image);
      back_image.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
		 onBackPressed();
 	  }
	  });
     
      WebView webview=(WebView) findViewById(R.id.webview);
      
       webview.getSettings().setJavaScriptEnabled(true);
       webview.getSettings().setDomStorageEnabled(true); //本地缓存
   
      webview.setWebViewClient(new WebViewClient(){
    	  @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
    	
        //拦截自身的加入购物车事件
    	if(url.contains("shopcart")){
    		return true;
    	}	  
    	
    	//拦截登录事件
    	if(url.contains("wx_wap/login")){
    		return true;
    	}	  
    	
       
    	
    	
    		  
    	view.loadUrl(url);	  
    		  
    		  
    	return true;
    }});
      
      if(ActivityShare!=null){
    	  webview.loadUrl(ActivityShare) ;	  
      }else{
    	  
    	  if(ActivityId==null){
     webview.loadUrl(AllUrl.URL_GBase+"/wx_wap/productdetail-wx.html?from=1&"+Pid+"&"+Id+"&activeId=&activeName=undefined");    		  
    	  }else{
    	  webview.loadUrl(AllUrl.URL_GBase+"/wx_wap/productdetail-wx.html?from=1&pid="+Pid+"&id="+Id+"&activeId="+ActivityId+"&activeName=undefined");    	  
    	  }
    	  
      
      
      }
   
     }

 


	 private void getCarCount() {
		 StringRequest quest=new StringRequest(AllUrl.URL_Base+"/o.ashx?m=getCartCount&UserId="+UserId+"&udid="+DeviceId, new Listener<String>() {

			@Override
			public void onResponse(String response) {
			
				if(gson.fromJson(response, Registe.class).Results!="0"){
					shoppingcar_num.setVisibility(View.VISIBLE);
			     	shoppingcar_num.setText(gson.fromJson(response, Registe.class).Results);	
				}
				
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				 
				
			}
		});
		queue.add(quest);
		 
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




	@Override
	public void onClick(View v) {
		switch(v.getId()){
		
		//加入购物车
		case R.id.input_shopcar:
		    if(UserId==null){
		    	startActivity(new Intent(DetailActivity_2.this,LoginActivity.class));
		    }else{
		    	sendData();	
		    } 
			
  	
			break;
		case R.id.toShoppingCar:
			
			
			Intent intent=new Intent(DetailActivity_2.this,ShoppingCarActivity.class);
	 		System.out.println("shop");
			startActivity(intent);
 		    
			
			
			
			
			break;
		
 
		}
 
	}

 
	private void sendData() {
 StringRequest request=new StringRequest(AllUrl.URL_Base+"/o.ashx?m=addlist&count=1&mobileType=3&pid="+Pid+"&spid="+Id+"&udid="+DeviceId+"&activeId="+ActivityId+"&UserId="+UserId, new Listener<String>() {

			@Override
			public void onResponse(String response) {
		 	  try {
					num=Integer.parseInt(shoppingcar_num.getText().toString());
			        System.out.println("........"+num);
					 
					//最多添加三个
					if(num==3){
						Toast.makeText(DetailActivity_2.this,"限购三件", 0).show();
					}else{
				 	
						if(gson.fromJson(response, Registe.class).Status=="true"){
			 				
							shoppingcar_num.setVisibility(View.VISIBLE);
					     	shoppingcar_num.setText(String.valueOf(num+1));
							num=num+1; 
							 
							System.out.println("加1了");
						
						} 
					
					}
		 	  
		 	  
		 	    } catch (Exception e) {
					num=0;
					shoppingcar_num.setVisibility(View.VISIBLE);
					shoppingcar_num.setText(String.valueOf(num+1));
					System.out.println(num);
	 	
			   
			}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				 System.out.println("网络不通");
				
			}
		}); 
		
		queue.add(request);
		
		
	}















}
