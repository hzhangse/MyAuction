package com.train.auction.dao.impl;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.train.auction.dao.AuctionUserDao;
import com.train.auction.model.AuctionUser;


public class AuctionUserDaoHibernate
	extends HibernateDaoSupport implements AuctionUserDao  
{
	/**
	 * 根据id查找用户
	 * @param id 需要查找的用户id
	 */
	public AuctionUser get(BigInteger id)
	{
		return (AuctionUser)getHibernateTemplate()
			.get(AuctionUser.class , id);
	}

	/**
	 * 增加用户
	 * @param user 需要增加的用户
	 */
	public void save(AuctionUser user)
	{
		getHibernateTemplate().save(user);
	}

	/**
	 * 修改用户
	 * @param user 需要修改的用户
	 */
	public void update(AuctionUser user)
	{
		getHibernateTemplate().saveOrUpdate(user);
	}

	/**
	 * 删除用户
	 * @param id 需要删除的用户id
	 */ 
	public void delete(BigInteger id)
	{
		getHibernateTemplate().delete(get(id));
	}

	/**
	 * 删除用户
	 * @param user 需要删除的用户
	 */
	public void delete(AuctionUser user)
	{
		getHibernateTemplate().delete(user);
	}

	/**
	 * 查询全部用户
 	 * @return 获得全部用户
	 */ 
	public List<AuctionUser> findAll()
	{
		return (List<AuctionUser>)getHibernateTemplate()
			.find("from AuctionUser");
	}

	/**
	 * 根据用户名，密码查找用户
	 * @param username 查询所需的用户名
	 * @param pass 查询所需的密码
	 * @return 指定用户名、密码对应的用户
	 */
	public AuctionUser findUserByNameAndPass(String username , String pass)
	{
		//执行HQL查询
		List<AuctionUser> ul = (List<AuctionUser>)getHibernateTemplate()
			.find("from AuctionUser au where au.username = ? and au.userpass = ?" ,
			username , pass);
		//返回查询得到的第一个AuctionUser对象
		if (ul.size() == 1)
		{
			return (AuctionUser)ul.get(0);
		}
		return null;
	}

	public Query getQuery(AuctionUser criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	public AuctionUser findOne(Query query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dropCollection() {
		// TODO Auto-generated method stub
		
	}
}