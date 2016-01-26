package activity;

 
 
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

public class BaseActivity extends FragmentActivity {
     
 
	 @Override
	protected void onCreate(Bundle arg0) {
		 
		super.onCreate(arg0);
		 
	 }
	
  
	 //检查网络
     public void checkNet() {
    	//判断网络是否连接
     	if(!util.NetworkUtils.isNetworkAvailable(this))
     	 {
             Toast.makeText(this, "请检查网络连接",0).show();     
     		 System.out.println("无连接");   
     		 return ;
     	}      
     }
      
	 
     
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 @Override
	protected void onDestroy() {
	 
		super.onDestroy();
         
	 }
	 
 
}
