package biz.binarysolutions.waiter.activities.selecttable;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import biz.binarysolutions.waiter.R;
import biz.binarysolutions.waiter.data.Table;

/**
 * 
 *
 */
class ButtonAdapter extends BaseAdapter {
	
	private SelectTableActivity activity;
	
	private ArrayList<Table> tables = new ArrayList<Table>();

	/**
	 * 
	 * @param buttonId
	 * @return
	 */
	private String getButtonText(long buttonId) {
		return activity.getString(R.string.Table) + " " + buttonId;
	}

	/**
	 * 
	 * @param tables
	 */
	public ButtonAdapter
		(
				final SelectTableActivity inActivity, 
				final ArrayList<Table>    inTables
		) {
		
		activity = inActivity;
		tables   = inTables;
	}

	@Override
	public int getCount() {
		return tables.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {

		Table table = tables.get(position);
		return table.getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Button button;
		if (convertView == null) {
			button = new Button(activity);
		} else {
			button = (Button) convertView;
		}

		final int buttonId = (int) getItemId(position);
		button.setText(getButtonText(buttonId));
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				activity.onTableSelected(buttonId);
			}
		});
		
		return button;
	}

}
