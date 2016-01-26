package activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import util.AllUrl;


import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.app.R;
import com.google.gson.Gson;

import domain.Find;
import domain.PingLun;
import domain.Find.Results8;
import domain.PingLun.Results9;

import adapter.FindAdapter;
import adapter.PingLunAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import application.MySingle;
import application.Myapplication;

public class PingLunActivity extends BaseActivity implements OnClickListener {
    
	
	 public Gson gsonf=new Gson();  
     private StringRequest request ;      //网络请求
	 public RequestQueue queue;
	 public String Id;
	
	 public SharedPreferences sp;
	 
	 
	 public Context context;
	 
	 public ListView listView_comment;   //评论的ListView
	 
	 
	 public String  pinlun_Url;
	 public String UserId;
	 
	 public PingLunAdapter adapter;
	 public  ArrayList<Results9>  resultlist=new ArrayList<Results9>();
	 public ImageView iv_back;
	 public TextView tv_send;  //发送
	 public EditText et_comment;  //输入框
	
	
	@Override
	public  void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_pinglun);
	    context=this;
	    
	    
	    //检查网络
		 checkNet();
	    
	    
	    ActivityCollector.addActivity(this);
	    
	    sp=context.getSharedPreferences("LoginData",context.MODE_PRIVATE);
	    
	    UserId=sp.getString("Id", null);
	    
	    
	    Id=getIntent().getStringExtra("Id");           //listview的itemId
	    listView_comment=(ListView) findViewById(R.id.listView_comment);		
	    iv_back=(ImageView) findViewById(R.id.iv_back);  //后退
	    et_comment=(EditText) findViewById(R.id.et_comment);
	    
	    tv_send=(TextView) findViewById(R.id.tv_send);   
	    
	    queue=MySingle.getInstance(context).getRequestQueue();
	    
	    iv_back.setOnClickListener(this);
	    tv_send.setOnClickListener(this);
	    
	    pinlun_Url=AllUrl.URL_Base+"/u.ashx?m=ReviewList&BaskOrderId="+Id+"&UserId=";
	    
	    getDataFromServer(pinlun_Url);
		
		
	  
	
	
	
	}


	 
        
	 public void getDataFromServer(final String url) {
	    	request=new StringRequest(url, new Listener<String>() {

				@Override
				public void onResponse(String response) {
		 		    if(url==pinlun_Url){
		 		    	Gsonwith(response);}
					
					
				}

			
			}, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
				 System.out.println("网络不同");
				}
			});
	    	queue.add(request);
		}
		
	    
	    //解析
	    public void Gsonwith(String response) {
	  	     resultlist= gsonf.fromJson(response, PingLun.class).Results;
	          
	         adapter=new PingLunAdapter(context,resultlist);  
	         listView_comment.setAdapter(adapter);
	         
	   
			
		 }




		@Override
		public void onClick(View v) {
			 switch(v.getId()){
			 case R.id.iv_back:
			     onBackPressed();
				 
				 
				 break;
			 case R.id.tv_send:
				String comment;
				try {
					comment = URLEncoder.encode(et_comment.getText().toString().trim(), "UTF-8");
				
					 if(TextUtils.isEmpty(comment)){
							Toast.makeText(context, "忘记写评论了哦",0).show();
						}else{
							
							if(UserId==null){
						 		context.startActivity(new Intent(context,LoginActivity.class));
					 		}else{
					 			String reUrl=AllUrl.URL_Base+"/u.ashx?m=CreateReview&UserId="+UserId+"&BaskOrderId="+Id+"&Content="+comment; 	
					 		    getDataFromServer(reUrl);
					 		    getDataFromServer(pinlun_Url);
					 		        
					 		    Toast.makeText(context, "评论成功", 0).show();
					 		    et_comment.setText(null);
					 		}
							
							
							
							
							
							
							
							
							
							
							
							
						}
				
				} catch (UnsupportedEncodingException e) {
					 
					e.printStackTrace();
				} 
		 	 
				 
				 break; 
			 }
			
			
			
			
			
			
			
		}
}
