package com.train.auction.dao.impl.mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class BaseMongoDao {
	protected Mongo mg = null;
	protected DB db;
	protected DBCollection dbCollection;
	private String dbName = "auction";
	protected String tableName = null;
	@Before
	public void init() {
		try {
			mg = new Mongo();
			// mg = new Mongo("localhost", 27017);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
		// 获取temp DB；如果默认没有创建，mongodb会自动创建
		db = mg.getDB(dbName);
		// 获取users DBCollection；如果默认没有创建，mongodb会自动创建
		dbCollection = db.getCollection(tableName);
	}
	
	/**
	 * <b>function:</b> 查询所有数据
	 */
	protected void queryAll() {
	    print("查询所有数据：");
	    //db游标
	    DBCursor cur = dbCollection.find();
	    while (cur.hasNext()) {
	        print(cur.next());
	    }
	}
	 
	@Test
	public void add() {
	   
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
