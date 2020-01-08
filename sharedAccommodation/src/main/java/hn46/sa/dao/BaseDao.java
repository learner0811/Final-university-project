package hn46.sa.dao;

import java.util.Map;

public interface BaseDao<T> {
	T save(T entity);
	T update(T entity);
	T findById(int primaryKey);
	Iterable<T> findByParam(Map<String, Object> params);
	Iterable<T> findAll();         
	void delete(T entity); 
	boolean existsById(int primaryKey); 
}
