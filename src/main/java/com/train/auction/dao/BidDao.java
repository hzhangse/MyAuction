package com.train.auction.dao;

import java.math.BigInteger;
import java.util.List;

import com.train.auction.model.AuctionUser;
import com.train.auction.model.Bid;

public interface BidDao  extends IDao<Bid>
{
	/**
	 * �����������Ҿ��ۼ�¼
	 * @param bidId ����id;
	 * @return id��Ӧ�ľ��ۼ�¼
	 */
	Bid get(BigInteger bidId);

	/**
	 * ���澺�ۼ�¼
	 * @param bid ��Ҫ����ľ��ۼ�¼
	 */    
	void save(Bid bid);

	/**
	 * �޸ľ��ۼ�¼
	 * @param bid ��Ҫ�޸ĵľ��ۼ�¼
	 */
	void update(Bid bid);

	/**
	 * ɾ�����ۼ�¼
	 * @param id ��Ҫɾ���ľ���id
	 */
	void delete(BigInteger id);

	/**
	 * ɾ������
	 * @param bid ��Ҫɾ���ľ���
	 */
	void delete(Bid bid);

	/**
	 * �����û����Ҿ���
	 * @param id �û�id
	 * @return �û���Ӧ��ȫ��
	 * @return �û���Ӧ��ȫ������
	 */
	List<Bid> findByUser(BigInteger userId);

	/**
	 * ������Ʒid���Լ����۲�ѯ�û�
	 * @param itemId ��Ʒid;
	 * @param price ���۵ļ۸�
	 * @return ��ָ����Ʒ��ָ�����۶�Ӧ���û�
	 */
	AuctionUser findUserByItemAndPrice(BigInteger itemId , Double price);
}
