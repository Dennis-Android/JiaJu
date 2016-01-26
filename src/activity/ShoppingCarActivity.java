package activity;

import com.app.R;

import fragment.Fragment4;
 
 
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
 

public class ShoppingCarActivity extends  BaseActivity{
              
	  
	   @Override
	   public void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
 	
		setContentView(R.layout.activity_shoppingcar);
 
		 //检查网络
		 checkNet();
		
		
       Fragment4 f4=new Fragment4(); 
       getSupportFragmentManager().beginTransaction().replace(R.id.shop_fl, f4).commit();
	 
	   
	   
	  
	   
	   
	   
	   }
 
}
