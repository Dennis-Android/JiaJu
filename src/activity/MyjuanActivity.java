package activity;

import com.app.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class MyjuanActivity extends BaseActivity implements OnClickListener {
                          
	public ImageView quan_back;
	 
	public CheckBox rel_quan_no;
	
	public CheckBox rel_quan_yes;
	
	public CheckBox rel_quan_last;
	 
	  @Override
       protected void onCreate(Bundle savedInstanceState) {
 
       super.onCreate(savedInstanceState);
        
       setContentView(R.layout.activity_myquan);
	  
       
       //检查网络
		 checkNet();
       
	  
       quan_back=(ImageView) findViewById(R.id.quan_back);
       rel_quan_no=(CheckBox) findViewById(R.id.rel_quan_no);
       rel_quan_yes=(CheckBox) findViewById(R.id.rel_quan_yes);
       rel_quan_last=(CheckBox) findViewById(R.id.rel_quan_last);
       
       
       quan_back.setOnClickListener(this);
       rel_quan_no.setOnClickListener(this);
       rel_quan_yes.setOnClickListener(this);
       rel_quan_last.setOnClickListener(this);
       
     
	  }

 

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.quan_back:
			onBackPressed();
			break;
        case R.id.rel_quan_no:
        	rel_quan_no.setChecked(true);
        	rel_quan_yes.setChecked(false);
        	rel_quan_last.setChecked(false);
        	
			break;
       case R.id.rel_quan_yes:
	
    	 rel_quan_no.setChecked(false);
       	rel_quan_yes.setChecked(true);
       	rel_quan_last.setChecked(false);
    	   
    	   
    	   
	        break;
       case R.id.rel_quan_last:
    	 rel_quan_no.setChecked(false);
       	rel_quan_yes.setChecked(false);
       	rel_quan_last.setChecked(true);
	        break;
		
		
		
		
		
		
		
		
		
		} 
		
		
		
		
	}
}
