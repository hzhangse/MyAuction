package com.train.auction.dao.impl;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.train.auction.dao.StateDao;
import com.train.auction.model.State;


public class StateDaoHibernate
	extends HibernateDaoSupport implements StateDao  
{
	/**
	 * 根据id查找状态
	 * @param id 需要查找的状态id
	 */
	public State get(BigInteger id)
	{
		return (State)getHibernateTemplate().get(State.class , id);
	}
	/**
	 * 增加状态
	 * @param state 需要增加的状态
	 */  
	public void save(State state)
	{
		getHibernateTemplate().save(state);
	}

	/**
	 * 修改状态
	 * @param state 需要修改的状态
	 */
	public void update(State state)
	{
		getHibernateTemplate().saveOrUpdate(state);
	}

	/**
	 * 删除状态
	 * @param id 需要删除的状态id
	 */
	public void delete(BigInteger id)
	{
		getHibernateTemplate().delete(get(id));
	}

	/**
	 * 删除状态
	 * @param state 需要删除的状态
	 */
	public void delete(State state)
	{
		getHibernateTemplate().delete(state);
	}

	/**
	 * 查询全部状态
	 * @return 获得全部状态
	 */
	public List<State> findAll()
	{
		return (List<State>)getHibernateTemplate().find("from State");
	}
	public Query getQuery(State criteria) {
		// TODO Auto-generated method stub
		return null;
	}
	public State findOne(Query query) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void dropCollection() {
		this.getHibernateTemplate().deleteAll(findAll());
		
	}
}