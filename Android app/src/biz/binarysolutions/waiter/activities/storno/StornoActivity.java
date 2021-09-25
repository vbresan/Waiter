package biz.binarysolutions.waiter.activities.storno;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import biz.binarysolutions.waiter.OrderActivity;
import biz.binarysolutions.waiter.R;
import biz.binarysolutions.waiter.activities.ActivityRequestCodes;
import biz.binarysolutions.waiter.activities.selecttable.SelectTableActivity;
import biz.binarysolutions.waiter.activities.send.storno.SendStornoActivity;
import biz.binarysolutions.waiter.data.DisplayableOrderItem;
import biz.binarysolutions.waiter.data.OrderItem;
import biz.binarysolutions.waiter.db.DatabaseAdapter;

/**
 * 
 *
 */
public class StornoActivity extends OrderActivity {
	
	private int tableId;
	
	/**
	 * 
	 */
	private void clearOrderView() {
		
		LinearLayout view = 
			(LinearLayout) findViewById(R.id.LinearLayoutItems);
		
		view.removeAllViews();
	}	
	
	/**
	 * 
	 * @param orderItems
	 */
	private void displayOrder(ArrayList<OrderItem> orderItems) {
		
		for (OrderItem orderItem : orderItems) {
			new DisplayableOrderItem(orderItem, this).setButtonsEnabled(false);
		}
	}

	/**
	 * 
	 */
	private void startSelectTableActivity() {
		
		Intent intent = new Intent(this, SelectTableActivity.class);
		startActivityForResult(intent, ActivityRequestCodes.SELECT_TABLE);
	}

	/**
	 * 
	 * @param resultCode
	 * @param data
	 */
	private void processSelectTableResult(int resultCode, Intent data) {
	
		if (resultCode == RESULT_OK) {
			
			Bundle bundle = data.getExtras();
			String key = getPackageName() + getString(R.string.app_extras_table);
			
			tableId = bundle.getInt(key);
			setSelectTableButtonText(getString(R.string.Table) + " " + tableId);
			setSelectTableButtonEnabled(true);
			
			ArrayList<OrderItem> orderItems = 
				DatabaseAdapter.getSentOrderItems(this, tableId);
			
			clearOrderView();
			
			if (orderItems.size() > 0) {
				setStornoButtonEnabled(true);
				displayOrder(orderItems);
			} else {
				setStornoButtonEnabled(false);
			}
		}
	}

	/**
	 * 
	 * @param resultCode
	 * @param data
	 */
	private void processSendStornoResult(int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {
			
			DatabaseAdapter.deleteSentOrderItems(this, tableId);

			setResult(RESULT_OK);
			finish();
		}
	}

	/**
	 * 
	 */
	private void setSelectTableButtonEnabled(boolean enabled) {

		Button button = (Button) findViewById(R.id.ButtonSelectTable);
		button.setEnabled(enabled);
	}
	
	/**
	 * 
	 * @param selectedTable
	 */
	private void setSelectTableButtonText(String text) {

		Button button = (Button) findViewById(R.id.ButtonSelectTable);
		button.setText(text);
	}	
	
	/**
	 * 
	 */
	private void setSelectTableButtonListener() {
		
		Button button = (Button) findViewById(R.id.ButtonSelectTable);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startSelectTableActivity();				
			}
		});
	}	
	
	/**
	 * 
	 * @param enabled
	 */
	private void setStornoButtonEnabled(boolean enabled) {

		Button button = (Button) findViewById(R.id.ButtonStorno);
		button.setEnabled(enabled);
	}	
	
	/**
	 * 
	 */
	private void setStornoButtonListener() {

		Button button = (Button) findViewById(R.id.ButtonStorno);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				
				Intent intent = new Intent(
					StornoActivity.this, 
					SendStornoActivity.class
				);
				
				String packageName = getPackageName();
				String key;
				
				key = packageName + getString(R.string.app_extras_table_id);
				intent.putExtra(key, tableId);
				
				startActivityForResult(
					intent, 
					ActivityRequestCodes.SEND_STORNO
				);

			}
		});
	}

	/**
	 * 
	 */
	private void setButtons() {

		setSelectTableButtonEnabled(false);
		setSelectTableButtonListener();
		
		setStornoButtonEnabled(false);
		setStornoButtonListener();
	}	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.storno);
				
        setButtons();
        startSelectTableActivity();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (requestCode) {
		case ActivityRequestCodes.SELECT_TABLE:
			processSelectTableResult(resultCode, data);
			break;
			
		case ActivityRequestCodes.SEND_STORNO:
			processSendStornoResult(resultCode, data);
			break;			

		default:
			break;
		}
	}

	@Override
	public void removeItemFromCurrentOrder
		(
				DisplayableOrderItem displayableOrderItem
		) {
		// do nothing
	}	
}
