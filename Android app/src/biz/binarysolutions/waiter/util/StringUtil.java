package biz.binarysolutions.waiter.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 
 *
 */
public class StringUtil {

	/**
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String getString(InputStream is) throws IOException {
		
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder  sb     = new StringBuilder();
	    
	    String line = null;
	    while ((line = reader.readLine()) != null) {
	    	sb.append(line + "\n");
	    }
	
	    return sb.toString();
	}	
}
