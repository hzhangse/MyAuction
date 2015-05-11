package com.train.auction.dao;

import java.math.BigInteger;
import java.util.List;

import com.train.auction.model.Kind;

public interface KindDao extends IDao<Kind> 
{
	/**
	 * ����id��������
	 * @param id ��Ҫ���ҵ������id
	 */
	Kind get(BigInteger id);

	/**
	 * ��������
	 * @param kind ��Ҫ���ӵ�����
	 */
	void save(Kind kind);

	/**
	 * �޸�����
	 * @param kind ��Ҫ�޸ĵ�����
	 */
	void update(Kind kind);

	/**
	 * ɾ������
	 * @param id ��Ҫɾ��������id
	 */
	void delete(BigInteger id);

	/**
	 * ɾ������
	 * @param kind ��Ҫɾ��������
	 */
	void delete(Kind kind);

	/**
	 * ��ѯȫ������
	 * @return ���ȫ������
	 */
	List<Kind> findAll();
}
