package com.jhd.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EmailTemplateReader {
	public static Map<String, String> readTemplate(String path){
		Map<String, String> mp=null;
	 
		Properties props = new Properties();
		InputStream input = null;

		try {

			input = EmailTemplateReader.class.getResourceAsStream(".//email_templates//"+path+".properties");
			props.load(input);
			
			mp=new HashMap<>();
			mp.put("subject", props.getProperty("subject"));
			mp.put("content", props.getProperty("content"));
			input.close();
			
	} catch (FileNotFoundException ex) {
	    // file does not exist
	} catch (IOException ex) {
	    // I/O error
	}
		return mp;
	}
	
	public static HashMap jsonToMap(JSONObject json) throws JSONException {
		HashMap retMap = new HashMap();

	    if(json != JSONObject.NULL) {
	        retMap = toMap(json);
	    }
	    return retMap;
	}

	public static HashMap toMap(JSONObject object) throws JSONException {
		HashMap map = new HashMap<>();

	    Iterator<String> keysItr = object.keys();
	    while(keysItr.hasNext()) {
	        String key = keysItr.next();
	        Object value = object.get(key);

	        if(value instanceof JSONArray) {
	            value = toList((JSONArray) value);
	        }

	        else if(value instanceof JSONObject) {
	            value = toMap((JSONObject) value);
	        }
	        map.put(key, value);
	    }
	    return map;
	}

	public static List<Object> toList(JSONArray array) throws JSONException {
	    List<Object> list = new ArrayList<Object>();
	    for(int i = 0; i < array.length(); i++) {
	        Object value = array.get(i);
	        if(value instanceof JSONArray) {
	            value = toList((JSONArray) value);
	        }

	        else if(value instanceof JSONObject) {
	            value = toMap((JSONObject) value);
	        }
	        list.add(value);
	    }
	    return list;
	}
	
	public static void main(String args[]){
		readTemplate("welcome");
		/*File f1=new File("D:\\Git\\JHD\\src\\com\\jhd\\queue\\email_templates\\new.properties");
		File f2=new File("D:\\Git\\JHD\\src\\com\\jhd\\queue");
		System.out.println(getRelativePath(f1,f2));*/
	}
	
	
	public static String getRelativePath(File file, File folder) {
	    String filePath = file.getAbsolutePath();
	    String folderPath = folder.getAbsolutePath();
	    if (filePath.startsWith(folderPath)) {
	        return filePath.substring(folderPath.length() + 1);
	    } else {
	        return null;
	    }
	}
}
