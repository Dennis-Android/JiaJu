package fragment;
 
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import util.AllUrl;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.R;
import com.google.gson.Gson;

import domain.Deatail_1_list;
import domain.Fenlei;
import domain.Deatail_1_list.Results4;
import domain.Fenlei.Results5;
 

import activity.GridView_DetailActivity;
import activity.HomeActivity;
import activity.Search_DetailActivity;
import adapter.GridviewAdapter;
import adapter.ListviewAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
 
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import application.MySingle;
import application.Myapplication;

public class Fragment2 extends Fragment {
    public  Activity mactivity;  
	public  EditText  search_EditText;   //搜索框内的内容
	public ImageView  search_image;      //搜索按钮
	public ListView fenlei_listView;     //分类的Listview
	public GridView fenlei_gridView;     //分类的gridview
	public RequestQueue queue;
	public Gson gson=new Gson();
	public ArrayList<Results5>  listview_list;  //listview列表数据
	public static ArrayList<Results5>  Gridview_list=new ArrayList<Results5>();  
	public static ArrayList<Results5>  Gridview_list1=new ArrayList<Results5>();   //第一个GridView的数据
	public static ArrayList<Results5>  Gridview_list2=new ArrayList<Results5>();   
	public static ArrayList<Results5>  Gridview_list3=new ArrayList<Results5>();   
	public static ArrayList<Results5>  Gridview_list4=new ArrayList<Results5>();   
	public static ArrayList<Results5>  Gridview_list5=new ArrayList<Results5>();   
	public static ArrayList<Results5>  Gridview_list6=new ArrayList<Results5>();   
	public static ArrayList<Results5>  Gridview_list7=new ArrayList<Results5>();   
	public static ArrayList<Results5>  Gridview_list8=new ArrayList<Results5>();   
	public static ArrayList<Results5>  Gridview_list9=new ArrayList<Results5>();  
	 
	public String input;
	
	public ListviewAdapter adapter;
	public GridviewAdapter g_adapter;
	public String searchUrl;  //搜索的url
	
	public String in;
   
	public Context context;
 
	public static int xposition;
	public View view;     //缓存Fragment view
	
	 public HomeActivity home;
	
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
    	 
    	  if(view==null){
     	 	  view=View.inflate(mactivity, R.layout.fragment2, null);	
            }
  	    //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。  
          ViewGroup parent = (ViewGroup) view.getParent();  
          if (parent != null) {  
              parent.removeView(view);  
              return view;
          }    
    	  
    
    	  search_EditText=(EditText) view.findViewById(R.id.search_EditText);
    	  search_image=(ImageView) view.findViewById(R.id.search_image);
    	  fenlei_listView=(ListView) view.findViewById(R.id.fenlei_listView);
    	  fenlei_gridView=(GridView) view.findViewById(R.id.fenlei_gridView);
     
    	  home.checkNet();
    	  
    	  
    	  
         queue=MySingle.getInstance(context).getRequestQueue();
    	  //请求网络
    	  getDataFromrServer();
    
	    
	    //搜索内容
    	  search_image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				input=search_EditText.getText().toString().trim();
				
