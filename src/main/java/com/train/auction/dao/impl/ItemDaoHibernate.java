package com.train.auction.dao.impl;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.train.auction.dao.ItemDao;
import com.train.auction.model.Item;


public class ItemDaoHibernate
	extends HibernateDaoSupport implements ItemDao  
{
	/**
	 * ��������������Ʒ
	 * @param itemId ����ҵ���Ʒ��id;
	 * @return id��Ӧ����Ʒ
	 */
	public Item get(BigInteger itemId)
	{
		return (Item)getHibernateTemplate().get(Item.class , itemId);
	}

	/**
	 * ������Ʒ
	 * @param item ��Ҫ�������Ʒ
	 */
	public void save(Item item)
	{
		getHibernateTemplate().save(item);
	}

	/**
	 * �޸���Ʒ
	 * @param item ��Ҫ�޸ĵ���Ʒ
	 */
	public void update(Item item)
	{
		getHibernateTemplate().saveOrUpdate(item);
	}

	/**
	 * ɾ����Ʒ
	 * @param id ��Ҫɾ������Ʒid
	 */
	public void delete(BigInteger id)
	{
		getHibernateTemplate().delete(get(id));
	}

	/**
	 * ɾ����Ʒ
	 * @param item ��Ҫɾ������Ʒ
	 */
	public void delete(Item item)
	{
		getHibernateTemplate().delete(item);
	}

	/**
	 * ���ݲ�Ʒ���࣬��ȡ��ǰ������ȫ����Ʒ
	 * @param kindId ����id;
	 * @return �����ȫ����Ʒ
	 */
	public List<Item> findItemByKind(BigInteger kindId)
	{
		return (List<Item>)getHibernateTemplate()
			.find("from Item as i where i.kind.id = ? and i.itemState.id = 1"
			, kindId);
	}

	/**
	 * ���������߲��Ҵ��������е���Ʒ
	 * @param useId ������Id;
	 * @return ָ���û����������е�ȫ����Ʒ
	 */
	public List<Item> findItemByOwner(BigInteger userId)
	{
		return (List<Item>)getHibernateTemplate()
			.find("from Item as i where i.owner.id = ? and i.itemState.id = 1"
			, userId);
	}

	/**
	 * ����Ӯȡ�߲�����Ʒ
	 * @param userId Ӯȡ��Id;
	 * @return ָ���û�Ӯȡ��ȫ����Ʒ
	 */
	public List<Item> findItemByWiner(BigInteger userId)
	{
		return (List<Item>)getHibernateTemplate()
			.find("from Item as i where i.winer.id = ? and i.itemState.id = 2"
			,userId);
	}

	/**
	 * ������Ʒ״̬������Ʒ
	 * @param stateId ״̬Id;
	 * @return ��״̬�µ�ȫ����Ʒ
	 */
	public List<Item> findItemByState(BigInteger stateId)
	{
		return (List<Item>)getHibernateTemplate()
			.find("from Item as i where i.itemState.id = ?" , stateId);
	}

	public List<Item> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Query getQuery(Item criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	public Item findOne(Query query) {
		// TODO Auto-generated method stub
		return null;
	}
}