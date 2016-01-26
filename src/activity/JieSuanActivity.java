package activity;
 
import java.util.ArrayList;

import util.AllUrl;


import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.app.R;
import com.bmob.pay.tool.BmobPay;
import com.bmob.pay.tool.PayListener;
import com.google.gson.Gson;

import domain.Address;
import domain.Price;
import domain.Address.Results11;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import application.MySingle;

public class JieSuanActivity extends BaseActivity implements OnClickListener {
        
	    
	public ImageView setting_back;    //返回
	
	
	public RequestQueue queue;
	public Gson gson=new Gson();
	
	
	
	public SharedPreferences sp;
	 
	public String  UserId;
	
	
	
	
	public ArrayList<Results11>   address_list=new ArrayList<Results11>();
	
	
	
	public Results11  default_list;
	
	
	public  TextView jiesuan_goods_price;     //商品价格
	public  TextView jiesuan_yunfei_price;     //运费
	public  TextView jiesuan_all_price;        //总价
	
	public RelativeLayout jiesuan_select_address;;  //新增地址的rl
	public RelativeLayout jiesuan_select;     //编辑收获地址
	
	public  TextView jiesuan_name;	     //编辑地址里的名字
	public  TextView jiesuan_phone;      //编辑地址里的号码
	public  TextView jiesuan_address;    //编辑地址
	
	public String DeviceId;
	
	public  Button jiesuan_bianji;
	
	
	
	public Button jiesuan_btn_select_address; //新增收获地址
	
	public CheckBox jiesuan_zhifubao_select;     //支付宝支付的圆圈
	public CheckBox jiesuan_weixin_select;       //微信支付的圆圈
	public Button jiesuan_tijiao_dindan;          //立即支付
	public BmobPay  pay;
	
	    @Override
        protected void onCreate(Bundle savedInstanceState) {
        	 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiesuan);
        //检查网络
		 checkNet(); 
           
        setting_back=(ImageView) findViewById(R.id.setting_back);    
        jiesuan_btn_select_address=(Button) findViewById(R.id.jiesuan_btn_select_address);   
        jiesuan_zhifubao_select=(CheckBox) findViewById(R.id.jiesuan_zhifubao_select); 
        jiesuan_weixin_select=(CheckBox) findViewById(R.id.jiesuan_weixin_select);   
        jiesuan_tijiao_dindan=(Button) findViewById(R.id.jiesuan_tijiao_dindan);   
        
        jiesuan_name=(TextView)findViewById(R.id.jiesuan_name);
        jiesuan_phone=(TextView)findViewById(R.id.jiesuan_phone);
        jiesuan_address=(TextView)findViewById(R.id.jiesuan_address);
        
        jiesuan_select_address=(RelativeLayout) findViewById(R.id.jiesuan_select_address);  
        jiesuan_select=(RelativeLayout) findViewById(R.id.jiesuan_select); 
        
        jiesuan_goods_price=(TextView)findViewById(R.id.jiesuan_goods_price); 
        jiesuan_yunfei_price=(TextView)findViewById(R.id.jiesuan_yunfei_price); 
        jiesuan_all_price=(TextView)findViewById(R.id.jiesuan_all_price); 
        
      
        jiesuan_bianji =(Button)findViewById(R.id. jiesuan_bianji ); 
           
        setting_back.setOnClickListener(this);   
        jiesuan_btn_select_address.setOnClickListener(this);  
        jiesuan_tijiao_dindan.setOnClickListener(this);   
        jiesuan_zhifubao_select.setOnClickListener(this);
        jiesuan_weixin_select.setOnClickListener(this); 
        jiesuan_bianji.setOnClickListener(this); 
        sp=getSharedPreferences("LoginData", MODE_PRIVATE);
        
        UserId=sp.getString("Id", null);  //用户ID
        
        
        
        
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        DeviceId=tm.getDeviceId();  
        
        
        queue=MySingle.getInstance(this).getRequestQueue(); 
           
