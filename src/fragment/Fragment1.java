package fragment;
 
import java.util.ArrayList;


import util.AllUrl;
import view.ImageCycleView;


import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.R;
 
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.nostra13.universalimageloader.core.ImageLoader;

import domain.Lunbo;
import domain.Shouye_list;
import domain.Zhongjian;
import domain.Shouye_list.Results3;
 

import activity.DetailActivity_1;
import activity.HaiWaiActivity;
import activity.HomeActivity;
import activity.MeiriActivity;
import adapter.Shouye_adapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceActivity.Header;
 
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
 
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import application.MySingle;
import application.Myapplication;

public class Fragment1 extends Fragment implements OnClickListener {
	private  Activity mactivity;  
    private PullToRefreshListView  pull_listview; //上拉加载控件 
    private ImageCycleView mAdView;   //广告轮播组件    
    private Lunbo lunbo;             //轮播解析
    private String account1;         //轮播条的activityid
    private String account2;
    private String account3;
    private String account4;
    
    private  ImageView image1;          //四个中间的图片
    private  ImageView image2;
    private  ImageView image3;
    private  ImageView image4;
     
    private static ArrayList<String>  ImageUrllist;  //轮播图url集合
    private View view;     //缓存Fragment view
    
    private Zhongjian zhongjian;       
   
    private static String url_image1;      //中间的图片   
    private static String url_image2; 
    private static String url_image3; 
    private static String url_image4; 
    
    private static String SpecialId_1;    //中间图片的id
    private static String SpecialId_2;
    private static String SpecialId_3;
    private static String SpecialId_4;
    
    private StringRequest request1;      //网络请求
    private StringRequest request2;
    private StringRequest request3;
    private Shouye_list shouye_list;
    private static ArrayList<Results3>shouye_arraylist; //首页的Listview集合
    
    //网络请求
    private RequestQueue queue; 
    private Gson gson=new Gson();  
    
	 private View header;
    
    private Handler handler=new Handler();
    private Context context;
    private Shouye_adapter shouye_adapter;  //listview适配器
    private RelativeLayout    qianggou;  //每日抢购等
    private RelativeLayout    meiri;  //每日抢购等
    private RelativeLayout    faxian;  //每日抢购等
    
    private ArrayList<String> cache_lunbo_list =new ArrayList<String>();
    
    private SharedPreferences sp;
    
    private String cache_imageurl1;
    private String cache_imageurl2;
    private String cache_imageurl3;
    private String cache_imageurl4;
    private HomeActivity home;
    
   private ProgressBar  progress;
    
    @Override
	public void onCreate(Bundle savedInstanceState) {		 
		super.onCreate(savedInstanceState);
		mactivity=getActivity(); 
		context=getActivity();
 
	 }
	
 
      @Override
	    
