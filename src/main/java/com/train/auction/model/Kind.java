package com.train.auction.model;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Kind
{
	//标识属性
	private BigInteger id;
	//种类名
	private String kindName;
	//种类描述
	private String kindDesc;
	//该种类下的所有物品
	@DBRef
	private Set<Item> items = new HashSet<Item>();

	//无参数的构造器
	public Kind()
	{
	}
	//初始化全部基本属性的构造器
	public Kind(BigInteger id , String kindName , String kindDesc)
	{
		this.id = id;
		this.kindName = kindName;
		this.kindDesc = kindDesc;
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

	//kindName属性的setter和getter方法
	public void setKindName(String kindName)
	{
		this.kindName = kindName;
	}
	public String getKindName()
	{
		return this.kindName;
	}

	//kindDesc属性的setter和getter方法
	public void setKindDesc(String kindDesc)
	{
		this.kindDesc = kindDesc;
	}
	public String getKindDesc()
	{
		return this.kindDesc;
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