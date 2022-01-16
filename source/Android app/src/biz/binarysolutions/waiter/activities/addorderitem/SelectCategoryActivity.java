package biz.binarysolutions.waiter.activities.addorderitem;

import java.util.ArrayList;
import java.util.LinkedHashMap;

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
import biz.binarysolutions.waiter.activities.ActivityRequestCodes;
import biz.binarysolutions.waiter.data.Category;
import biz.binarysolutions.waiter.db.DatabaseAdapter;
import biz.binarysolutions.waiter.util.DefaultOnItemClickListener;
import biz.binarysolutions.waiter.util.DefaultTextWatcher;

/**
 * 
 *
 */
public class SelectCategoryActivity extends ListActivity {
	
	private LinkedHashMap<String, Category> categories =
		new LinkedHashMap<String, Category>();
	
	
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
	private void getCategories() {
		
		ArrayList<Category> array = DatabaseAdapter.getCategories(this); 
		for (Category category : array) {
			categories.put(category.getName(), category);
		}
	}

	/**
	 * 
	 * @return
	 */
	private String[] getCategoryNames() {
		
		ArrayList<String> arrayList = new ArrayList<String>();
		
		for (String name : categories.keySet()) {
			arrayList.add(name);
		}
		
		return arrayList.toArray(new String[0]);
	}

	/**
	 * 
	 */
	private void setOnItemClickListener() {
		
		ListView listView = getListView();
		listView.setOnItemClickListener(new DefaultOnItemClickListener() {
	
			/**
			 * @param categoryId 
			 * 
			 */
			private void startSelectOrderItemActivity(int categoryId) {
				
				Intent intent = new Intent(
					SelectCategoryActivity.this, 
					SelectArticleActivity.class
				);
				
				String key = 
					getPackageName() + 
					getString(R.string.app_extras_category_id);
				
				intent.putExtra(key, categoryId);
				startActivityForResult(
					intent, 
					ActivityRequestCodes.GET_ORDER_ITEM
				);
			}

			@Override
			protected void onItemClick(View view) {

				if (view instanceof TextView) {
					
					String name = ((TextView) view).getText().toString();
					
					Category category = categories.get(name);
					if (category != null) {
						startSelectOrderItemActivity(category.getId());
					}
				}
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		
		getCategories();
		
		adapter = new ArrayAdapter<String>(
			this, android.R.layout.simple_list_item_1, getCategoryNames());
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) {
			
			setResult(RESULT_OK, data);
			finish();
		}
	}	
}
