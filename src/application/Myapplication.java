package application;
 
 

import org.litepal.LitePalApplication;
 

import com.app.R;
 
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
 
import android.content.Context;
import android.graphics.Bitmap;
 
public class Myapplication extends LitePalApplication{
	public static ImageLoaderConfiguration config; 
	public static DisplayImageOptions option;       
    public Context context;          
    
    
    /*  
	 *  自定义的application 
	 */
	@Override
	public void onCreate() {
	
		super.onCreate();
	 	
	   //创建默认的配置参数
		 config=ImageLoaderConfiguration.createDefault(this);
		//初始化配置
	    ImageLoader.getInstance().init(config);
		 
		
		//显示图片的参数设置
		 option=new DisplayImageOptions.Builder()
		 .showImageOnFail(R.drawable.loading_failure) //加载失败显示的图片	    	 
	     .showImageOnLoading(R.drawable.loading)  //加载中显示的图片
	     .cacheInMemory(true) //受否缓存在内存中	    	 
	     .cacheOnDisk(true)   //受否缓存在SK卡中	    	 
	     .bitmapConfig(Bitmap.Config.RGB_565)
	     .build();
	  
	      }  
     
	 
	      
}
