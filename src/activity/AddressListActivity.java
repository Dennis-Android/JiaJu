package activity;

import java.util.ArrayList;

import util.AllUrl;


import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.app.R;
import com.google.gson.Gson;

import domain.Address;
import domain.Address.Results11;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import application.MySingle;

public class AddressListActivity extends BaseActivity implements OnClickListener {
              

	public SharedPreferences sp;
	 
	public String  UserId;
	
	public ImageView address_list_back;
	public Button address_list_btn_new;
	public ListView address_list_gridview;
	
	
	public ArrayList<Results11>   address_list=new ArrayList<Results11>();
	
	 
	
	public RequestQueue queue;
	public Gson gson=new Gson();
	
	public String DeviceId;
	
	public TextView no_address;
	
 
	public TextView item_addresslist_name;      //姓名
 	public TextView item_addresslist_phone;    //电话
 	public TextView item_addresslist_address;    //地址
 	public  RelativeLayout item_addresslist_rel_bianji;  //删除
 	public RelativeLayout item_addresslist_rel_delete;  //编辑
	
	 
 	
 	
	
	 @Override
     protected void onCreate(Bundle savedInstanceState) {
     	 
     super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_address_list);
      
   //检查网络
   	 checkNet();
	
     address_list_back=(ImageView)findViewById(R.id.address_list_back);
     address_list_btn_new=(Button)findViewById(R.id.address_list_btn_new);
     address_list_gridview=(ListView)findViewById(R.id.address_list_gridview);
     no_address=(TextView)findViewById(R.id.no_address);
     
    
    
     
   
     address_list_back.setOnClickListener(this);
     address_list_btn_new.setOnClickListener(this);
     
     
     
     sp=getSharedPreferences("LoginData", MODE_PRIVATE);
     
     UserId=sp.getString("Id", null);  //用户ID
     
     
     TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
     DeviceId=tm.getDeviceId();  
     
     
     
     queue=MySingle.getInstance(this).getRequestQueue(); 
     
     getAddress();  
     
     
    
     
     
	
	 
	 }

	 
	 private void getAddress() {
		 //没登陆
		 if(UserId==null){
			 startActivity(new Intent(this,LoginActivity.class));
	 	     finish();
			 return;
		 }
		 
		 StringRequest quest=new StringRequest(AllUrl.URL_Base+"/o.ashx?m=getAddress&UserId="+UserId, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				 
			address_list=gson.fromJson(response,Address.class).Results;
		 	if(address_list.size()==0){
		 		no_address.setVisibility(View.VISIBLE); 		
		 	}else{
 			  
		 		
		 		address_list_gridview.setVisibility(View.VISIBLE);
		 		
		 		address_list_gridview.setAdapter(new BaseAdapter() {
					
					@Override
					public View getView(final int position, View convertView, ViewGroup parent) {
						 
				     View view=View.inflate(AddressListActivity.this, R.layout.item_address_list, null);
					
				   
				     item_addresslist_name=(TextView)view.findViewById(R.id.item_addresslist_name);
				     item_addresslist_phone=(TextView)view.findViewById(R.id.item_addresslist_phone); 
				     item_addresslist_address=(TextView)view.findViewById(R.id.item_addresslist_address);
				     item_addresslist_rel_bianji=(RelativeLayout)view.findViewById(R.id.item_addresslist_rel_bianji);
				     item_addresslist_rel_delete=(RelativeLayout)view.findViewById(R.id.item_addresslist_rel_delete);   
	 			   
				     item_addresslist_rel_bianji.setOnClickListener(AddressListActivity.this);
				     item_addresslist_rel_delete.setOnClickListener(AddressListActivity.this);
					
				     
				     item_addresslist_name.setText(address_list.get(position).TrueName);		    
				     item_addresslist_phone.setText(address_list.get(position).PhoneTel);
				   
				     item_addresslist_address.setText(address_list.get(position).Province+address_list.get(position).City+address_list.get(position).Country+address_list.get(position).DetailAddress);
				   
				    
						 
				 item_addresslist_rel_delete.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						 
		   StringRequest quest1=new StringRequest(AllUrl.URL_Base+"/o.ashx?m=delAddress&UserId="+UserId+"&addressId="+address_list.get(position).Id, new Listener<String>() {

							@Override
							public void onResponse(String response) {
			 			         
								 getAddress();  
							}
                          	}, new ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError error) {
								
								
							}
						});
						queue.add(quest1);
			 		
					}
				});
				     
					
				 
				 
				 
				     return view;
					}
					
					@Override
					public long getItemId(int position) {
						 
						return position;
					}
					
					@Override
					public Object getItem(int position) {
					 
						return address_list.get(position);
					}
					
					@Override
					public int getCount() {
						 
						return address_list.size();
					}
				});
		 		
		 		
		 		
		 		
		 		
		 		
		 		
 		}			 
			}
			
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				
				
			}
		});
		queue.add(quest);
		 
	   }


	@Override
	public void onClick(View v) {
		 switch (v.getId()) {
		case R.id.address_list_back:
 			onBackPressed();
 		break;
        case R.id.address_list_btn_new:
			
			startActivity(new Intent(this,AddAdressActivity.class)); 
			
			break;
         case R.id.item_addresslist_rel_bianji:
			
        	 startActivity(new Intent(this,AddAdressActivity.class));
        	 
         
        	 
			break;
		 
        
		 
        	 
        	
	 
		 
		 
		 
		 
		 
		 
		 
		 }
		
	}	
}
