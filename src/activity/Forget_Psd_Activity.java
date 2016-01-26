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
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import application.MySingle;

public class Forget_Psd_Activity extends BaseActivity implements OnClickListener {
   public  RequestQueue queue; 
	
   public String url_forget;
   public String url_check;
	
   private Gson gson=new Gson();
   
   private Context context;
   public TimeCount time;
   
   public String phone;
   
   
   
   
   public ImageView forget_psd_back;       //返回键
	public EditText  forget_phone;          //输入手机号
	public Button forget_psd_btn_code;      //获取短信验证码按钮
	public EditText forget_psd_edt_code;     //输入短信验证码 
	public Button forget_psd_btn_check   ;    //验证按钮
	   @Override
	  protected void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_forget_psd);
	   
	    context=this;
	  //检查网络
		 checkNet();
	    forget_psd_back=(ImageView) findViewById(R.id.forget_psd_back);
	    forget_phone=(EditText) findViewById(R.id.forget_phone);
	    forget_psd_btn_code=(Button) findViewById(R.id.forget_psd_btn_code);
	    forget_psd_edt_code=(EditText) findViewById(R.id.forget_psd_edt_code);
	    forget_psd_btn_check=(Button) findViewById(R.id.forget_psd_btn_check);
	    
	    
	    forget_psd_back.setOnClickListener(this);
	    forget_psd_btn_code.setOnClickListener(this);
	    forget_psd_btn_check.setOnClickListener(this); 
	     
	    
	    time=new TimeCount(60000, 1000);
	    
	    queue=MySingle.getInstance(this).getRequestQueue();
	    
	    
	    
	   
	   
	   }
	@Override
	public void onClick(View v) {
		 switch(v.getId()){
		 case R.id.forget_psd_back:
		    onBackPressed();
 	     break;
		 case R.id.forget_psd_btn_code:
			   phone=forget_phone.getText().toString().trim();
			 
			 url_forget=AllUrl.URL_Base+"/u.ashx?a=gfeng&m=getSafetyCode&tel="+phone+"&getType=1"; 
			 
			 if(PhoneEmail.isMobileNO(phone)==false){
					Toast.makeText(this,"抱歉，请输入正确的手机号", 0).show(); 
				}else{ 
					 getSafaCode(url_forget);  //获取验证码
					 }
		 
			  break;
		 case R.id.forget_psd_btn_check:
			 phone=forget_phone.getText().toString().trim();
			 String code=forget_psd_edt_code.getText().toString().trim();
			   
			 if(PhoneEmail.isMobileNO(phone)==false){
				 Toast.makeText(this,"抱歉，请输入正确的手机号", 0).show(); 
			 }else{
				 if(TextUtils.isEmpty(code)){
					 Toast.makeText(this,"请输入验证码", 0).show();  
				 }else{
					 url_check=AllUrl.URL_Base+"/u.ashx?m=getmcode&mobile="+phone+"&mcode="+code;
					 SendSafaCode(url_check);
				 }
				 
	 		 
			 }
			 
		  
			  break;
		 
		 }
		
	}
	       private void SendSafaCode(String url_check) {
	    	   StringRequest request1=new StringRequest(url_check, new Listener<String>() {

				@Override
				public void onResponse(String response) {
					if(gson.fromJson(response, Registe.class).Status=="true"){
					 	Intent intent=new Intent(context,Forget_Psd2_Activity.class);  
						intent.putExtra("phone", phone);
					 	startActivity(intent);
			   	            
					 }else{
			  		Toast.makeText(context,gson.fromJson(response, Registe.class).Results, 0).show(); 
			  	  }
					
					
				}
			}, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					 
		 	
				}
			});    
	    	   
	    	   queue.add(request1);   
	    	   
	    	   
	    	   
		
	}
		private void getSafaCode(String url) {
		 StringRequest request=new StringRequest(url, new Listener<String>() {

			@Override
			public void onResponse(String response) {
		 	  
				
				if(gson.fromJson(response, Registe.class).Status=="true"){
					Toast.makeText(context,"请注意查收短信", 0).show();
					time.start();  //开始计时
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
	  
	       
	       
	       
	       
	       
	       
	       
	       
	       
	       
	       
	       
	       
	       
	       
	    //倒计时
       class  TimeCount  extends  CountDownTimer{

		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			 
		}

		@Override
		public void onTick(long millisUntilFinished) {
			forget_psd_btn_code.setEnabled(false);
			forget_psd_btn_code.setText(millisUntilFinished/1000+"秒");
			
		}

		@Override
		public void onFinish() {
			forget_psd_btn_code.setEnabled(true);
			forget_psd_btn_code.setText("获取短信验证"); 
			
		}
    	   
       }
	     
	     
	     
	     
	   
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
}
