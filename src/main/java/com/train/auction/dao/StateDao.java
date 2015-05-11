package com.train.auction.dao;

import java.math.BigInteger;
import java.util.List;

import com.train.auction.model.State;

public interface StateDao  extends IDao<State>
{
	/**
	 * ����id����״̬
	 * @param id ��Ҫ���ҵ�״̬id
	 */ 
	State get(BigInteger id);

	/**
	 * ����״̬
	 * @param state ��Ҫ���ӵ�״̬
	 */      
	void save(State state);

	/**
	 * �޸�״̬
	 * @param state ��Ҫ�޸ĵ�״̬
	 */
	void update(State state);

	/**
	 * ɾ��״̬
	 * @param id ��Ҫɾ����״̬id
	 */ 
	void delete(BigInteger id);

	/**
	 * ɾ��״̬
	 * @param state ��Ҫɾ����״̬
	 */
	void delete(State state);

	/**
	 * ��ѯȫ��״̬
	 * @return ���ȫ��״̬
	 */ 
	List<State> findAll();
}
