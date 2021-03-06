package com.train.auction.dao;

import java.math.BigInteger;
import java.util.List;

import com.train.auction.model.Kind;

public interface KindDao extends IDao<Kind> 
{
	/**
	 * 根据id查找种类
	 * @param id 需要查找的种类的id
	 */
	Kind get(BigInteger id);

	/**
	 * 增加种类
	 * @param kind 需要增加的种类
	 */
	void save(Kind kind);

	/**
	 * 修改种类
	 * @param kind 需要修改的种类
	 */
	void update(Kind kind);

	/**
	 * 删除种类
	 * @param id 需要删除的种类id
	 */
	void delete(BigInteger id);

	/**
	 * 删除种类
	 * @param kind 需要删除的种类
	 */
	void delete(Kind kind);

	/**
	 * 查询全部种类
	 * @return 获得全部种类
	 */
	List<Kind> findAll();
}
