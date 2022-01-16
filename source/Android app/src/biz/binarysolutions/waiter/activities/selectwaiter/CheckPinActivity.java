package biz.binarysolutions.waiter.activities.selectwaiter;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import biz.binarysolutions.waiter.R;

/**
 * 
 *
 */
public class CheckPinActivity extends Activity {
	
	private String pin = "";
	
	/**
	 * 
	 */
	private void getExtras() {
		
		Bundle extras = getIntent().getExtras();
	    if(extras != null) {
	    	
	    	String key = getPackageName() + getString(R.string.app_extras_pin);
	    	pin = extras.getString(key);
	    }
	}
	
	/**
	 * 
	 * @return
	 */
	private boolean isPinOK() {
		
		EditText editText = (EditText) findViewById(R.id.EditTextPin);
		String   text     = editText.getText().toString();
		
		return pin.equals(text);
	}
	
	/**
	 * 
	 */
	private void setEditText() {
		
		EditText editText = (EditText) findViewById(R.id.EditTextPin);
		editText.setInputType(InputType.TYPE_CLASS_PHONE);
		editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
	}
	
	/**
	 * 
	 */
	private void setButtonListener() {
		
		Button button = (Button) findViewById(R.id.ButtonOK);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (isPinOK()) {
					setResult(RESULT_OK);
				} else {
					setResult(RESULT_CANCELED);
				}
				
				finish();
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pin);
		
		getExtras();
		
		setEditText();
		setButtonListener();
	}

}
