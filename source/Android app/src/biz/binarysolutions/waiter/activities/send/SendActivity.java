package biz.binarysolutions.waiter.activities.send;

import java.util.List;

import org.apache.http.NameValuePair;

import biz.binarysolutions.waiter.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

/**
 * 
 *
 */
public abstract class SendActivity extends Activity {
	
	/**
	 * 
	 */
	protected abstract void getExtras();

	/**
	 * 
	 * @return
	 */
	protected abstract List<NameValuePair> getParameters();

	/**
	 * 
	 * @return
	 */
	protected abstract String getRequestURL();

	/**
	 * 
	 */
	private void sendRequest(String requestURL) {
		
		Handler handler = new SendRequestHandler(this);
		List<NameValuePair> parameters = getParameters();
		
		new SendRequest(handler, requestURL, parameters).start();
	}	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progress);
		
		getExtras();
		
		String requestURL = getRequestURL();
		sendRequest(requestURL);
	}	

	/**
	 * 
	 */
	public void onConnectionError() {
		// TODO handle error
		finish();
	}

	/**
	 * 
	 */
	public void onDataError() {
		// TODO Auto-generated method stub
		finish();
	}

	/**
	 * 
	 */
	public void onSent() {
	
		setResult(RESULT_OK);
		finish();
	}

}
