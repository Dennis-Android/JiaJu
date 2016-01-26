package activity;

import java.util.HashMap;

import util.AllUrl;


import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.app.R;
import com.google.gson.Gson;

import domain.Login;
import domain.Registe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import application.MySingle;
import application.Myapplication;

public class LoginActivity extends BaseActivity implements OnClickListener  {
          
	public ImageView login_back;        //返回按钮
	public EditText edt_username;        //输入号码
	public EditText edt_userpsd;       //输入密码
	public Button login_login;       //登录按钮
	
	public String username;         //输入框获取的用户名
	public String userpsd;          //输入框获取的密码
	
	public RequestQueue queue;
	public Gson gson=new Gson();
	public StringRequest request1;
	
	public String UDID; 
	public String DeviceId;       //设备Id
	
	public Button login_forget_psd;  //忘记密码
	public Button login_register;    //注册按钮
	
	 
    public  String Id;
    public  String NickName;   //用户名
    public  String Score ;  //积分
   
    public  String auth;   
    public  String Photo;   //头像
    public  String CartCount;  //购物车数量
	
    public ImageView login_img_weibo;       //微博登录
 	public ImageView login_img_weixin;      //微信登录
	public ImageView login_img_qq;          //QQ登录
	
	public SharedPreferences sp;
	
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	 
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_login);
	    
	    sp=getSharedPreferences("LoginData", MODE_PRIVATE);
	   
	    //检查网络
		 checkNet();
	    
	   
	    
	    login_back=(ImageView) findViewById(R.id.login_back);
	    edt_username=(EditText) findViewById(R.id.edt_username);
	    edt_userpsd=(EditText) findViewById(R.id.edt_userpsd);
	    login_login=(Button) findViewById(R.id.login_login);
	    login_forget_psd=(Button) findViewById(R.id.login_forget_psd);
	    login_register=(Button) findViewById(R.id.login_register);
	   
	   
	    login_back.setOnClickListener(this);
	    login_login.setOnClickListener(this);
	    login_forget_psd.setOnClickListener(this);
	    login_register.setOnClickListener(this);
	 
	    queue=MySingle.getInstance(this).getRequestQueue();
	    TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        DeviceId=tm.getDeviceId();   
	    UDID =Secure.getString(getContentResolver(), Secure.ANDROID_ID); 
	    
	 }
 
	
	@Override
	public void onClick(View v) {
		 switch(v.getId()){
		 //返回
		 case R.id.login_back:
		    onBackPressed();
			 break;
		 //登录
		 case R.id.login_login:
			 username=edt_username.getText().toString().trim(); 
			 userpsd=edt_userpsd.getText().toString().trim(); 
     
			 if(TextUtils.isEmpty(username)){
				 Toast.makeText(this,"请输入用户名", 0).show();
			 }else{
				 
				 if(TextUtils.isEmpty(userpsd)){
					 Toast.makeText(this,"请输入密码", 0).show();
				 }else{
			          //获取登录数据
				   getLoginData();
					 
	 		 } 
 		     }
			 
 
				 break;
		 //忘记密码
		 case R.id.login_forget_psd:
			    
			 startActivity(new Intent(this,Forget_Psd_Activity.class));
			 
			 
				 break;	 
		 
		//注册		 
		 case R.id.login_register:
			     
			 startActivity(new Intent(this, RegisteActivity.class));
				 break; 
	 	 }
	}

  

	 private void getLoginData () {
request1=new StringRequest(AllUrl.URL_Base+"/u.ashx?a=gfeng&m=checkShopUser&username="+username+"&password="+userpsd+"&loginType=web&Photo=&openid=&deviceType=&gender=&nickName=&address=&udid="+DeviceId, new Listener<String>() {

						@Override
						public void onResponse(String response) {
							 gsonWith(response);
						   
						}
	 		
					}, new ErrorListener() {
	 					@Override
						public void onErrorResponse(VolleyError error) {
	 					  System.out.println("网络不通");
						}
					});
		         queue.add(request1);
		             }

	                 //解析
		             private void gsonWith(String response) {
		            
		            	if(gson.fromJson(response, Login.class).Status=="false"){
		            	Toast.makeText(this,gson.fromJson(response, Registe.class).Results, 0).show(); 
		              }else{
		            	  if(gson.fromJson(response, Login.class).Status=="false"){
		  	            	Toast.makeText(this,gson.fromJson(response, Registe.class).Results, 0).show(); 
		  	              }else{
		  	            	Score=gson.fromJson(response, Login.class).Score; 
		  	            	NickName=gson.fromJson(response, Login.class).NickName;  
		  	            	Photo=gson.fromJson(response, Login.class).Photo;   
		  	            	CartCount=gson.fromJson(response, Login.class).CartCount;  
		  	            	Id=gson.fromJson(response, Login.class).Id;
		  	            	
		  	            	 
		  	            	
		  	            	sp.edit().putString("Score", Score).commit();
		  	            	sp.edit().putString("NickName", NickName).commit();
		  	            	sp.edit().putString("Photo", Photo).commit();
		  	            	sp.edit().putString("CartCount", CartCount).commit();
		  	            	
		  	            	sp.edit().putString("Id", Id).commit();
		  	            	
		  	                sp.edit().putBoolean("Logined", true).commit();
	   	                   ActivityCollector.removeAll();
		  	           	
		  	               Intent intent=new Intent(this,HomeActivity.class);
		  	 			   intent.putExtra("Tiao", "Tiao");
		   	 			   startActivity(intent); 
		                   finish();
		              } 
		       			
					 } 


      }

  			 
}
