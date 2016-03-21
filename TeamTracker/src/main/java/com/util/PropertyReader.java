package com.util;

import java.util.ResourceBundle;

/**
 * Reads the Property file and gives the value
 * 
 * @author M1031956
 *
 */
public class PropertyReader {
	private static final ResourceBundle resource = ResourceBundle.getBundle("queries");
	
	/**
     * Reads the given key in the properties file and returns the value
     */
    public static String getValue(String key){
        return resource.getString(key);
    }

}
