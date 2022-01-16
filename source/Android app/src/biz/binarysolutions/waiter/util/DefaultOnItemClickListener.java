package biz.binarysolutions.waiter.util;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 *
 */
public abstract class DefaultOnItemClickListener implements OnItemClickListener {

	/**
	 * Using only view since position and id change when list is filtered.
	 */
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		onItemClick(view);
	}

	/**
	 * 
	 * @param view
	 */
	protected abstract void onItemClick(View view);
}
