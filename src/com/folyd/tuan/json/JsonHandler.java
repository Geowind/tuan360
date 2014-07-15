package com.folyd.tuan.json;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
@Deprecated
public class JsonHandler {
    private static Gson gson = new Gson();
    
    
	public static String toJson(Object obj){
		String jsonString = gson.toJson(obj);
		return jsonString;
	}
	
	
	public static String fromJson(String jsonString){
		return jsonString;
		
	}
	public static <T> List<T> getObjectFromJson(String jsonString,Class<T> object){
		List<T> list = new ArrayList<T>();
		list  = (List<T>) gson.fromJson(jsonString, object);
		return list;
	}
	
	public static String convertObjectToJson(Class<?> object){
		return null;
	}
}
