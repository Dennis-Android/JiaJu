package domain;

import java.util.ArrayList;
 

public class Shouye_list {
	public ArrayList<Results3>  Results ; 

	public static class Results3{ 
	
	public String Id;            //activityid
	public String ActiveName;    //listview下面文本
	public String ActivityPic;   //listview图片
	public String DesGuide ;     //"满2件包邮",
	public String DiscountDes;   //"5折起"
	public String EndTime ;      //  2015/12/9 22:48:46
	public String StartTime ;    //  2015/11/10 8:00:00
	@Override
	public String toString() {
		return "Results3 [Id=" + Id + ", ActiveName=" + ActiveName
				+ ", ActivityPic=" + ActivityPic + ", DesGuide=" + DesGuide
				+ ", DiscountDes=" + DiscountDes + ", EndTime=" + EndTime
				+ ", StartTime=" + StartTime + "]";
	}
	 
	
	
	}
}
