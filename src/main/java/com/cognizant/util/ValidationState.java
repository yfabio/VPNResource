package com.cognizant.util;

/**
 *  The ValidationState is used to validate the inputfields
 * @author yfabio
 *
 */
public interface ValidationState {
	
	enum Field {
		PROJECT,
		IPSOURCE,
		IPTARGET,
		PORT,
		SERVICE;
		
		
		String message = new String();

		public String getMessages() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
		
	}
	
	public void change(Field field);
}
