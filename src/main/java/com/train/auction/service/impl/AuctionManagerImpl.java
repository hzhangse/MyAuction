package com.train.auction.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.train.auction.business.BidBean;
import com.train.auction.business.ItemBean;
import com.train.auction.business.KindBean;
import com.train.auction.dao.AuctionUserDao;
import com.train.auction.dao.BidDao;
import com.train.auction.dao.ItemDao;
import com.train.auction.dao.KindDao;
import com.train.auction.dao.StateDao;
import com.train.auction.exception.AuctionException;
import com.train.auction.model.AuctionUser;
import com.train.auction.model.Bid;
import com.train.auction.model.Item;
import com.train.auction.model.Kind;
import com.train.auction.model.State;
import com.train.auction.service.AuctionManager;
import com.train.mongo.AuctionServiceTool;


public class AuctionManagerImpl implements AuctionManager
{
	static Logger log = Logger.getLogger(
		AuctionManagerImpl.class.getName());
	//�����Ǹ�ҵ���߼������������DAO���
	private AuctionUserDao userDao;
	private BidDao bidDao;
	private ItemDao itemDao;
	private KindDao kindDao;
	private StateDao stateDao;
	//ҵ���߼���������ʼ�������������Bean
	private MailSender mailSender;
	private SimpleMailMessage message;
	//Ϊҵ���߼��������ע��DAO��������setter����
	public void setUserDao(AuctionUserDao userDao) 
	{
		this.userDao = userDao; 
	}
	public void setBidDao(BidDao bidDao) 
	{
		this.bidDao = bidDao; 
	}
	public void setItemDao(ItemDao itemDao) 
	{
		this.itemDao = itemDao; 
	}
	public void setKindDao(KindDao kindDao) 
	{
		this.kindDao = kindDao; 
	}
	public void setStateDao(StateDao stateDao) 
	{
		this.stateDao = stateDao; 
	}
	//Ϊҵ���߼����ע�������ʼ�����Bean��setter����
	public void setMailSender(MailSender mailSender)
	{
		this.mailSender = mailSender;
	}
	public void setMessage(SimpleMailMessage message)
	{
		this.message = message;
	}

	/**
	 * ����Ӯȡ�߲�ѯ��Ʒ
	 * @param winerId Ӯȡ�ߵ�ID
	 * @return Ӯȡ�߻�õ�ȫ����Ʒ
	 */
	public List<ItemBean> getItemByWiner(BigInteger winerId)
		throws AuctionException
	{
		try
		{
			List<Item> items = itemDao.findItemByWiner(winerId);
			List<ItemBean> result = new ArrayList<ItemBean>();
			for (Item item : items )
			{
				ItemBean ib = new ItemBean();
				initItem(ib,item);
				result.add(ib);
			}
			return result;
		}
		catch (Exception e)
		{
			log.debug(e.getMessage());
			throw new AuctionException("��ѯ�û���Ӯȡ����Ʒ�����쳣,������");
		}
	}

	private State getState(String name) {
		Query stq = stateDao.getQuery(new State(null, name));
		return stateDao.findOne(stq);
	}

	private Kind getKind(String name) {
		Query q = kindDao.getQuery(new Kind(null, name, null));
		Kind kind = kindDao.findOne(q);
		return kind;
	}

	private AuctionUser getUser(String name) {
		Query uq = userDao.getQuery(new AuctionUser(null, name, null, null));
		AuctionUser owner = userDao.findOne(uq);
		return owner;
	}
	/**
	 * ��ѯ���ĵ�ȫ����Ʒ
	 * @return ȫ��������Ʒ
	 */
	public List<ItemBean> getFailItems() throws AuctionException
	{
		try
		{	
			BigInteger stateId = BigInteger.valueOf(3);
			
			
			List<Item> items = itemDao.findItemByState(stateId);
			List<ItemBean> result = new ArrayList<ItemBean>();
			for (Item item : items )
			{
				ItemBean ib = new ItemBean();
				initItem(ib,item);
				result.add(ib);
			}
			return result;
		}
		catch (Exception e)
		{
			log.debug(e.getMessage());
			throw new AuctionException("��ѯ������Ʒ�����쳣,������");
		}
	}

