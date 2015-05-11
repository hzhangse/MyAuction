package com.train.auction.dao.impl.mongo;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.train.auction.model.AuctionUser;

@SuppressWarnings("static-access")

public class AuctionUserSpring extends AbstractBaseMongoTemplete<AuctionUser>  {
	

	public AuctionUser get(Integer id) {
		Criteria criteria = Criteria.where("id").is(id);
		Query query = new Query(criteria);

		return mongoTemplate.find(query, AuctionUser.class).get(0);
	}

	public void save(AuctionUser user) {
		mongoTemplate.save(user);

	}

	public void clean(){
		mongoTemplate.getDb().dropDatabase();
	}
	public void save(List<AuctionUser> users) {
		mongoTemplate.insertAll(users);

	}

	/**
	 * �������޸�, ����ĵ���û�����key ������ ʹ��$set�޸��� <br>
	 * ------------------------------<br>
	 * 
	 * @param user
	 */
	
	
	public void update(AuctionUser user) {
		Criteria criteria = Criteria.where("id").is(user.getId());  
        Query query = new Query(criteria);  
        Update update = Update.update("username", user.getUsername()).set(
				"userpass", user.getUsername());
		mongoTemplate.updateFirst(query, update, AuctionUser.class);
	}
	/**
	 * �޸Ķ��� <br>
	 * ------------------------------<br>
	 * 
	 * @param criteriaUser
	 * @param user
	 */
	public void update(AuctionUser criteriaUser, AuctionUser user) {

		Query query = this.getQuery(criteriaUser);
		Update update = Update.update("username", user.getUsername()).set(
				"userpass", user.getUsername());
		mongoTemplate.updateMulti(query, update, AuctionUser.class);
	}

	public void delete(BigInteger id) {
		AuctionUser user = new AuctionUser(id, null, null, null);
		mongoTemplate.remove(user);

	}

	public void delete(AuctionUser criteriaUser) {
		Criteria criteria = Criteria.where("username").is(
				criteriaUser.getUsername());
		;
		Query query = new Query(criteria);
		mongoTemplate.remove(query, AuctionUser.class);

	}

	public List<AuctionUser> findAll() {

		return mongoTemplate.findAll(AuctionUser.class);
	}

	public void deleteAll() {
		mongoTemplate.dropCollection(AuctionUser.class);
	}

	public AuctionUser findUserByNameAndPass(String username, String pass) {
		AuctionUser criteriaUser = new AuctionUser(null, username, pass, null);
		Query query = new Query();
		if (criteriaUser.getUsername() != null
				&& criteriaUser.getUserpass() != null) {
			query = getQuery(criteriaUser);
			query.skip(0);
			query.limit(1);
			return mongoTemplate.find(query, AuctionUser.class).get(0);
		} else
			return null;
	}

	public AuctionUser findAndRemove(AuctionUser criteriaUser) {
		Query query = getQuery(criteriaUser);
		return mongoTemplate.findAndRemove(query, AuctionUser.class);
	}

	public List<AuctionUser> find(AuctionUser criteriaUser, int skip, int limit) {
		Query query = getQuery(criteriaUser);
		query.skip(skip);
		query.limit(limit);
		return mongoTemplate.find(query, AuctionUser.class);
	}

	/**
	 * ����������ѯ������ ��ȥ�޸� <br>
	 * ------------------------------<br>
	 * 
	 * @param criteriaUser
	 *            ��ѯ����
	 * @param updateUser
	 *            �޸ĵ�ֵ����
	 * @return
	 */
	public AuctionUser findAndModify(AuctionUser criteriaUser,
			AuctionUser updateUser) {
		Query query = getQuery(criteriaUser);
		Update update = Update.update("userpass", updateUser.getUserpass())
				.set("username", updateUser.getUsername());
		return mongoTemplate.findAndModify(query, update, AuctionUser.class);
	}

	public long count(AuctionUser criteriaUser) {
		Query query = getQuery(criteriaUser);
		return mongoTemplate.count(query, AuctionUser.class);
	}

	public Query getQuery(AuctionUser criteriaUser) {
		if (criteriaUser == null) {
			criteriaUser = new AuctionUser();
		}
		Query query = new Query();
		if (criteriaUser.getId() != null) {
			Criteria criteria = Criteria.where("id").is(criteriaUser.getId());
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

	
	@Override
	protected Class getEntityClass() {
		// TODO Auto-generated method stub
		return AuctionUser.class;
	}

	

	
}
