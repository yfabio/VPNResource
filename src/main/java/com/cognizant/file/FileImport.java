package com.cognizant.file;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.cognizant.model.Data;
import com.cognizant.util.JSONConverter;
import com.cognizant.util.StringUtil;

public class FileImport extends FileTaskImport<List<Data>> {
	
	private FileStreamCallBack fileStreamCallBack;
	
	private Path dir;
	
	public FileImport(FileStreamCallBack fileStreamCallBack, File file) {
		this.fileStreamCallBack = fileStreamCallBack;
		this.dir = file.toPath();
	}

	@Override
	public List<Data> imported() {		
		List<Data> list = new ArrayList<>();
		try(BufferedReader br = Files.newBufferedReader(dir)){
			String line = "";
			while((line = br.readLine()) != null) {
				if(!StringUtil.isEmpty(line)) {
					Data data = JSONConverter.stringToData(line);
					list.add(data);
					Thread.sleep(100);
				}
			}			
		}catch(Exception e){
			throw new RuntimeException("Unable to import " + e.getMessage());
		}
		return list;
	}

	@Override
	protected void scheduled() {
		fileStreamCallBack.scheduled();
	}

	@Override
	protected void succeeded() {
		fileStreamCallBack.succeeded();
	}

	@Override
	protected void failed() {
		fileStreamCallBack.failed();
	}

	


	
}
