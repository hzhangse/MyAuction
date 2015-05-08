package com.train.auction.business;

import java.io.Serializable;
import java.util.Date;


public class BidBean implements Serializable
{
	private Integer id;
	private String user;
	private String item;
	private double price;
	private Date bidDate;


	//无参数的构造器
	public BidBean()
	{
	}
	//初始化全部属性的构造器
	public BidBean(Integer id , String user , String item ,
		double price , Date bidDate)
	{
		this.id = id;
		this.user = user;
		this.item = item;
		this.price = price;
		this.bidDate = bidDate;
	}

	//id属性的setter和getter方法
	public void setId(Integer id)
	{
		this.id = id;
	}
	public Integer getId()
	{
		return this.id;
	}

	//user属性的setter和getter方法
	public void setUser(String user)
	{
		this.user = user;
	}
	public String getUser()
	{
		return this.user;
	}

	//item属性的setter和getter方法
	public void setItem(String item)
	{
		this.item = item;
	}
	public String getItem()
	{
		return this.item;
	}

	//price属性的setter和getter方法
	public void setPrice(double price)
	{
		this.price = price;
	}
	public double getPrice()
	{
		return this.price;
	}

	//bidDate属性的setter和getter方法
	public void setBidDate(Date bidDate)
	{
		this.bidDate = bidDate;
	}
	public Date getBidDate()
	{
		return this.bidDate;
	}

}