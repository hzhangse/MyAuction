package com.train.auction.dao.impl.mongo;

import java.math.BigInteger;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.train.auction.dao.BidDao;
import com.train.auction.model.AuctionUser;
import com.train.auction.model.Bid;
import com.train.auction.model.State;

@SuppressWarnings("static-access")
@Repository("bidDao")
public class BidDaoMongo extends AbstractBaseMongoTemplete<Bid> implements
		BidDao {

	public Query getQuery(Bid bid) {
		Query query = new Query();
		if (bid.getId() != null) {
			Criteria criteria = Criteria.where("id").is(bid.getId());

			query.addCriteria(criteria);
		}

		if (bid.getBidUser() != null) {
			Criteria criteria = Criteria.where("bidUser.$id").is(
					bid.getBidUser().getId());

			query.addCriteria(criteria);
		}

		return query;
	}

	@Override
	protected Class<Bid> getEntityClass() {
		return Bid.class;
	}

	public List<Bid> findByUser(BigInteger userId) {
		Query query = new Query();
		Criteria criteria = Criteria.where("bidUser.$id").is(new ObjectId(userId.toString(16)));
		query.addCriteria(criteria);

		return this.find(query);
	}

	public AuctionUser findUserByItemAndPrice(BigInteger itemId, Double price) {
		// TODO Auto-generated method stub
		Query query = new Query();
		
		Criteria criteria = Criteria.where("bidItem.$id").is(new ObjectId(itemId.toString(16)));
		query.addCriteria(criteria);
		criteria = Criteria.where("bidPrice").is(price);
		query.addCriteria(criteria);
		Bid bid = this.findOne(query);
		if (bid != null)
			return bid.getBidUser();
		else
			return null;

	}

}
