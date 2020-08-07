package com.cognizant.util;

import com.cognizant.model.Data;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class JSONConverter {
	
	private static Gson gson = new Gson();

	private static class Holder {
		public Integer id;
		public String project;
		public String ipSource;
		public String ipTarget;
		public String port;
		public String service;
		public String info;
	}

	public static String dataToString(Data data) {

		JsonObject json = new JsonObject();

		json.add("id", new JsonPrimitive(data.getId()));
		json.add("project", new JsonPrimitive(data.getProject()));
		json.add("ipSource", new JsonPrimitive(data.getIPSource()));
		json.add("ipTarget", new JsonPrimitive(data.getIPTarget()));
		json.add("port", new JsonPrimitive(data.getPort()));
		json.add("service", new JsonPrimitive(data.getService()));
		json.add("info", new JsonPrimitive(data.getInfo() == null ? "": data.getInfo()));

		return gson.toJson(json);

	}

	public static Data stringToData(String value) {
		
		Holder holder = gson.fromJson(value,Holder.class);
		
		Data data = new Data();
		
		data.setId(holder.id);	
		data.setProject(holder.project);
		data.setIPSource(holder.ipSource);
		data.setIPTarget(holder.ipTarget);
		data.setPort(holder.port);
		data.setService(holder.service);
		data.setInfo(holder.info);
		
		return data;
	}

}
