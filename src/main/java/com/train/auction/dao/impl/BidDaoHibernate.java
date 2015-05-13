package com.train.auction.dao.impl;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.train.auction.dao.BidDao;
import com.train.auction.model.AuctionUser;
import com.train.auction.model.Bid;


public class BidDaoHibernate 
	extends HibernateDaoSupport implements BidDao  
{
	/**
	 * �����������Ҿ��ۼ�¼
	 * @param bidId ����id;
	 * @return id��Ӧ�ľ��ۼ�¼
	 */
	public Bid get(BigInteger bidId)
	{
		return(Bid)getHibernateTemplate().get(Bid.class , bidId);
	}

	/**
	 * ���澺�ۼ�¼
	 * @param bid ��Ҫ����ľ��ۼ�¼
	 */
	public void save(Bid bid)
	{
		getHibernateTemplate().save(bid);
	}

	/**
	 * �޸ľ��ۼ�¼
	 * @param bid ��Ҫ�޸ĵľ��ۼ�¼
	 */
	public void update(Bid bid)
	{
		getHibernateTemplate().saveOrUpdate(bid);
	}

	/**
	 * ɾ�����ۼ�¼
	 * @param id ��Ҫɾ���ľ���id
	 */
	public void delete(BigInteger id)
	{
		getHibernateTemplate().delete(get(id));
	}

	/**
	 * ɾ������
	 * @param bid ��Ҫɾ���ľ���
	 */
	public void delete(Bid bid)
	{
		getHibernateTemplate().delete(bid);
	}

	/**
	 * �����û����Ҿ���
	 * @param id �û�id
	 * @return �û���Ӧ��ȫ��
	 * @return �û���Ӧ��ȫ������
	 */
	public List<Bid> findByUser(BigInteger userId)
	{
		return (List<Bid>)getHibernateTemplate()
			.find("from Bid as bid where bid.bidUser.id = ?" , userId);
	}
	/**
	 * ������Ʒid���Լ����۲�ѯ�û�
	 * @param itemId ��Ʒid;
	 * @param price ���۵ļ۸�
	 * @return ��ָ����Ʒ��ָ�����۶�Ӧ���û�
	 */
	public AuctionUser findUserByItemAndPrice(BigInteger itemId , Double price)
	{
		//ִ��HQL��ѯ
		List<Bid> l = (List<Bid>)getHibernateTemplate()
			.find("from Bid as bid where bid.bidItem.id = ? and bid.bidPrice = ?"
			, new Object[]{itemId , price});
		//���ز�ѯ�õ��ĵ�һ��Bid���������AuctionUser����
		if (l.size() >= 1)
		{
			Bid b = (Bid)l.get(0);
			return b.getBidUser();
		}
		return null;
	}

	public List<Bid> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Query getQuery(Bid criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	public Bid findOne(Query query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dropCollection() {
		// TODO Auto-generated method stub
		
	}
}
