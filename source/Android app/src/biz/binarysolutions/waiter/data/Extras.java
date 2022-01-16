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
public class Extras implements Serializable {
	
	private static final long serialVersionUID = 5910243110054413054L;
	
	private int id;
	private ArrayList<String> differences = new ArrayList<String>();

	/**
	 * 
	 * @param jsonObject
     * @throws JSONException
	 */
	public Extras(JSONObject jsonObject) throws JSONException {
		
		this.id = jsonObject.getInt("id");
		
		JSONArray array = jsonObject.getJSONArray("differences");
		for (int i = 0, length = array.length(); i < length; i++) {
			differences.add(array.getString(i));
		}
	}

	/**
	 * 
	 * @param id
	 * @param differences
	 */
	public Extras(int id, ArrayList<String> differences) {
		this.id = id;
		this.differences = differences;
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
	public ArrayList<String> getDifferences() {
		return differences;
	}

}
