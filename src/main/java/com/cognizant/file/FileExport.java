package com.cognizant.file;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import com.cognizant.model.Data;
import com.cognizant.util.JSONConverter;

public class FileExport extends FileTaskExport<Void> {
	
	private FileStreamCallBack fileStreamCallBacks;
	
	private List<Data> list;
		
	private Path dir;
	
	

	public FileExport(FileStreamCallBack fileStreamCallBacks, List<Data> list, File file) {
		this.fileStreamCallBacks = fileStreamCallBacks;		
		this.dir = file.toPath();
		this.list = list;
	}

	@Override
	public void exported() {
		
		if(list!=null) {
			try(BufferedWriter bw = Files.newBufferedWriter(dir,StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING)){
				for (Data data : list) {
					bw.write(JSONConverter.dataToString(data));
					bw.flush();
					bw.newLine();
					Thread.sleep(100);
				}
			}catch(Exception e){
				throw new RuntimeException(e.getMessage());
			}
		}
		
	}

	@Override
	protected void succeeded() {
		fileStreamCallBacks.succeeded();
	}

	@Override
	protected void failed() {
		fileStreamCallBacks.failed();
	}

	@Override
	protected void scheduled() {
		fileStreamCallBacks.scheduled();
	}

	
	
	

	
}
