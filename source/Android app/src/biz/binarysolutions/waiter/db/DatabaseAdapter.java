package biz.binarysolutions.waiter.db;

import java.util.ArrayList;
import java.util.Collection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import biz.binarysolutions.waiter.data.Article;
import biz.binarysolutions.waiter.data.Category;
import biz.binarysolutions.waiter.data.DisplayableOrderItem;
import biz.binarysolutions.waiter.data.Extras;
import biz.binarysolutions.waiter.data.Favorite;
import biz.binarysolutions.waiter.data.OrderItem;
import biz.binarysolutions.waiter.data.Table;
import biz.binarysolutions.waiter.data.Waiter;

/**
 * 
 *
 */
public class DatabaseAdapter {
	
	private Context context;
	
	private DatabaseHelper databaseHelper;
	private SQLiteDatabase database;

	/**
	 * @param tableName
	 * @param columns
	 * @return
	 */
	private Cursor getCursor(String tableName, String[] columns, String where) {
		return database.query(tableName, columns, where, null, null, null, null);
	}

	/**
	 * @param tableName
	 * @param columns
	 * @return
	 */
	private Cursor getCursor(String tableName, String[] columns) {
		return getCursor(tableName, columns, null);
	}
	
	/**
	 * 
	 * @param tableName
	 * @param where
	 */
	private void deleteTable(String tableName, String where) {
		database.delete(tableName, where, null);
	}

	/**
	 * 
	 * @param tableName
	 */
	private void deleteTable(String tableName) {
		deleteTable(tableName, null);
	}

	/**
	 * 
	 * @return
	 */
	private ArrayList<Waiter> getWaiters() {
		
		ArrayList<Waiter> waiters = new ArrayList<Waiter>();
		
		String   tableName = "Waiters"; 
		String[] columns   = new String[] { "id", "name", "pin" }; 
		Cursor   cursor    = getCursor(tableName, columns);
		
		if (cursor.moveToFirst()) {
	
			do {
				int    id   = cursor.getInt(0);
				String name = cursor.getString(1);
				String pin  = cursor.getString(2);
				
				Waiter waiter = new Waiter(id, name, pin);
				waiters.add(waiter);
			} while (cursor.moveToNext());
		}
		
		if (!cursor.isClosed()) {
			cursor.close();
		}
		
		return waiters;
	}

	/**
	 * 
	 * @return
	 */
	private ArrayList<Table> getTables() {
		
		ArrayList<Table> tables = new ArrayList<Table>();
		
		String   tableName = "Tables";
		String[] columns   = new String[] { "id" };
		Cursor   cursor    = getCursor(tableName, columns);
		
		if (cursor.moveToFirst()) {
	
			do {
				tables.add(new Table(cursor.getInt(0)));
			} while (cursor.moveToNext());
		}
		
		if (!cursor.isClosed()) {
			cursor.close();
		}
		
		return tables;
	}

	/**
	 * 
	 * @return
	 */
	private ArrayList<Category> getCategories() {
		
		ArrayList<Category> categories = new ArrayList<Category>();
		
		String   tableName = "Categories"; 
		String[] columns   = new String[] { "id", "name" }; 
		Cursor   cursor    = getCursor(tableName, columns);
		
		if (cursor.moveToFirst()) {
	
			do {
				int    id   = cursor.getInt(0);
				String name = cursor.getString(1);
				
				Category category = new Category(id, name);
				categories.add(category);
			} while (cursor.moveToNext());
		}
		
		if (!cursor.isClosed()) {
			cursor.close();
		}
		
		return categories;
	}

	/**
		 * 
		 * @return
		 */
		private ArrayList<Article> getArticles(int categoryId) {
			
			ArrayList<Article> articles = new ArrayList<Article>();
			
			String   tableName = "Articles"; 
			String[] columns   = new String[] { "id", "name" }; 
			String   where     = "categoryId = " + categoryId;
			Cursor   cursor    = getCursor(tableName, columns, where);
			
			if (cursor.moveToFirst()) {
	
				do {
					int    id   = cursor.getInt(0);
					String name = cursor.getString(1);
					
					ArrayList<Integer> extrasIds = getExtrasIds(id);
					
					Article article = 
						new Article(id, categoryId, name, extrasIds);

					articles.add(article);

				} while (cursor.moveToNext());
			}
			
			if (!cursor.isClosed()) {
				cursor.close();
			}
			
			return articles;
		}

