package domain;

import java.util.ArrayList;
 

public class Deatail_1_list {
	public String  Status;
	
	
	
	
	public ArrayList<Results4>  Results ; 
  
	@Override
	public String toString() {
		return "Deatail_1_list [Status=" + Status + ", Results=" + Results
				+ "]";
	}

 

	public static class Results4{ 
	
	public String MarketPrice;            //市场价
	public String PName;    //图片下面文字
	public String Pid;   
	public String SellPrice ;     //价格
	public String SkuNo;   //  图片要用
	public String DiscountDes ;      // 折扣  
	public String Id;
	public int Stock;      //存货s
	public String ActiveId;
	public String Account;      //  图片要用
	@Override
	public String toString() {
		return "Results4 [MarketPrice=" + MarketPrice + ", PName=" + PName
				+ ", Pid=" + Pid + ", SellPrice=" + SellPrice + ", SkuNo="
				+ SkuNo + ", DiscountDes=" + DiscountDes + ", Id=" + Id
				+ ", Stock=" + Stock + ", ActiveId=" + ActiveId + ", Account="
				+ Account + "]";
	}
	 
	 
	
	  
	   
}
}