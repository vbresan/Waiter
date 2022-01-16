package biz.binarysolutions.waiter.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 *
 */
public class Category {
	
	private int    id;
	private String name;
	
	/**
	 * 
	 * @param jsonObject
	 * @throws JSONException 
	 */
	public Category(JSONObject jsonObject) throws JSONException {

		this.id   = jsonObject.getInt("id");
		this.name = jsonObject.getString("name");
	}

	/**
	 * 
	 * @param id
	 * @param name
	 */
	public Category(int id, String name) {
		
		this.id   = id;
		this.name = name;
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
	public String getName() {
		return name;
	}
}
