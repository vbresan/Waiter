package biz.binarysolutions.waiter.activities.send.order;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import android.os.Bundle;
import biz.binarysolutions.waiter.R;
import biz.binarysolutions.waiter.activities.RequestURLBuilder;
import biz.binarysolutions.waiter.activities.send.SendActivity;
import biz.binarysolutions.waiter.data.OrderItem;

/**
 * 
 *
 */
public class SendOrderActivity extends SendActivity {
	
	private int waiterId;
	private int tableId;
	
	private ArrayList<OrderItem> order; 

	/**
	 * 
	 * @return
	 */
	private String getOrder() {
		
		JSONArray array = new JSONArray();
		for (OrderItem orderItem : order) {
			array.put(orderItem.toJSONObject());
		}
		
		return array.toString();
	}

	@Override
	protected void getExtras() {
	
		Bundle extras = getIntent().getExtras();
	    if(extras != null) {
	    	
	    	String packageName = getPackageName();
	    	String key;
	    	
	    	key = packageName + getString(R.string.app_extras_waiter_id);
	    	waiterId = extras.getInt(key);
	    	
	    	key = packageName + getString(R.string.app_extras_table_id);
	    	tableId = extras.getInt(key);
	    	
	    	key = packageName + getString(R.string.app_extras_order);
	    	order = (ArrayList<OrderItem>) extras.getSerializable(key);
	    }
	}

	@Override
	protected List<NameValuePair> getParameters() {
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("waiterId", "" + waiterId));
		nameValuePairs.add(new BasicNameValuePair("tableId", "" + tableId));
		nameValuePairs.add(new BasicNameValuePair("order", getOrder()));

		return nameValuePairs;
	}

	@Override
	protected String getRequestURL() {
		return RequestURLBuilder.sendOrder(this); 
	}
}
