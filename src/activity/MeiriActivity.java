package activity;

import util.AllUrl;

import com.app.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

public class MeiriActivity extends BaseActivity {
	public ImageButton back_image; 
	@Override
      protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_meiri);         
      
      //检查网络
		 checkNet();
      
      
      back_image=(ImageButton) findViewById(R.id.back_image);
      back_image.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
		 onBackPressed();
 	  }
	  });
     
      WebView webview=(WebView) findViewById(R.id.webview);
      
      webview.getSettings().setJavaScriptEnabled(true);
      webview.getSettings().setDomStorageEnabled(true); //本地缓存
      
      webview.setWebViewClient(new WebViewClient(){
    	  @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
    	 
    	view.loadUrl(url);	  
    		  
    		  
    	return true;
    }});
      
      webview.loadUrl(AllUrl.URL_Find);
      
      
     }
}
