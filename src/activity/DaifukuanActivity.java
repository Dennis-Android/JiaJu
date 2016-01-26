package activity;

import com.app.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class DaifukuanActivity extends BaseActivity {
                          
	
	public ImageView back;
	public TextView  textview_title;
	
	  @Override
       protected void onCreate(Bundle savedInstanceState) {
 
       super.onCreate(savedInstanceState);
        
       setContentView(R.layout.nodata);
     //检查网络
  	 checkNet();
	  
       back=(ImageView) findViewById(R.id.back);
       textview_title=(TextView) findViewById(R.id.textview_title);
       
       back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 onBackPressed();
		}
		});
       
       
       textview_title.setText("待付款");
       
	  
	  
	  
	  
	  }
}
