package domain;

import java.util.ArrayList;

public class Address {
              
	public String  Status ;
	public ArrayList<Results11>   Results;
	
	@Override
	public String toString() {
		return "Address [Status=" + Status + ", Results=" + Results + "]";
	}

	public class  Results11{
		public String City;   //城市
		public String Country; //区县 
		public String DetailAddress; //具体地址
		public String Id;            //地址ID 
		public int IsDefault;    //是否默认
		public String Province;     //省
		public String PhoneTel;      //电话
		public String Regi;            //国家
		public String TrueName;         //姓名
		public String UserId;            //用户ID
		@Override
		public String toString() {
			return "Results11 [City=" + City + ", Country=" + Country
					+ ", DetailAddress=" + DetailAddress + ", Id=" + Id
					+ ", IsDefault=" + IsDefault + ", Province=" + Province
					+ ", PhoneTel=" + PhoneTel + ", Regi=" + Regi
					+ ", TrueName=" + TrueName + ", UserId=" + UserId + "]";
		}
	
	
	}
}
