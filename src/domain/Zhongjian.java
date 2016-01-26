package domain;

import java.util.ArrayList;
 

public class Zhongjian {
       
	   public ArrayList<Results2>  Results ;         
       
	   public static class Results2{
    	  public String SpecialId; 
    	
    	 public String BannerPic;

		@Override
		public String toString() {
			return "Results [SpecialId=" + SpecialId + ", BannerPic="
					+ BannerPic + "]";
		}
		 
		
    	 
           	   
    	   
       }  
}
