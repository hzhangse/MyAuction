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


public class AuctionManagerImpl implements AuctionManager
{
	static Logger log = Logger.getLogger(
		AuctionManagerImpl.class.getName());
	//以下是该业务逻辑组件所依赖的DAO组件
	private AuctionUserDao userDao;
	private BidDao bidDao;
	private ItemDao itemDao;
	private KindDao kindDao;
	private StateDao stateDao;
	//业务逻辑组件发送邮件所依赖的两个Bean
	private MailSender mailSender;
	private SimpleMailMessage message;
	//为业务逻辑组件依赖注入DAO组件所需的setter方法
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
	//为业务逻辑组件注入两个邮件发送Bean的setter方法
	public void setMailSender(MailSender mailSender)
	{
		this.mailSender = mailSender;
	}
	public void setMessage(SimpleMailMessage message)
	{
		this.message = message;
	}

	/**
	 * 根据赢取者查询物品
	 * @param winerId 赢取者的ID
	 * @return 赢取者获得的全部物品
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
			throw new AuctionException("查询用户所赢取的物品出现异常,请重试");
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
	 * 查询流拍的全部物品
	 * @return 全部流拍物品
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
			throw new AuctionException("查询流拍物品出现异常,请重试");
		}
	}

	/**
	 * 根据用户名，密码验证登录是否成功
	 * @param username 登录的用户名
 	 * @param pass 登录的密码
	 * @return 登录成功返回用户ID，否则返回-1
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
			throw new AuctionException("处理用户登录出现异常,请重试");
		}
	}

	/**
	 * 查询用户的全部出价
	 * @param userId 竞价用户的ID
	 * @return 用户的全部出价
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
			throw new AuctionException("浏览用户的全部竞价出现异常,请重试");
		}
	}

	/**
	 * 根据用户查找目前仍在拍卖中的全部物品
	 * @param userId 所属者的ID
	 * @return 属于当前用户的、处于拍卖中的全部物品。
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
			throw new AuctionException("查询用户所有的物品出现异常,请重新");
		}
	}

	/**
	 * 查询全部种类
	 * @return 系统中全部全部种类
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
			throw new AuctionException("查询全部种类出现异常,请重试");
		}
	}

	/**
	* 添加物品
	* @param name 物品名称
	* @param desc 物品描述
	* @param remark 物品备注
	* @param avail 有效天数
	* @param kind 物品种类
	* @param userId 添加者的ID
	* @return 新增物品的主键
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
			//创建Item对象
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
			//持久化Item对象
			itemDao.save(item);
			return item.getId();
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//			log.debug(e.getMessage());
//			throw new AuctionException("添加物品出现异常,请重试");
//		}
	}

	/**
	 * 添加种类
	 * @param name 种类名称
	 * @param desc 种类描述
	 * @return 新增种类的主键
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
			throw new AuctionException("添加种类出现异常,请重试");
		}
	}

	/**
	 * 根据产品分类，获取处于拍卖中的全部物品
	 * @param kindId 种类id;
	 * @return 该类的全部产品
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
			throw new AuctionException("根据种类获取物品出现异常,请重试");
		}
	}

	/**
	 * 根据种类id获取种类名
	 * @param kindId 种类id;
	 * @return 该种类的名称
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
			throw new AuctionException("根据种类id获取种类名称出现异常,请重试");
		}
	}

	/**
	 * 根据物品id，获取物品
	 * @param itemId 物品id;
	 * @return 指定id对应的物品
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
			throw new AuctionException("根据物品id获取物品详细信息出现异常,请重试");
		}
	}

	/**
	 * 增加新的竞价，并对竞价用户发邮件通知
	 * @param itemId 物品id;
	 * @param bidPrice 竞价价格
	 * @param userId 竞价用户的ID
	 * @return 返回新增竞价记录的ID
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
			//初始化Bid对象
			Bid bid = new Bid();
			bid.setBidItem(item);
			bid.setBidUser(au);
			bid.setBidDate(new Date());
			bid.setBidPrice(bidPrice);
			//持久化Bid对象
			bidDao.save(bid);
			//准备发送邮件
			SimpleMailMessage msg = new SimpleMailMessage(this.message);
			msg.setTo(au.getEmail());
			msg.setText("Dear "
				+ au.getUsername()
				+ ", 谢谢你参与竞价，你的竞价的物品的是: "
				+ item.getItemName());
			mailSender.send(msg);
			return bid.getId();
		}
		catch(Exception ex)
		{
			log.debug(ex.getMessage());
			throw new AuctionException("处理用户竞价出现异常,请重试");
		}
	}

	/**
	 * 根据时间来修改物品的状态、赢取者
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
					//根据指定物品和最高竞价来查询用户
					AuctionUser au = bidDao.findUserByItemAndPrice(
						item.getId() , item.getMaxPrice());
					//如果该物品的最高竞价者不为null
					if (au != null)
					{
						//将该竞价者设为赢取者
						item.setWiner(au);
						//修改物品的状态成为“被赢取”
						item.setItemState(stateDao.get(BigInteger.valueOf(2)));
						itemDao.save(item);
					}
					else
					{
						//设置该物品的状态为“流拍”
						item.setItemState(stateDao.get(BigInteger.valueOf(3)));
						itemDao.save(item);
					}
				}
			}
		}
		catch (Exception ex)
		{
			log.debug(ex.getMessage());
			throw new AuctionException("根据时间来修改物品的状态、赢取者出现异常,请重试");
		}
	}

	/**
	 * 将一个Bid对象转换成BidBean对象
	 * @param bb BidBean对象
	 * @param bid Bid对象
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
	 * 将一个Item PO转换成ItemBean的VO
	 * @param ib ItemBean的VO
	 * @param item Item的PO
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
}