package biz.binarysolutions.waiter.activities.send;

import android.os.Handler;
import android.os.Message;

/**
 * 
 *
 */
public class SendRequestHandler extends Handler {

	private SendActivity activity;

	/**
	 * 
	 * @param activity
	 */
	public SendRequestHandler(SendActivity activity) {
		super();
		
		this.activity = activity;
	}

	/**
	 * 
	 */
	public void handleMessage(Message message) {
		
		switch (message.what) {
		
		case MessageStatus.ERROR_CONNECTING_TO_SERVER:
			activity.onConnectionError();
			break;
			
		case MessageStatus.DATA_ERROR:
			activity.onDataError();
			break;
			
		case MessageStatus.DONE:
			activity.onSent();
			break;			
	
		default:
			break;
		}
	}

}
