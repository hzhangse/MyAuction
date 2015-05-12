package com.train.auction.dao.impl.mongo;

import java.math.BigInteger;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.DBObject;
import com.mongodb.MongoCredential;
import com.mongodb.util.JSON;
import com.train.auction.dao.IDao;
import com.train.auction.model.AuctionUser;
import com.train.auction.model.Bid;
import com.train.auction.model.Kind;
import com.train.auction.model.State;

public abstract class AbstractBaseMongoTemplete<T> implements
		ApplicationContextAware, IDao<T> {

	protected MongoTemplate mongoTemplate;

	/**
	 * ����mongoTemplate
	 * 
	 * @param mongoTemplate
	 *            the mongoTemplate to set
	 */
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		MongoTemplate mongoTemplate = applicationContext.getBean(
				"mongoTemplate", MongoTemplate.class);
		setMongoTemplate(mongoTemplate);
	}

	public T get(BigInteger id) {
		return mongoTemplate.findById(id, this.getEntityClass());

	}

	/*
	 * (non-Javadoc)
	 * @see com.train.auction.dao.IDao#save(java.lang.Object)
	 */
	public void save(T bean) {
		mongoTemplate.save(bean);
		
	}

	public void update(T o) {
		
		this.mongoTemplate.save(o);

	}
	public void deleteCollection() {
		this.mongoTemplate.dropCollection(this.getEntityClass());;
	}
	
	public void dropCollection() {	
		this.mongoTemplate.dropCollection(this.getEntityClass());
	}
	
	
	public void delete(T o) {
		this.mongoTemplate.remove(o,this.mongoTemplate.getCollectionName(this.getEntityClass()));
	}

	public void delete(BigInteger id) {

		mongoTemplate.remove(mongoTemplate.findById(id, this.getEntityClass()));

	}

	
	/**
	 * ͨ��������ѯʵ��(����)
	 * 
	 * @param query
	 */
	public List<T> find(Query query) {
		return mongoTemplate.find(query, this.getEntityClass());
	}

	/**
	 * ͨ��һ����������ѯһ��ʵ��
	 * 
	 * @param query
	 * @return
	 */
	public T findOne(Query query) {
		return mongoTemplate.findOne(query, this.getEntityClass());
	}

	/**
	 * ��ѯ����������
	 * 
	 * @return
	 */
	public List<T> findAll() {
		return this.mongoTemplate.findAll(getEntityClass());
	}

	/**
	 * ��ѯ�����޸ļ�¼
	 * 
	 * @param query
	 * @param update
	 * @return
	 */
	public T findAndModify(Query query, Update update) {

		return this.mongoTemplate.findAndModify(query, update,
				this.getEntityClass());
	}

	/**
	 * ��������ѯ,����ɾ����¼
	 * 
	 * @param query
	 * @return
	 */
	public T findAndRemove(Query query) {
		return this.mongoTemplate.findAndRemove(query, this.getEntityClass());
	}

	/**
	 * ͨ��������ѯ��������
	 * 
	 * @param query
	 * @param update
	 * @return
	 */
	public void updateFirst(Query query, Update update) {
		mongoTemplate.updateFirst(query, update, this.getEntityClass());
	}

	/**
	 * ����һ������mongodb
	 * 
	 * @param bean
	 * @return
	 */
	public T saveandReturn(T bean) {
		mongoTemplate.save(bean);
		return bean;
	}

	/**
	 * ͨ��ID��ȡ��¼
	 * 
	 * @param id
	 * @return
	 */
	public T findById(BigInteger id) {
		return mongoTemplate.findById(id, this.getEntityClass());
	}

	/**
	 * ͨ��ID��ȡ��¼,����ָ���˼�����(�����˼)
	 * 
	 * @param id
	 * @param collectionName
	 *            ������
	 * @return
	 */
	public T findById(String id, String collectionName) {
		return mongoTemplate
				.findById(id, this.getEntityClass(), collectionName);
	}

	/**
	 * ��ȡ��Ҫ������ʵ����class
	 * 
	 * @return
	 */
	protected abstract Class<T> getEntityClass();

	public Query getQuery(T criteria) {
		
		Query query = new Query();
		
		return query;
	}
	
	protected State findState(BigInteger id){
		String name = "������";
		Query query = new Query();
		if (id.doubleValue() <= 3) {
			switch (id.toString()) {
			case "1":
				name = "������";
				break;
			case "2":
				name = "�����ɹ�";
				break;
			case "3":
				name = "����";
				break;
			}
			
			Criteria criteria = Criteria.where("stateName").regex("^" + name);
			query.addCriteria(criteria);	
			return mongoTemplate.findOne(query, State.class);
		}else {
			
			return mongoTemplate.findById(id, State.class);
		}		
	}
	
	
}
