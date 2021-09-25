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
public class Storno {
	
	@SuppressWarnings("unused")
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
	
	@Persistent
	private Date date;

	@Persistent
	private String tableId;

	/**
	 * 
	 * @param tableId
	 */
	public Storno(String tableId) {

		this.date    = new Date();
		this.tableId = tableId;
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
	public String getTableId() {
		return tableId;
	}

}