	/**
	 * �����û�����������֤��¼�Ƿ�ɹ�
	 * @param username ��¼���û���
 	 * @param pass ��¼������
	 * @return ��¼�ɹ������û�ID�����򷵻�-1
	 */
	public BigInteger validLogin(String username , String pass) throws AuctionException
	{
		try
		{
			AuctionUser u = userDao.findUserByNameAndPass(username , pass);
			if (u != null)
			{
				return u.getId();
			}
			return BigInteger.valueOf(-1);
		}
		catch (Exception e)
		{    e.printStackTrace();
		
			log.debug(e.getMessage());
			throw new AuctionException("�����û���¼�����쳣,������");
		}
	}
	
	
	@Override
	public ExecuteResult registUser(String username, String pass, String email)
			throws AuctionException {
		return userDao.registUser(username, pass, email);
		
	}

	/**
	 * ��ѯ�û���ȫ������
	 * @param userId �����û���ID
	 * @return �û���ȫ������
	 */
	public List<BidBean> getBidByUser(BigInteger userId) throws AuctionException
	{
		try
		{
			List<Bid> l = bidDao.findByUser(userId);
			List<BidBean> result = new ArrayList<BidBean>();
			for ( int i = 0 ; i < l.size() ; i++ )
			{
				Bid bid = l.get(i);
				BidBean bb = new BidBean();
				initBid(bb, bid);
				result.add(bb);
			}
			return result;
		}
		catch (Exception e)
		{
			log.debug(e.getMessage());
			throw new AuctionException("����û���ȫ�����۳����쳣,������");
		}
	}

	/**
	 * �����û�����Ŀǰ���������е�ȫ����Ʒ
	 * @param userId �����ߵ�ID
	 * @return ���ڵ�ǰ�û��ġ����������е�ȫ����Ʒ��
	 */
	public List<ItemBean> getItemsByOwner(BigInteger userId)
		throws AuctionException
	{
		try
		{
			List<ItemBean> result = new ArrayList<ItemBean>();
			List<Item> items = itemDao.findItemByOwner(userId);
			for (Item item : items )
			{
				ItemBean ib = new ItemBean();
				initItem(ib,item);
				result.add(ib);
			}
			return result;
		}
		catch (Exception e)
		{
			log.debug(e.getMessage());
			throw new AuctionException("��ѯ�û����е���Ʒ�����쳣,������");
		}
	}

