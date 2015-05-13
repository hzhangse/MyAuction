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
	 * ����id��������
	 * @param id ��Ҫ���ҵ������id
	 */
	public Kind get(BigInteger id)
	{
		return (Kind)getHibernateTemplate().get(Kind.class , id);
	}

	/**
	 * ��������
	 * @param kind ��Ҫ���ӵ�����
	 */
	public void save(Kind kind)
	{
		getHibernateTemplate().save(kind);  
	}

	/**
	 * �޸�����
	 * @param kind ��Ҫ�޸ĵ�����
	 */ 
	public void update(Kind kind)
	{
		getHibernateTemplate().saveOrUpdate(kind);  
	}

	/**
	 * ɾ������
	 * @param id ��Ҫɾ��������id
	 */ 
	public void delete(BigInteger id)
	{
		getHibernateTemplate().delete(get(id));  
	}

	/**
	 * ɾ������
	 * @param kind ��Ҫɾ��������
	 */
	public void delete(Kind kind)
	{
		getHibernateTemplate().delete(kind);  
	}

	/**
	 * ��ѯȫ������
	 * @return ���ȫ������
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
