package biz.binarysolutions.android.gae.test.waiter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 *
 */
@SuppressWarnings("serial")
public class GetTablesServlet extends HttpServlet {
	
	private static String URL_TABLES = 
		"https://spreadsheets.google.com/pub?key=0AnSwphxjbXqFdFJfblotOV94STV1cDBRY3k5Zk9yVFE&hl=en&single=true&gid=0&output=csv";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

		try {
			
			String output = "[";
			
			URL url = new URL(URL_TABLES);
			BufferedReader reader = 
	        	new BufferedReader(
	        			new InputStreamReader(url.openStream(), "UTF-8"));
	        
	        String line;
	        while ((line = reader.readLine()) != null) {
	            output += line + ",";
	        }
	        reader.close();
	        
	        if (output.endsWith(",")) {
				output = output.substring(0, output.length() - 1);
			}
	        
	        output += "]";
	        
	        resp.setCharacterEncoding("UTF-8");
	        resp.setContentType("application/json");
	        resp.getWriter().print(output);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		doPost(req, resp);
	}

}
