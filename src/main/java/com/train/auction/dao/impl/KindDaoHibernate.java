package com.train.auction.dao.impl;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.train.auction.dao.KindDao;
import com.train.auction.model.Kind;


public class KindDaoHibernate 
	extends HibernateDaoSupport implements KindDao  
{
	/**
	 * 根据id查找种类
	 * @param id 需要查找的种类的id
	 */
	public Kind get(BigInteger id)
	{
		return (Kind)getHibernateTemplate().get(Kind.class , id);
	}

	/**
	 * 增加种类
	 * @param kind 需要增加的种类
	 */
	public void save(Kind kind)
	{
		getHibernateTemplate().save(kind);  
	}

	/**
	 * 修改种类
	 * @param kind 需要修改的种类
	 */ 
	public void update(Kind kind)
	{
		getHibernateTemplate().saveOrUpdate(kind);  
	}

	/**
	 * 删除种类
	 * @param id 需要删除的种类id
	 */ 
	public void delete(BigInteger id)
	{
		getHibernateTemplate().delete(get(id));  
	}

	/**
	 * 删除种类
	 * @param kind 需要删除的种类
	 */
	public void delete(Kind kind)
	{
		getHibernateTemplate().delete(kind);  
	}

	/**
	 * 查询全部种类
	 * @return 获得全部种类
	 */
	public List<Kind> findAll()
	{
		return (List<Kind>)getHibernateTemplate().find("from Kind");
	}

	public Query getQuery(Kind criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	public Kind findOne(Query query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dropCollection() {
		// TODO Auto-generated method stub
		
	}
}
