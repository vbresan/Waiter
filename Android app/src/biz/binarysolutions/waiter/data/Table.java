package biz.binarysolutions.waiter.data;

/**
 * 
 *
 */
public class Table {
	
	private int id;

	/**
	 * 
	 * @param id
	 */
	public Table(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		
		if (o instanceof Table) {
			return id == ((Table) o).id;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return id;
	}

}
