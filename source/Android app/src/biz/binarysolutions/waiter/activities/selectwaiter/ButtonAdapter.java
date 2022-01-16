package biz.binarysolutions.waiter.activities.selectwaiter;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import biz.binarysolutions.waiter.data.Waiter;

/**
 * 
 *
 */
class ButtonAdapter extends BaseAdapter {
	
	private SelectWaiterActivity activity;
	
	private ArrayList<Waiter> waiters = new ArrayList<Waiter>();

	/**
	 * 
	 * @param position
	 * @return
	 */
	private String getButtonText(int position) {
		
		Waiter waiter = waiters.get(position);
		String name   = waiter.getName();
		
		return name;
	}

	/**
	 * 
	 * @param waiters
	 */
	public ButtonAdapter
		(
				SelectWaiterActivity activity, 
				ArrayList<Waiter>    waiters
		) {
		
		this.activity = activity;
		this.waiters  = waiters;
	}

	@Override
	public int getCount() {
		return waiters.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		Button button;
		if (convertView == null) {
			button = new Button(activity);
		} else {
			button = (Button) convertView;
		}

		String text = getButtonText(position);
		button.setText(text);
		
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Waiter waiter = waiters.get(position);
				activity.onWaiterSelected(waiter);
			}
		});
		
		return button;
	}

}
