package com.train.auction.dao.impl.mongo;

import java.io.IOException;
import java.math.BigInteger;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.BasicDBObject;
import com.mongodb.Bytes;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.MongoOptions;
import com.mongodb.QueryOperators;
import com.mongodb.ServerAddress;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;
import com.train.auction.dao.IDao;
import com.train.auction.model.AuctionUser;

public abstract class BaseMongoDao<T> implements IDao<T> {
	protected Mongo mg = null;
	protected DB db;
	protected DBCollection dbCollection;
	private String dbName = "auction";
	protected String tableName = "test";

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Before
	public void init() {

		try {
			Properties prop = new Properties();

			prop.load(this.getClass().getResourceAsStream("mongodb.properties"));
			ServerAddress addr = new ServerAddress((String) prop.getProperty(
					"db.host", "localhost"), Integer.valueOf(prop
					.getProperty("db.port")));
			MongoOptions options = new MongoOptions();
			options.connectionsPerHost = Integer.valueOf(prop
					.getProperty("db.options.connectionsPerHost"));
			options.maxWaitTime = Integer.valueOf(prop
					.getProperty("db.options.maxWaitTime"));
			options.socketKeepAlive = new Boolean(
					prop.getProperty("db.options.socketKeepAlive"));

			options.threadsAllowedToBlockForConnectionMultiplier = Integer
					.valueOf(prop
							.getProperty("db.options.threadsAllowedToBlockForConnectionMultiplier"));
			mg = new Mongo(addr, options);
			// mg = new Mongo("localhost", 27017);
			// 获取temp DB；如果默认没有创建，mongodb会自动创建
			
			// 获取users DBCollection；如果默认没有创建，mongodb会自动创建

			MongoCredential credential = MongoCredential.createPlainCredential(prop.getProperty("db.user"), dbName, prop
					.getProperty("db.password").toCharArray());
		
			//MongoClient mongoClient = new MongoClient(addr, Arrays.asList(credential));
			MongoClientURI uri = new MongoClientURI("mongodb://auction:auction@127.0.0.1/?authSource=auction&&authMechanism=SCRAM-SHA-1");
			MongoClient mongoClient = new MongoClient(uri);
			db = mongoClient.getDB(dbName);
			dbCollection = db.getCollection("test");
//			if (auth) { // 如果用户名密码验证成功
//				System.out.println("成功.......");
//				dbCollection = db.getCollection(tableName);
//				// select(dbCollection);
//			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		} catch (IOException e) {

		}

	}

	public BaseMongoDao() {

		init();
	}

	/**
	 * <b>function:</b> 查询所有数据
	 */
	@Test
	protected void queryAll() {
		print("查询所有数据：");
		// db游标
		DBCursor cur = dbCollection.find();
		while (cur.hasNext()) {
			print(cur.next());
		}
	}

	@Test
	public void add() {
		// 先查询所有数据
		queryAll();
		print("count: " + dbCollection.count());

		DBObject user = new BasicDBObject();
		user.put("name", "hoojo");
		user.put("age", 24);
		// users.save(user)保存，getN()获取影响行数
		// print(users.save(user).getN());

		// 扩展字段，随意添加字段，不影响现有数据
		user.put("sex", "男");
		print(dbCollection.save(user).getN());

		// 添加多条数据，传递Array对象
		print(dbCollection.insert(user, new BasicDBObject("name", "tom"))
				.getN());

		// 添加List集合
		List<DBObject> list = new ArrayList<DBObject>();
		list.add(user);
		DBObject user2 = new BasicDBObject("name", "lucy");
		user.put("age", 22);
		list.add(user2);
		// 添加List集合
		print(dbCollection.insert(list).getN());

		// 查询下数据，看看是否添加成功
		print("count: " + dbCollection.count());
		queryAll();
	}

	@Test
	public void remove() {
		queryAll();
		print("删除id = 4de73f7acd812d61b4626a77："
				+ dbCollection.remove(
						new BasicDBObject("_id", new ObjectId(
								"4de73f7acd812d61b4626a77"))).getN());
		print("remove age >= 24: "
				+ dbCollection
						.remove(new BasicDBObject("age", new BasicDBObject(
								"$gte", 24))).getN());
	}

