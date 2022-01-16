package biz.binarysolutions.android.gae.test.waiter;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biz.binarysolutions.android.gae.test.waiter.data.Order;
import biz.binarysolutions.android.gae.test.waiter.data.PMF;

/**
 * 
 *
 */
@SuppressWarnings("serial")
public class SendOrderServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		
		String waiter    = req.getParameter("waiterId");
		String tableId   = req.getParameter("tableId");
		String jsonOrder = req.getParameter("order");
			
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Order order = new Order(waiter, tableId, jsonOrder);
		
		try {
			pm.makePersistent(order);
		} finally {
			pm.close();
		}			
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		doPost(req, resp);
	}
	
}
