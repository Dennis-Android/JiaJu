package model;

import org.litepal.crud.DataSupport;

public class PingLunData extends DataSupport {
         private int id;
         private String BaskOrderId;
		
         public int getId() {
			return id;
		}
         public void setId(int id) {
			this.id = id;
		}
         public String getBaskOrderId() {
			return BaskOrderId;
		}
         public void setBaskOrderId(String baskOrderId) {
			BaskOrderId = baskOrderId;
		}
		
         
        
         
         
         
}
