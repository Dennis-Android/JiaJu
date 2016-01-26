package domain;

import java.util.ArrayList;

public class Fenlei {
     public ArrayList<Results5>  Results;

     public class Results5{
    	 public String CategoryIcon;    //图片
    	 public	String CategoryName;    //名称           
    	 public String Id;              //id
		
   
    	 
    	 
    	 @Override
		 public String toString() {
			return "Results5 [CategoryIcon=" + CategoryIcon + ", CategoryName="
					+ CategoryName + ", Id=" + Id + "]";
		}
 
     } 
}
