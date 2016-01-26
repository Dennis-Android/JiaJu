package domain;

import java.util.ArrayList;

public class Lunbo {
    
 
	    
		public ArrayList<Results1>  Results ;   
	    
 
		@Override
		public String toString() {
			return "Lunbo [Results=" + Results + "]";
		}

 

		public static class Results1{ 
		
		 
			public String Account;
		    public String ImgUrl;
			@Override
			public String toString() {
				return "Results1 [Account=" + Account + ", ImgUrl=" + ImgUrl + "]";
			}
	 	            
		}	
	    

	}

    

 
