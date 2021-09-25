package biz.binarysolutions.waiter.data;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import biz.binarysolutions.waiter.OrderActivity;
import biz.binarysolutions.waiter.R;


/**
 * 
 *
 */
@SuppressWarnings("serial")
public class DisplayableOrderItem extends OrderItem {
	
	private OrderActivity activity;
	private LinearLayout  layout;
	
	/**
	 * 
	 */
	private void createLayout() {
		
		layout = (LinearLayout) View.inflate(activity, R.layout.item, null);

		LinearLayout parent = 
			(LinearLayout) activity.findViewById(R.id.LinearLayoutItems);
		
		parent.addView(layout);
	}

	/**
	 * 
	 */
	private void destroyLayout() {
	
		LinearLayout parent = (LinearLayout) layout.getParent();
		parent.removeView(layout);
	}

	/**
	 * 
	 */
	private void removeFromOrder() {
		activity.removeItemFromCurrentOrder(this);
	}

	/**
	 * 
	 */
	private void displayName() {

		TextView view = (TextView) layout.findViewById(R.id.TextViewName);
		view.setText(getName());		
	}

	/**
	 * 
	 */
	private void displayQuantity() {

		TextView view = (TextView) layout.findViewById(R.id.TextViewQuantity);
		view.setText("" + getQuantity());		
	}
	
	/**
	 * 
	 */
	private void displayId() {

		TextView view = (TextView) layout.findViewById(R.id.TextViewId);
		view.setText("" + getId());		
	}

	/**
	 * 
	 */
	private void setIncrementButtonListener() {

		Button button = (Button) layout.findViewById(R.id.ButtonIncrement);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				incrementQuantity();
			}
		});		
	}

	/**
	 * 
	 */
	private void setDecrementButtonListener() {

		Button button = (Button) layout.findViewById(R.id.ButtonDecrement);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				decrementQuantity();
			}
		});		
	}

	/**
	 * 
	 */
	private void setButtonListeners() {
	
		setIncrementButtonListener();
		setDecrementButtonListener();
	}

	/**
	 * 
	 * @param orderItem
	 * @param activity
	 */
	public DisplayableOrderItem(OrderItem orderItem, OrderActivity activity) {
		super(orderItem);
		
		this.activity = activity;
		
		createLayout();		
		displayQuantity();
		displayName();
		displayId();
		
		setButtonListeners();		
	}

	@Override
	public void incrementQuantity() {
		super.incrementQuantity();
		
		displayQuantity();
	}

	@Override
	public void decrementQuantity() {
		super.decrementQuantity();
		
		if (getQuantity() != 0) {
			displayQuantity();
		} else {
			destroyLayout();
			removeFromOrder();
		}
	}

	/**
	 * 
	 * @return
	 */
	public LinearLayout getView() {
		return layout;
	}

	/**
	 * 
	 * @param enabled
	 */
	public void setButtonsEnabled(boolean enabled) {

		Button button = (Button) layout.findViewById(R.id.ButtonIncrement);
		button.setEnabled(enabled);
		
		button = (Button) layout.findViewById(R.id.ButtonDecrement);
		button.setEnabled(enabled);
	}	
}
