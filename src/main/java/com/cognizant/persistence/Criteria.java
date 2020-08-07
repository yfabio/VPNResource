package com.cognizant.persistence;

import java.util.function.Supplier;

public interface Criteria {
	
	public static Supplier<String> fileSeach(String value){
		return () -> value;
	}

}
