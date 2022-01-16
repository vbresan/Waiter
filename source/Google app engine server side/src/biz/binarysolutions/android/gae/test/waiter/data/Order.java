package biz.binarysolutions.android.gae.test.waiter.data;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * 
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Order {
	
	@SuppressWarnings("unused")
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
	
	@Persistent
	private Date date;
	
	@Persistent
	private String waiter;
	
	@Persistent
	private String tableId;
	
	@Persistent
	private String jsonOrder;

	/**
	 * 
	 * @param waiter
	 * @param tableId
	 * @param jsonOrder
	 */
	public Order(String waiter, String tableId, String jsonOrder) {

		this.date      = new Date();
		this.waiter    = waiter;
		this.tableId   = tableId;
		this.jsonOrder = jsonOrder;
	}

	/**
	 * 
	 * @return
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * 
	 * @return
	 */
	public String getWaiter() {
		return waiter;
	}

	/**
	 * 
	 * @return
	 */
	public String getTableId() {
		return tableId;
	}

	/**
	 * 
	 * @return
	 */
	public String getJSONOrder() {
		return jsonOrder;
	}

}
