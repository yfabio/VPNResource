package com.cognizant.file;

/**
 * 
 * The FileStream is used either to backup the data but also to import 
 * 
 * @author yfabio
 *
 * @param <E>
 * 
 */
public interface FileStream<E>  {	
			
	public void exported();
	public E imported(); 	 
}
