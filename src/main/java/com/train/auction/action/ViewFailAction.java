package com.train.auction.action;

import java.util.*;

import com.opensymphony.xwork2.ActionContext;
import com.train.auction.action.base.BaseAction;
import com.train.auction.exception.AuctionException;
import com.train.auction.service.AuctionManager;

public class ViewFailAction extends BaseAction
{
	private List failItems;

	public String execute()throws Exception
	{
		setFailItems(mgr.getFailItems());
		return SUCCESS;
	}

	public void setFailItems(List failItems)
	{
		this.failItems = failItems;
	}
	public List getFailItems()
	{
		 return this.failItems;
	}
}