package application;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingle {

	private RequestQueue queue;
	private Context context;
	private static MySingle mSingle;
	
	
	
	private MySingle(Context context){
		this.context=context;
		queue=getRequestQueue(); 
	}

    
	public static synchronized MySingle getInstance(Context context){
		if(mSingle==null){
			mSingle=new MySingle(context);
		}
          return mSingle;
		
	}
	 

	public RequestQueue getRequestQueue() {
		if(queue==null){
			queue=Volley.newRequestQueue(context.getApplicationContext());
		} 
		
		return queue;
	} 
	
	 
	
	
	
	
	
	
	
	
	
	
	

}
