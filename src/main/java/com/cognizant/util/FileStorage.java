package com.cognizant.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.cognizant.model.Data;
import com.cognizant.persistence.Criteria;

public class FileStorage {
	
	public static final Path FILE_NAME = Paths.get(System.getProperty("user.home"),"vpnresource","vpn.txt");
		
	public static void save(Data data) throws IOException {
		try(BufferedWriter bw = Files.newBufferedWriter(FILE_NAME,StandardOpenOption.WRITE,StandardOpenOption.APPEND)){
			bw.write(JSONConverter.dataToString(data));
			bw.flush();
			bw.newLine();
		}catch(Exception e){
			throw new IOException(e);
		}
	}

	public static void delete(Data data) throws IOException {
		List<Data> list = get();		
		list.remove(data);
		clean(list);
	}

	public static void udpate(Data data) throws IOException {
		List<Data> list = get();
		int index = list.indexOf(data);
		list.set(index, data);
		clean(list);			
	}

	public static Data get(int id) throws IOException {
		List<Data> list = get();
		return list.stream().filter(e -> e.getId() == id).findFirst().orElseThrow(IOException::new);		
	}
	
	public static List<Data> get() throws IOException{
		List<Data> list = new ArrayList<>();
		try(BufferedReader br = Files.newBufferedReader(FILE_NAME)){
			String line = "";
			while((line = br.readLine()) != null) {
				if(!StringUtil.isEmpty(line)) {
					Data data = JSONConverter.stringToData(line);
					list.add(data);
				}
			}
		}catch(Exception e){
			throw new IOException(e);
		}
		return list;
	}
	
	public static List<Data> query(Criteria query,String value) throws IOException {		
		List<Data> list = get();
		
		list = list.stream().map(e -> JSONConverter.dataToString(e)).collect(Collectors.toList())
			.stream().filter(e -> e.contains(value)).map(e -> JSONConverter.stringToData(e))
			.collect(Collectors.toList());
				
		return list;
		
	}
	
	
	public static int getId() throws IOException {
		List<Data> list = get();
		return list.stream().mapToInt(e -> e.getId()).max().orElse(0) + 1;
	}
	
	private static void clean(List<Data> list) throws IOException {		
		try(BufferedWriter bw = Files.newBufferedWriter(FILE_NAME,StandardOpenOption.WRITE,StandardOpenOption.TRUNCATE_EXISTING)){
			for (Data data : list) {
				bw.write(JSONConverter.dataToString(data));
				bw.flush();
				bw.newLine();
			}			
		}
	}

}
