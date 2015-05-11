package com.train.auction.dao;

import java.math.BigInteger;
import java.util.List;

import com.train.auction.model.Item;


public interface ItemDao  extends IDao<Item>
{
	/**
	 * ��������������Ʒ
	 * @param itemId ����ҵ���Ʒ��id;
	 * @return id��Ӧ����Ʒ
	 */
	Item get(BigInteger itemId);

	/**
	 * ������Ʒ
	 * @param item ��Ҫ�������Ʒ
	 */
	void save(Item item);

	/**
	 * �޸���Ʒ
	 * @param item ��Ҫ�޸ĵ���Ʒ
	 */
	void update(Item item);

	/**
	 * ɾ����Ʒ
	 * @param id ��Ҫɾ������Ʒid
	 */
	void delete(BigInteger id);

	/**
	 * ɾ����Ʒ
	 * @param item ��Ҫɾ������Ʒ
	 */
	void delete(Item item);

	/**
	 * ���ݲ�Ʒ���࣬��ȡ��ǰ������ȫ����Ʒ
	 * @param kindId ����id;
	 * @return �����ȫ����Ʒ
	 */
	List<Item> findItemByKind(BigInteger kindId);

	/**
	 * ���������߲��Ҵ��������е���Ʒ
	 * @param useId ������Id;
	 * @return ָ���û����������е�ȫ����Ʒ
	 */
	List<Item> findItemByOwner(BigInteger userId);

	/**
	 * ����Ӯȡ�߲�����Ʒ
	 * @param userId Ӯȡ��Id;
	 * @return ָ���û�Ӯȡ��ȫ����Ʒ
	 */
	List<Item> findItemByWiner(BigInteger userId);

	/**
	 * ������Ʒ״̬������Ʒ
	 * @param stateId ״̬Id;
	 * @return ��״̬�µ�ȫ����Ʒ
	 */
	List<Item> findItemByState(BigInteger stateId);
}