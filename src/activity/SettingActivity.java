package activity;

import com.app.R;

import fragment.Fragment5;
 
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends BaseActivity implements OnClickListener {
                          
	public ImageView setting_back;              //返回
    public RelativeLayout setting_rel_cleanHuancun; //清除缓存
 	public RelativeLayout setting_rel_kefudianhua;  //客服电话
	public RelativeLayout setting_rel_banbengengxin; //版本更新
    public TextView  tv_cacheSize;                   //缓存大小
	
    public SharedPreferences sp;
    public Button exist;
    public  String UserId;
    
	public Context context;  
       @Override
       protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        
       setContentView(R.layout.activity_setting);
       context=this;
	  
       //检查网络
		 checkNet();
       
       
       sp=getSharedPreferences("LoginData", MODE_PRIVATE);
       UserId=sp.getString("Id", null);  //用户ID
       
       setting_back=(ImageView) findViewById(R.id.setting_back);
       tv_cacheSize=(TextView) findViewById(R.id.tv_cacheSize);
       setting_rel_cleanHuancun=(RelativeLayout) findViewById(R.id.setting_rel_cleanHuancun);
       setting_rel_kefudianhua=(RelativeLayout) findViewById(R.id.setting_rel_kefudianhua);
       setting_rel_banbengengxin=(RelativeLayout) findViewById(R.id.setting_rel_banbengengxin);
       
       exist=(Button) findViewById(R.id.exist);
       
       setting_rel_cleanHuancun.setOnClickListener(this); 
       setting_rel_kefudianhua.setOnClickListener(this); 
       setting_rel_banbengengxin.setOnClickListener(this); 
       setting_back.setOnClickListener(this); 
       exist.setOnClickListener(this); 
       //当前缓存大小
         
       
       if(UserId==null){
    	   exist.setVisibility(View.GONE); 
       }
       
       
       
       
       
       
  	 try {
  		tv_cacheSize.setText(util.DataCleanManager.getTotalCacheSize(this));  
  	  } catch (Exception e) {
  	 	e.printStackTrace();
  	  }
    }


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.setting_back:
			onBackPressed();
			break;
       case R.id.setting_rel_kefudianhua:
     
			 Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:4009696876"));
			 context.startActivity(intent);		
			 
			
			break;
			
       case R.id.setting_rel_cleanHuancun:
	
    	   util.DataCleanManager.clearAllCache(SettingActivity.this);
			 try {
				 tv_cacheSize.setText(util.DataCleanManager.getTotalCacheSize(SettingActivity.this));
			} catch (Exception e) {
				 
				e.printStackTrace();
			} 
			Toast.makeText(SettingActivity.this,"清除成功", 0).show();
    	   
    	   
	        break;	
       case R.id.setting_rel_banbengengxin:
	        
    	   Toast.makeText(this,"当前已是最新版本", 0).show();
    	   
    	   
	       break;	
       case R.id.exist:
    	  AlertDialog.Builder dialog=new AlertDialog.Builder(context);
    	  dialog.setMessage("确定要退出吗");
    	  dialog.setPositiveButton("确认",new DialogInterface.OnClickListener() {
	 	@Override
			public void onClick(DialogInterface dialog, int which) {
		 	
				 sp.edit().putString("Id", null).commit();
				 sp.edit().putBoolean("Logined", false).commit();		
	 		     exist.setVisibility(View.GONE);
			    
 		}
                        
	 	}); 
    	  dialog.setNegativeButton("取消",new DialogInterface.OnClickListener() {
  		 	@Override
  			public void onClick(DialogInterface dialog, int which) {
  				dialog.dismiss(); 
  				
  			}
  		});  
    	   
    	   dialog.show();
	       
    	   break;		
			
	 	 
		} 
      }
 

	    @Override
	    public void onBackPressed() {
	    	 
	    	super.onBackPressed();
	      
	   
	    
	    
	    
	    }
                          

	    

}
