package domain;

import java.util.ArrayList;
     

    public class Find {
 	 
    public  ArrayList<Results8> Results;
  
   


	@Override
	public String toString() {
		return "Find [Results=" + Results + "]";
	}









	public  class Results8{
    	 
      public String	   ActivityShare; //点击详情页2的网址    	 
      public String	   Content;  //内容
   	  public String     NickName;
  	  public String	   ImageUrl;             //中间大图片
      public String	   Photo;
      public int	   ReviewCount;
  	  public String	   ShareUrl;
      public int	   SomePraiseCount;
      public String Id;   
     
     
      
      
      public ArrayList<UserReview1>  UserReview;
   	  
      
     





	public   class  UserReview1{
        	
   		  
   		  
   		    public String UserId;   
        	public String Content;       //用户的评论
        	public String ReviewTime;    //评论的时间
			@Override
			public String toString() {
				return "UserReview1 [UserId=" + UserId + ", Content=" + Content
						+ ", ReviewTime=" + ReviewTime + "]";
			}
			 
        } 
   }
 
}
