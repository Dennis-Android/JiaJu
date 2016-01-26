package activity;
 
import com.app.R;
import com.bmob.pay.tool.BmobPay;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class SplashActivity extends BaseActivity {
	public ImageView splash_image;   //闪屏页动画
	public SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		//开始动画 
		startAnimation();   
			
		sp=getSharedPreferences("config", MODE_PRIVATE); 
           
		 //支付初始化
		 BmobPay.init(this,"d76dc748fb84ea53bf2af2e0493fc96a");
		
		
		
		
	}


	private void startAnimation() {
	    splash_image=(ImageView) findViewById(R.id.splash_image);
		AlphaAnimation anima=new AlphaAnimation(0, 1);
	    anima.setDuration(1500);
	    splash_image.startAnimation(anima);  
	    anima.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
			}			
			
			@Override
			public void onAnimationRepeat(Animation animation) {				 				
			}			
			
			@Override
			public void onAnimationEnd(Animation animation) {
				boolean isSetUp= sp.getBoolean("splashed", false); 
				 if(isSetUp){
					 Intent intent=new Intent(SplashActivity.this,HomeActivity.class);
					 startActivity(intent); 
					 finish();
				 }else{
					 Intent intent=new Intent(SplashActivity.this,GuideActivity.class);
				     startActivity(intent);
				     finish();}
				 
				
			}
		});





	}
}