	/**
	 * 
	 * @param id
	 * @return
	 */
	private Article getArticle(int id) {
		
		Article article = null;

		String   tableName = "Articles";
		String[] columns   = new String[] { "categoryId", "name"};
		String   where     = "id = " + id;
		Cursor   cursor    = getCursor(tableName, columns, where);
		
		if (cursor.moveToFirst()) {
			
			int    categoryId = cursor.getInt(0);
			String name       = cursor.getString(1);
			
			ArrayList<Integer> extrasIds = getExtrasIds(id);
			
			article = new Article(id, categoryId, name, extrasIds);
		}
		
		if (!cursor.isClosed()) {
			cursor.close();
		}

		return article;
	}

	/**
	 * 
	 * @param articleId
	 * @return
	 */
	private ArrayList<Integer> getExtrasIds(int articleId) {
		
		ArrayList<Integer> extrasIds = new ArrayList<Integer>();
		
		String   tableName = "RelationArticlesExtras";
		String[] columns   = new String[] { "extrasId" };
		String   where     = "articleId = " + articleId;
		Cursor   cursor    = getCursor(tableName, columns, where);
		
		if (cursor.moveToFirst()) {
			
			do {
				extrasIds.add(new Integer(cursor.getInt(0)));
			} while (cursor.moveToNext());
		}
		
		if (!cursor.isClosed()) {
			cursor.close();
		}		
		
		return extrasIds;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	private Extras getExtras(int id) {
		
		Extras extras = null;
		ArrayList<String> differences = new ArrayList<String>(); 
		
		String   tableName = "Extras";
		String[] columns   = new String[] { "difference" };
		String   where     = "id = " + id;
		Cursor   cursor    = getCursor(tableName, columns, where);
		
		if (cursor.moveToFirst()) {
			
			do {
				differences.add(cursor.getString(0));
			} while (cursor.moveToNext());
			
			extras = new Extras(id, differences);
		}
		
		if (!cursor.isClosed()) {
			cursor.close();
		}

		return extras;
	}

	/**
	 * 
	 * @return
	 */
	private ArrayList<Favorite> getFavorites() {
		
		ArrayList<Favorite> favorites = new ArrayList<Favorite>();
		
		String   tableName = "Favorites";
		String[] columns   = new String[] { "articleId" };
		Cursor   cursor    = getCursor(tableName, columns);
		
		if (cursor.moveToFirst()) {
	
			do {
				favorites.add(new Favorite(cursor.getInt(0)));
			} while (cursor.moveToNext());
		}
		
		if (!cursor.isClosed()) {
			cursor.close();
		}
		
		return favorites;
	}

	/**
	 * 
	 * @return
	 */
	private ArrayList<OrderItem> getSentOrderItems(int tableId) {
		
		ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
		
		String   tableName = "SentOrderItems";
		String[] columns   = new String[] { "quantity", "name", "articleId" };
		String   where     = "tableId = " + tableId;
		Cursor   cursor    = getCursor(tableName, columns, where);
		
		if (cursor.moveToFirst()) {
	
			do {
				int    quantity  = cursor.getInt(0);
				String name      = cursor.getString(1);
				int    articleId = cursor.getInt(2);
				
				OrderItem orderItem = new OrderItem(quantity, name, articleId);
				orderItems.add(orderItem);
				
			} while (cursor.moveToNext());
		}
		
		if (!cursor.isClosed()) {
			cursor.close();
		}		
		
		return orderItems;
	}

	/**
	 * 
	 * @param tableId
	 */
	private void deleteSentOrderItems(int tableId) {
		deleteTable("SentOrderItems", "tableId = " + tableId);
	}

	/**
	 * 
	 * @param items
	 */
	private void saveSentOrderItems
		(
				int  tableId,
				long timestamp,
				Collection<DisplayableOrderItem> items
		) {
	
		for (DisplayableOrderItem item : items) {
			
			ContentValues values = new ContentValues();
			values.put("tableId",   new Integer(tableId));
			values.put("timestamp", new Long(timestamp));
			values.put("quantity",  new Integer(item.getQuantity()));
			values.put("name",      item.getName());
			values.put("articleId", new Integer(item.getId()));
			
			database.insert("SentOrderItems", null, values);
		}
	}

	/**
	 * 
	 * @param context
	 */
	public DatabaseAdapter(Context context) {
		this.context = context;
	}

	/**
	 * 
	 */
	public DatabaseAdapter open() {

		databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        
        return this;
	}

	/**
	 * 
	 */
	public void close() {
		databaseHelper.close();
	}

	/**
	 * 
	 */
	public void deleteTables() {
		deleteTable("Tables");
		
	}

	/**
	 * 
	 */
	public void deleteWaiters() {
		deleteTable("Waiters");
	}

	/**
	 * 
	 */
	public void deleteCategories() {
		deleteTable("Categories");
	}

	/**
	 * 
	 */
	public void deleteArticles() {
		deleteTable("Articles");
		deleteTable("RelationArticlesExtras");
	}

	/**
	 * 
	 */
	public void deleteExtras() {
		deleteTable("Extras");
	}

	/**
	 * 
	 */
	public void deleteFavorites() {
		deleteTable("Favorites");
	}

	/**
	 * 
	 * @param tables
	 */
	public void saveTables(ArrayList<Table> tables) {

		for (Table table : tables) {
			
			ContentValues values = new ContentValues();
			values.put("id", new Integer(table.getId()));
			
			database.insert("Tables", null, values);
		}
	}

	/**
	 * 
	 * @param waiters
	 */
	public void saveWaiters(ArrayList<Waiter> waiters) {

		for (Waiter waiter : waiters) {
			
			ContentValues values = new ContentValues();
			values.put("id", new Integer(waiter.getId()));
			values.put("name", waiter.getName());
			values.put("pin", waiter.getPin());
			
			database.insert("Waiters", null, values);
		}
		
	}

	/**
	 * 
	 * @param categories
	 */
	public void saveCategories(ArrayList<Category> categories) {

		for (Category category : categories) {
			
			ContentValues values = new ContentValues();
			values.put("id", new Integer(category.getId()));
			values.put("name", category.getName());
			
			database.insert("Categories", null, values);
		}
	}

	/**
	 * 
	 * @param articles
	 */
	public void saveArticles(ArrayList<Article> articles) {

		for (Article article : articles) {
			
			Integer articleId = new Integer(article.getId());
			
			ContentValues articleValues = new ContentValues();
			articleValues.put("id", articleId);
			articleValues.put("categoryId", new Integer(article.getCategoryId()));
			articleValues.put("name", article.getName());
			
			database.insert("Articles", null, articleValues);

			ArrayList<Integer> extrasIds = article.getExtrasIds();
			for (Integer extrasId : extrasIds) {
				
				ContentValues relationValues = new ContentValues();
				relationValues.put("articleId", articleId);
				relationValues.put("extrasId",  extrasId);
				
				database.insert("RelationArticlesExtras", null, relationValues);
			}
		}
	}

	/**
	 * 
	 * @param extras
	 */
	public void saveExtras(ArrayList<Extras> extras) {

		for (Extras extra : extras) {
			
			int id = extra.getId();
			
			ArrayList<String> differences = extra.getDifferences();
			for (String difference : differences) {
				
				ContentValues values = new ContentValues();
				values.put("id", new Integer(id));
				values.put("difference", difference);
				
				database.insert("Extras", null, values);
			}
		}
	}

	/**
	 * 
	 * @param favorites
	 */
	public void saveFavorites(ArrayList<Favorite> favorites) {

		for (Favorite favorite : favorites) {
			
			ContentValues values = new ContentValues();
			values.put("articleId", new Integer(favorite.getArticleId()));
			
			database.insert("Favorites", null, values);
		}
	}

	/**
	 * 
	 * @param context
	 * @param items
	 */
	public static void saveSentOrderItems
		(
				Context context,
				int     tableId,
				long    timestamp,
				Collection<DisplayableOrderItem> items
		) {
		
		DatabaseAdapter databaseAdapter = new DatabaseAdapter(context);
		databaseAdapter.open();
		databaseAdapter.deleteSentOrderItems(tableId);
		databaseAdapter.saveSentOrderItems(tableId, timestamp, items);
		databaseAdapter.close();
	}

	/**
	 * 
	 * @param context
	 * @param tableId
	 */
	public static void deleteSentOrderItems(Context context, int tableId) {
		
		DatabaseAdapter databaseAdapter = new DatabaseAdapter(context);
		databaseAdapter.open();
		databaseAdapter.deleteSentOrderItems(tableId);
		databaseAdapter.close();
	}	

	/**
	 * 
	 * @param context
	 * @return
	 */
	public static ArrayList<Waiter> getWaiters(Context context) {
		
		DatabaseAdapter databaseAdapter = new DatabaseAdapter(context);
		databaseAdapter.open();
		
		ArrayList<Waiter> waiters = databaseAdapter.getWaiters();
		databaseAdapter.close();
		
		return waiters;
	}

	/**
	 * 
	 * @param context
	 * @return
	 */
	public static ArrayList<Table> getTables(Context context) {
		
		DatabaseAdapter databaseAdapter = new DatabaseAdapter(context);
		databaseAdapter.open();
		
		ArrayList<Table> tables = databaseAdapter.getTables();
		databaseAdapter.close();
		
		return tables;
	}

	/**
	 * 
	 * @param context
	 * @return
	 */
	public static ArrayList<Category> getCategories(Context context) {
		
		DatabaseAdapter databaseAdapter = new DatabaseAdapter(context);
		databaseAdapter.open();
		
		ArrayList<Category> categories = databaseAdapter.getCategories();
		databaseAdapter.close();
		
		return categories;
	}	

	/**
	 * 
	 * @param context
	 * @param categoryId
	 * @return
	 */
	public static ArrayList<Article> getArticles
		(
				Context context, 
				int     categoryId
		) {

		DatabaseAdapter databaseAdapter = new DatabaseAdapter(context);
		databaseAdapter.open();
		
		ArrayList<Article> articles = databaseAdapter.getArticles(categoryId);
		databaseAdapter.close();
		
		return articles;
	}

	/**
	 * 
	 * @param articleId
	 * @return
	 */
	public static Article getArticle(Context context, int articleId) {
		
		DatabaseAdapter databaseAdapter = new DatabaseAdapter(context);
		databaseAdapter.open();
		
		Article article = databaseAdapter.getArticle(articleId);
		databaseAdapter.close();
		
		return article;	
	}

	/**
	 * 
	 * @param context 
	 * @param extrasId
	 * @return
	 */
	public static Extras getExtras(Context context, int extrasId) {
		
		DatabaseAdapter databaseAdapter = new DatabaseAdapter(context);
		databaseAdapter.open();
		
		Extras extras = databaseAdapter.getExtras(extrasId);
		databaseAdapter.close();
		
		return extras;	
	}

	/**
	 * 
	 * @param context
	 * @return
	 */
	public static ArrayList<Favorite> getFavorites(Context context) {
		
		DatabaseAdapter databaseAdapter = new DatabaseAdapter(context);
		databaseAdapter.open();
		
		ArrayList<Favorite> favorites = databaseAdapter.getFavorites();
		databaseAdapter.close();
		
		return favorites;
	}

	/**
	 * 
	 * @param context
	 * @param tableId
	 * @return
	 */
	public static ArrayList<OrderItem> getSentOrderItems
		(
				Context context, 
				int     tableId
		) {
		
		DatabaseAdapter databaseAdapter = new DatabaseAdapter(context);
		databaseAdapter.open();
		
		//TODO: delete stale order items
		ArrayList<OrderItem> orderItems = 
			databaseAdapter.getSentOrderItems(tableId);
		
		databaseAdapter.close();
		
		return orderItems;
	}	
}
