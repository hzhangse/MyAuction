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
	 * ����id����״̬
	 * @param id ��Ҫ���ҵ�״̬id
	 */
	public State get(BigInteger id)
	{
		return (State)getHibernateTemplate().get(State.class , id);
	}
	/**
	 * ����״̬
	 * @param state ��Ҫ���ӵ�״̬
	 */  
	public void save(State state)
	{
		getHibernateTemplate().save(state);
	}

	/**
	 * �޸�״̬
	 * @param state ��Ҫ�޸ĵ�״̬
	 */
	public void update(State state)
	{
		getHibernateTemplate().saveOrUpdate(state);
	}

	/**
	 * ɾ��״̬
	 * @param id ��Ҫɾ����״̬id
	 */
	public void delete(BigInteger id)
	{
		getHibernateTemplate().delete(get(id));
	}

	/**
	 * ɾ��״̬
	 * @param state ��Ҫɾ����״̬
	 */
	public void delete(State state)
	{
		getHibernateTemplate().delete(state);
	}

	/**
	 * ��ѯȫ��״̬
	 * @return ���ȫ��״̬
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