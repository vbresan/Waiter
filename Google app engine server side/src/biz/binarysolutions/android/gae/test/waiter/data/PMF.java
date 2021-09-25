package biz.binarysolutions.android.gae.test.waiter.data;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * 
 *
 */
public final class PMF {
	
    private static final PersistenceManagerFactory pmfInstance =
        JDOHelper.getPersistenceManagerFactory("transactions-optional");

    /**
     * 
     */
    private PMF() {}

    /**
     * 
     * @return
     */
    public static PersistenceManagerFactory get() {
        return pmfInstance;
    }
}
