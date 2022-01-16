package biz.binarysolutions.waiter.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 *
 */
class DatabaseHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME    = "data.db";
	private static final int    DATABASE_VERSION = 5;
	
    private static final String CREATE_TABLE_TABLES =
    	"CREATE TABLE Tables (id INTEGER NOT NULL);";
    
    private static final String CREATE_TABLE_WAITERS = 
    	"CREATE TABLE Waiters (id INTEGER NOT NULL, name TEXT NOT NULL, pin TEXT NOT NULL);";
    
    private static final String CREATE_TABLE_CATEGORIES = 
    	"CREATE TABLE Categories (id INTEGER NOT NULL, name TEXT NOT NULL);";
    
    private static final String CREATE_TABLE_ARTICLES = 
    	"CREATE TABLE Articles (id INTEGER NOT NULL, categoryId INTEGER NOT NULL, name TEXT NOT NULL);";
    
    private static final String CREATE_TABLE_RELATION_ARTICLES_EXTRAS = 
    	"CREATE TABLE RelationArticlesExtras (articleId INTEGER NOT NULL, extrasId INTEGER NOT NULL);";
    
    private static final String CREATE_TABLE_EXTRAS = 
    	"CREATE TABLE Extras (id INTEGER NOT NULL, difference TEXT NOT NULL);";
    
    private static final String CREATE_TABLE_FAVORITES =
    	"CREATE TABLE Favorites (articleId INTEGER NOT NULL);";
    
    private static final String CREATE_TABLE_SENT_ORDER_ITEMS = 
    	"CREATE TABLE SentOrderItems (tableId INTEGER NOT NULL, timestamp INTEGER NOT NULL, quantity INTEGER NOT NULL, name TEXT NOT NULL, articleId INTEGER NOT NULL);";
    
    
    private static final String DROP_TABLE_TABLES = 
    	"DROP TABLE IF EXISTS Tables;";
    
    private static final String DROP_TABLE_WAITERS = 
    	"DROP TABLE IF EXISTS Waiters;";
    
    private static final String DROP_TABLE_CATEGORIES = 
    	"DROP TABLE IF EXISTS Categories;";
    
    private static final String DROP_TABLE_ARTICLES = 
    	"DROP TABLE IF EXISTS Articles;";
    
    private static final String DROP_TABLE_RELATION_ARTICLES_EXTRAS = 
    	"DROP TABLE IF EXISTS RelationArticlesExtras;";
    
    private static final String DROP_TABLE_EXTRAS = 
    	"DROP TABLE IF EXISTS Extras;";
    
    private static final String DROP_TABLE_FAVORITES = 
    	"DROP TABLE IF EXISTS Favorites;";
    
    private static final String DROP_TABLE_SENT_ORDER_ITEMS = 
    	"DROP TABLE IF EXISTS SentOrderItems;";    

	/**
	 * 
	 * @param inContext
	 */
	public DatabaseHelper(final Context inContext) {
        super(inContext, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
        db.execSQL(CREATE_TABLE_TABLES);
        db.execSQL(CREATE_TABLE_WAITERS);
        db.execSQL(CREATE_TABLE_CATEGORIES);
        db.execSQL(CREATE_TABLE_ARTICLES);
        db.execSQL(CREATE_TABLE_RELATION_ARTICLES_EXTRAS);
        db.execSQL(CREATE_TABLE_EXTRAS);
        db.execSQL(CREATE_TABLE_FAVORITES);
        db.execSQL(CREATE_TABLE_SENT_ORDER_ITEMS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
        db.execSQL(DROP_TABLE_TABLES);
        db.execSQL(DROP_TABLE_WAITERS);
        db.execSQL(DROP_TABLE_CATEGORIES);
        db.execSQL(DROP_TABLE_ARTICLES);
        db.execSQL(DROP_TABLE_RELATION_ARTICLES_EXTRAS);
        db.execSQL(DROP_TABLE_EXTRAS);
        db.execSQL(DROP_TABLE_FAVORITES);
        db.execSQL(DROP_TABLE_SENT_ORDER_ITEMS);
        
        onCreate(db);
	}
	
}