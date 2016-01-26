package activity;

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
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.nostra13.universalimageloader.core.ImageLoader;

import domain.Deatail_1_list;
import domain.Detail_1;
import domain.SearchDeatail_list;
import domain.Shouye_list;
import domain.Deatail_1_list.Results4;
import domain.SearchDeatail_list.Results7;

import adapter.Activity_detail_1_adapter;
import adapter.Fenlei_pull_listview_adapter;
import adapter.SearchDetail_adapter;
 
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
 
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
 
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
 
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import application.MySingle;
import application.Myapplication;

public class Search_DetailActivity extends BaseActivity implements OnClickListener {
	 
	
	 
	 public String CategoryName;    //顶部名称
	 public ImageButton back_image_1;
	 public String Id;
  
	 
	 public PullToRefreshListView search_detail_pull_listview; //listview
	 
	 public RequestQueue queue;//网络请求
	 public StringRequest request_search;
	 public Gson gson =new Gson();
	 public static SearchDeatail_list  searchDeatail_list;
	 public static ArrayList<Results7>  nlist=new ArrayList<Results7>(); 
	 
	 public Context context;
	 public String searchUrl;
 
	 
	 public SearchDetail_adapter adapter;
	 
	 private Handler handler=new Handler(); 
	 
	 
	 
 
	 @Override    
	 public  void onCreate(Bundle savedInstanceState) {
    	 
     	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);
        
        
        //检查网络
		 checkNet();
        
        context=this;
       
        back_image_1=(ImageButton) findViewById(R.id.back_image_1);
        
        search_detail_pull_listview=(PullToRefreshListView) findViewById(R.id.search_detail_pull_listview);
        
      
        back_image_1.setOnClickListener(this);
        
        searchUrl=getIntent().getStringExtra("searchUrl");
        
        System.out.println(searchUrl);
     
        queue=MySingle.getInstance(context).getRequestQueue();
    
         
        getDatafromServer(searchUrl);
       
        
        
        
	     
	     //下拉刷新
	     search_detail_pull_listview.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(final PullToRefreshBase<ListView> refreshView) {
				
				 handler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						 
	 			       refreshView.onRefreshComplete();
					}
				}, 2000);
				
				
				
			}
		});
	     
	     //点击事件
	     search_detail_pull_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				 
				  Intent intent=new Intent(Search_DetailActivity.this,DetailActivity_2.class);
				   intent.putExtra("Id", nlist.get(position-1).Id);
				   intent.putExtra("Pid",  nlist.get(position-1).Pid);
				   intent.putExtra("PName",  nlist.get(position-1).PName);
				   intent.putExtra("Account", nlist.get(position-1).ActiveId);
				   
				   startActivity(intent); 
			}
      });
	     
	     
        
        
		}



	private void getDatafromServer(String url) {
		 request_search=new StringRequest(url, new Listener<String>() {

			@Override
			public void onResponse(String response) {
 			GsonWith(response);				
			}			
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				 				
			}
		});
		 queue.add(request_search);
	}
      
	//解析
	 public void GsonWith(String response) {
       	 try {
       		searchDeatail_list=gson.fromJson(response, SearchDeatail_list.class); 
		
       	    nlist= searchDeatail_list.Results;
       	 
       	 } catch (Exception e) {
			 Toast.makeText(context, "没有找到相关商品", 0).show();
		    
		}
		 
         
		 
		 
		
      
		 //适配器
	     adapter=new SearchDetail_adapter(context);
		 
	     search_detail_pull_listview.setAdapter(adapter);
  	     adapter.notifyDataSetChanged();
		 
		 } 
	 
	@Override
	public void onClick(View v) {
		 switch( v.getId()){
		 
		 case R.id.back_image_1:
			 onBackPressed();
			 break;
		 
		 
		 
		 }
		
	}  
}
