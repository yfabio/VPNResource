package com.cognizant.util;

import com.cognizant.model.Data;
import com.cognizant.util.ValidationState.Field;


public class StringUtil {
	
	public static boolean isEmpty(String text) {
		return text == null ? true : text.trim().length() == 0 ? true : false;
	}
	
	
	public static boolean validate(Data data, ValidationState state) {
		
		boolean result = true;
		
		if (isEmpty(data.getProject())) {
			Field field = Field.PROJECT;
			field.setMessage("project is required!");
			state.change(field);
			result = false;
		} 
		
		if (isEmpty(data.getIPSource())) {
			Field field = Field.IPSOURCE;
			field.setMessage("ip source is required!");
			state.change(field);
			result = false;
		} 
		
		if (isEmpty(data.getIPTarget())) {
			Field field = Field.IPTARGET;
			field.setMessage("ip target is required!");
			state.change(field);
			result = false;
		} 
		
		if (isEmpty(data.getPort())) {
			Field field = Field.PORT;
			field.setMessage("port is required!");
			state.change(field);
			result = false;
		}
		
		if(isEmpty(data.getService())) {
			Field field = Field.SERVICE;
			field.setMessage("service is required!");
			state.change(field);
			result = false;
		}
		
		return result;
	}

}
