package biz.binarysolutions.waiter.activities.send.storno;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import biz.binarysolutions.waiter.R;
import biz.binarysolutions.waiter.activities.RequestURLBuilder;
import biz.binarysolutions.waiter.activities.send.SendActivity;

/**
 * 
 *
 */
public class SendStornoActivity extends SendActivity {
	
	private int tableId;
	
	@Override
	protected void getExtras() {

		Bundle extras = getIntent().getExtras();
	    if(extras != null) {
	    	
	    	String packageName = getPackageName();
	    	String key;
	    	
	    	key = packageName + getString(R.string.app_extras_table_id);
	    	tableId = extras.getInt(key);
	    }
	}
	
	@Override
	protected List<NameValuePair> getParameters() {
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("tableId", "" + tableId));

		return nameValuePairs;
	}

	@Override
	protected String getRequestURL() {
		return RequestURLBuilder.sendStorno(this);
	}	
}