				if(TextUtils.isEmpty(input)){
			 		return;
				} else{
					
				  try {
					 in=URLEncoder.encode(input, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					 
					e.printStackTrace();
				} //把中文转换成UTF-8
				  searchUrl=AllUrl.URL_Base+"/p.ashx?m=searchProduct&key="+in+"&pageNum=1&pageSize=20";	
			 	  Intent intent=new Intent(mactivity,Search_DetailActivity.class); 
		          intent.putExtra("searchUrl",searchUrl);
			      System.out.println("跳转");
		 	      
		 	      startActivity(intent);
		 	      
				}
	   
				
				
				
				
			}

			
		});
    	  
    	  
    	  fenlei_listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				xposition=position;  //当前选中位置，传给adapter
                adapter.notifyDataSetChanged();
         
                switch(position){
                case 0:
                Gridview_list=Gridview_list1;  
                g_adapter.notifyDataSetChanged();
                break;
                case 1:
                	Gridview_list=Gridview_list2;  
                    g_adapter.notifyDataSetChanged(); 
                    break; 
                case 2:
                	Gridview_list=Gridview_list3;  
                    g_adapter.notifyDataSetChanged(); 
                    break;
                case 3:
                	Gridview_list=Gridview_list4;  
                    g_adapter.notifyDataSetChanged(); 
                    break;
                case 4:
                	Gridview_list=Gridview_list5;  
                    g_adapter.notifyDataSetChanged();
                    break;
                case 5:
                	Gridview_list=Gridview_list6;  
                    g_adapter.notifyDataSetChanged(); 
                    break;
                case 6:
                	Gridview_list=Gridview_list7;  
                    g_adapter.notifyDataSetChanged(); 
                    break;
                case 7:
                	Gridview_list=Gridview_list8;  
                    g_adapter.notifyDataSetChanged(); 
                    break;
                case 8:
                	Gridview_list=Gridview_list9;  
                    g_adapter.notifyDataSetChanged(); 
                    break;
      
                
                }
                
                
                   
                
                
		        
				
			} });
    	  
   
    	  //GridView设置监听
    	  fenlei_gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				  Intent intent=new Intent(mactivity,GridView_DetailActivity.class); 
				  intent.putExtra("Id", Gridview_list.get(position).Id);
		 	      intent.putExtra("CategoryName", Gridview_list.get(position).CategoryName);
	   		      startActivity(intent);
			}
		});
    	  
    	  
    	  
    	  return view;
 

      
      
      }


	private void getDataFromrServer() {
		 StringRequest request=new StringRequest(AllUrl.URL_fenlei, new Listener<String>() {
			@Override
			public void onResponse(String response) {
				 Gsonwith(response); //解析			
			} 		
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				System.out.println("网络不通");				
			}
		}); 
		 queue.add(request);
	}	
	
	private void Gsonwith(String response) {
		Fenlei fenlei=gson.fromJson(response, Fenlei.class);
		 listview_list=new ArrayList<Results5>();
		 
		 
		 //防止重复
		 
		 Gridview_list1.clear();
		 Gridview_list2.clear();
		 Gridview_list3.clear();
		 Gridview_list4.clear();
		 Gridview_list5.clear();
		 Gridview_list6.clear();
		 Gridview_list7.clear();
		 Gridview_list8.clear();
		 Gridview_list9.clear();
		 
		  
		 
		 
		 for(int i=72;i<81;i++){
			 listview_list.add(fenlei.Results.get(i));
		 } 
	  	
		 //第1个页面数据
		 
		 for(int i=0;i<9;i++){
			 Gridview_list1.add(fenlei.Results.get(i));
		 } 
		 Gridview_list1.add(fenlei.Results.get(64));
		 Gridview_list1.add(fenlei.Results.get(65));
		 
		 
		 
		 //第2个页面数据
		 for(int i=9;i<15;i++){
			 Gridview_list2.add(fenlei.Results.get(i));
		 } 
		 Gridview_list2.add(fenlei.Results.get(66));
		 
		 
		//第3个页面数据
		 for(int i=15;i<21;i++){
			 Gridview_list3.add(fenlei.Results.get(i));
		 } 
		  
		//第4个页面数据
		 for(int i=21;i<32;i++){
			 Gridview_list4.add(fenlei.Results.get(i));
		 } 
		 
		//第5个页面数据
		 
	    Gridview_list5.add(fenlei.Results.get(71));
		 
		 
		 
		//第6个页面数据
		 for(int i=32;i<45;i++){
			 Gridview_list6.add(fenlei.Results.get(i));
		 } 
		 Gridview_list6.add(fenlei.Results.get(67));
		 Gridview_list6.add(fenlei.Results.get(68));
		 
		 
		//第7个页面数据
		 for(int i=45;i<51;i++){
			 Gridview_list7.add(fenlei.Results.get(i));
		 } 
			 
		//第8个页面数据
		 for(int i=51;i<60;i++){
			 Gridview_list8.add(fenlei.Results.get(i));
		 } 
		 
		 
		//第9个页面数据
		 for(int i=60;i<64;i++){
			 Gridview_list9.add(fenlei.Results.get(i));
		 } 
		 
		 //初始值
		 Gridview_list=Gridview_list1;
		 
		 
		 //listview
		 adapter=new ListviewAdapter(context,listview_list);
		 
		 fenlei_listView.setAdapter(adapter);  
		  //gridview
		 g_adapter=new GridviewAdapter(context);
		 fenlei_gridView.setAdapter(g_adapter);
		 
 
		  }
	  
     	
	  
	  
     }

 
