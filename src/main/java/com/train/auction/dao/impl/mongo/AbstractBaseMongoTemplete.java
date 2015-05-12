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
	 * 设置mongoTemplate
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
	 * 通过条件查询实体(集合)
	 * 
	 * @param query
	 */
	public List<T> find(Query query) {
		return mongoTemplate.find(query, this.getEntityClass());
	}

	/**
	 * 通过一定的条件查询一个实体
	 * 
	 * @param query
	 * @return
	 */
	public T findOne(Query query) {
		return mongoTemplate.findOne(query, this.getEntityClass());
	}

	/**
	 * 查询出所有数据
	 * 
	 * @return
	 */
	public List<T> findAll() {
		return this.mongoTemplate.findAll(getEntityClass());
	}

	/**
	 * 查询并且修改记录
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
	 * 按条件查询,并且删除记录
	 * 
	 * @param query
	 * @return
	 */
	public T findAndRemove(Query query) {
		return this.mongoTemplate.findAndRemove(query, this.getEntityClass());
	}

	/**
	 * 通过条件查询更新数据
	 * 
	 * @param query
	 * @param update
	 * @return
	 */
	public void updateFirst(Query query, Update update) {
		mongoTemplate.updateFirst(query, update, this.getEntityClass());
	}

	/**
	 * 保存一个对象到mongodb
	 * 
	 * @param bean
	 * @return
	 */
	public T saveandReturn(T bean) {
		mongoTemplate.save(bean);
		return bean;
	}

	/**
	 * 通过ID获取记录
	 * 
	 * @param id
	 * @return
	 */
	public T findById(BigInteger id) {
		return mongoTemplate.findById(id, this.getEntityClass());
	}

	/**
	 * 通过ID获取记录,并且指定了集合名(表的意思)
	 * 
	 * @param id
	 * @param collectionName
	 *            集合名
	 * @return
	 */
	public T findById(String id, String collectionName) {
		return mongoTemplate
				.findById(id, this.getEntityClass(), collectionName);
	}

	/**
	 * 获取需要操作的实体类class
	 * 
	 * @return
	 */
	protected abstract Class<T> getEntityClass();

	public Query getQuery(T criteria) {
		
		Query query = new Query();
		
		return query;
	}
	
	protected State findState(BigInteger id){
		String name = "拍卖中";
		Query query = new Query();
		if (id.doubleValue() <= 3) {
			switch (id.toString()) {
			case "1":
				name = "拍卖中";
				break;
			case "2":
				name = "拍卖成功";
				break;
			case "3":
				name = "流拍";
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
