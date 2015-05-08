package com.train.auction.dao;

import java.util.List;

import com.train.auction.business.*;
import com.train.auction.model.*;

public interface StateDao  
{
	/**
	 * 根据id查找状态
	 * @param id 需要查找的状态id
	 */ 
	State get(Integer id);

	/**
	 * 增加状态
	 * @param state 需要增加的状态
	 */      
	void save(State state);

	/**
	 * 修改状态
	 * @param state 需要修改的状态
	 */
	void update(State state);

	/**
	 * 删除状态
	 * @param id 需要删除的状态id
	 */ 
	void delete(Integer id);

	/**
	 * 删除状态
	 * @param state 需要删除的状态
	 */
	void delete(State state);

	/**
	 * 查询全部状态
	 * @return 获得全部状态
	 */ 
	List<State> findAll();
}
