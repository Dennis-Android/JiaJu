package domain;

 

public class Login {
     public  String  Results;  
     public  String  Status;
     public  String Id;
     public  String NickName;   //用户名
     public  String Score ;  //积分
    
     public  String auth;   
     public  String Photo;   //头像
     public  String CartCount;  //购物车数量
	@Override
	public String toString() {
		return "Login [Results=" + Results + ", Status=" + Status + ", Id="
				+ Id + ", NickName=" + NickName + ", Score=" + Score
				+ ", auth=" + auth + ", Photo=" + Photo + ", CartCount="
				+ CartCount + "]";
	}
     
     
}
