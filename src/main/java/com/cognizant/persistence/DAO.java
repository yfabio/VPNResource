package com.cognizant.persistence;

import java.util.List;

public abstract class DAO<E> {
	
	public abstract void save(E e) throws DAOException;
	public abstract void delete(E e)throws DAOException;
	public abstract void update(E e)throws DAOException;
	public abstract List<E> get()throws DAOException;
	public abstract E get(int id)throws DAOException;
	public abstract List<E> get(Criteria query,String value)throws DAOException;
	public abstract int getMaxID()throws DAOException;
	

}
