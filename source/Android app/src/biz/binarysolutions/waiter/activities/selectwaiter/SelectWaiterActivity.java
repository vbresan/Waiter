package biz.binarysolutions.waiter.activities.selectwaiter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import biz.binarysolutions.waiter.R;
import biz.binarysolutions.waiter.activities.ActivityRequestCodes;
import biz.binarysolutions.waiter.data.Waiter;
import biz.binarysolutions.waiter.db.DatabaseAdapter;

/**
 * 
 *
 */
public class SelectWaiterActivity extends Activity {
	
	private Waiter selectedWaiter = null;
	
	/**
	 * @param waiters 
	 * 
	 */
	private void setWaiterButtons(ArrayList<Waiter> waiters) {
		
		GridView grid = (GridView) findViewById(R.id.GridViewWaiters);
		grid.setAdapter(new ButtonAdapter(this, waiters));
	}	

	/**
	 * 
	 */
	private void checkPin(String pin) {
		
		Intent intent = new Intent(this, CheckPinActivity.class);
		String key    = getPackageName() + getString(R.string.app_extras_pin);
		
		intent.putExtra(key, pin);
		
		startActivityForResult(intent, ActivityRequestCodes.CHECK_PIN);	}

	/**
	 * 
	 */
	private void finishActivity() {
	
		String key = getPackageName() + getString(R.string.app_extras_waiter);
	
		Intent intent = new Intent();
		intent.putExtra(key, selectedWaiter);
		
		setResult(RESULT_OK, intent);
		finish();		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.waiters);
		
		ArrayList<Waiter> waiters = DatabaseAdapter.getWaiters(this);
		setWaiterButtons(waiters);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == ActivityRequestCodes.CHECK_PIN &&
			resultCode  == RESULT_OK) {
			finishActivity();
		}
	}

	/**
	 * 
	 * @param waiter
	 */
	public void onWaiterSelected(Waiter waiter) {
		
		selectedWaiter = waiter;
		
		String pin = waiter.getPin();
		if (pin.length() > 0) {
			checkPin(pin);
		} else {
			finishActivity();
		}
	}
}
