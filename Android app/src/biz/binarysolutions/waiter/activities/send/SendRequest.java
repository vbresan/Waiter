package biz.binarysolutions.waiter.activities.send;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Handler;

/**
 * 
 *
 */
public class SendRequest extends Thread {
	
	private static final int STATUS_OK = 200;

	private Handler handler;
	private String  requestURL;
	private List<NameValuePair> parameters;

	/**
	 * 
	 * @param httpResponse
	 * @return
	 */
	private boolean isHttpResponseOK(HttpResponse httpResponse) {
	
		if (httpResponse != null) {
			
			StatusLine statusLine = httpResponse.getStatusLine();
			if (statusLine != null) {
				
				int status = statusLine.getStatusCode();
				if (status == STATUS_OK) {
					return true;
				}
			}
		}
		
		return false;
	}

	/**
	 * 
	 * @param handler
	 * @param requestURL
	 * @param parameters
	 */
	public SendRequest
		(
				Handler handler, 
				String  requestURL,
				List<NameValuePair> parameters
		) {
		
		this.handler    = handler;
		this.requestURL = requestURL;
		this.parameters = parameters;
	}

	@Override
	public void run() {
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost   httpPost   = new HttpPost(requestURL);
		
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
			
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (isHttpResponseOK(httpResponse)) {
				handler.sendEmptyMessage(MessageStatus.DONE);
			} else {
				handler.sendEmptyMessage(MessageStatus.DATA_ERROR);
			}
			
		} catch (Exception e) {
			handler.sendEmptyMessage(MessageStatus.ERROR_CONNECTING_TO_SERVER);
		} 
	}

}
