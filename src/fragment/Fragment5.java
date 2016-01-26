package fragment;
 
import java.util.HashMap;
import java.util.Map;

import view.CircleImageView;
 

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import activity.AboutUsActivity;
import activity.AddressListActivity;
import activity.AllordersActivity;
import activity.CollectorActivity;
import activity.DaifahuoActivity;
import activity.DaishouhuoActivity;
import activity.HomeActivity;
import activity.LoginActivity;
import activity.MyjuanActivity;
import activity.RegisteActivity;
import activity.SettingActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
 
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import application.Myapplication;

public class Fragment5 extends Fragment implements OnClickListener {
    public  Activity mactivity;  
    public View view;
    
    public LinearLayout ll_no_login; //登录和注册所在的LinearLayout
    public LinearLayout rel_top;
    
    public Button bt_regist;     //注册按钮
    public Button bt_login;       //登录按钮
    
    public ImageView  myself_rel_daifukuan;   //待付款按钮
    public ImageView myself_rel_daifahuo;      //待发货
    public ImageView myself_rel_daishouhuo;    //待收货
    
    public SharedPreferences sp;
    
    public String  Score;
    public String  NickName;
    public String  Photo;
    public String  CartCount;
    public String UserId;
    
    public  RelativeLayout rel_aboutus; //关于我们
    public  RelativeLayout rel_myShouhuoDizhi;//地址管理
    
    public  RelativeLayout rel_myYouHuiQuan;  //优惠卷
    public  RelativeLayout rel_myalldindan;    //全部订单
    public  RelativeLayout rel_mysave;          //我的收藏
    public  RelativeLayout setting_rel_kefudianhua; //客服电话
    public  RelativeLayout rel_setting;            //设置
   
    public HomeActivity home;
    
    public Context context;  
    
  public CircleImageView img_touxiang;  //头像
  public TextView myself_nickname;      //用户名
  public TextView myself_text_jifen;    //积分
  public Bitmap bitmap;
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
        
    	 view=View.inflate(mactivity, R.layout.fragment5, null);	
    	  
        
    	 home.checkNet();
    	 
    	 
    	 
    	 
    	ll_no_login=(LinearLayout) view.findViewById(R.id.ll_no_login); 
    	rel_top=(LinearLayout) view.findViewById(R.id.rel_top);
    	img_touxiang=(CircleImageView) view.findViewById(R.id.img_touxiang);
    	myself_nickname=(TextView) view.findViewById(R.id.myself_nickname);
    	myself_text_jifen=(TextView) view.findViewById(R.id.myself_text_jifen);
     	rel_aboutus=(RelativeLayout) view.findViewById(R.id.rel_aboutus);
    	rel_myShouhuoDizhi=(RelativeLayout) view.findViewById(R.id.rel_myShouhuoDizhi);
    	rel_myYouHuiQuan=(RelativeLayout) view.findViewById(R.id.rel_myYouHuiQuan);
    	rel_myalldindan=(RelativeLayout) view.findViewById(R.id.rel_myalldindan);
    	rel_mysave=(RelativeLayout) view.findViewById(R.id.rel_mysave);
    	setting_rel_kefudianhua=(RelativeLayout) view.findViewById(R.id.setting_rel_kefudianhua);
    	 
    	rel_setting=(RelativeLayout) view.findViewById(R.id.rel_setting);
     	bt_login=(Button) view.findViewById(R.id.bt_login); 
    	bt_regist=(Button) view.findViewById(R.id.bt_regist); 
    	
    	myself_rel_daifukuan=(ImageView) view.findViewById(R.id.myself_rel_daifukuan);  
    	myself_rel_daifahuo=(ImageView) view.findViewById(R.id.myself_rel_daifahuo);  
    	myself_rel_daishouhuo=(ImageView) view.findViewById(R.id.myself_rel_daishouhuo); 
       
       
    	ll_no_login.setOnClickListener(this);
    	bt_login.setOnClickListener(this);
    	bt_regist.setOnClickListener(this);
    	myself_rel_daifukuan.setOnClickListener(this);
    	myself_rel_daifahuo.setOnClickListener(this);
    	myself_rel_daishouhuo.setOnClickListener(this);
     
    	rel_aboutus.setOnClickListener(this);
    	rel_myShouhuoDizhi.setOnClickListener(this);
    	rel_myalldindan.setOnClickListener(this);
    	rel_mysave.setOnClickListener(this);
    	setting_rel_kefudianhua.setOnClickListener(this);
    	
