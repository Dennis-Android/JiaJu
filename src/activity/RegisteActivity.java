package activity;

import util.AllUrl;
import util.PhoneEmail;


import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.R;
import com.google.gson.Gson;

import domain.Registe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import application.MySingle;
import application.Myapplication;
 
public class RegisteActivity extends BaseActivity implements OnClickListener {
          
	public ImageView register_back;  //返回按钮
	public TextView tv_login;        //登录按钮
	public EditText register_phone;  //请输入手机号
	public EditText register_code;  //短信验证码
    public EditText register_psd_1;  //输入密码
    
    public Button    register_btn_getcode;  //获取验证码
    public EditText  m_code;        //输入验证码
    public ImageView fresh_code;   //刷新验证码
    public Button    register_btn_ok;  //注册按钮
	
    public String  phoneNumber;  //输入的手机号
    public String  yanZhenCode;  //输入的短信验证码
    public String  PassWord;  //输入的密码
	
    public RequestQueue queue;
	public Gson gson=new Gson();
	
	public TimeCount time;   //倒计时
	
	public String UserId; //注册后的Id
	public SharedPreferences sp;
	
	public StringRequest request1;
	public StringRequest request2;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
            	 
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);
   
    
    //检查网络
	 checkNet();
    
    
    sp=getSharedPreferences("UserId", MODE_PRIVATE);
    
    register_back=(ImageView) findViewById(R.id.register_back);
    tv_login=(TextView) findViewById(R.id.tv_login);
    register_phone=(EditText) findViewById(R.id.register_phone);
    register_code=(EditText) findViewById(R.id.register_code);
    register_psd_1=(EditText) findViewById(R.id.register_psd_1);
    m_code=(EditText) findViewById(R.id.m_code);
    fresh_code=(ImageView) findViewById(R.id.fresh_code);
    register_btn_ok=(Button) findViewById(R.id.register_btn_ok);
    register_btn_getcode=(Button) findViewById(R.id.register_btn_getcode);
    
    register_back.setOnClickListener(this);
    tv_login.setOnClickListener(this);
    fresh_code.setOnClickListener(this);
    register_btn_ok.setOnClickListener(this);
    register_btn_getcode.setOnClickListener(this);
    
    queue=MySingle.getInstance(this).getRequestQueue();
    
    
    time=new TimeCount(120000, 1000); //计时120秒，1秒的间隔
	 
	}
 
	@Override
	public void onClick(View v) {
		 switch(v.getId()){
		 case R.id.register_back:
		    onBackPressed();
			 break;
		 
		 case R.id.register_btn_ok:
			 phoneNumber=register_phone.getText().toString().trim(); 
			 yanZhenCode=register_code.getText().toString().trim();
			 PassWord=register_psd_1.getText().toString().trim(); 
 		 if(TextUtils.isEmpty(phoneNumber)){
				 Toast.makeText(this,"请输入手机号", 0).show();
			 }else{
				 
				 if(TextUtils.isEmpty(yanZhenCode)){
					 Toast.makeText(this,"请输入验证码", 0).show(); 
				 }else{
					 
					 if(TextUtils.isEmpty(PassWord)){
						 Toast.makeText(this,"请输入密码", 0).show();  
					 }else{
						 getRealCode();
						 //判断请求返回的是否是对的
				    }
 			 }
		 
			 }
	 		 break;
		 case R.id.fresh_code:
	 		 break;
         //获取短信验证码
		 case R.id.register_btn_getcode:
			 phoneNumber=register_phone.getText().toString().trim(); 
			 if(TextUtils.isEmpty(phoneNumber)){
				 Toast.makeText(this,"请输入手机号", 0).show(); 
			 }else{
				if( PhoneEmail.isMobileNO(phoneNumber)==false){
					Toast.makeText(this,"抱歉，请输入正确的手机号", 0).show(); 
				}else{ 
					 getSafaCode();}
 		   }
			 
                   	break;
 	 case R.id.tv_login:
			Intent intent=new Intent(RegisteActivity.this,LoginActivity.class);
			startActivity(intent);
			 
			 break; 
		  
		 }
}
 
                //获取短信验证码
                public void getSafaCode() {
           request2=new StringRequest(AllUrl.URL_GetCode+phoneNumber, new Listener<String>() {

               					@Override
               					public void onResponse(String response) {
            					   //在这里给button倒计时 
               						time.start();
            				}
                		
               				}, new ErrorListener() {
                					@Override
               					public void onErrorResponse(VolleyError error) {
                					  System.out.println("网络不通");
               					}
               				});
               	          queue.add(request2);
                           }









				//注册是否通过
	             private void getRealCode() {
	 request1=new StringRequest(AllUrl.URL_Base+"/u.ashx?a=gfeng&m=registerNew&mobile="+phoneNumber+"&password="+yanZhenCode+"&sex=2&mcode="+PassWord+"&sourceType=wandoujia", new Listener<String>() {

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
	            
	            	if(gson.fromJson(response, Registe.class).Status=="false"){
	            	Toast.makeText(this,gson.fromJson(response, Registe.class).Results, 0).show(); 
	              }else{
	            	 UserId=gson.fromJson(response, Registe.class).Results;
	            	 Toast.makeText(this,"注册成功", 0).show();
	               
	            	 sp.edit().putString("UserId", UserId).commit();     
               } 
	       			
				 }
  
	 /**
	 * @author Dennis
	 *倒计时实现
	 */
	class TimeCount extends  CountDownTimer{
        //millisInFuture为总时长，countDownInterval为时间间隔
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			 
		}
        
		//计时过程中
		@Override
		public void onTick(long millisUntilFinished) {
			register_btn_getcode.setEnabled(false);  
			register_btn_getcode.setText(millisUntilFinished/1000+"秒");
		}
		//计时完成
		@Override
		public void onFinish() {
			register_btn_getcode.setEnabled(true);   
			register_btn_getcode.setText("获取验证码");
		}
		
	}
 
}