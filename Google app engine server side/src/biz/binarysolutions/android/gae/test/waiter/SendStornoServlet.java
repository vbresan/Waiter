package biz.binarysolutions.android.gae.test.waiter;

import javax.jdo.PersistenceManager;
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
public class SendStornoServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		
		String tableId = req.getParameter("tableId");
			
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Storno storno = new Storno(tableId);
		
		try {
			pm.makePersistent(storno);
		} finally {
			pm.close();
		}			
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		doPost(req, resp);
	}
	
}
