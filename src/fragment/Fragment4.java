package fragment;
 
import java.util.ArrayList;

import util.AllUrl;
 
 
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.app.R;
import com.google.gson.Gson;

import domain.Registe;
import domain.ShoppingCar;
import domain.ShoppingCar.Results10;

import activity.ActivityCollector;
import activity.DetailActivity_2;
import activity.HomeActivity;
import activity.JieSuanActivity;
import activity.LoginActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
 
 
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;
 
import application.MySingle;

public class Fragment4 extends Fragment {
    
	public  Activity mactivity;  
	
    public Button shoppingcar_gotobuy;   //去逛逛
    
    public RelativeLayout shoppingcar_no;  //为空时显示的布局
    
    public WebView shoppingcar_webview;   //不为空时显示的webview
    
    public RequestQueue queue;
	public Gson gson=new Gson();
    
	public Context context;
    
	public SharedPreferences sp;
	public String Id;
	public String DeviceId;
	
	
	public ArrayList<Results10>  list=new ArrayList<ShoppingCar.Results10>();
	public HomeActivity home;
	
	
    public View view;
    
    
   @Override
	public void onCreate(Bundle savedInstanceState) {		 
		super.onCreate(savedInstanceState);
		mactivity=getActivity(); 
		context=mactivity;
	 
   } 
	
	
      @SuppressLint("SetJavaScriptEnabled")
	@Override
	    
	  public View onCreateView(LayoutInflater inflater,ViewGroup container,
			Bundle savedInstanceState) {
      view=View.inflate(mactivity, R.layout.fragment4, null);
      
        
      shoppingcar_gotobuy=(Button)view.findViewById(R.id.shoppingcar_gotobuy);
      shoppingcar_no=(RelativeLayout)view.findViewById(R.id.shoppingcar_no);
      shoppingcar_webview=(WebView)view.findViewById(R.id.shoppingcar_webview);
      
      sp=context.getSharedPreferences("LoginData", context.MODE_PRIVATE);
      
      Id=sp.getString("Id", null);  //用户ID
     
      //没登陆
      if(Id==null){
    	  shoppingcar_no.setVisibility(View.VISIBLE); 
      }
      
      
      
      
      try {
    	  home=(HomeActivity)mactivity;
    	  home.getCarCount();
      } catch (Exception e) {
		    ActivityCollector.removeAll(); 
			Intent intent=new Intent(context,HomeActivity.class);			
		    intent.putExtra("跳3",true);
			startActivity(intent);
		 	getActivity().finish(); 
	 	  
	  } 
      
      
     
      
      shoppingcar_gotobuy.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			 
			try {
				home.tabhost.setCurrentTab(0);
			} catch (Exception e) {
				ActivityCollector.removeAll(); 
				Intent intent=new Intent(context,HomeActivity.class);			
  			    startActivity(intent);
			 	getActivity().finish();
				
			}
			
	 	
			
			
			
		}
	});
      
      TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
      DeviceId=tm.getDeviceId();
      
     
      shoppingcar_webview.getSettings().setJavaScriptEnabled(true);
      shoppingcar_webview.getSettings().setDomStorageEnabled(true);
      
      shoppingcar_webview.setWebViewClient(new WebViewClient(){
       	  @Override
         public boolean shouldOverrideUrlLoading(WebView view, String url) {
       	 
       		  //如果点击结算，则自动发出一个url,里面有addorder，那么跳到结算activity
       		if(url.contains("addorder")){
 	    	  context.startActivity(new Intent(mactivity,JieSuanActivity.class));
       		  return true;
       		}		  		  
       		 
       		//点击删除
       		if(url.contains("hide")){
       			shoppingcar_webview.setVisibility(View.GONE);
       			shoppingcar_no.setVisibility(View.VISIBLE);
         			
         		  return true;
         		}		
       		
        //点击详情
       		if(url.contains("pid=")){
       			 
       			 System.out.println(url);
       			 
       		String	 urlq=url.substring(url.lastIndexOf("id="));  //id  例子id=20282
       		String	 urlQ=url.substring(url.indexOf("pid="));	  // 例子pid=12912&id=20282
      		String	 url3=urlQ.substring(urlQ.indexOf("pid="), urlQ.indexOf("&"));//pid=12912
       	    
      		Intent intent=new Intent(mactivity,DetailActivity_2.class);
      		intent.putExtra("Id", urlq);
      		intent.putExtra("Pid", url3);
      	
      		context.startActivity(intent);
      		  return true;
         		}	 
       		
    
       		
       	view.loadUrl(url);	  
       		  
       		  
       	return true;
       }});
   
      
      queue=MySingle.getInstance(context).getRequestQueue();
      
  
      
      getCarData();  //获取购物车数量
    	 
    	  return view;
 
 
}


	 private void getCarData() {
		 StringRequest request= new StringRequest(AllUrl.URL_Base+"/o.ashx?m=shopcart&a=&UserId="+Id+"&udid="+DeviceId, new Listener<String>() {
 		    
			@Override
			public void onResponse(String response) {
			 list=gson.fromJson(response, ShoppingCar.class).Results;
	 	 
			 if(list.size()==0){
				 shoppingcar_no.setVisibility(View.VISIBLE);
			 
			 }else{
				 shoppingcar_no.setVisibility(View.GONE);
				 shoppingcar_webview.loadUrl("http://www.jumeimiao.com/wap/car.html?&UserId="+Id+"&udid="+DeviceId); 
	 	         System.out.println("加载购物车");
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
