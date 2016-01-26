package activity;

import util.AllUrl;


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
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import application.MySingle;

public class Forget_Psd2_Activity extends BaseActivity implements OnClickListener {

	public ImageView chongzhi_psd_back; 
	
	public EditText chongzhi_psd_edt_1;
	public EditText chongzhi_psd_edt_2;   
	public Button chongzhi_psd_btn_ok;
	
	
	public Context context;
	
	public String phone;
	
	public RequestQueue queue;
	public Gson gson=new Gson();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_forget_psd_2);
	    
	    //检查网络
		 checkNet();
	    
	    
	    chongzhi_psd_back=(ImageView) findViewById(R.id.chongzhi_psd_back);
	    chongzhi_psd_edt_1=(EditText) findViewById(R.id.chongzhi_psd_edt_1);
	    chongzhi_psd_edt_2=(EditText) findViewById(R.id.chongzhi_psd_edt_2);
	    chongzhi_psd_btn_ok=(Button) findViewById(R.id.chongzhi_psd_btn_ok);
	    
	    chongzhi_psd_back.setOnClickListener(this);
	    chongzhi_psd_btn_ok.setOnClickListener(this);
	    
	    context=this;
	    
	     phone=getIntent().getStringExtra("phone"); 
	     queue=MySingle.getInstance(context).getRequestQueue();
	    
	  
	   }

 


	@Override
	public void onClick(View v) {
		 switch(v.getId()){
		 case R.id.chongzhi_psd_back:
		    onBackPressed();
		   break;
		 case R.id.chongzhi_psd_btn_ok:
		 String text1=chongzhi_psd_edt_1.getText().toString().trim();
		 String text2=chongzhi_psd_edt_2.getText().toString().trim(); 
		
		 if(TextUtils.isEmpty(text1)){
			 Toast.makeText(this,"请输入密码", 0).show(); 
		 }else{
			 if(TextUtils.isEmpty(text2)){
				 Toast.makeText(this,"请再次输入密码", 0).show(); 
			 }else{
				 if(text1==text2){
					 getPassword(text1);
				 }else{
					 Toast.makeText(this,"输入的两次密码不一致", 0).show(); 
				 }
 		    }
	 	 }
	 
		   break;
		 
		 
		 
		 }
		
	}










	private void getPassword(String text1) {
		 StringRequest request= new StringRequest(AllUrl.URL_Base+"/u.ashx?m=resetPwd&mobile="+phone+"&newPwd="+text1, new Listener<String>() {
 		    
			@Override
			public void onResponse(String response) {
			 if(gson.fromJson(response,Registe.class).Status=="true"){
				 Toast.makeText(context,"密码修改成功", 0).show();
			 }
	 		
			}
		  }, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				 
				
			}
		 });
		
		queue.add(request);
	}
 
}
