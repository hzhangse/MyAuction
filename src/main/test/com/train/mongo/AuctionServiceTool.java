package com.train.mongo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.query.Query;

import com.train.auction.dao.AuctionUserDao;
import com.train.auction.dao.BidDao;
import com.train.auction.dao.ItemDao;
import com.train.auction.dao.KindDao;
import com.train.auction.dao.StateDao;
import com.train.auction.dao.impl.mongo.AuctionUserDaoMongo;
import com.train.auction.dao.impl.mongo.BidDaoMongo;
import com.train.auction.dao.impl.mongo.ItemDaoMongo;
import com.train.auction.dao.impl.mongo.KindDaoMongo;
import com.train.auction.dao.impl.mongo.StateDaoMongo;
import com.train.auction.model.AuctionUser;
import com.train.auction.model.Bid;
import com.train.auction.model.Item;
import com.train.auction.model.Kind;
import com.train.auction.model.State;
import com.train.auction.service.AuctionManager;

public class AuctionServiceTool {

	private static ApplicationContext app;
	private static AuctionManager service;
	private static AuctionUserDao userDao;
	private static KindDao kindDao;
	private static BidDao bidDao;
	private static ItemDao itemDao;
	private static StateDao stateDao;

	
	public AuctionServiceTool(){
		
	}
	
//	public AuctionServiceTool(AuctionUserDao userDao,
//			KindDao kindDao, BidDao bidDao, ItemDao itemDao,
//			StateDao stateDao) {
//		this.userDao = userDao;
//		this.kindDao = kindDao;
//		this.bidDao = bidDao;
//		this.itemDao = itemDao;
//		this.stateDao = stateDao;
//	}

	public void saveUser() {
		AuctionUser user = new AuctionUser();
		user.setUsername("rainbow954");
		user.setEmail("rainbow954@163.com");
		user.setUserpass("rainbow");
		userDao.save(user);
		
		user = new AuctionUser();
		user.setUsername("angela");
		user.setEmail("1311054093@qq.com");
		user.setUserpass("rainbow");
		userDao.save(user);
		
		user = new AuctionUser();
		user.setUsername("admin");
		user.setEmail("rainbow954@163.com");
		user.setUserpass("admin");
		userDao.save(user);

	}

	public void saveKind() {
		Kind k1 = new Kind();
		// k1.setId(new BigInteger("1"));
		k1.setKindName("IT产品");
		k1.setKindDesc("电脑，台式机，笔记本，显卡，内存，零配件......");

		
		Kind k2 = new Kind();
		// k1.setId(new BigInteger("1"));
		k2.setKindName("家装设备");
		k2.setKindDesc("厨卫，电器等");
		
		
		Kind k3 = new Kind();
		// k1.setId(new BigInteger("1"));
		k3.setKindName("汽车");
		k3.setKindDesc("整车及零配件");
		
		Kind k4 = new Kind();
		// k1.setId(new BigInteger("2"));
		k4.setKindName("电子产品");
		k4.setKindDesc("手机，电视，pad，mp3");
		
		kindDao.save(k1);
		kindDao.save(k2);
		kindDao.save(k3);
		kindDao.save(k4);
	}

