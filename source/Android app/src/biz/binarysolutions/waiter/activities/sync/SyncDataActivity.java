package biz.binarysolutions.waiter.activities.sync;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import biz.binarysolutions.waiter.R;
import biz.binarysolutions.waiter.activities.RequestURLBuilder;
import biz.binarysolutions.waiter.data.Article;
import biz.binarysolutions.waiter.data.Category;
import biz.binarysolutions.waiter.data.Extras;
import biz.binarysolutions.waiter.data.Favorite;
import biz.binarysolutions.waiter.data.Table;
import biz.binarysolutions.waiter.data.Waiter;
import biz.binarysolutions.waiter.db.DatabaseAdapter;

/**
 * 
 *
 */
public class SyncDataActivity extends Activity {
	
	private DatabaseAdapter databaseAdapter;
	
	private boolean tablesReceived     = false;
	private boolean waitersReceived    = false;
	private boolean categoriesReceived = false;
	private boolean articlesReceived   = false;
	private boolean extrasReceived     = false;
	private boolean favoritesReceived  = false;
	
	/**
	 * @param requestCode
	 * @param requestURL
	 */
	private void sync(int requestCode, String requestURL) {
		
		Handler handler = new FetchDataHandler(this);
		new FetchData(handler, requestCode, requestURL).start();
	}
	
	/**
	 * 
	 */
	private void syncTables() {
		
		sync(
			MessageStatus.DONE_GET_TABLES, 
			RequestURLBuilder.getTables(this)
		);
	}

	/**
	 * 
	 */
	private void syncWaiters() {
		
		sync(
			MessageStatus.DONE_GET_WAITERS, 
			RequestURLBuilder.getWaiters(this)
		);
	}

	/**
	 * 
	 */
	private void syncCategories() {
		
		sync(
			MessageStatus.DONE_GET_CATEGORIES, 
			RequestURLBuilder.getCategories(this)
		);
	}

	/**
	 * 
	 */
	private void syncArticles() {
		
		sync(
			MessageStatus.DONE_GET_ARTICLES, 
			RequestURLBuilder.getArticles(this)
		);
	}

	/**
	 * 
	 */
	private void syncExtras() {
		
		sync(
			MessageStatus.DONE_GET_EXTRAS, 
			RequestURLBuilder.getExtras(this)
		);
	}

	/**
	 * 
	 */
	private void syncFavorites() {
		
		sync(
			MessageStatus.DONE_GET_FAVORITES, 
			RequestURLBuilder.getFavorites(this)
		);
	}

	/**
	 * 
	 */
	private void syncData() {
		
		syncTables();
		syncWaiters();
		syncCategories();
		syncArticles();
		syncExtras();
		syncFavorites();
	}

	/**
	 * @return
	 */
	private boolean isAllReceived() {
		
		boolean isAllReceived = 
			tablesReceived     &&
		   	waitersReceived    && 
		   	categoriesReceived &&
		   	articlesReceived   &&
		   	extrasReceived     &&
		   	favoritesReceived;
		
		return isAllReceived;
	}

	/**
	 * 
	 */
	private void finishIfAllReceived() {

		if (isAllReceived()) {
			
			setResult(RESULT_OK);
			finish();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progress);
		
		databaseAdapter = new DatabaseAdapter(this);
		databaseAdapter.open();
		
		syncData();
	}

	@Override
	protected void onStop() {
		super.onStop();
		
		databaseAdapter.close();
	}

	/**
	 * 
	 */
	public void onConnectionError() {
		// TODO Auto-generated method stub
		finish();
	}

	/**
	 * 
	 */
	public void onDataError() {
		// TODO Auto-generated method stub
		finish();
	}
	
	/**
	 * 
	 * @param json
	 * @return
	 * @throws JSONException 
	 */
	private ArrayList<Table> getTables(String json) throws JSONException {
		
		ArrayList<Table> tables = new ArrayList<Table>();
		
		JSONArray array = new JSONArray(json);
		
		for (int i = 0, length = array.length(); i < length; i++) {
			
			int   id    = array.getInt(i);
			Table table = new Table(id);
			
			tables.add(table);
		}
		
		return tables;
	}
	
