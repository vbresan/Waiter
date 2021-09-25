package biz.binarysolutions.waiter.data;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 *
 */
public class OrderItem extends Article {

	private static final long serialVersionUID = 730084929214102025L;
	
	private LinkedHashMap<Integer, String> selectedExtras =
		new LinkedHashMap<Integer, String>();
	
	private int quantity = 1;
	
	/**
	 * 
	 * @return
	 * @throws JSONException
	 */
	private JSONArray getExtrasJSONArray() throws JSONException {
		
		JSONArray jsonArray = new JSONArray();
		
		for (Map.Entry<Integer, String> entry : selectedExtras.entrySet()) {
			
			Integer id         = entry.getKey();
			String  difference = entry.getValue();
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("difference", difference);
			jsonObject.put("id", id.intValue());
			
			jsonArray.put(jsonObject);
		}
		
		return jsonArray;
	}

	/**
	 * 
	 */
	protected void incrementQuantity() {
		quantity++;
	}

	/**
	 * 
	 */
	protected void decrementQuantity() {
		quantity--;
	}	

	/**
	 * 
	 * @param article
	 */
	public OrderItem(Article article) {
		super(article);
		
		if (article instanceof OrderItem) {
			
			OrderItem other = (OrderItem) article;
			
			this.selectedExtras = other.selectedExtras;
			this.quantity       = other.quantity;
		}
	}
	
	/**
	 * 
	 * @param quantity
	 * @param name
	 * @param articleId
	 */
	public OrderItem(int quantity, String name, int articleId) {
		super(articleId, name);

		this.quantity = quantity;
	}

	/**
	 * 
	 * @param id
	 * @param difference
	 */
	public void addSelectedExtra(int id, String difference) {
		selectedExtras.put(new Integer(id), difference);
	}

	@Override
	public String getName() {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(super.getName());
		for (String difference : selectedExtras.values()) {
			sb.append(" ");
			sb.append(difference);
		}
		
		return sb.toString();
	}
	
	/**
	 * 
	 * @return
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * TODO: fix this
	 * 
	 * @return
	 */
	public JSONObject toJSONObject() {
		
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("id", getId());
			jsonObject.put("quantity", quantity);
			
			if (selectedExtras.size() > 0) {
				jsonObject.put("extras", getExtrasJSONArray());
			}
		} catch (JSONException e) {
			// TODO handle error
		}
		
		return jsonObject;
	}	
}
