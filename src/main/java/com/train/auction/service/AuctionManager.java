package com.train.auction.service;

import java.math.BigInteger;
import java.util.List;

import com.train.auction.business.BidBean;
import com.train.auction.business.ItemBean;
import com.train.auction.business.KindBean;
import com.train.auction.exception.AuctionException;


public interface AuctionManager  
{
	/**
	 * ����Ӯȡ�߲�ѯ��Ʒ
	 * @param winerId Ӯȡ�ߵ�ID
	 * @return Ӯȡ�߻�õ�ȫ����Ʒ
	 */
	List<ItemBean> getItemByWiner(BigInteger winerId) 
		throws AuctionException;

	/**
	 * ��ѯ���ĵ�ȫ����Ʒ
	 * @return ȫ��������Ʒ
	 */
	List<ItemBean> getFailItems()throws AuctionException;

	/**
	 * �����û�����������֤��¼�Ƿ�ɹ�
	 * @param username ��¼���û���
 	 * @param pass ��¼������
	 * @return ��¼�ɹ������û�ID�����򷵻�-1
	 */
	BigInteger validLogin(String username , String pass)
		throws AuctionException;

	/**
	 * ��ѯ�û���ȫ������
	 * @param userId �����û���ID
	 * @return �û���ȫ������
	 */
	List<BidBean> getBidByUser(BigInteger userId)
		throws AuctionException;

	/**
	 * �����û�����Ŀǰ���������е�ȫ����Ʒ
	 * @param userId �����ߵ�ID
	 * @return ���ڵ�ǰ�û��ġ����������е�ȫ����Ʒ��
	 */
	List<ItemBean> getItemsByOwner(BigInteger userId)
		throws AuctionException;

	/**
	 * ��ѯȫ������
	 * @return ϵͳ��ȫ��ȫ������
	 */
	List<KindBean> getAllKind() throws AuctionException; 

	/**
	* �����Ʒ
	* @param name ��Ʒ����
	* @param desc ��Ʒ����
	* @param remark ��Ʒ��ע
	* @param avail ��Ч����
	* @param kind ��Ʒ����
	* @param userId ����ߵ�ID
	* @return ������Ʒ������
	*/ 
	BigInteger addItem(String name , String desc , String remark , 
		double initPrice , int avail , BigInteger kind , BigInteger userId) 
		throws AuctionException;

	/**
	 * �������
	 * @param name ��������
	 * @param desc ��������
	 * @return �������������
	 */ 
	BigInteger addKind(String name , String desc) throws AuctionException;

	/**
	 * ���ݲ�Ʒ���࣬��ȡ���������е�ȫ����Ʒ
	 * @param kindId ����id;
	 * @return �����ȫ����Ʒ
	 */
	List<ItemBean> getItemsByKind(BigInteger kindId) throws AuctionException;

	/**
	 * ��������id��ȡ������
	 * @param kindId ����id;
	 * @return �����������
	 */
	String getKind(BigInteger kindId) throws AuctionException;

	/**
	 * ������Ʒid����ȡ��Ʒ
	 * @param itemId ��Ʒid;
	 * @return ָ��id��Ӧ����Ʒ
	 */
	ItemBean getItem(BigInteger itemId) throws AuctionException;

	/**
	 * �����µľ��ۣ����Ծ����û����ʼ�֪ͨ
	 * @param itemId ��Ʒid;
	 * @param bidPrice ���ۼ۸�
	 * @param userId �����û���ID
	 * @return �����������ۼ�¼��ID
	 */
	BigInteger addBid(BigInteger itemId , double bidPrice ,BigInteger userId)
		throws AuctionException;

	/**
	 * ����ʱ�����޸���Ʒ��Ӯȡ��
	 */
	void updateWiner()throws AuctionException;
	
	void initData();
}