package activity;

import com.app.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class CollectorActivity extends BaseActivity {
                          
	public ImageView save_back;
	 
	
	  @Override
       protected void onCreate(Bundle savedInstanceState) {
 
       super.onCreate(savedInstanceState);
        
       setContentView(R.layout.activity_save);
     //检查网络
  	 checkNet();
	  
       save_back=(ImageView) findViewById(R.id.save_back);
       
       save_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 onBackPressed();
 		}
		});
        
     
        
        
        
        
        
        
        
        
        
        
        
        
        
	  
	  
	  }
}
