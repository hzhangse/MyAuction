package com.train.auction.dao.impl.mongo;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.sun.mail.iap.ByteArray;
import com.train.auction.dao.ItemDao;
import com.train.auction.model.Item;
import com.train.auction.model.State;

@Repository("itemDao")
public class ItemDaoMongo extends AbstractBaseMongoTemplete<Item> implements
		ItemDao {

	@Override
	protected Class<Item> getEntityClass() {

		return Item.class;
	}

	public List<Item> findItemByKind(BigInteger kindId) {
		ObjectId oid = null;
		try {
			oid = new ObjectId(kindId.toString(16));
		} catch (Exception ex) {
			return null;
		}
		Query query = new Query();
		Criteria criteria = Criteria.where("kind.$id").is(
				oid);
		query.addCriteria(criteria);
		List<Item> result = this.find(query);
		System.out.println(result);
		return result;

	}

	public List<Item> findItemByOwner(BigInteger userId) {
		Query query = new Query();
		Criteria criteria = Criteria.where("owner.$id").is(
				new ObjectId(userId.toString(16)));
		query.addCriteria(criteria);
		List<Item> result = this.find(query);

		return result;
	}

	public List<Item> findItemByWiner(BigInteger userId) {
		Query query = new Query();
		Criteria criteria = Criteria.where("winer.$id").is(
				new ObjectId(userId.toString(16)));
		query.addCriteria(criteria);
		List<Item> result = this.find(query);

		return result;
	}

	
	
	public List<Item> findItemByState(BigInteger stateId) {
		
		Query query = new Query();
		ObjectId oid = null;
		try {
			oid = new ObjectId(stateId.toString(16));
		} catch (Exception ex) {		
				State state = this.findState(stateId);
				stateId = state.getId();
				oid = new ObjectId(stateId.toString(16));
		}
		Criteria criteria = Criteria.where("itemState.$id").is(oid);
		query.addCriteria(criteria);
		List<Item> result = this.find(query);
		return result;
	}

}
