package com.cognizant.file;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * 
 * The FileTaskImported holds a task that will be performed when the import is fired
 * 
 * @author yfabio
 *
 * @param <E>
 */
public abstract class FileTaskImport<E> extends Service<E> implements FileStream<E> {

	@Override
	public E imported() {
		return null;
	}

	@Override
	public void exported() {		
	}

	@Override
	protected Task<E> createTask() {		
		Task<E> task = new Task<E>() {
			@Override
			protected E call() throws Exception {					
				return imported();
			}					
		};
		return task;
	}

	
	
	
}
