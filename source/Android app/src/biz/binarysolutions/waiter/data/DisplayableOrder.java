package biz.binarysolutions.waiter.data;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * 
 *
 */
@SuppressWarnings("serial")
public class DisplayableOrder 
	extends LinkedHashMap<String, DisplayableOrderItem> {

	/**
	 * 
	 * @return
	 */
	public ArrayList<OrderItem> getOrderItems() {
		
		ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
		
		for (DisplayableOrderItem displayableOrderItem : values()) {
			orderItems.add(new OrderItem(displayableOrderItem));
		}
		
		return orderItems; 
	}

}
