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

public class BaseMongoDao {
	protected Mongo mg = null;
	protected DB db;
	protected DBCollection dbCollection;
	private String dbName = "auction";
	protected String tableName = "test";

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Test
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

			MongoCredential credential = MongoCredential.createPlainCredential(
					prop.getProperty("db.user"), dbName,
					prop.getProperty("db.password").toCharArray());

			// MongoClient mongoClient = new MongoClient(addr,
			// Arrays.asList(credential));
			MongoClientURI uri = new MongoClientURI(
					"mongodb://oneUser:12345@127.0.0.1/?authSource=auction&&authMechanism=SCRAM-SHA-1");
			MongoClient mongoClient = new MongoClient(uri);
			db = mongoClient.getDB(dbName);
			dbCollection = db.getCollection("auctionUser");
			DBCursor cursor = dbCollection.find();
			while (cursor.hasNext()) {
				cursor.next();
			}
			// if (auth) { // 如果用户名密码验证成功
			// System.out.println("成功.......");
			// dbCollection = db.getCollection(tableName);
			// // select(dbCollection);
			// }
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

}
