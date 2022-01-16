package biz.binarysolutions.waiter.data;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 *
 */
public class Article implements Serializable {

	private static final long serialVersionUID = 708360873127426818L;
	
	private int    id;
	private int    categoryId;
	private String name;
	
	private ArrayList<Integer> extrasIds = new ArrayList<Integer>();

	/**
	 * 
	 * @param id
	 * @param categoryId
	 * @param name
	 * @param extrasIds 
	 */
	public Article
		(
				int                id, 
				int                categoryId, 
				String             name,
				ArrayList<Integer> extrasIds
		) {

		this.id         = id;
		this.categoryId = categoryId;
		this.name       = name;
		this.extrasIds  = extrasIds;
	}
	
	/**
	 * 
	 * @param id
	 * @param name
	 */
	public Article(int id, String name) {

		this.id   = id;
		this.name = name;
	}

	/**
	 * 
	 * @param other
	 */
	public Article(Article other) {
		
		id         = other.id;
		categoryId = other.categoryId;
		name       = other.name;
		extrasIds  = other.extrasIds;
	}

	/**
	 * 
	 * @param jsonObject
	 * @throws JSONException 
	 */
	public Article(JSONObject jsonObject) throws JSONException {
		
		this.id = jsonObject.getInt("id");
		this.categoryId = jsonObject.getInt("categoryId");
		this.name = jsonObject.getString("name");
		
		try {
			JSONArray array = jsonObject.getJSONArray("extras");
			for (int i = 0, length = array.length(); i < length; i++) {
				
				Integer key = new Integer(array.getInt(i));
				extrasIds.add(key);
			}
		} catch (JSONException e) {
			//TODO: do nothing?
		}
	}

	/**
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @return
	 */
	public int getCategoryId() {
		return categoryId;
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return
	 */
	public boolean hasExtras() {
		return !extrasIds.isEmpty();
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<Integer> getExtrasIds() {
		return extrasIds;
	}
}
