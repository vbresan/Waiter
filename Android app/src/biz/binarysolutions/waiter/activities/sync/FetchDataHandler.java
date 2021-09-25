package biz.binarysolutions.waiter.activities.sync;

import android.os.Handler;
import android.os.Message;

/**
 * 
 *
 */
class FetchDataHandler extends Handler {
	
	private SyncDataActivity activity;

	/**
	 * 
	 * @param activity
	 */
	public FetchDataHandler(SyncDataActivity activity) {
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
			
		case MessageStatus.DONE_GET_TABLES:
			activity.onTablesAvailable(message.getData().getString("data"));
			break;
			
		case MessageStatus.DONE_GET_WAITERS:
			activity.onWaitersAvailable(message.getData().getString("data"));
			break;
			
		case MessageStatus.DONE_GET_CATEGORIES:
			activity.onCategoriesAvailable(message.getData().getString("data"));
			break;
			
		case MessageStatus.DONE_GET_ARTICLES:
			activity.onOrderItemsAvailable(message.getData().getString("data"));
			break;	
			
		case MessageStatus.DONE_GET_EXTRAS:
			activity.onExtrasAvailable(message.getData().getString("data"));
			break;
			
		case MessageStatus.DONE_GET_FAVORITES:
			activity.onFavoritesAvailable(message.getData().getString("data"));
			break;
	
		default:
			break;
		}
	}

}
