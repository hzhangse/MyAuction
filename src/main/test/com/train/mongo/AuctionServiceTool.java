package com.train.mongo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
		user.setUsername("tomcat");
		user.setEmail("rainbow954@163.com");
		user.setUserpass("tomcat");

		AuctionUser user1 = new AuctionUser();
		user1.setUsername("mysql");
		user1.setEmail("rainbow954@163.com");
		user1.setUserpass("mysql");

		userDao.save(user);
		userDao.save(user1);

	}

	public void saveKind() {
		Kind k1 = new Kind();
		// k1.setId(new BigInteger("1"));
		k1.setKindName("电脑硬件");
		k1.setKindDesc("这里并不是很主流的产品，但价格绝对令你心动");

		Kind k2 = new Kind();
		// k1.setId(new BigInteger("2"));
		k2.setKindName("房产");
		k2.setKindDesc("提供非常稀缺的房源");
		kindDao.save(k1);
		kindDao.save(k2);

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

	public void saveBid1(Item item) {

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(new Date().getTime());
		cal.add(Calendar.DAY_OF_YEAR, -2);
		saveBid("mysql", 250, cal.getTime(), item);

	}

	public void saveBid2(Item item) {

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(new Date().getTime());
		cal.add(Calendar.DAY_OF_YEAR, -6);
		saveBid("tomcat", 25000, cal.getTime(), item);

	}

	public void saveBid3(Item item) {

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(new Date().getTime());
		cal.add(Calendar.DAY_OF_YEAR, -1);
		saveBid("mysql", 20000, cal.getTime(), item);

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
		calend.add(Calendar.DAY_OF_YEAR, 30);

		Item item = saveItem("主板", "老式主板", "老主板，还可以用", caladd.getTime(),
				calend.getTime(), 230, 250, this.getKind("电脑硬件"),
				getState("拍卖中"), this.getUser("tomcat"), null);

		this.saveBid1(item);

		caladd.setTimeInMillis(new Date().getTime());
		caladd.add(Calendar.DAY_OF_YEAR, -2);

		calend.setTimeInMillis(new Date().getTime());
		calend.add(Calendar.DAY_OF_YEAR, -1);

		saveItem("显卡", "老式显卡", "老显卡，还可以用", caladd.getTime(), calend.getTime(),
				210, 210, this.getKind("电脑硬件"), getState("流拍"),
				this.getUser("mysql"), null);

		calend.setTimeInMillis(new Date().getTime());
		calend.add(Calendar.DAY_OF_YEAR, -1);

		Item item3 = saveItem("老房子", "40年的老房子", "40年的老房子，还可以用",
				caladd.getTime(), calend.getTime(), 21000, 25000,
				getKind("房产"), getState("拍卖成功"), this.getUser("mysql"),
				this.getUser("tomcat"));

		this.saveBid2(item3);
		this.saveBid3(item3);

		Item item1 = this.itemDao.get(item.getId());

		AuctionUser user1 = this.bidDao.findUserByItemAndPrice(item3.getId(),
				new Double(25000));

		Assert.assertTrue(item1 != null);
		Assert.assertTrue(this.bidDao
				.findByUser(this.getUser("tomcat").getId()) != null);
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
		this.getUser("tomcat");
		this.bidDao.findByUser(this.getUser("tomcat").getId());

	}

	@Test
	public void testItemDao() {
		List<Item> result = null;
		Assert.assertTrue(this.itemDao.findItemByKind(this.getKind("房产")
				.getId()) != null);
		result = this.itemDao.findItemByKind(this.getKind("房产").getId());
		for (Item item : result)
			System.out.println(item.getItemName() + "_"
					+ item.getKind().getKindName());

		result = itemDao.findItemByOwner(this.getUser("mysql").getId());
		for (Item item : result)
			System.out.println(item.getItemName() + "_"
					+ item.getOwner().getUsername());

		Assert.assertTrue(this.itemDao.findItemByState(this.getState("拍卖成功")
				.getId()) != null);
		result = this.itemDao.findItemByState(this.getState("拍卖成功").getId());
		for (Item item : result)
			System.out.println(item.getItemName() + "_"
					+ item.getItemState().getStateName());

		Assert.assertTrue(this.itemDao.findItemByWiner(this.getUser("tomcat")
				.getId()) != null);

		result = this.itemDao.findItemByWiner(this.getUser("tomcat").getId());
		for (Item item : result)
			System.out.println(item.getItemName() + "_"
					+ item.getWiner().getUsername());
	}

	@Test
	public void testUserDao() {
		AuctionUser user = this.userDao.findUserByNameAndPass("tomcat",
				"tomcat");
		Assert.assertTrue(user.getUserpass().equalsIgnoreCase("tomcat"));
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
