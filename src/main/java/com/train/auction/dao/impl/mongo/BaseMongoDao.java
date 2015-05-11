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
			// ��ȡtemp DB�����Ĭ��û�д�����mongodb���Զ�����
			
			// ��ȡusers DBCollection�����Ĭ��û�д�����mongodb���Զ�����

			MongoCredential credential = MongoCredential.createPlainCredential(prop.getProperty("db.user"), dbName, prop
					.getProperty("db.password").toCharArray());
		
			//MongoClient mongoClient = new MongoClient(addr, Arrays.asList(credential));
			MongoClientURI uri = new MongoClientURI("mongodb://auction:auction@127.0.0.1/?authSource=auction&&authMechanism=SCRAM-SHA-1");
			MongoClient mongoClient = new MongoClient(uri);
			db = mongoClient.getDB(dbName);
			dbCollection = db.getCollection("test");
//			if (auth) { // ����û���������֤�ɹ�
//				System.out.println("�ɹ�.......");
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
	 * <b>function:</b> ��ѯ��������
	 */
	@Test
	protected void queryAll() {
		print("��ѯ�������ݣ�");
		// db�α�
		DBCursor cur = dbCollection.find();
		while (cur.hasNext()) {
			print(cur.next());
		}
	}

	@Test
	public void add() {
		// �Ȳ�ѯ��������
		queryAll();
		print("count: " + dbCollection.count());

		DBObject user = new BasicDBObject();
		user.put("name", "hoojo");
		user.put("age", 24);
		// users.save(user)���棬getN()��ȡӰ������
		// print(users.save(user).getN());

		// ��չ�ֶΣ���������ֶΣ���Ӱ����������
		user.put("sex", "��");
		print(dbCollection.save(user).getN());

		// ��Ӷ������ݣ�����Array����
		print(dbCollection.insert(user, new BasicDBObject("name", "tom"))
				.getN());

		// ���List����
		List<DBObject> list = new ArrayList<DBObject>();
		list.add(user);
		DBObject user2 = new BasicDBObject("name", "lucy");
		user.put("age", 22);
		list.add(user2);
		// ���List����
		print(dbCollection.insert(list).getN());

		// ��ѯ�����ݣ������Ƿ���ӳɹ�
		print("count: " + dbCollection.count());
		queryAll();
	}

	@Test
	public void remove() {
		queryAll();
		print("ɾ��id = 4de73f7acd812d61b4626a77��"
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
		print("�޸ģ�"
				+ dbCollection.update(
						new BasicDBObject("_id", new ObjectId(
								"4dde25d06be7c53ffbd70906")),
						new BasicDBObject("age", 99)).getN());
		print("�޸ģ�"
				+ dbCollection.update(
						new BasicDBObject("_id", new ObjectId(
								"4dde2b06feb038463ff09042")),
						new BasicDBObject("age", 121), true,// ������ݿⲻ���ڣ��Ƿ����
						false// �����޸�
						).getN());
		print("�޸ģ�"
				+ dbCollection.update(new BasicDBObject("name", "haha"),
						new BasicDBObject("name", "dingding"), true,// ������ݿⲻ���ڣ��Ƿ����
						true// falseֻ�޸ĵ�һ�죬true����ж����Ͳ��޸�
						).getN());

		// �����ݿⲻ���ھͲ��޸ġ���������ݣ����������ݾͲ��޸�
		// print("�޸Ķ�����" + coll.updateMulti(new BasicDBObject("_id", new
		// ObjectId("4dde23616be7c19df07db42c")), new BasicDBObject("name",
		// "199")));
	}

	@Test
	public void query() {
		// ��ѯ����
		// queryAll();

		// ��ѯid = 4de73f7acd812d61b4626a77
		print("find id = 4de73f7acd812d61b4626a77: "
				+ dbCollection.find(
						new BasicDBObject("_id", new ObjectId("4de73f7acd812d61b4626a77"))).toArray());

		// ��ѯage = 24
		print("find age = 24: "
				+ dbCollection.find(new BasicDBObject("age", 24)).toArray());

		// ��ѯage >= 24
		print("find age >= 24: "
				+ dbCollection
						.find(new BasicDBObject("age", new BasicDBObject(
								"$gte", 24))).toArray());
		print("find age <= 24: "
				+ dbCollection
						.find(new BasicDBObject("age", new BasicDBObject(
								"$lte", 24))).toArray());

		print("��ѯage!=25��"
				+ dbCollection.find(
						new BasicDBObject("age", new BasicDBObject("$ne", 25)))
						.toArray());
		print("��ѯage in 25/26/27��"
				+ dbCollection.find(
						new BasicDBObject("age", new BasicDBObject(
								QueryOperators.IN, new int[] { 25, 26, 27 })))
						.toArray());
		print("��ѯage not in 25/26/27��"
				+ dbCollection.find(
						new BasicDBObject("age", new BasicDBObject(
								QueryOperators.NIN, new int[] { 25, 26, 27 })))
						.toArray());
		print("��ѯage exists ����"
				+ dbCollection.find(
						new BasicDBObject("age", new BasicDBObject(
								QueryOperators.EXISTS, true))).toArray());

		print("ֻ��ѯage���ԣ�"
				+ dbCollection.find(null, new BasicDBObject("age", true))
						.toArray());
		print("ֻ�����ԣ�"
				+ dbCollection.find(null, new BasicDBObject("age", true), 0, 2)
						.toArray());
		print("ֻ�����ԣ�"
				+ dbCollection.find(null, new BasicDBObject("age", true), 0, 2,
						Bytes.QUERYOPTION_NOTIMEOUT).toArray());

		// ֻ��ѯһ�����ݣ�����ȥ��һ��
		print("findOne: " + dbCollection.findOne());
		print("findOne: " + dbCollection.findOne(new BasicDBObject("age", 26)));
		print("findOne: "
				+ dbCollection.findOne(new BasicDBObject("age", 26),
						new BasicDBObject("name", true)));

		// ��ѯ�޸ġ�ɾ��
		print("findAndRemove ��ѯage=25�����ݣ�����ɾ��: "
				+ dbCollection.findAndRemove(new BasicDBObject("age", 25)));

		// ��ѯage=26�����ݣ������޸�name��ֵΪAbc
		print("findAndModify: "
				+ dbCollection.findAndModify(new BasicDBObject("age", 26),
						new BasicDBObject("name", "Abc")));
		print("findAndModify: "
				+ dbCollection.findAndModify(new BasicDBObject("age", 28), // ��ѯage=28������
						new BasicDBObject("name", true), // ��ѯname����
						new BasicDBObject("age", true), // ����age����
						false, // �Ƿ�ɾ����true��ʾɾ��
						new BasicDBObject("name", "Abc"), // �޸ĵ�ֵ����name�޸ĳ�Abc
						true, true));

		queryAll();
	}

	public void testOthers() {
		DBObject user = new BasicDBObject();
		user.put("name", "hoojo");
		user.put("age", 24);

		// JSON ����ת��
		print("serialize: " + JSON.serialize(user));
		// �����л�
		print("parse: " + JSON.parse("{ \"name\" : \"hoojo\" , \"age\" : 24}"));

		print("�ж�temp Collection�Ƿ����: " + db.collectionExists("temp"));

		// ��������ھʹ���
		if (!db.collectionExists("temp")) {
			DBObject options = new BasicDBObject();
			options.put("size", 20);
			options.put("capped", 20);
			options.put("max", 20);
			print(db.createCollection("account", options));
		}

		// ����dbΪֻ��
		//db.setReadOnly(true);

		// ֻ������д������
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

	/** ��ѯ */
	private static void select(DBCollection col) {
		DBObject conditions = new BasicDBObject(); // ������
		Integer[] num = { 10, 99 };

		// conditions.put("collor", "red"); // ����
		// conditions.put("size", new BasicDBObject("$gt", 5)); // gt(>), lt(<),
		// gte(>=), lte(<=), in(����), nin((������)
		// conditions.put("size", new BasicDBObject("$gt", 5).append("$lt",
		// 15)); // >5 and <15, �����Զ��������
		// conditions.put("num", new BasicDBObject("$in", num)); // ��Ч
		// conditions.put("properties.skills", "java"); // �Ӳ�ѯ��list
		// conditions.put("properties.age", 23); // �Ӳ�ѯ��map
		// DBCursor ite = col.find().skip(1).limit(2); // ȡ��Χ

		DBCursor ite = col.find(conditions); // �������������������������ѯ���У�
		DBCursor ite_ = col.find(new BasicDBObject // �������ֱ��������
				("$where", "this.size==10")); // >, <, >=, <=, ==

		while (ite.hasNext()) {
			System.out.println(ite.next());
		}
		System.out.println("............");

		// =========== ģ����ѯ��ֻ�������� ============
		Pattern john = Pattern.compile("ed"); // ����
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
//		// users.save(user)���棬getN()��ȡӰ������
//		// print(users.save(user).getN());
//
//		// ��չ�ֶΣ���������ֶΣ���Ӱ����������
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

