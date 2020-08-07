package com.cognizant.persistence;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import com.cognizant.model.Data;
import com.cognizant.util.FileStorage;

public class DAOData extends DAO<Data> implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Override
	public void save(Data data) throws DAOException {
		try {
			FileStorage.save(data);
		} catch (IOException e) {
			throw new DAOException("Error while It was saving",e);
		}
	}

	@Override
	public void delete(Data data) throws DAOException {
		try {
			FileStorage.delete(data);
		} catch (IOException e) {
			throw new DAOException("Error while It was deleting!",e);
		}
	}

	@Override
	public void update(Data data) throws DAOException {
		try {
			FileStorage.udpate(data);
		} catch (IOException e) {
			throw new DAOException("Error while It was updating",e);
		}
	}

	@Override
	public List<Data> get() throws DAOException {
		try {
			return FileStorage.get();
		} catch (IOException e) {
			throw new DAOException("Error while It was fetching",e);
		}		
	}
	
	@Override
	public List<Data> get(Criteria query,String value) throws DAOException {
		try {
			return FileStorage.query(query, value);
		} catch (IOException e) {
			throw new DAOException("error while It was quering the data!",e);
		}		
	}

	@Override
	public Data get(int id) throws DAOException {
		try {
			return FileStorage.get(id);
		} catch (IOException e) {
			throw new DAOException("Unable to get and id!",e);
		}
	}

	@Override
	public int getMaxID()throws DAOException {
		try {
			return FileStorage.getId();
		} catch (IOException e) {
			throw new DAOException("Unable to generate id!",e);
		}
	}

}
