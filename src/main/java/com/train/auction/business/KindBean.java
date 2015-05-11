package com.train.auction.business;

import java.math.BigInteger;


public class KindBean
{
	private BigInteger id;
	private String kindName;
	private String kindDesc;

	//无参数的构造器
	public KindBean()
	{
	}
	//初始化全部属性的构造器
	public KindBean(BigInteger id , String kindName , String kindDesc)
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
}