	/**
	 * ��ѯȫ������
	 * @return ϵͳ��ȫ��ȫ������
	 */   
	public List<KindBean> getAllKind() throws AuctionException
	{
		List<KindBean> result = new ArrayList<KindBean>();
		try
		{
			List<Kind> kl = kindDao.findAll();
			for (Kind k : kl )
			{
				result.add(new KindBean(k.getId(),
					k.getKindName(), k.getKindDesc()));
			}
			return result;
		}
		catch (Exception e)
		{
			log.debug(e.getMessage());
			throw new AuctionException("��ѯȫ����������쳣,������");
		}
	}

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
	public BigInteger addItem(String name , String desc , String remark ,
		double initPrice , int avail , BigInteger kind , BigInteger userId)
		throws AuctionException
	{
		System.out.println("userId ===" + userId);
//		try
//		{
			System.out.println("---" + kind);
			Kind k = kindDao.get(kind);
			AuctionUser owner = userDao.get(userId);
			//����Item����
			Item item = new Item();
			item.setItemName(name);
			item.setItemDesc(desc);
			item.setItemRemark(remark);
			item.setAddtime(new Date());
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE , avail);
			item.setEndtime(c.getTime());
			item.setInitPrice(new Double(initPrice));
			item.setMaxPrice(new Double(initPrice));
			item.setItemState(stateDao.get(BigInteger.valueOf(1)));
			item.setKind(k);
			item.setOwner(owner);
			//�־û�Item����
			itemDao.save(item);
			return item.getId();
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//			log.debug(e.getMessage());
//			throw new AuctionException("�����Ʒ�����쳣,������");
//		}
	}

	/**
	 * �������
	 * @param name ��������
	 * @param desc ��������
	 * @return �������������
	 */ 
	public BigInteger addKind(String name , String desc)
		throws AuctionException
	{
		try
		{
			Kind k = new Kind();
			k.setKindName(name);
			k.setKindDesc(desc);
			kindDao.save(k);
			return k.getId();
		}
		catch (Exception e)
		{
			log.debug(e.getMessage());
			throw new AuctionException("�����������쳣,������");
		}
	}

	/**
	 * ���ݲ�Ʒ���࣬��ȡ���������е�ȫ����Ʒ
	 * @param kindId ����id;
	 * @return �����ȫ����Ʒ
	 */
	public List<ItemBean> getItemsByKind(BigInteger kindId)
		throws AuctionException
	{
		List<ItemBean> result = new ArrayList<ItemBean>();
		try
		{
			List<Item> items = itemDao.findItemByKind(kindId);
			for (Item item : items )
			{
				ItemBean ib = new ItemBean();
				initItem(ib , item);
				result.add(ib);
			}
			return result;
		}
		catch (Exception e)
		{
			log.debug(e.getMessage());
			throw new AuctionException("���������ȡ��Ʒ�����쳣,������");
		}
	}

	/**
	 * ��������id��ȡ������
	 * @param kindId ����id;
	 * @return �����������
	 */
	public String getKind(BigInteger kindId) throws AuctionException
	{
		try
		{
			Kind  k = kindDao.get(kindId);
			if (k != null)
			{
				return k.getKindName();
			}
			return null;
		}
		catch (Exception ex)
		{
			log.debug(ex.getMessage());
			throw new AuctionException("��������id��ȡ�������Ƴ����쳣,������");
		}
	}

	/**
	 * ������Ʒid����ȡ��Ʒ
	 * @param itemId ��Ʒid;
	 * @return ָ��id��Ӧ����Ʒ
	 */
	public ItemBean getItem(BigInteger itemId)
		throws AuctionException
	{
		try
		{
			Item item = itemDao.get(itemId);
			ItemBean ib = new ItemBean();
			initItem(ib, item);
			return ib;
		}
		catch (Exception ex)
		{
			log.debug(ex.getMessage());
			throw new AuctionException("������Ʒid��ȡ��Ʒ��ϸ��Ϣ�����쳣,������");
		}
	}

	public void sendMail(AuctionUser au,Item item,String content){
		SimpleMailMessage msg = new SimpleMailMessage(this.message);
		msg.setTo(au.getEmail());
		msg.setText("Dear "
			+ au.getUsername()
			+ ", лл����뾺�ۣ���ľ��۵���Ʒ����: "
			+ item.getItemName());
		mailSender.send(msg);
	}
	/**
	 * �����µľ��ۣ����Ծ����û����ʼ�֪ͨ
	 * @param itemId ��Ʒid;
	 * @param bidPrice ���ۼ۸�
	 * @param userId �����û���ID
	 * @return �����������ۼ�¼��ID
	 */
	public BigInteger addBid(BigInteger itemId , double bidPrice , BigInteger userId)
		throws AuctionException
	{
		try
		{
			AuctionUser au = userDao.get(userId);
			Item item = itemDao.get(itemId);
			if (bidPrice > item.getMaxPrice())
			{
				item.setMaxPrice(new Double(bidPrice));
				itemDao.save(item);
			}
			//��ʼ��Bid����
			Bid bid = new Bid();
			bid.setBidItem(item);
			bid.setBidUser(au);
			bid.setBidDate(new Date());
			bid.setBidPrice(bidPrice);
			//�־û�Bid����
			bidDao.save(bid);
			//׼�������ʼ�
			SimpleMailMessage msg = new SimpleMailMessage(this.message);
			msg.setTo(au.getEmail());
			msg.setText("Dear "
				+ au.getUsername()
				+ ", лл����뾺�ۣ���ľ��۵���Ʒ����: "
				+ item.getItemName());
			mailSender.send(msg);
			return bid.getId();
		}
		catch(Exception ex)
		{
			log.debug(ex.getMessage());
			throw new AuctionException("�����û����۳����쳣,������");
		}
	}

	/**
	 * ����ʱ�����޸���Ʒ��״̬��Ӯȡ��
	 */
	public void updateWiner()throws AuctionException
	{
		try
		{
			BigInteger stateId = BigInteger.valueOf(1);
			
			List itemList = itemDao.findItemByState(stateId);
			for (int i = 0 ; i < itemList.size() ; i++ )
			{
				Item item = (Item)itemList.get(i);
				if (!item.getEndtime().after(new Date()))
				{
					//����ָ����Ʒ����߾�������ѯ�û�
					AuctionUser au = bidDao.findUserByItemAndPrice(
						item.getId() , item.getMaxPrice());
					//�������Ʒ����߾����߲�Ϊnull
					if (au != null)
					{
						//���þ�������ΪӮȡ��
						item.setWiner(au);
						//�޸���Ʒ��״̬��Ϊ����Ӯȡ��
						item.setItemState(stateDao.get(BigInteger.valueOf(2)));
						itemDao.save(item);
						SimpleMailMessage msg = new SimpleMailMessage(this.message);
						msg.setTo(au.getEmail());
						msg.setText("Dear "
							+ au.getUsername()
							+ ", лл����뾺�ۣ��������е���Ʒ����: "
							+ item.getItemName() );
						mailSender.send(msg);
					}
					else
					{
						//���ø���Ʒ��״̬Ϊ�����ġ�
						item.setItemState(stateDao.get(BigInteger.valueOf(3)));
						itemDao.save(item);
					}
				}
			}
		}
		catch (Exception ex)
		{
			log.debug(ex.getMessage());
			throw new AuctionException("����ʱ�����޸���Ʒ��״̬��Ӯȡ�߳����쳣,������");
		}
	}

	/**
	 * ��һ��Bid����ת����BidBean����
	 * @param bb BidBean����
	 * @param bid Bid����
	 */
	private void initBid(BidBean bb , Bid bid)
	{
		bb.setId(bid.getId());
		if (bid.getBidUser() != null )
			bb.setUser(bid.getBidUser().getUsername());
		if (bid.getBidItem() != null )
			bb.setItem(bid.getBidItem().getItemName());
		bb.setPrice(bid.getBidPrice());
		bb.setBidDate(bid.getBidDate());
	}

	/**
	 * ��һ��Item POת����ItemBean��VO
	 * @param ib ItemBean��VO
	 * @param item Item��PO
	 */
	private void initItem(ItemBean ib , Item item)
	{
		ib.setId(item.getId());
		ib.setName(item.getItemName());
		ib.setDesc(item.getItemDesc());
		ib.setRemark(item.getItemRemark());
		if (item.getKind() != null)
			ib.setKind(item.getKind().getKindName());
		if (item.getOwner() != null)
			ib.setOwner(item.getOwner().getUsername());
		if (item.getWiner() != null)
			ib.setWiner(item.getWiner().getUsername());
		ib.setAddTime(item.getAddtime());
		ib.setEndTime(item.getEndtime());
		if (item.getItemState() != null)
			ib.setState(item.getItemState().getStateName());
		ib.setInitPrice(item.getInitPrice());
		ib.setMaxPrice(item.getMaxPrice());
	}
	
	public void initData(){
		AuctionServiceTool tool = new AuctionServiceTool() ;
		tool.setBidDao(bidDao);
		tool.setItemDao(itemDao);
		
		tool.setKindDao(kindDao);
		tool.setService(this);
		tool.setStateDao(stateDao);
		tool.setUserDao(userDao);
		tool.initData();
		
	}
	
}