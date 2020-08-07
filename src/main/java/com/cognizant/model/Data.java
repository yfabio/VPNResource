package com.cognizant.model;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Data implements Serializable {
	

	
	private static final long serialVersionUID = 1L;
		
	private Integer id;	
	private StringProperty project = new SimpleStringProperty();
	private StringProperty IPSource = new SimpleStringProperty();
	private StringProperty IPTarget = new SimpleStringProperty();
	private StringProperty port = new SimpleStringProperty();
	private StringProperty service = new SimpleStringProperty();
	private StringProperty info = new SimpleStringProperty();
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public StringProperty projectProperty() {
		return this.project;
	}

	public String getProject() {
		return this.projectProperty().get();
	}

	public void setProject(String project) {
		this.projectProperty().set(project);
	}

	public StringProperty IPSourceProperty() {
		return this.IPSource;
	}

	public String getIPSource() {
		return this.IPSourceProperty().get();
	}

	public void setIPSource(String IPSource) {
		this.IPSourceProperty().set(IPSource);
	}

	public StringProperty IPTargetProperty() {
		return this.IPTarget;
	}

	public String getIPTarget() {
		return this.IPTargetProperty().get();
	}

	public void setIPTarget(String IPTarget) {
		this.IPTargetProperty().set(IPTarget);
	}

	public StringProperty portProperty() {
		return this.port;
	}

	public String getPort() {
		return this.portProperty().get();
	}

	public void setPort(String port) {
		this.portProperty().set(port);
	}

	public StringProperty serviceProperty() {
		return this.service;
	}

	public String getService() {
		return this.serviceProperty().get();
	}

	public void setService(String service) {
		this.serviceProperty().set(service);
	}

	public StringProperty infoProperty() {
		return this.info;
	}

	public String getInfo() {
		return this.infoProperty().get();
	}

	public void setInfo(String info) {
		this.infoProperty().set(info);
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Data other = (Data) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Data [id=" + id.intValue() + ", project=" + project.get() + ", IPSource=" + IPSource.get() + ", IPTarget=" + IPTarget.get()
				+ ", port=" + port.get() + ", service=" + service.get() + ", info=" + info.get() + "]";
	}
	
	
	
		
}
