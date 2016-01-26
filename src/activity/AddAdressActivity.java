package activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import util.AllUrl;
import util.PhoneEmail;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.R;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import application.MySingle;

public class AddAdressActivity extends BaseActivity implements OnClickListener {
 	
	public ImageView add_address_back;         //返回
	 
	public EditText create_address_name;       //收货人姓名
	public EditText create_address_phone;      //手机号
	
	public EditText address_1;       //省市的地址
	public EditText address_2;       //市的地址
	public EditText address_3;       //区县的地址
	
	public EditText create_address_detail;     //详细地址
	
	public CheckBox create_address_isdefault; //设置为默认地址
 
	public Button create_address_ok;         //保存地址按钮
 
	public String IsDefault;  //选择默认地址的标记
	public SharedPreferences sp;
	 
	public RequestQueue queue;
	public Gson gson=new Gson();
	public String  address_name;    //姓名
	public String  address_phone;   //号码
	public String  address1;     //省地址
	public String  address2;      //市地址
	public String  address3;    //县区地址
	public String  address_detail;//详细地址
	public String  UserId;
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_add_address);
	  
	    //检查网络
		 checkNet();
	   
	    add_address_back=(ImageView) findViewById(R.id.add_address_back);
	    create_address_name=(EditText) findViewById(R.id.create_address_name);
	    create_address_phone=(EditText) findViewById(R.id.create_address_phone);
	    address_1=(EditText) findViewById(R.id.address_1);
	    address_2=(EditText) findViewById(R.id.address_2);
	    address_3=(EditText) findViewById(R.id.address_3);
	    create_address_isdefault=(CheckBox) findViewById(R.id.create_address_isdefault);
	    create_address_detail=(EditText) findViewById(R.id.create_address_detail);
	    create_address_ok=(Button) findViewById(R.id.create_address_ok);
	    
	    
	    add_address_back.setOnClickListener(this);
	    create_address_ok.setOnClickListener(this);
	    
	    
	    sp=getSharedPreferences("LoginData", MODE_PRIVATE);
        
        UserId=sp.getString("Id", null);  //用户ID
	    
	    
	    queue=MySingle.getInstance(this).getRequestQueue(); 
	  
	    
	    
	    
	    
    
	  }
  @Override
	public void onClick(View v) {
		 switch (v.getId()) {
		case R.id.add_address_back:
			onBackPressed();    //返回
	 		break;
      
       case R.id.create_address_ok:
			try {
				address_name=URLEncoder.encode(create_address_name.getText().toString().trim(), "UTF-8");
				address_phone=create_address_phone.getText().toString().trim();
				address1=URLEncoder.encode(address_1.getText().toString().trim(), "UTF-8");
				address2=URLEncoder.encode(address_2.getText().toString().trim(), "UTF-8");
				address3=URLEncoder.encode(address_3.getText().toString().trim(), "UTF-8");
				address_detail=URLEncoder.encode(create_address_detail.getText().toString().trim(), "UTF-8");
			
			
				 if(TextUtils.isEmpty(address_name)){
		    		   Toast.makeText(this,"请输入姓名",0).show();
		    	   }else{
			    	  
		    		   if(PhoneEmail.isMobileNO(address_phone)==false){
		        		   Toast.makeText(this,"请输入正确的联系人电话",0).show();
		        	   }else{
		        		   
		        		   if(TextUtils.isEmpty(address1)||TextUtils.isEmpty(address2)||TextUtils.isEmpty(address3)||TextUtils.isEmpty(address_detail)){
		            		   Toast.makeText(this,"请输入完整的地址",0).show();
		            	   }else{
		            		    if(create_address_isdefault.isChecked()){
		              	    	 IsDefault="1";
		              	        }else{
		              	    	 IsDefault="0";
		              	       }
		String url=AllUrl.URL_Base+"/o.ashx?m=setAddress&UserId="+UserId+"&userName="+address_name+"&tel="+address_phone+"&accept=&province="+address1
		+"&city="+address2+"&country="+address3+"&detailAddress="+address_detail+"IsDefault="+IsDefault+
		"&addressId=&Region=&zoneCode=1";
		            		    StringRequest request=new StringRequest(url, new Listener<String>(){

					@Override
					public void onResponse(String response) {
						System.out.println(response); 
 					     onBackPressed();
					}
				}, new ErrorListener() {
 				@Override
					public void onErrorResponse(VolleyError error) {
 				}
				});
	 	         queue.add(request);   		   
 	            	   }
 	        	   }  
                } 
 		        } catch (UnsupportedEncodingException e) {
 			      e.printStackTrace();
			    }
               break;
    	      }
		
		
		 
		
	}
	
	
	
	  
}
