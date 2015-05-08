package com.train.auction.action;

import java.util.*;

import com.opensymphony.xwork2.*;
import com.train.auction.action.base.BaseActionInterface;
import com.train.auction.business.*;
import com.train.auction.exception.AuctionException;
import com.train.auction.model.*;
import com.train.auction.service.AuctionManager;

public class ViewDetailAction extends BaseActionInterface
{
	//封装用户请求参数的属性
	private int itemId;
	private ItemBean item;
	//封装系统错误提示的属性
	private String errMsg;
	//处理用户请求
	public String execute()throws Exception
	{
		if (itemId <= 0)
		{
			setErrMsg("您选择物品ID不是一个有效的物品ID！");
			return ERROR;
		}
		else
		{
			setItem(mgr.getItem(itemId));
			return SUCCESS;
		}
	}

	public void setItemId(int itemId)
	{
		this.itemId = itemId;
	}
	public int getItemId()
	{
		 return this.itemId;
	}

	public void setItem(ItemBean item)
	{
		this.item = item;
	}
	public ItemBean getItem()
	{
		 return this.item;
	}

	public void setErrMsg(String errMsg)
	{
		this.errMsg = errMsg;
	}
	public String getErrMsg()
	{
		 return this.errMsg;
	}

}