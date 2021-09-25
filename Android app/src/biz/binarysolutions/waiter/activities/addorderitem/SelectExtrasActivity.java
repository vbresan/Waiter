package biz.binarysolutions.waiter.activities.addorderitem;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import biz.binarysolutions.waiter.R;
import biz.binarysolutions.waiter.data.Extras;
import biz.binarysolutions.waiter.db.DatabaseAdapter;
import biz.binarysolutions.waiter.util.DefaultOnItemClickListener;
import biz.binarysolutions.waiter.util.DefaultTextWatcher;

/**
 * TODO: merge functionality with SelectCategoryActivity?
 *
 */
public class SelectExtrasActivity extends ListActivity {
	
	private Extras extras;
	
	private ArrayAdapter<String> adapter;
	
	private EditText    editText;
	private TextWatcher textWatcher = new DefaultTextWatcher() {

		@Override
		protected void onTextChanged(CharSequence s) {
			adapter.getFilter().filter(s);
		}
	};		

	/**
	 * 
	 */
	private void getExtras() {
		
		Bundle bundle = getIntent().getExtras();
	    if(bundle != null) {
	    	
	    	String key = 
	    		getPackageName() + 
	    		getString(R.string.app_extras_extras_id);
	    	
	    	int extrasId = bundle.getInt(key);
	    	extras = DatabaseAdapter.getExtras(this, extrasId);
	    }
	}	

	/**
	 * 
	 * @return
	 */
	private String[] getDifferences() {
		
		ArrayList<String> arrayList = extras.getDifferences();
		return arrayList.toArray(new String[0]);
	}

	/**
	 * 
	 */
	private void setOnItemClickListener() {
		
		ListView listView = getListView();
		listView.setOnItemClickListener(new DefaultOnItemClickListener() {

			@Override
			protected void onItemClick(View view) {

				if (view instanceof TextView) {
					
					String difference = ((TextView) view).getText().toString();
					
					Intent intent = new Intent();
					
					String packageName = getPackageName(); 
					String key;
					
					key = packageName + getString(R.string.app_extras_extras_id);
					intent.putExtra(key, extras.getId());
					
					key = packageName + getString(R.string.app_extras_extras_difference);
					intent.putExtra(key, difference);
					
					setResult(RESULT_OK, intent);
					finish();					
				}
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		
		getExtras();
		
		adapter = new ArrayAdapter<String>(
			this, android.R.layout.simple_list_item_1, getDifferences());
		setListAdapter(adapter);
		
		editText = (EditText) findViewById(R.id.EditTextFilter);
		editText.addTextChangedListener(textWatcher);
		
		setOnItemClickListener();
	}

	@Override
	protected void onDestroy() {
	    
		editText.removeTextChangedListener(textWatcher);
	    super.onDestroy();
	}

}
