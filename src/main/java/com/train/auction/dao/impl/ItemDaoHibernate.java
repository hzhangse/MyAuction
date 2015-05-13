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
	 * 根据主键查找物品
	 * @param itemId 想查找到物品的id;
	 * @return id对应的物品
	 */
	public Item get(BigInteger itemId)
	{
		return (Item)getHibernateTemplate().get(Item.class , itemId);
	}

	/**
	 * 保存物品
	 * @param item 需要保存的物品
	 */
	public void save(Item item)
	{
		getHibernateTemplate().save(item);
	}

	/**
	 * 修改物品
	 * @param item 需要修改的物品
	 */
	public void update(Item item)
	{
		getHibernateTemplate().saveOrUpdate(item);
	}

	/**
	 * 删除物品
	 * @param id 需要删除的物品id
	 */
	public void delete(BigInteger id)
	{
		getHibernateTemplate().delete(get(id));
	}

	/**
	 * 删除物品
	 * @param item 需要删除的物品
	 */
	public void delete(Item item)
	{
		getHibernateTemplate().delete(item);
	}

	/**
	 * 根据产品分类，获取当前拍卖的全部商品
	 * @param kindId 种类id;
	 * @return 该类的全部产品
	 */
	public List<Item> findItemByKind(BigInteger kindId)
	{
		return (List<Item>)getHibernateTemplate()
			.find("from Item as i where i.kind.id = ? and i.itemState.id = 1"
			, kindId);
	}

	/**
	 * 根据所有者查找处于拍卖中的物品
	 * @param useId 所有者Id;
	 * @return 指定用户处于拍卖中的全部物品
	 */
	public List<Item> findItemByOwner(BigInteger userId)
	{
		return (List<Item>)getHibernateTemplate()
			.find("from Item as i where i.owner.id = ? and i.itemState.id = 1"
			, userId);
	}

	/**
	 * 根据赢取者查找物品
	 * @param userId 赢取者Id;
	 * @return 指定用户赢取的全部物品
	 */
	public List<Item> findItemByWiner(BigInteger userId)
	{
		return (List<Item>)getHibernateTemplate()
			.find("from Item as i where i.winer.id = ? and i.itemState.id = 2"
			,userId);
	}

	/**
	 * 根据物品状态查找物品
	 * @param stateId 状态Id;
	 * @return 该状态下的全部物品
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

	@Override
	public void dropCollection() {
		// TODO Auto-generated method stub
		
	}
}