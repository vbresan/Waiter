package biz.binarysolutions.waiter.activities;

import android.app.Activity;
import biz.binarysolutions.waiter.R;

/**
 * 
 *
 */
public class RequestURLBuilder {

	private static String baseURL;
	
	private static String getTablesURL;
	private static String getWaitersURL;
	private static String getCategoriesURL;
	private static String getArticlesURL;
	private static String getExtrasURL;
	private static String getFavoritesURL;
	private static String sendOrderURL;
	private static String sendStornoURL;
	
	/**
	 * 
	 * @param activity 
	 * @return
	 */
	private static String getBaseURL(Activity activity) {
		
		if (baseURL == null) {
			baseURL = activity.getString(R.string.app_url_sync_base);
		}

		return baseURL;
	}

	/**
	 * 
	 * @param activity 
	 * @return
	 */
	private static String getTablesURL(Activity activity) {
		
		if (getTablesURL == null) {
			getTablesURL = activity.getString(R.string.app_url_sync_getTables);
		}
	
		return getTablesURL;
	}

	/**
	 * 
	 * @param activity
	 * @return
	 */
	private static String getWaitersURL(Activity activity) {

		if (getWaitersURL == null) {
			getWaitersURL = activity.getString(R.string.app_url_sync_getWaiters);
		}
		
		return getWaitersURL;
	}

	/**
	 * 
	 * @param activity
	 * @return
	 */
	private static String getCategoriesURL(Activity activity) {
		
		if (getCategoriesURL == null) {
			getCategoriesURL = activity.getString(R.string.app_url_sync_getCategories);
		}

		return getCategoriesURL;
	}

	/**
	 * 
	 * @param activity
	 * @return
	 */
	private static String getArticlesURL(Activity activity) {
		
		if (getArticlesURL == null) {
			getArticlesURL = activity.getString(R.string.app_url_sync_getArticles);
		}
		
		return getArticlesURL;
	}

	/**
	 * 
	 * @param activity
	 * @return
	 */
	private static String getExtrasURL(Activity activity) {
		
		if (getExtrasURL == null) {
			getExtrasURL = activity.getString(R.string.app_url_sync_getExtras);
		}
		
		return getExtrasURL;
	}

	/**
	 * 
	 * @param activity
	 * @return
	 */
	private static String getFavoritesURL(Activity activity) {
		
		if (getFavoritesURL == null) {
			getFavoritesURL = activity.getString(R.string.app_url_sync_getFavorites);
		}
		
		return getFavoritesURL;
	}

	/**
	 * 
	 * @param activity
	 * @return
	 */
	private static String sendOrderURL(Activity activity) {
		
		if (sendOrderURL == null) {
			sendOrderURL = activity.getString(R.string.app_url_sendOrder);
		}

		return sendOrderURL;
	}
	
	/**
	 * 
	 * @param activity
	 * @return
	 */
	private static String sendStornoURL(Activity activity) {
		
		if (sendStornoURL == null) {
			sendStornoURL = activity.getString(R.string.app_url_sendStorno);
		}

		return sendStornoURL;
	}	

	/**
	 * 
	 * @param activity
	 * @return
	 */
	public static String getTables(Activity activity) {
		return getBaseURL(activity) + getTablesURL(activity);
	}

	/**
	 * 
	 * @param activity
	 * @return
	 */
	public static String getWaiters(Activity activity) {
		return getBaseURL(activity) + getWaitersURL(activity);
	}

	/**
	 * 
	 * @param activity
	 * @return
	 */
	public static String getCategories(Activity activity) {
		return getBaseURL(activity) + getCategoriesURL(activity);
	}

	/**
	 * 
	 * @param activity
	 * @return
	 */
	public static String getArticles(Activity activity) {
		return getBaseURL(activity) + getArticlesURL(activity);
	}

	/**
	 * 
	 * @param activity
	 * @return
	 */
	public static String getExtras(Activity activity) {
		return getBaseURL(activity) + getExtrasURL(activity);
	}

	/**
	 * 
	 * @param activity
	 * @return
	 */
	public static String getFavorites(Activity activity) {
		return getBaseURL(activity) + getFavoritesURL(activity);
	}

	/**
	 * 
	 * @param activity
	 * @return
	 */
	public static String sendOrder(Activity activity) {
		return getBaseURL(activity) + sendOrderURL(activity);
	}

	/**
	 * 
	 * @param activity
	 * @return
	 */
	public static String sendStorno(Activity activity) {
		return getBaseURL(activity) + sendStornoURL(activity);
	}

}
