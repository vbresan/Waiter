package biz.binarysolutions.waiter;

import java.util.Collection;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import biz.binarysolutions.waiter.activities.ActivityRequestCodes;
import biz.binarysolutions.waiter.activities.addorderitem.GetOrderItemActivity;
import biz.binarysolutions.waiter.activities.addorderitem.SelectCategoryActivity;
import biz.binarysolutions.waiter.activities.addorderitem.SelectFavoriteActivity;
import biz.binarysolutions.waiter.activities.selecttable.SelectTableActivity;
import biz.binarysolutions.waiter.activities.selectwaiter.SelectWaiterActivity;
import biz.binarysolutions.waiter.activities.send.order.SendOrderActivity;
import biz.binarysolutions.waiter.activities.storno.StornoActivity;
import biz.binarysolutions.waiter.activities.sync.SyncDataActivity;
import biz.binarysolutions.waiter.data.Article;
import biz.binarysolutions.waiter.data.DisplayableOrder;
import biz.binarysolutions.waiter.data.DisplayableOrderItem;
import biz.binarysolutions.waiter.data.OrderItem;
import biz.binarysolutions.waiter.data.Table;
import biz.binarysolutions.waiter.data.Waiter;
import biz.binarysolutions.waiter.db.DatabaseAdapter;

/**
 * 
 *
 */
public class MainActivity extends OrderActivity {
	
	private Waiter           currentWaiter;
	private Table            currentTable;
	private DisplayableOrder currentOrder;
	
	private HashMap<Table, DisplayableOrder> orders = 
		new HashMap<Table, DisplayableOrder>();
	
	
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
	 */
	private void setAddArticleButtonListener() {
		
		Button button = (Button) findViewById(R.id.ButtonAddArticle);
		button.setOnClickListener(new OnClickListener() {

			/**
			 * 
			 */
			private void startSelectCategoryActivity() {

				Intent intent = new Intent(
					MainActivity.this, 
					SelectCategoryActivity.class
				);
				
				startActivityForResult(
					intent, 
					ActivityRequestCodes.GET_ORDER_ITEM
				);
			}

			/**
			 * 
			 * @param article
			 */
			private void startGetOrderItemActivity(Article article) {
				
				Intent intent = new Intent(
					MainActivity.this, 
					GetOrderItemActivity.class
				);
				
				String key = 
					getPackageName() + 
					getString(R.string.app_extras_article);
				
				intent.putExtra(key, article);
				startActivityForResult(
					intent, 
					ActivityRequestCodes.GET_ORDER_ITEM
				);	
			}

			/**
			 * @param editText
			 */
			private void clearEditText(EditText editText) {
				
				editText.setText("");
				editText.clearFocus();
				
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
			}

			@Override
			public void onClick(View v) {
				
				EditText editText = 
					(EditText) findViewById(R.id.EditTextArticleId);
				
				String text = editText.getText().toString();
				if (text != null && text.length() > 0) {
					
					try {
						int id = Integer.parseInt(text);
						
						Article article = 
							DatabaseAdapter.getArticle(MainActivity.this, id);
						if (article != null) {
							startGetOrderItemActivity(article);
						}
						
						clearEditText(editText);
						
					} catch (NumberFormatException e) {
						// TODO: do nothing?
					}
				} else {
					startSelectCategoryActivity();
				}
			}
		});
	}