	@Test
	public void modify() {
		print("修改："
				+ dbCollection.update(
						new BasicDBObject("_id", new ObjectId(
								"4dde25d06be7c53ffbd70906")),
						new BasicDBObject("age", 99)).getN());
		print("修改："
				+ dbCollection.update(
						new BasicDBObject("_id", new ObjectId(
								"4dde2b06feb038463ff09042")),
						new BasicDBObject("age", 121), true,// 如果数据库不存在，是否添加
						false// 多条修改
						).getN());
		print("修改："
				+ dbCollection.update(new BasicDBObject("name", "haha"),
						new BasicDBObject("name", "dingding"), true,// 如果数据库不存在，是否添加
						true// false只修改第一天，true如果有多条就不修改
						).getN());

		// 当数据库不存在就不修改、不添加数据，当多条数据就不修改
		// print("修改多条：" + coll.updateMulti(new BasicDBObject("_id", new
		// ObjectId("4dde23616be7c19df07db42c")), new BasicDBObject("name",
		// "199")));
	}

	@Test
	public void query() {
		// 查询所有
		// queryAll();

		// 查询id = 4de73f7acd812d61b4626a77
		print("find id = 4de73f7acd812d61b4626a77: "
				+ dbCollection.find(
						new BasicDBObject("_id", new ObjectId("4de73f7acd812d61b4626a77"))).toArray());

		// 查询age = 24
		print("find age = 24: "
				+ dbCollection.find(new BasicDBObject("age", 24)).toArray());

		// 查询age >= 24
		print("find age >= 24: "
				+ dbCollection
						.find(new BasicDBObject("age", new BasicDBObject(
								"$gte", 24))).toArray());
		print("find age <= 24: "
				+ dbCollection
						.find(new BasicDBObject("age", new BasicDBObject(
								"$lte", 24))).toArray());

		print("查询age!=25："
				+ dbCollection.find(
						new BasicDBObject("age", new BasicDBObject("$ne", 25)))
						.toArray());
		print("查询age in 25/26/27："
				+ dbCollection.find(
						new BasicDBObject("age", new BasicDBObject(
								QueryOperators.IN, new int[] { 25, 26, 27 })))
						.toArray());
		print("查询age not in 25/26/27："
				+ dbCollection.find(
						new BasicDBObject("age", new BasicDBObject(
								QueryOperators.NIN, new int[] { 25, 26, 27 })))
						.toArray());
		print("查询age exists 排序："
				+ dbCollection.find(
						new BasicDBObject("age", new BasicDBObject(
								QueryOperators.EXISTS, true))).toArray());

		print("只查询age属性："
				+ dbCollection.find(null, new BasicDBObject("age", true))
						.toArray());
		print("只查属性："
				+ dbCollection.find(null, new BasicDBObject("age", true), 0, 2)
						.toArray());
		print("只查属性："
				+ dbCollection.find(null, new BasicDBObject("age", true), 0, 2,
						Bytes.QUERYOPTION_NOTIMEOUT).toArray());

		// 只查询一条数据，多条去第一条
		print("findOne: " + dbCollection.findOne());
		print("findOne: " + dbCollection.findOne(new BasicDBObject("age", 26)));
		print("findOne: "
				+ dbCollection.findOne(new BasicDBObject("age", 26),
						new BasicDBObject("name", true)));

		// 查询修改、删除
		print("findAndRemove 查询age=25的数据，并且删除: "
				+ dbCollection.findAndRemove(new BasicDBObject("age", 25)));

		// 查询age=26的数据，并且修改name的值为Abc
		print("findAndModify: "
				+ dbCollection.findAndModify(new BasicDBObject("age", 26),
						new BasicDBObject("name", "Abc")));
		print("findAndModify: "
				+ dbCollection.findAndModify(new BasicDBObject("age", 28), // 查询age=28的数据
						new BasicDBObject("name", true), // 查询name属性
						new BasicDBObject("age", true), // 按照age排序
						false, // 是否删除，true表示删除
						new BasicDBObject("name", "Abc"), // 修改的值，将name修改成Abc
						true, true));

		queryAll();
	}

