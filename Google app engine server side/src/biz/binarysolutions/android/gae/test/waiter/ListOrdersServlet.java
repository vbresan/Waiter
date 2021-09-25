package biz.binarysolutions.android.gae.test.waiter;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
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
public class ListOrdersServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
		throws IOException {
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query query = pm.newQuery(Order.class);
		query.setOrdering("date desc");
		
		List<Order> results = (List<Order>) query.execute();
		for (Order order : results) {
			
			Date   date      = order.getDate();
			String waiter    = order.getWaiter();
			String tableId   = order.getTableId();
			String jsonOrder = order.getJSONOrder();
			
			String output = 
				date + "; Konobar " + waiter + "; Stol " + tableId + ": " + 
				jsonOrder + "<br />"; 
			
			resp.setCharacterEncoding("UTF-8");
	        resp.setContentType("text/html");
			resp.getWriter().println(output);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
		throws IOException {
		doPost(req, resp);
	}

}