	/**
	 * 
	 */
	private void setAddFavoriteButtonListener() {
		
		Button button = (Button) findViewById(R.id.ButtonAddFavorite);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(
					MainActivity.this, 
					SelectFavoriteActivity.class
				);
				
				startActivityForResult(
					intent, 
					ActivityRequestCodes.GET_ORDER_ITEM
				);				
			}
		});
	}

	/**
	 * 
	 */
	private void setSendOrderButtonListener() {

		Button button = (Button) findViewById(R.id.ButtonSendOrder);
		button.setOnClickListener(new OnClickListener() {

			/**
			 * 
			 * @param intent
			 */
			private void bundleSendOrderData(Intent intent) {
				
				String packageName = getPackageName();
				String key;
				
				key = packageName + getString(R.string.app_extras_waiter_id);
				intent.putExtra(key, currentWaiter.getId());
				
				key = packageName + getString(R.string.app_extras_table_id);
				intent.putExtra(key, currentTable.getId());
				
				key = packageName + getString(R.string.app_extras_order);
				intent.putExtra(key, currentOrder.getOrderItems());
			}

			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(
					MainActivity.this, 
					SendOrderActivity.class
				);
				
				bundleSendOrderData(intent);
				
				startActivityForResult(
					intent, 
					ActivityRequestCodes.SEND_ORDER
				);
			}
		});
	}
	
	/**
	 * 
	 */
	private void startSyncDataActivity() {
		
		Intent intent = new Intent(this, SyncDataActivity.class);
		startActivityForResult(intent, ActivityRequestCodes.SYNC_DATA);
	}

	/**
	 * 
	 */
	private void startSelectWaiterActivity() {
	
		Intent intent = new Intent(this, SelectWaiterActivity.class);
		startActivityForResult(intent, ActivityRequestCodes.SELECT_WAITER);
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
	 */
	private void startStornoActivity() {
		
		Intent intent = new Intent(this, StornoActivity.class);
		startActivityForResult(intent, ActivityRequestCodes.STORNO);	}

	/**
	 * 
	 */
	private void setSelectTableButtonEnabled(boolean enabled) {

		Button button = (Button) findViewById(R.id.ButtonSelectTable);
		button.setEnabled(enabled);
	}	

	/**
	 * 
	 * @param enabled
	 */
	private void setArticleIdEditTextEnabled(boolean enabled) {

		EditText editText = (EditText) findViewById(R.id.EditTextArticleId);
		editText.setEnabled(enabled);
	}

	/**
	 * 
	 */
	private void setAddArticleButtonEnabled(boolean enabled) {

		Button button = (Button) findViewById(R.id.ButtonAddArticle);
		button.setEnabled(enabled);
	}

	/**
	 * 
	 * @param enabled
	 */
	private void setAddFavoriteButtonEnabled(boolean enabled) {

		Button button = (Button) findViewById(R.id.ButtonAddFavorite);
		button.setEnabled(enabled);
	}

	/**
	 * 
	 * @param enabled
	 */
	private void setSendOrderButtonEnabled(boolean enabled) {

		Button button = (Button) findViewById(R.id.ButtonSendOrder);
		button.setEnabled(enabled);
	}

	/**
	 * 
	 */
	private void changeSendOrderButtonState() {
		
		if (currentOrder.size() > 0) {
			setSendOrderButtonEnabled(true);
		} else {
			setSendOrderButtonEnabled(false);
		}
	}

	/**
	 * 
	 */
	private void setButtons() {

		setSelectTableButtonEnabled(false);
		setSelectTableButtonListener();
		
		setArticleIdEditTextEnabled(false);
		
		setAddArticleButtonEnabled(false);
		setAddArticleButtonListener();
		
		setAddFavoriteButtonEnabled(false);
		setAddFavoriteButtonListener();
		
		setSendOrderButtonEnabled(false);
		setSendOrderButtonListener();
	}
	
	/**
	 * 
	 */
	private void logOutCurrentWaiter() {
		
		currentWaiter = null;
		
		setTitle(getString(R.string.app_name));
		setSelectTableButtonText(getString(R.string.SelectTable));
		
		setSelectTableButtonEnabled(false);
		setArticleIdEditTextEnabled(false);
		setAddArticleButtonEnabled(false);
		setAddFavoriteButtonEnabled(false);
	}

	/**
	 * 
	 * @param resultCode
	 * @param data
	 */
	private void processSyncDataResult(int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {
	
			//TODO: da li prikazat dialog nakon logiranja konobara?
			startSelectWaiterActivity();
		}
	}

	/**
	 * 
	 * @param resultCode
	 * @param data
	 */
	private void processSelectWaiterResult(int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {
			
			Bundle bundle = data.getExtras();
			String key = getPackageName() + 
				getString(R.string.app_extras_waiter);
			
			currentWaiter = (Waiter) bundle.get(key);
			
			//TODO: set title for other activities as well
			setTitle(getString(R.string.app_name) + ": " + currentWaiter.getName());
			setSelectTableButtonEnabled(true);
			
			startSelectTableActivity();
		} else {
			//TODO: must select currentWaiter
		}
	}
	
	/**
	 * 
	 */
	private void saveSentOrder() {

		final int  tableId   = currentTable.getId();
		final long timestamp = System.currentTimeMillis() / 1000L;
		final Collection<DisplayableOrderItem> items = currentOrder.values();

		new Thread() {

			@Override
			public void run() {
				
				DatabaseAdapter.saveSentOrderItems(
					MainActivity.this,
					tableId,
					timestamp,
					items
				);
			}
		}.start();
	}

	/**
	 * 
	 */
	private void saveCurrentOrder() {
		
		if (currentTable != null && 
		    currentOrder != null && 
		    currentOrder.size() > 0) {
			
			orders.put(currentTable, currentOrder);
		}
	}

	/**
	 * 
	 * @param tableId
	 */
	private void loadOrder(int tableId) {
		
		Table table = new Table(tableId);
		DisplayableOrder order = orders.get(table);
		if (order != null) {
		
			currentTable = table;
			currentOrder = order;
		} else {
			
			currentTable = table;
			currentOrder = new DisplayableOrder();
		}
		
		changeSendOrderButtonState();
	}

	/**
	 * 
	 */
	private void displayCurrentOrder() {

		LinearLayout parent = 
			(LinearLayout) findViewById(R.id.LinearLayoutItems);
		
		parent.removeAllViews();
		for (DisplayableOrderItem item : currentOrder.values()) {
			parent.addView(item.getView());
		}
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
			
			int tableId = bundle.getInt(key);
			setSelectTableButtonText(getString(R.string.Table) + " " + tableId);
			
			setArticleIdEditTextEnabled(true);
			setAddArticleButtonEnabled(true);
			setAddFavoriteButtonEnabled(true);
			
			saveCurrentOrder();
			loadOrder(tableId);
			displayCurrentOrder();
		}
	}

	/**
	 * 
	 * @param orderItem
	 */
	private void addItemToCurrentOrder(DisplayableOrderItem orderItem) {
		
		currentOrder.put(orderItem.getName(), orderItem);
		changeSendOrderButtonState();
	}

	/**
	 * 
	 * @param orderItem
	 */
	private void addOrderItem(OrderItem orderItem) {
		
		String name = orderItem.getName();

		DisplayableOrderItem existing = currentOrder.get(name);
		if (existing != null) {
			existing.incrementQuantity();
		} else {
		
			DisplayableOrderItem item = 
				new DisplayableOrderItem(orderItem, this);
			
			addItemToCurrentOrder(item);
		}		
	}

	/**
	 * 
	 * @param resultCode
	 * @param data
	 */
	private void processGetOrderItemResult(int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {
			
			Bundle bundle = data.getExtras();
			String key = 
				getPackageName() + 
				getString(R.string.app_extras_orderItem);
			
			OrderItem orderItem = (OrderItem) bundle.get(key);
			addOrderItem(orderItem);
		}
	}

	/**
	 * 
	 * @param selectedTable
	 */
	private void clearSelectTableButtonText() {
	
		Button button = (Button) findViewById(R.id.ButtonSelectTable);
		button.setText(getString(R.string.SelectTable));
	}

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
	 */
	private void clearOrder() {
		
		orders.remove(currentTable);
		
		currentTable = null;
		currentOrder = null;
		
		setArticleIdEditTextEnabled(false);
		setAddArticleButtonEnabled(false);
		setAddFavoriteButtonEnabled(false);
		setSendOrderButtonEnabled(false);
		
		clearSelectTableButtonText();
		clearOrderView();
	}

	/**
	 * 
	 * @param resultCode
	 */
	private void processSendOrderResult(int resultCode) {

		if (resultCode == RESULT_OK) {
			saveSentOrder();
			clearOrder();
		}
	}

	/**
	 * @return
	 */
	private boolean allTablesClosed() {
		
		boolean areAllTablesClosed = 
			orders.size() == 0 && 
			(currentOrder == null || (currentOrder != null && currentOrder.size() == 0));
		
		return areAllTablesClosed;
	}

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        setButtons();
        
        //TODO: samo ako postoje konobari ... ili ne pokazat uopće?
        startSelectWaiterActivity();
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		
		case R.id.menuItemSynchronize:
			if (allTablesClosed()) {
				startSyncDataActivity();
			} else {
				//TODO: inform waiter about open tables
				//TODO: move if clause before switch-case
			}
			return true;
			
		case R.id.menuItemLogInOut:
			if (allTablesClosed()) {
				
				logOutCurrentWaiter();
				startSelectWaiterActivity();
			} else {
				//TODO: inform waiter about open tables
			}
			return true;
			
		case R.id.menuItemStorno:
			startStornoActivity();
			return true;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (requestCode) {
		case ActivityRequestCodes.SYNC_DATA:
			processSyncDataResult(resultCode, data);
			break;
			
		case ActivityRequestCodes.SELECT_WAITER:
			processSelectWaiterResult(resultCode, data);
			break;			
			
		case ActivityRequestCodes.SELECT_TABLE:
			processSelectTableResult(resultCode, data);
			break;
			
		case ActivityRequestCodes.GET_ORDER_ITEM:
			processGetOrderItemResult(resultCode, data);
			break;
			
		case ActivityRequestCodes.SEND_ORDER:
			processSendOrderResult(resultCode);
			break;

		default:
			break;
		}
	}

	@Override
	public void removeItemFromCurrentOrder(DisplayableOrderItem orderItem) {
		
		currentOrder.remove(orderItem.getName());
		changeSendOrderButtonState();
		
		if (currentOrder.size() == 0) {
			orders.remove(currentTable);
		}
	}
}