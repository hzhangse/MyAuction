package com.train.auction.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

public interface IDao<T> {

	T get(BigInteger id);

	void save(T o);

	void update(T o);

	void delete(BigInteger id);

	void delete(T o);

	List<T> findAll();

	public Query getQuery(T criteria);
	
	public T findOne(Query query) ;
}