	public void saveState() {
		State s1 = new State();
		// s1.setId(new BigInteger("1"));
		s1.setStateName("拍卖中");
		stateDao.save(s1);
		s1 = new State();
		// s1.setId(new BigInteger("2"));
		s1.setStateName("拍卖成功");
		stateDao.save(s1);
		s1 = new State();
		// s1.setId(new BigInteger("3"));
		s1.setStateName("流拍");
		stateDao.save(s1);
	}

	
	
	
	public void saveBid(Item item,String username) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(new Date().getTime());
		Random r = new Random();
		cal.add(Calendar.DAY_OF_YEAR, -1*(r.nextInt(5)));
		double d= r.nextInt(10);
		saveBid(username, item.getInitPrice()+d, cal.getTime(), item);

	}

	public void saveBid(Item item,String username,double price) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(new Date().getTime());
		Random r = new Random();
		cal.add(Calendar.DAY_OF_YEAR, -1*(r.nextInt(5)));
		
		saveBid(username, price, cal.getTime(), item);

	}


	public void saveBid(String user, double price, Date time, Item item) {
		Bid bid = new Bid();
		bid.setBidUser(this.getUser(user));
		bid.setBidPrice(price);
		bid.setBidItem(item);
		item.getBids().add(bid);
		bid.setBidDate(time);
		this.bidDao.save(bid);

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

	public void saveItems() {

		Calendar caladd = Calendar.getInstance();
		caladd.setTimeInMillis(new Date().getTime());
		caladd.add(Calendar.DAY_OF_YEAR, -5);

		Calendar calend = Calendar.getInstance();
		calend.setTimeInMillis(new Date().getTime());
		calend.add(Calendar.DAY_OF_YEAR, 3);

		Item item = saveItem("iphone6s", "苹果6s手机", "公司年会奖品，全新未开封", caladd.getTime(),
				calend.getTime(), 3000, 4500, this.getKind("电子产品"),
				getState("拍卖中"), this.getUser("admin"), null);

		this.saveBid(item,"rainbow954");
		this.saveBid(item,"angela");

		caladd.setTimeInMillis(new Date().getTime());
		caladd.add(Calendar.DAY_OF_YEAR, -10);

		calend.setTimeInMillis(new Date().getTime());
		calend.add(Calendar.DAY_OF_YEAR, 3);

		item = saveItem("金士顿DDR-3内存-2G", "终身质保，全球售后", "2G 单条", caladd.getTime(), calend.getTime(),
				100, 210, this.getKind("IT产品"), getState("拍卖中"),
				this.getUser("admin"), null);
		this.saveBid(item,"rainbow954");
		this.saveBid(item,"angela");


		calend.setTimeInMillis(new Date().getTime());
		calend.add(Calendar.DAY_OF_YEAR, -1);

		Item item3 = saveItem("宝马X5高配版", "5万公里", "没有任何事故，包括挂蹭",
				caladd.getTime(), calend.getTime(), 210000, 250000,
				getKind("汽车"), getState("拍卖中"), this.getUser("admin"),
				null);

		Item item4 = saveItem("苏泊尔电饭锅", "终身质保，全球售后", "公司年会奖品，全新未开封",
				caladd.getTime(), calend.getTime(), 200, 280,
				getKind("家装设备"), getState("拍卖中"), this.getUser("rainbow954"),
				null);
		
		saveBid(item3,"rainbow954",250000);
		saveBid(item3,"angela");
		saveBid(item4,"admin");
		saveBid(item4,"angela");
		

		Item item1 = this.itemDao.get(item.getId());

		AuctionUser user1 = this.bidDao.findUserByItemAndPrice(item3.getId(),
				new Double(250000));

		Assert.assertTrue(item1 != null);
		Assert.assertTrue(this.bidDao
				.findByUser(this.getUser("rainbow954").getId()) != null);
		Assert.assertTrue(user1 != null);
	}

	public Item saveItem(String name, String remark, String desc, Date addtime,
			Date endtime, double initprice, double maxprice, Kind kind,
			State state, AuctionUser owner, AuctionUser winner) {
		Item item = new Item();
		item.setItemName(name);
		item.setItemRemark(remark);
		item.setItemDesc(desc);
		item.setAddtime(addtime);

		item.setEndtime(endtime);

		item.setInitPrice(initprice);
		item.setMaxPrice(maxprice);

		item.setKind(kind);
		item.setOwner(owner);
		item.setItemState(state);
		item.setWiner(winner);
		itemDao.save(item);
		return item;
	}

	
	@Test
	public void initData() {
		userDao.dropCollection();
		kindDao.dropCollection();
		bidDao.dropCollection();
		itemDao.dropCollection();
		stateDao.dropCollection();
		this.saveUser();
		this.saveKind();
		this.saveState();
		this.saveItems();

	}

	@Test
	public void testBidDao() {
		this.getUser("rainbow954");
		this.bidDao.findByUser(this.getUser("rainbow954").getId());

	}

	@Test
	public void testItemDao() {
		List<Item> result = null;
		Assert.assertTrue(this.itemDao.findItemByKind(this.getKind("汽车")
				.getId()) != null);
		result = this.itemDao.findItemByKind(this.getKind("汽车").getId());
		for (Item item : result)
			System.out.println(item.getItemName() + "_"
					+ item.getKind().getKindName());

		result = itemDao.findItemByOwner(this.getUser("admin").getId());
		for (Item item : result)
			System.out.println(item.getItemName() + "_"
					+ item.getOwner().getUsername());

		Assert.assertTrue(this.itemDao.findItemByState(this.getState("拍卖成功")
				.getId()) != null);
		result = this.itemDao.findItemByState(this.getState("拍卖成功").getId());
		for (Item item : result)
			System.out.println(item.getItemName() + "_"
					+ item.getItemState().getStateName());

		Assert.assertTrue(this.itemDao.findItemByWiner(this.getUser("rainbow954")
				.getId()) != null);

		result = this.itemDao.findItemByWiner(this.getUser("rainbow954").getId());
		for (Item item : result)
			System.out.println(item.getItemName() + "_"
					+ item.getWiner().getUsername());
	}

	@Test
	public void testUserDao() {
		AuctionUser user = this.userDao.findUserByNameAndPass("rainbow954",
				"rainbow");
		Assert.assertTrue(user.getUserpass().equalsIgnoreCase("rainbow"));
	}

	
	@BeforeClass
	public static void initSpring() {
		app = new ClassPathXmlApplicationContext(new String[] {

		"classpath:mongoDaoContext.xml" });
		// service = (AuctionManager) app.getBean("mgr");
		userDao = (AuctionUserDaoMongo) app.getBean("auctionUserDao");
		kindDao = (KindDaoMongo) app.getBean("kindDao");
		bidDao = (BidDaoMongo) app.getBean("bidDao");
		itemDao = (ItemDaoMongo) app.getBean("itemDao");
		stateDao = (StateDaoMongo) app.getBean("stateDao");

	}

	public static ApplicationContext getApp() {
		return app;
	}

	public static void setApp(ApplicationContext app) {
		AuctionServiceTool.app = app;
	}

	public static AuctionManager getService() {
		return service;
	}

	public static void setService(AuctionManager service) {
		AuctionServiceTool.service = service;
	}

	public static AuctionUserDao getUserDao() {
		return userDao;
	}

	public static void setUserDao(AuctionUserDao userDao) {
		AuctionServiceTool.userDao = userDao;
	}

	public static KindDao getKindDao() {
		return kindDao;
	}

	public static void setKindDao(KindDao kindDao) {
		AuctionServiceTool.kindDao = kindDao;
	}

	public static BidDao getBidDao() {
		return bidDao;
	}

	public static void setBidDao(BidDao bidDao) {
		AuctionServiceTool.bidDao = bidDao;
	}

	public static ItemDao getItemDao() {
		return itemDao;
	}

	public static void setItemDao(ItemDao itemDao) {
		AuctionServiceTool.itemDao = itemDao;
	}

	public static StateDao getStateDao() {
		return stateDao;
	}

	public static void setStateDao(StateDao stateDao) {
		AuctionServiceTool.stateDao = stateDao;
	}
}
