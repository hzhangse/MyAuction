package com.train.auction.dao.impl.mongo;

import java.math.BigInteger;
import java.util.List;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.train.auction.dao.AuctionUserDao;
import com.train.auction.model.AuctionUser;
import com.train.auction.service.impl.AuctionManagerImpl;

@SuppressWarnings("static-access")
@Repository("auctionUserDao")
public class AuctionUserDaoMongo extends AbstractBaseMongoTemplete<AuctionUser>
		implements AuctionUserDao {
	static Logger log = Logger.getLogger(AuctionUserDaoMongo.class.getName());

	@Override
	protected Class getEntityClass() {
		return AuctionUser.class;
	}

	public Query getQuery(AuctionUser criteriaUser) {
		if (criteriaUser == null) {
			criteriaUser = new AuctionUser();
		}
		Query query = new Query();
		if (criteriaUser.getId() != null) {
			Criteria criteria = Criteria.where("id").is(
					new ObjectId(criteriaUser.getId().toString(16)));
			query.addCriteria(criteria);
		}

		if (criteriaUser.getUsername() != null) {
			Criteria criteria = Criteria.where("username").regex(
					"^" + criteriaUser.getUsername());
			query.addCriteria(criteria);
		}

		if (criteriaUser.getUserpass() != null) {
			Criteria criteria = Criteria.where("userpass").regex(
					"^" + criteriaUser.getUserpass());
			query.addCriteria(criteria);
		}
		return query;
	}

	public AuctionUser findUserByNameAndPass(String username, String pass) {
		AuctionUser criteriaUser = new AuctionUser(null, username, pass, null);
		Query query = new Query();
		if (criteriaUser.getUsername() != null
				&& criteriaUser.getUserpass() != null) {
			query = getQuery(criteriaUser);
			query.skip(0);
			query.limit(1);
			AuctionUser user = null;
			
			try {
				log.error("start to valid user in mongodb");
				user = mongoTemplate.find(query, AuctionUser.class).get(0);
				if (user != null)
					log.error("get valid user:" + user.getUsername());
			} catch (Exception ex) {
				log.error(ex.getLocalizedMessage());
			}
			return user;

		} else
			return null;

	}

}
