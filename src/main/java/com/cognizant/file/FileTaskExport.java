package com.cognizant.file;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *  The FIleTaskBackup holds a task that will be performed when the backup is fired
 * @author yfabio
 *
 * @param <E>
 */
public abstract class FileTaskExport<E> extends Service<E> implements FileStream<E> {

	@Override
	public void exported() {		
	}

	@Override
	public E imported() {	
		return null;
	}

	@Override
	protected Task<E> createTask() {
		Task<E> task = new Task<E>() {
			@Override
			protected E call() throws Exception {
				exported();
				return null;
			}
		};
		return task;
	}

	
	

	

	
	
	
	
}
