package com.cognizant.file;

/**
 * 
 * The FileStreamCallBacks is used to notify about a fail, succeed and an scheduled
 * 
 * @author yfabio
 *
 */
public interface FileStreamCallBack {
	
	public void failed();

	public void succeeded();

	public void scheduled();
	
}
