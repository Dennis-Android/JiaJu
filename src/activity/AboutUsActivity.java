package activity;
 
 

import util.AllUrl;

import com.app.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutUsActivity extends BaseActivity {
   
	public ImageView aboutUs_back;
	
	public WebView webview;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
	 
	super.onCreate(savedInstanceState);
	  
	setContentView(R.layout.activity_aboutus);
    aboutUs_back=(ImageView) findViewById(R.id.aboutUs_back);
	webview=(WebView) findViewById(R.id.aboutus_web);
 
	//检查网络
	 checkNet();
	
	
	
	aboutUs_back.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			 onBackPressed();
 		}
	});
	
	
	 webview.getSettings().setJavaScriptEnabled(true);
     webview.getSettings().setDomStorageEnabled(true); //本地缓存
	
	
     webview.setWebViewClient(new WebViewClient(){
   	  @Override
     public boolean shouldOverrideUrlLoading(WebView view, String url) {
   	 
   	view.loadUrl(url);	  
   		  
   		  
   	return true;
   }});
	
     webview.loadUrl(AllUrl.URL_AboutUs); 
	
	
	
 
	
	
      
   
   } 
}
