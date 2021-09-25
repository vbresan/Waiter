package biz.binarysolutions.waiter.activities.addorderitem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

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
import biz.binarysolutions.waiter.data.Article;
import biz.binarysolutions.waiter.data.Favorite;
import biz.binarysolutions.waiter.db.DatabaseAdapter;
import biz.binarysolutions.waiter.util.DefaultOnItemClickListener;
import biz.binarysolutions.waiter.util.DefaultTextWatcher;

/**
 * 
 *
 */
public class SelectFavoriteActivity extends ListActivity  {
	
	private ArrayList<Favorite>            favorites;
	private LinkedHashMap<String, Article> articles;

	
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
	 * @return
	 */
	private ArrayList<Favorite> getFavorites() {
		return DatabaseAdapter.getFavorites(this);
	}
	
	/**
	 * 
	 * @return
	 */
	private LinkedHashMap<String, Article> getArticles() {
		
		LinkedHashMap<String, Article> map = new LinkedHashMap<String, Article>();
		
		for (Favorite favorite : favorites) {
			
			int id = favorite.getArticleId();
			Article article = DatabaseAdapter.getArticle(this, id);
			
			map.put(article.getName(), article);
		}
		
		return map;
	}

	/**
	 * 
	 * @return
	 */
	private String[] getFavoritesNames() {
		
		ArrayList<String> arrayList = new ArrayList<String>();
		
		for (Map.Entry<String, Article> entry : articles.entrySet()) {
			arrayList.add(entry.getKey());
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
			 * 
			 * @param article
			 */
			private void startGetOrderItemActivity(Article article) {
				
				Intent intent = new Intent(
					SelectFavoriteActivity.this, 
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

			@Override
			protected void onItemClick(View view) {
			
				if (view instanceof TextView) {
					
					String name = ((TextView) view).getText().toString();
					
					Article article = articles.get(name);
					if (article != null) {
						startGetOrderItemActivity(article);
					}
				}
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		
		favorites = getFavorites();
		articles  = getArticles();
		
		adapter = new ArrayAdapter<String>(
			this, android.R.layout.simple_list_item_1, getFavoritesNames());
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