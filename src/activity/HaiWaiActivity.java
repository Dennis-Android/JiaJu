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
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import domain.Detail_1;
import domain.Shouye_list;
import domain.Shouye_list.Results3;

import adapter.Shouye_adapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import application.MySingle;
import application.Myapplication;

public class HaiWaiActivity extends BaseActivity {
	private StringRequest request;      //网络请求
	 public RequestQueue queue; 
     public Gson gson=new Gson();  
	public ImageButton back_image;
	public PullToRefreshListView pull_haiwai_listview;
	public Context context;
	 public Shouye_list shouye_list;
	  public static ArrayList<Results3> shouye_arraylist; //首页的Listview集合
	  public Shouye_adapter shouye_adapter;  //listview适配器
	  private Handler handler=new Handler();
	  
	  
	  @Override
      protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_haiwai);         
      context=this;
      //检查网络
		 checkNet();
      //只建立一个队列对象，避免浪费资源
      queue=MySingle.getInstance(context).getRequestQueue();
      
      getDataFromServer();
      
      
      
      pull_haiwai_listview=(PullToRefreshListView) findViewById(R.id.pull_haiwai_listview);
      back_image=(ImageButton) findViewById(R.id.back_image);
      back_image.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
		 onBackPressed();
 	  }
	  });
      
      //监听刷新
      pull_haiwai_listview.setOnRefreshListener(new OnRefreshListener<ListView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ListView> refreshView) {
			 getDataFromServer();
			
			handler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
	 	 	pull_haiwai_listview.onRefreshComplete(); //收起下拉刷新	
				}
			}, 2000);
			
			
		}
	});
      
    
      //点击监听  
      pull_haiwai_listview.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent=new Intent(HaiWaiActivity.this,DetailActivity_1.class);
            intent.putExtra("account",  shouye_arraylist.get(position-1).Id);
        
            startActivity(intent);
			 
			
		}
	});
          
      
      
      
       
     }
	 
	private void getDataFromServer() {
		 request=new StringRequest(AllUrl.URL_haiwai, new Listener<String>() {
	    	 
				@Override
				public void onResponse(String response) {					 					 
					PraseWithGson(response);									   
				}			
			}, new ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {					 
					System.out.println("网络不同1");					 				
				}
			 });	    
		 queue.add(request);
	}


	    // 请求解析
		public void PraseWithGson(String response) {
			shouye_list =gson.fromJson(response, Shouye_list.class);
			shouye_arraylist =shouye_list.Results; 	     
	        
			 if( shouye_arraylist!=null){
	        	 //listview
	         	shouye_adapter=new Shouye_adapter(context, shouye_arraylist);	
	         	pull_haiwai_listview.setAdapter(shouye_adapter);
	         	shouye_adapter.notifyDataSetChanged();
	               
		}		
           
             



}
}