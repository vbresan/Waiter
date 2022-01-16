package biz.binarysolutions.waiter.activities.addorderitem;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import biz.binarysolutions.waiter.R;
import biz.binarysolutions.waiter.activities.ActivityRequestCodes;
import biz.binarysolutions.waiter.data.Article;
import biz.binarysolutions.waiter.data.OrderItem;

/**
 * 
 *
 */
public class GetOrderItemActivity extends Activity {

	private OrderItem orderItem;
	
	private ArrayList<Integer> extrasIds;
	private int extrasIndex = 0;

	/**
	 * 
	 * @return
	 */
	private Article getArticle() {

		Article article = null;
		
		Bundle bundle = getIntent().getExtras();
	    if(bundle != null) {
	    	
	    	String key = getPackageName() + getString(R.string.app_extras_article);
	    	article = (Article) bundle.get(key);
	    }
		
		return article;
	}
	
	/**
	 * 
	 * @param extrasId
	 */
	private void startSelectExtrasActivity(Integer extrasId) {
		
		Intent intent = new Intent(
			GetOrderItemActivity.this, 
			SelectExtrasActivity.class
		);
		
		String key = 
			getPackageName() + 
			getString(R.string.app_extras_extras_id);
		
		intent.putExtra(key, extrasId.intValue());
		startActivityForResult(
			intent, 
			ActivityRequestCodes.SELECT_EXTRAS
		);				
	}	
	
	/**
	 * 
	 */
	private void startNextSelectExtrasActivity() {

		if (extrasIndex >= 0 && extrasIndex < extrasIds.size()) {
			
			Integer extrasId = extrasIds.get(extrasIndex++);
			startSelectExtrasActivity(extrasId);
		}
	}	
	
	/**
	 * 
	 */
	private void finishActivity() {
		
		String key = 
			getPackageName() + 
			getString(R.string.app_extras_orderItem);
		
		Intent intent = new Intent();
		intent.putExtra(key, orderItem);
		
		setResult(RESULT_OK, intent);
		finish();	
	}	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Article article = getArticle();
		if (article != null) {
			
			orderItem = new OrderItem(article);
			if (orderItem.hasExtras()) {
				
				extrasIds = orderItem.getExtrasIds();
				startNextSelectExtrasActivity();
				
			} else {
				finishActivity();
			}
			
		} else {
			
			setResult(RESULT_CANCELED);
			finish();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) {
			
			Bundle bundle = data.getExtras();
			
			String packageName = getPackageName(); 
			String key;
			
			key = packageName + getString(R.string.app_extras_extras_id);
			int extrasId = bundle.getInt(key);
			
			key = packageName + getString(R.string.app_extras_extras_difference);
			String difference = bundle.getString(key);
			
			orderItem.addSelectedExtra(extrasId, difference);
			
			if (extrasIndex >= 0 && extrasIndex < extrasIds.size()) {
				
				Integer nextExtrasId = extrasIds.get(extrasIndex++);
				startSelectExtrasActivity(nextExtrasId);
			} else {
				finishActivity();
			}
		} else {
			
			if (extrasIndex - 2 >= 0) {
				
				extrasIndex -= 2;
				Integer nextExtrasId = extrasIds.get(extrasIndex++);
				startSelectExtrasActivity(nextExtrasId);
				
			} else {
				
				setResult(RESULT_CANCELED);
				finish();
			}
		}
	}
}
