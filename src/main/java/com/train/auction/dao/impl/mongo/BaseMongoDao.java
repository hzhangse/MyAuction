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
		// ��ȡtemp DB�����Ĭ��û�д�����mongodb���Զ�����
		db = mg.getDB(dbName);
		// ��ȡusers DBCollection�����Ĭ��û�д�����mongodb���Զ�����
		dbCollection = db.getCollection(tableName);
	}
	
	/**
	 * <b>function:</b> ��ѯ��������
	 */
	protected void queryAll() {
	    print("��ѯ�������ݣ�");
	    //db�α�
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
