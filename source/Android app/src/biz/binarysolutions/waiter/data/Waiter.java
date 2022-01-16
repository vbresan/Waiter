package biz.binarysolutions.waiter.data;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 *
 */
public class Waiter implements Serializable {
	
	private static final long serialVersionUID = 6243065219674850764L;
	
	private int    id;
	private String name;
	private String pin;

	/**
	 * 
	 * @param jsonObject
	 * @throws JSONException 
	 */
	public Waiter(JSONObject jsonObject) throws JSONException {

		this.id   = jsonObject.getInt("id");
		this.name = jsonObject.getString("name");
		this.pin  = jsonObject.getString("pin");
	}

	/**
	 * 
	 * @param id
	 * @param name
	 * @param pin
	 */
	public Waiter(final int id, final String name, final String pin) {

		this.id   = id;
		this.name = name;
		this.pin  = pin;
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

	/**
	 * 
	 * @return
	 */
	public String getPin() {
		return pin;
	}

}
