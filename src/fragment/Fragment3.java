package fragment;
 
import java.util.ArrayList;

import util.AllUrl;



import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.R;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import domain.Find;
import domain.Find.Results8;

import activity.HomeActivity;
import adapter.FindAdapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
 
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import application.MySingle;
import application.Myapplication;
 
public class Fragment3 extends Fragment {
    public  Activity mactivity;  
	
    public  View view;
    public RequestQueue queue;
    public Gson gsonf=new Gson();  
    private StringRequest request ;      //网络请求
    private Handler handler=new Handler();
    public Context context;
    
    public  ArrayList<Results8>  resultlist=new ArrayList<Results8>();    
    public   FindAdapter adapter;
    
    public   int mposition;
   
    public HomeActivity home;
    
	public static ArrayList<Boolean> list=new ArrayList<Boolean>();
   
    public PullToRefreshListView find_pull_listview;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {		 
		super.onCreate(savedInstanceState);
		mactivity=getActivity(); 
		context=mactivity;
		home=(HomeActivity) mactivity; 
	}
    @Override
	    
	  public View onCreateView(LayoutInflater inflater,ViewGroup container,
			Bundle savedInstanceState) {
       //缓存view
    	if(view==null){
    		view=View.inflate(mactivity, R.layout.fragment3, null);
    	} 
    	 
    	ViewGroup parent=(ViewGroup) view.getParent();
    	
    	if(parent!=null){
    		parent.removeView(view);
    		return view;
    	}
    	
     
    	  
     find_pull_listview=(PullToRefreshListView) view.findViewById(R.id.find_pull_listview);
    	 
      
     //检查是否有网
     home.checkNet();
     
     queue=MySingle.getInstance(context).getRequestQueue();
      
      getDataFromServer();
      
      //下拉刷新
      
     find_pull_listview.setOnRefreshListener(new OnRefreshListener<ListView>() {

		@Override
		public void onRefresh(final PullToRefreshBase<ListView> refreshView) {
			handler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					getDataFromServer();
				   refreshView.onRefreshComplete();
					
				}
			}, 2000);
			
		}
	});  
      
    
     
     
     //点击事件
      find_pull_listview.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
 	     
 	  
           
			
		}
	});
      
      
      
      
      
      
    	  
    	  return view;
 
 
}
	
    
    public   void getDataFromServer() {
    	request=new StringRequest(AllUrl.URL_BaskOrderList, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				
				 
				Gsonwith(response);
				
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
  	     resultlist= gsonf.fromJson(response, Find.class).Results;
          
         adapter=new FindAdapter(context,resultlist);  
         find_pull_listview.setAdapter(adapter);
         adapter.notifyDataSetChanged(); 
    	
     	 for(int i=0;i<resultlist.size();i++){
     		 list.add(false);
     	 }
    	
    	 
    	 
    	 
		
	}
	
}
