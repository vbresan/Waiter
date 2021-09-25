package biz.binarysolutions.android.gae.test.waiter;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biz.binarysolutions.android.gae.test.waiter.data.PMF;
import biz.binarysolutions.android.gae.test.waiter.data.Storno;

/**
 * 
 *
 */
@SuppressWarnings("serial")
public class ListStornosServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
		throws IOException {
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query query = pm.newQuery(Storno.class);
		query.setOrdering("date desc");
		
		List<Storno> results = (List<Storno>) query.execute();
		for (Storno storno : results) {
			
			Date   date    = storno.getDate();
			String tableId = storno.getTableId();
			
			String output = date + "; Stol " + tableId + "<br />"; 
			
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
