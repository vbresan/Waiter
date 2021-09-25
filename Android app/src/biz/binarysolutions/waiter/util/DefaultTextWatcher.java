package biz.binarysolutions.waiter.util;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * 
 *
 */
public abstract class DefaultTextWatcher implements TextWatcher {

	@Override
	public void afterTextChanged(Editable s) {
		// do nothing
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// do nothing
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		onTextChanged(s);
	}

	/**
	 * 
	 * @param s
	 */
	protected abstract void onTextChanged(CharSequence s);
}
