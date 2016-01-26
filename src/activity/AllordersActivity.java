package activity;

import com.app.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class AllordersActivity extends BaseActivity {
                          
	
	   public ImageView order_back;
	
	   @Override
       protected void onCreate(Bundle savedInstanceState) {
 
       super.onCreate(savedInstanceState);
        
       setContentView(R.layout.activity_allorders);
	  
      
      //检查网络
  	  checkNet();
       
       
       order_back=(ImageView) findViewById(R.id.order_back);
       
       order_back.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			onBackPressed();
			
		}
	});
       
       
       Toast.makeText(this,"暂无数据", 0).show();
       
	  
	  
	  
	  }
}
