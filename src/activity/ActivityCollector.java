package activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class ActivityCollector {
      public static List<Activity> activities=new ArrayList<Activity>();  
	
     //添加activity 
	 public static void addActivity(Activity activity){
		 activities.add(activity);
	 }
	
	//移除activity
	 public static void removeActivity(Activity activity){
		 activities.remove(activity);  
  	 
	 }
	
 
	 //销毁所有activity
	 public static void removeAll(){
		 for(Activity activity :activities){
			 if(!activity.isFinishing()){
				 activity.finish();
			 }
	 	 
		 }
 
	 }
	 
	 
	
	
}
