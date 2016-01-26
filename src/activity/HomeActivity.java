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

import domain.Num;
import domain.Registe;
import domain.ShoppingCar;
import domain.ShoppingCar.Results10;
 
import fragment.Fragment1;
import fragment.Fragment2;
import fragment.Fragment3;
import fragment.Fragment4;
import fragment.Fragment5;
 
import android.R.bool;
import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
 
import android.support.v4.app.FragmentTabHost;
 
import android.view.KeyEvent;
import android.view.View;
 
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import application.MySingle;
import application.Myapplication;
 
public class HomeActivity extends  BaseActivity   {
     public static FragmentTabHost tabhost; 
     public ImageView tab_item_image;  //tab的图片
     public TextView tab_item_text;    //tab的文字
     public Button car_num;      //隐藏的红点
     public String Tiao;
     public int num;
     public RequestQueue queue;
 	public Gson gson=new Gson();
     
 	public SharedPreferences sp;
 	public String Id;
	public String DeviceId;
	public ArrayList<Results10>  list=new ArrayList<ShoppingCar.Results10>();
     public  long ExitTime=0;
     //fragment实例数组
     private Class fragmentArrat[]={Fragment1.class,Fragment2.class,Fragment3.class,
			 Fragment4.class,Fragment5.class};
     //区分每个Tab的标签
     private String titleTag[]={"首页","分类","发现","购物车","我"};   
     
     //自定义tab的图片数组 
     private int imageArray[]={R.drawable.tab_1_selector,R.drawable.tab_2_selector,
     R.drawable.tab_3_selector,R.drawable.tab_4_selector,R.drawable.tab_5_selector};
	     
     public  TabSpec tabspec;
    
     public View view;
     
     public boolean Tiao3;
     public StringRequest quest;
     
     @Override
         public void onCreate(Bundle savedInstanceState) {   	 
    	 super.onCreate(savedInstanceState);    	 
    	 setContentView(R.layout.activity_home);   	 
    	 ActivityCollector.addActivity(this);
    	 
    	  Tiao=getIntent().getStringExtra("Tiao");
    	  Tiao3=getIntent().getBooleanExtra("跳3", false);
    	  
    	  sp= getSharedPreferences("LoginData", MODE_PRIVATE);
          Id=sp.getString("Id", null);  //用户ID
    
          queue=MySingle.getInstance(this).getRequestQueue();
          
          getCarCount();
          initView();
          
 	      
          
      
    	 
    	 
      }
  
		    public void initView() {
 
			//1.初始化tabhost
			tabhost=(FragmentTabHost) findViewById(android.R.id.tabhost);
			tabhost.setup(this, getSupportFragmentManager(), R.id.home_fl);
            //2.添加
			 	for(int i=0;i<fragmentArrat.length;i++){
			     tabspec= tabhost.newTabSpec(titleTag[i]).setIndicator(MyView(i));	
	 	         tabhost.addTab(tabspec, fragmentArrat[i], null); 
	 	      
	 	         
	 	         
			  //跳到最后一个页面  
	    	  if(Tiao!=null){
	    		  tabhost.setCurrentTab(4); 
	    	   }
	    	  if(Tiao3==true){
	    		  tabhost.setCurrentTab(3); 
	    	   } 
			 	
			 	
			 	
			 	}  
	     
		    }
		    
	     //获取数据	
		 public void getCarCount() {
			 
			    if(Id!=null){
			 
				 quest=new StringRequest(AllUrl.URL_Base+"/o.ashx?m=getCartCount&UserId="+Id+"&udid="+DeviceId, new Listener<String>() {
             	@Override
				public void onResponse(String response) {
		  	    num =gson.fromJson(response, Num.class).Results;
	 		 	    if(num!=0){
						 System.out.println("数量"+num);
				    	 tabhost.getTabWidget().getChildAt(3).findViewById(R.id.car_num).setVisibility(View.VISIBLE);
 			 	    }else{
 			 	    	 tabhost.getTabWidget().getChildAt(3).findViewById(R.id.car_num).setVisibility(View.GONE);
 			 	    }
			 	     
         	}
 			   
		}, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
			}
			});
			queue.add(quest);
			 
		}
		 }		 
	 	 
		 public View MyView(int i) {
			 view=View.inflate(HomeActivity.this, R.layout.tab_item, null);
 		   //自定义的图片和文字
			 tab_item_image=(ImageView) view.findViewById(R.id.tab_item_image);
			 tab_item_text=(TextView) view.findViewById(R.id.tab_item_text);			 			 
			 car_num=(Button) view.findViewById(R.id.car_num);	
		     tab_item_image.setImageResource(imageArray[i]);
		 	 tab_item_text.setText(titleTag[i]); 
 	 	     return view;
		 }

 
	     //按2下退出   
			@Override
				public boolean onKeyDown(int keyCode, KeyEvent event) {
					   //如果按返回键
					   if(keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN){
						  if(System.currentTimeMillis()-ExitTime>2000){
							  Toast.makeText(getApplicationContext(),"再按一次退出程序",0).show(); 
						      ExitTime=System.currentTimeMillis();
						  }else{
							  finish();
				 		  System.exit(0);   //0表示程序正常退出，1表示非正常退出
		 				  }
					     return true;
					   }
	 			return super.onKeyDown(keyCode, event);
				}


			@Override
			protected void onDestroy() {
				 
				super.onDestroy();
				 ActivityCollector.removeActivity(this);
			
			}
  		 
}
