package biz.binarysolutions.waiter;

import biz.binarysolutions.waiter.data.DisplayableOrderItem;
import android.app.Activity;

/**
 * 
 *
 */
public abstract class OrderActivity extends Activity {

	/**
	 * 
	 * @param displayableOrderItem
	 */
	public abstract void removeItemFromCurrentOrder(
			DisplayableOrderItem displayableOrderItem);
}