	/**
	 * 
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	private ArrayList<Waiter> getWaiters(String json) throws JSONException {
		
		ArrayList<Waiter> waiters = new ArrayList<Waiter>();
		
		JSONArray array = new JSONArray(json);
		
		for (int i = 0, length = array.length(); i < length; i++) {
			
			JSONObject jsonObject = array.getJSONObject(i);
			Waiter     waiter     = new Waiter(jsonObject);
			
			waiters.add(waiter);
		}
		
		return waiters;
	}
	
	/**
	 * 
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	private ArrayList<Category> getCategories(String json) throws JSONException {
		
		ArrayList<Category> categories = new ArrayList<Category>();
		
		JSONArray array = new JSONArray(json);
		
		for (int i = 0, length = array.length(); i < length; i++) {
			
			JSONObject jsonObject = array.getJSONObject(i);
			Category   category   = new Category(jsonObject);
			
			categories.add(category);
		}
		
		return categories;
	}	

	/**
	 * 
	 * @param json
	 * @return
	 * @throws JSONException 
	 */
	private ArrayList<Article> getArticles(String json) throws JSONException {
		
		ArrayList<Article> articles = new ArrayList<Article>();
		
		JSONArray array = new JSONArray(json);
		
		for (int i = 0, length = array.length(); i < length; i++) {
			
			JSONObject jsonObject = array.getJSONObject(i);
			Article    article    = new Article(jsonObject);
			
			articles.add(article);
		}		
		
		return articles;
	}
	
	/**
	 * 
	 * @param json
	 * @return
	 * @throws JSONException 
	 */
	private ArrayList<Extras> getExtras(String json) throws JSONException {
		
		ArrayList<Extras> arrayList = new ArrayList<Extras>();
		
		JSONArray array = new JSONArray(json);
		
		for (int i = 0, length = array.length(); i < length; i++) {
			
			JSONObject jsonObject = array.getJSONObject(i);
			Extras     extra      = new Extras(jsonObject);
			
			arrayList.add(extra);
		}		
		
		return arrayList;
	}	

	/**
	 * 
	 * @param json
	 * @return
	 * @throws JSONException 
	 */
	private ArrayList<Favorite> getFavorites(String json) throws JSONException {
		
		ArrayList<Favorite> favorites = new ArrayList<Favorite>();
		
		JSONArray array = new JSONArray(json);
		
		for (int i = 0, length = array.length(); i < length; i++) {
			
			int id = array.getInt(i);
			Favorite favorite = new Favorite(id);
			
			favorites.add(favorite);
		}
		
		return favorites;
	}

	/**
	 * 
	 * @param data
	 */
	public void onTablesAvailable(String data) {
		
		try {
			ArrayList<Table> tables = getTables(data);
			
			databaseAdapter.deleteTables();
			databaseAdapter.saveTables(tables);
		} catch (JSONException e) {
			// TODO inform about not received data
		} 

		tablesReceived = true;
		finishIfAllReceived();
	}

	/**
	 * 
	 * @param data
	 */
	public void onWaitersAvailable(String data) {

		try {
			ArrayList<Waiter> waiters = getWaiters(data);
			
			databaseAdapter.deleteWaiters();
			databaseAdapter.saveWaiters(waiters);
		} catch (JSONException e) {
			// TODO inform about not received data
		} 		

		waitersReceived = true;
		finishIfAllReceived();
	}

	/**
	 * 
	 * @param data
	 */
	public void onCategoriesAvailable(String data) {

		try {
			ArrayList<Category> categories = getCategories(data);
			
			databaseAdapter.deleteCategories();
			databaseAdapter.saveCategories(categories);
		} catch (JSONException e) {
			// TODO inform about not received data
		} 		
		
		categoriesReceived = true;
		finishIfAllReceived();
	}

	/**
	 * 
	 * @param data
	 */
	public void onOrderItemsAvailable(String data) {
		
		try {
			ArrayList<Article> articles = getArticles(data);
			
			databaseAdapter.deleteArticles();
			databaseAdapter.saveArticles(articles);
		} catch (JSONException e) {
			// TODO inform about not received data
		} 
		
		articlesReceived = true;
		finishIfAllReceived();
	}

	/**
	 * 
	 * @param data
	 */
	public void onExtrasAvailable(String data) {

		try {
			ArrayList<Extras> extras = getExtras(data);
			
			databaseAdapter.deleteExtras();
			databaseAdapter.saveExtras(extras);
		} catch (JSONException e) {
			// TODO inform about not received data
		}
		
		extrasReceived = true;
		finishIfAllReceived();
	}

	/**
	 * 
	 * @param data
	 */
	public void onFavoritesAvailable(String data) {


		try {
			ArrayList<Favorite> favorites = getFavorites(data);
			
			databaseAdapter.deleteFavorites();
			databaseAdapter.saveFavorites(favorites);
		} catch (JSONException e) {
			// TODO inform about not received data
		} 			
		
		favoritesReceived = true;
		finishIfAllReceived();
	}
}
