package com.train.auction.model;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class State
{
	//标识属性
	private BigInteger id;
	//物品的状态名
	private String stateName;
	//该状态下的所有物品
	@DBRef
	private Set<Item> items = new HashSet<Item>();

	//无参数的构造器
	public State()
	{
	}
	//初始化全部基本属性的构造器
	public State(BigInteger id , String stateName)
	{
		this.id = id;
		this.stateName = stateName;
	}

	//id属性的setter和getter方法
	public void setId(BigInteger id)
	{
		this.id = id;
	}
	public BigInteger getId()
	{
		return this.id;
	}

	//stateName属性的setter和getter方法
	public void setStateName(String stateName)
	{
		this.stateName = stateName;
	}
	public String getStateName()
	{
		return this.stateName;
	}

	//items属性的setter和getter方法
	public void setItems(Set<Item> items)
	{
		this.items = items;
	}
	public Set<Item> getItems()
	{
		return this.items;
	}
}