        getAddress();   
           
        
         
         }
	  
	    private void getAddress() {
			 StringRequest quest=new StringRequest(AllUrl.URL_Base+"/o.ashx?m=getAddress&UserId="+UserId, new Listener<String>() {

				@Override
				public void onResponse(String response) {
					 
				address_list=gson.fromJson(response,Address.class).Results;
					
			 
				 
				if(address_list.size()!=0){
						jiesuan_select_address.setVisibility(View.GONE); 
					    jiesuan_select.setVisibility(View.VISIBLE); 	
				      
						 
						 for(int i=0;i<address_list.size();i++){
							 
							if(address_list.get(i).IsDefault==1){
					 	    default_list=address_list.get(i);
					        jiesuan_name.setText(default_list.TrueName);
							 jiesuan_phone.setText(default_list.PhoneTel); 
							 jiesuan_address.setText(default_list.Province+default_list.City+default_list.Country+default_list.DetailAddress);	  
			 				 
							  getPrice();  //获取价格
							 
			 			} 
	 					 
						 }
			 	
					}else{
						jiesuan_select_address.setVisibility(View.VISIBLE); 	
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
			//返回
			 case R.id.setting_back:
				onBackPressed();
				break;
            //新增地址
			case R.id.jiesuan_btn_select_address:
				Intent intent=new Intent(JieSuanActivity.this,AddAdressActivity.class);
				startActivity(intent);
				
				
				
				break;
		 //支付宝支付	
            case R.id.jiesuan_zhifubao_select:
            	jiesuan_zhifubao_select.setChecked(true);
            	jiesuan_weixin_select.setChecked(false);
            	
	           break;	
	      //微信支付	
            case R.id.jiesuan_weixin_select:
            	jiesuan_weixin_select.setChecked(true);
            	jiesuan_zhifubao_select.setChecked(false);
            	
	            break;	
		  //立即支付	
           case R.id.jiesuan_tijiao_dindan:
        	  
        	   pay=  new BmobPay(this);
        	 
        	 if(jiesuan_zhifubao_select.isChecked()){
        		 pay.pay(0.02,"某商品",new PayListener(){

     				@Override
     				public void fail(int arg0, String arg1) {
     		 	 	  System.out.println("取消了");
     				}

     				@Override
     				public void orderId(String arg0) {
     			 	}

     				@Override
     				public void succeed() {
     			 	    System.out.println("成功了");
     				}

     				@Override
     				public void unknow() {
     			 		
     				}});
        	 } 
        		
        	 
        	 
        	 
        	 if(jiesuan_weixin_select.isChecked()){
        	 
        		 pay.payByWX(0.02,"某商品",new PayListener(){
 			     
        			 @Override
					public void fail(int arg0, String arg1) {
		 		   System.out.println("是阿比"+arg0);
        				 System.out.println(arg1);
        			 }

					@Override
					public void orderId(String arg0) {
			 	}

					@Override
					public void succeed() {
				 		
						
					}

					@Override
					public void unknow() {
				 	}});
        	 
        	 }
        	 
        	 
        	   
        	  
        	   
        	   
        	   
        	   
        	   
	            break;	
           case R.id.jiesuan_bianji:	
             
        	  startActivity(new Intent(this,AddressListActivity.class));
        	   
        	   
        	   
        	   
           
           break;
           default:
				break;
			}
 	}



		   private void getPrice() {

			 StringRequest request=new StringRequest(AllUrl.URL_Base+"/o.ashx?m=Settlement&UserId="+UserId+"&addressId="+default_list.Id+"&udid="+DeviceId, new Listener<String>() {

				@Override
				public void onResponse(String response) {
					 
					Price price=gson.fromJson(response,Price.class);
					jiesuan_goods_price.setText(String.valueOf(price.GoodsMoney));
					jiesuan_yunfei_price.setText(String.valueOf(price.FreightMoney));
					jiesuan_all_price.setText(String.valueOf(price.TotalMoney));
 			
				}
 			
			}, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
	 			}
			});
			queue.add(request);
	 		}

 




}