    	rel_myYouHuiQuan.setOnClickListener(this);
    	
    	rel_setting.setOnClickListener(this);
    	
     	bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.person);
    	
    	sp=context.getSharedPreferences("LoginData", context.MODE_PRIVATE);
        
    	boolean Logined=sp.getBoolean("Logined", false);
        
        if(Logined==true){
    	   ll_no_login.setVisibility(View.GONE);
    	   rel_top.setVisibility(View.VISIBLE);
        }else{
           ll_no_login.setVisibility(View.VISIBLE);
      	   rel_top.setVisibility(View.GONE);
        	
        }
       
        UserId=sp.getString("Id", null);  //用户ID
      
       Score=sp.getString("Score", null);
       NickName=sp.getString("NickName", null);
       Photo=sp.getString("Photo", null);
       CartCount=sp.getString("CartCount", null);
       
       if(Photo!=null){
    	  ImageLoader.getInstance().displayImage(Photo, img_touxiang, Myapplication.option);  
       }else{
   	   img_touxiang.setImageBitmap(bitmap);
       }
       
       myself_nickname.setText(NickName);
       myself_text_jifen.setText(Score); 
    
    	  return view;
 
 
}


	@Override
	public void onClick(View v) {
		 switch(v.getId()){
		 case R.id.ll_no_login:
		     
			 break;
		 case R.id.bt_regist:
			 startActivity(new Intent(mactivity,RegisteActivity.class));
 	 
			 
			 break;
		 case R.id.bt_login:
			 startActivity(new Intent(mactivity,LoginActivity.class));
			 
			 
			 
			 break;
		 case R.id.myself_rel_daifukuan:
			 
			 if(UserId!=null){
			 startActivity(new Intent(mactivity,DaishouhuoActivity.class)); 
			 }else{
				 startActivity(new Intent(mactivity,LoginActivity.class));  
		     }
			 
			 break; 
		 case R.id.myself_rel_daifahuo:
			 if(UserId!=null){
			 startActivity(new Intent(mactivity,DaifahuoActivity.class)); 
			 }else{
				 startActivity(new Intent(mactivity,LoginActivity.class));  
		     }
			 break;
		 case R.id.myself_rel_daishouhuo:
			 if(UserId!=null){
			 startActivity(new Intent(mactivity,DaishouhuoActivity.class)); 
			 }else{
				 startActivity(new Intent(mactivity,LoginActivity.class));  
		     }
			 break;
		 //关于我们
		 case R.id.rel_aboutus:
			startActivity(new Intent(mactivity,AboutUsActivity.class));
	 	 
			 break;
		 //地址管理
		 case R.id.rel_myShouhuoDizhi:
			 if(UserId!=null){
			 startActivity(new Intent(mactivity,AddressListActivity.class));
			 }else{
				 startActivity(new Intent(mactivity,LoginActivity.class));  
		     }
			 break;
			    //全部订单
		 case R.id.rel_myalldindan:
				
			 if(UserId!=null){
			 startActivity(new Intent(mactivity,AllordersActivity.class));
			 }else{
				 startActivity(new Intent(mactivity,LoginActivity.class));  
		     }   
		 		 break;
				//我的收藏
		 case R.id.rel_mysave:
			 if(UserId!=null){
			 startActivity(new Intent(mactivity,CollectorActivity.class));
			 }else{
				 startActivity(new Intent(mactivity,LoginActivity.class));  
		     }
				 break;
				//客服电话
		 case R.id.setting_rel_kefudianhua:
				
			 Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:4009696876"));
			 context.startActivity(intent);		 
					 
		 		 break;
					//优惠卷
		 case R.id.rel_myYouHuiQuan:
			 if(UserId!=null){
		 	 startActivity(new Intent(mactivity,MyjuanActivity.class));
			 }else{
				 startActivity(new Intent(mactivity,LoginActivity.class));  
		     }
				 break;
		 case R.id.rel_setting:
				
			 startActivity(new Intent(mactivity,SettingActivity.class));
	 		 break;
	            }
   	       }
	 
            
	 
	
	
	
	
	
	
	                             
	 @Override
	public void onResume() {
	 	super.onResume();
	 	 UserId=sp.getString("Id", null);  //用户ID
	 	 if(UserId==null){
	  	 ll_no_login.setVisibility(View.VISIBLE);
	     rel_top.setVisibility(View.GONE);
	 
	 }
	            
	
	 }
	
}