	public void testOthers() {
		DBObject user = new BasicDBObject();
		user.put("name", "hoojo");
		user.put("age", 24);

		// JSON 对象转换
		print("serialize: " + JSON.serialize(user));
		// 反序列化
		print("parse: " + JSON.parse("{ \"name\" : \"hoojo\" , \"age\" : 24}"));

		print("判断temp Collection是否存在: " + db.collectionExists("temp"));

		// 如果不存在就创建
		if (!db.collectionExists("temp")) {
			DBObject options = new BasicDBObject();
			options.put("size", 20);
			options.put("capped", 20);
			options.put("max", 20);
			print(db.createCollection("account", options));
		}

		// 设置db为只读
		//db.setReadOnly(true);

		// 只读不能写入数据
		db.getCollection("test").save(user);

	}

	@After
	public void destory() {
		if (mg != null)
			mg.close();
		mg = null;
		db = null;
		this.dbCollection = null;
		System.gc();
	}

	public void print(Object o) {
		System.out.println(o);
	}

	/** 查询 */
	private static void select(DBCollection col) {
		DBObject conditions = new BasicDBObject(); // 条件类
		Integer[] num = { 10, 99 };

		// conditions.put("collor", "red"); // 等于
		// conditions.put("size", new BasicDBObject("$gt", 5)); // gt(>), lt(<),
		// gte(>=), lte(<=), in(包含), nin((不包含)
		// conditions.put("size", new BasicDBObject("$gt", 5).append("$lt",
		// 15)); // >5 and <15, （可以多个条件）
		// conditions.put("num", new BasicDBObject("$in", num)); // 无效
		// conditions.put("properties.skills", "java"); // 子查询：list
		// conditions.put("properties.age", 23); // 子查询：map
		// DBCursor ite = col.find().skip(1).limit(2); // 取范围

		DBCursor ite = col.find(conditions); // 结果集（属性条件：无条件查询所有）
		DBCursor ite_ = col.find(new BasicDBObject // 结果集（直接条件）
				("$where", "this.size==10")); // >, <, >=, <=, ==

		while (ite.hasNext()) {
			System.out.println(ite.next());
		}
		System.out.println("............");

		// =========== 模糊查询：只能用正则 ============
		Pattern john = Pattern.compile("ed"); // 正则
		BasicDBObject query = new BasicDBObject("collor", john);
		DBCursor ite2 = col.find(query);
		while (ite2.hasNext()) {
			System.out.println(ite2.next());
		}
	}

	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//	  
//		BaseMongoDao dao = new BaseMongoDao();
//		dao.dbCollection.drop();
//		AuctionUser user1 = new AuctionUser();
//		user1.setUsername("aaa");
//		user1.setEmail("aaa@.163.com");
//		dao.save(user1);
//		
//		
//		DBObject user = new BasicDBObject();
//		user.put("username", "hoojo");
//		user.put("userpass", "aaa");
//		// users.save(user)保存，getN()获取影响行数
//		// print(users.save(user).getN());
//
//		// 扩展字段，随意添加字段，不影响现有数据
//		
//
//		WriteResult result = dao.dbCollection.save(user);
//		DBObject obj = dao.dbCollection.findOne();
//		ObjectId id = (ObjectId)obj.get("_id");
//		int m=Integer.parseInt(id.toString(),16);
//		String jstr = JSON.serialize(obj);
//		Object o = JSON.parse(jstr);
//		JSONObject jobj = new JSONObject().fromObject(jstr);
//		Object hex = jobj.get("_id");
//		jobj.accumulate("id", id.toString());
//		jobj.remove("_id");
//		AuctionUser u = (AuctionUser)JSONObject.toBean(jobj, AuctionUser.class);
//		System.out.println(u);
//	}

	
	public T get(Integer id) {
		DBObject obj = this.dbCollection.findOne(id);
		String jstr = JSON.serialize(obj);
		JSONObject jsonObject = new JSONObject().fromObject(jstr);
		T u = (T)JSONObject.toBean(jsonObject, ((T)(new Object())).getClass());
		return u;
	}

	public void save(T o) {
		
		JSONObject jobj = JSONObject.fromObject(o);
		String jstr = jobj.toString();
		DBObject obj = (DBObject)JSON.parse(jstr);
		WriteResult result = this.dbCollection.save(obj);
	}

	public void update(T o) {
		// TODO Auto-generated method stub
		
	}

	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	public void delete(T o) {
		// TODO Auto-generated method stub
		
	}

	public List<T> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public T get(BigInteger id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(BigInteger id) {
		// TODO Auto-generated method stub
		
	}

	public Query getQuery(T criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	

}