	  public View onCreateView(  LayoutInflater inflater,  ViewGroup container,
			  Bundle savedInstanceState) {
    	      home=(HomeActivity) mactivity;
    	  
    	       if(view==null){
                view=View.inflate(mactivity, R.layout.fragment1, null);	  
               
    	       }
    	    //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。  
            ViewGroup parent = (ViewGroup) view.getParent();  
            if (parent != null) {  
                parent.removeView(view);  
                return view;
            }    
            
            findview();
            
            sp=context.getSharedPreferences("cache", context.MODE_PRIVATE);
             
            //只建立一个队列对象，避免浪费资源
            queue=MySingle.getInstance(context).getRequestQueue();
            
      
	       //缓存的轮播url
	       if(sp.getString("cache_lunbo1", null)!=null){
	    	   cache_lunbo_list.add(sp.getString("cache_lunbo1", null));
	       }
	        
	       if(sp.getString("cache_lunbo2", null)!=null){
	    	   cache_lunbo_list.add(sp.getString("cache_lunbo2", null));
	       }
	       
	       if(sp.getString("cache_lunbo3", null)!=null){
	    	   cache_lunbo_list.add(sp.getString("cache_lunbo3", null));
	       }
	       if(sp.getString("cache_lunbo4", null)!=null){
	    	   cache_lunbo_list.add(sp.getString("cache_lunbo4", null));
	       }
        
	       mAdView.setImageResources(cache_lunbo_list, mAdCycleViewListener);//设置资源和监听事件  
	        
	        
	        //从缓存在sharepreferce的Url，赋给四个图片
	       cache_imageurl1=sp.getString("image1", null);
	       cache_imageurl2= sp.getString("image2", null);
	       cache_imageurl3=sp.getString("image3", null);
	       cache_imageurl4=sp.getString("image4", null);
	        
	        
	        ImageLoader.getInstance().displayImage(cache_imageurl1, image1, Myapplication.option);
	        ImageLoader.getInstance().displayImage(cache_imageurl2, image2, Myapplication.option);
	        ImageLoader.getInstance().displayImage(cache_imageurl3, image3, Myapplication.option);
	        ImageLoader.getInstance().displayImage(cache_imageurl4, image4, Myapplication.option);
	        
	     
	        
	        //给新加入的header设置布局
	        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams
	        		(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);  	       
	       
	       header.setLayoutParams(layoutParams);	        	        	        
	       pull_listview.getRefreshableView().addHeaderView(header); 	         
	         
	       //检查是否有网
	       home.checkNet();
	       
	       
	       getDataFromServer();
	       
	       
           pull_listview.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(final PullToRefreshBase<ListView> refreshView) {
				 home.checkNet();	
				 getDataFromServer();
				   
				   //1秒后关掉下拉刷新,这样忘了请求在一秒内就完成了,保证数据得到更新
				   handler.postDelayed(new Runnable() {						
						@Override
						public void run() {							 							
											 
  				        if( shouye_arraylist!=null){
	 					shouye_adapter=new Shouye_adapter(context, shouye_arraylist);	
						 pull_listview.setAdapter(shouye_adapter);
						 shouye_adapter.notifyDataSetChanged();										
							} 							
							refreshView.onRefreshComplete(); //收起下拉刷新							
						}
					}, 2000); 	 
			} 			       
	       });   	        	        	      	      
		   //点击监听事件
	       pull_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				 System.out.println("你点击了"+position);
 	   		         Intent intent=new Intent(mactivity,DetailActivity_1.class);
		            intent.putExtra("account", shouye_arraylist.get(position-2).Id);
		             startActivity(intent);
 		       }
          });
        return view;
     }
      
   
		private void findview() {
			   //把pulltolistview上面所有组件单独存放在header布局文件中
	    	    header=View.inflate(mactivity, R.layout.shouye_list_header,null);
		        //轮播组件
		        mAdView = (ImageCycleView)header.findViewById(R.id.ad_view);//初始化控件  	   
		        //0元购，每日爆款，止痛贴
			       qianggou=(RelativeLayout) header.findViewById(R.id.qianggou);
			       meiri=(RelativeLayout) header.findViewById(R.id.meiri);
			       faxian=(RelativeLayout) header.findViewById(R.id.faxian);
			        
			       qianggou.setOnClickListener(this);
			       meiri.setOnClickListener(this);
			       faxian.setOnClickListener(this); 
			       
			        //四个中间的图片
			        image1=(ImageView) header.findViewById(R.id.image1);
			        image2=(ImageView) header.findViewById(R.id.image2);
			        image3=(ImageView) header.findViewById(R.id.image3);
			        image4=(ImageView) header.findViewById(R.id.image4);
	
			        image1.setOnClickListener(this);
			        image2.setOnClickListener(this);
			        image3.setOnClickListener(this);
			        image4.setOnClickListener(this);
			        
			        //pull_listview初始化
			        pull_listview=(PullToRefreshListView) view.findViewById(R.id.pull_listview);    	   	        
			        pull_listview.setMode(Mode.PULL_FROM_START); //仅仅支持下拉刷新
		     
			        progress=(ProgressBar) view.findViewById(R.id.progress);
		
		
		
		}


		private view.ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new view.ImageCycleView.ImageCycleViewListener() {  
      	  
	        @Override  
	        public void onImageClick(int position, View imageView) {  
	            System.out.println("轮播"+position);   
	        	Intent intent=new Intent(mactivity,DetailActivity_1.class);
	            intent.putExtra("account", lunbo.Results.get(position).Account);
	            intent.putExtra("detail_imageurl",AllUrl.URL_GBase+ lunbo.Results.get(position).ImgUrl);
	             startActivity(intent);
	             
	        }  
	  
	        @Override  
	        public void displayImage(String imageURL, ImageView imageView) {  
	            ImageLoader.getInstance().displayImage(imageURL, imageView,Myapplication.option);// 此处本人使用了ImageLoader对图片进行加装！  
	        }  
	    };    
      
 
	    
	    public void getDataFromServer(){
       	 
       	 //第一个请求
		    	 request1=new StringRequest(AllUrl.URL_Shouye_DiBu, new Listener<String>() {
		    	
					@Override
					public void onResponse(String response) {					 
						System.out.println("请求成功1");
				 		
						PraseWithGson1(response);					
					    System.out.println("解析完1");
					}				
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {					 
						System.out.println("网络不同1");
						 				
					}
				 });	    
       	   
       	   //第二个请求
	    	  request2=new StringRequest(AllUrl.URL_FirstBannerList, new Listener<String>() {
	 	    	
					@Override
					public void onResponse(String response) {					 
						System.out.println("请求成功2"); 
						PraseWithGson2(response);					
						 System.out.println("解析完2");
					}				
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {					 
						System.out.println("网络不同2");
						 				
					}
				 });	    
	    	    //第三个请求
		    	 request3=new StringRequest(AllUrl.URL_Shouye_list , new Listener<String>() {
		 	    	
						@Override
						public void onResponse(String response) {					 
							System.out.println("请求成功3");
							PraseWithGson3(response);					
							System.out.println("解析完3");
						}				
					}, new ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {					 
							System.out.println("网络不同3");
							 					
						}
					 });	    	
   
	    	  queue.add(request1);
	    	  queue.add(request2);
	    	  queue.add(request3);
          }
     
	  //第一个解析
		 public void PraseWithGson1(String response) {
 		
	  lunbo=gson.fromJson(response, Lunbo.class);
			
	  account1=lunbo.Results.get(0).Account;
	  account2=lunbo.Results.get(1).Account;
      account3=lunbo.Results.get(2).Account;
	  account4=lunbo.Results.get(3).Account;
      
	  
	  
	  
     ImageUrllist=new ArrayList<String>();
	  
     for(int i=0;i<lunbo.Results.size();i++)
        {
		  ImageUrllist.add(AllUrl.URL_GBase+lunbo.Results.get(i).ImgUrl);
          
        }
      if(ImageUrllist!=null){
     	//轮播
      mAdView.setImageResources(ImageUrllist, mAdCycleViewListener);//设置资源和监听事件  
      
      sp.edit().putString("cache_lunbo1", ImageUrllist.get(0));
      sp.edit().putString("cache_lunbo2", ImageUrllist.get(1));
      sp.edit().putString("cache_lunbo3", ImageUrllist.get(2));
      sp.edit().putString("cache_lunbo4", ImageUrllist.get(3));
      
       } 
     
		}
		 
		 //第二个解析
		 public void PraseWithGson2(String response) {
			  zhongjian=gson.fromJson(response, Zhongjian.class);	 
		     url_image1=zhongjian.Results.get(0).BannerPic;
		     url_image2=zhongjian.Results.get(1).BannerPic;
		     url_image3=zhongjian.Results.get(2).BannerPic;
		     url_image4=zhongjian.Results.get(3).BannerPic;
		 
		     SpecialId_1=zhongjian.Results.get(0).SpecialId;
		     SpecialId_2=zhongjian.Results.get(1).SpecialId;
		     SpecialId_3=zhongjian.Results.get(2).SpecialId;
		     SpecialId_4=zhongjian.Results.get(3).SpecialId;
		    
 	     
		   //中间图片
	        	if(url_image1!=null){
	        		ImageLoader.getInstance().displayImage( url_image1,image1,Myapplication.option);	
	        		sp.edit().putString("image1", url_image1).commit();
	        	} 
       	
	        	//中间图片
	        	if(url_image2!=null){
	        		 ImageLoader.getInstance().displayImage( url_image2,image2,Myapplication.option);	
	        		 sp.edit().putString("image2", url_image2).commit();
	        	}
	        	//中间图片
	        	if( url_image3!=null){
	        		ImageLoader.getInstance().displayImage( url_image3,image3,Myapplication.option);	
	        		sp.edit().putString("image3", url_image3).commit();
	        	}
	        	//中间图片
	        	if( url_image4!=null){
	        		ImageLoader.getInstance().displayImage( url_image4,image4,Myapplication.option);	
	        		sp.edit().putString("image4", url_image4).commit();
	        	}   
		     
	        	
	        	
		 }
 		
		 //第三个解析
		 public void PraseWithGson3(String response) {
			 shouye_list=gson.fromJson(response, Shouye_list.class);	 
			//首页的Listview集合
			 shouye_arraylist=shouye_list.Results;
		 	 if( shouye_arraylist!=null){
	        	 //listview
	         	shouye_adapter=new Shouye_adapter(context, shouye_arraylist);	
	            pull_listview.setAdapter(shouye_adapter);
	         	
	        }else{
	        	System.out.println("为空");
	         }
		 	 progress.setVisibility(View.GONE);
		 }
      
	     @Override
		public void onResume() {  
	        super.onResume();  
	         mAdView.startImageCycle();  
	      };  
	  
	    @Override
		public void onPause() {  
	        super.onPause();  
	       mAdView.pushImageCycle();  
	    }  
	  
	    @Override
		public void onDestroy() {  
	     	super.onDestroy();  
	         
	    }  
	    
	    @Override
	    public void onDestroyView() {
	    	super.onDestroyView();
	      mAdView.pushImageCycle(); 
	    
	    }
 
		@Override
		public void onClick(View v) {
			 
			switch(v.getId()){
			case R.id.qianggou: 
				Intent intent=new Intent(mactivity,DetailActivity_1.class);
	            intent.putExtra("account","61");
				startActivity(intent);
				
				break;
			
			case R.id.meiri: 
				
				 startActivity(new Intent(mactivity,MeiriActivity.class));
	 		break;
			case R.id.faxian: 
		   	HomeActivity.tabhost.setCurrentTab(2); 
	 			 break;	
	 		
			case R.id.image1: 
				Intent intent1=new Intent(mactivity,DetailActivity_1.class);
	            intent1.putExtra("account",SpecialId_1);
				startActivity(intent1);
	  		break;
			
			 case R.id.image2: 
				
				 Intent intent2=new Intent(mactivity,DetailActivity_1.class);
				 intent2.putExtra("account",SpecialId_2);
				 startActivity(intent2);
	 		
				break; 
			
			case R.id.image3: 
				Intent intent3=new Intent(mactivity,DetailActivity_1.class);
				intent3.putExtra("account",SpecialId_3);
				startActivity(intent3);
				break;
			
			case R.id.image4: 
	 			 startActivity(new Intent(mactivity,HaiWaiActivity.class));
	 		break;
			
			}
			
			
			
			
			
			
			
		} 	    	    	     
}
