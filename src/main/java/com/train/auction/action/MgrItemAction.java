package com.train.auction.action;

import java.util.*;

import com.opensymphony.xwork2.*;
import com.train.auction.action.base.BaseActionInterface;
import com.train.auction.exception.AuctionException;
import com.train.auction.service.AuctionManager;


public class MgrItemAction extends BaseActionInterface
{
	private List items;
	private List kinds;

	public String execute() throws Exception
	{
		Map session = ActionContext.getContext().getSession();
		Integer userId = (Integer)session.get("userId");
		setItems(mgr.getItemsByOwner(userId));
		setKinds(mgr.getAllKind());
		return SUCCESS;
	}

	public void setItems(List items)
	{
		this.items = items;
	}
	public List getItems()
	{
		 return this.items;
	}

	public void setKinds(List kinds)
	{
		this.kinds = kinds;
	}
	public List getKinds()
	{
		 return this.kinds;
	}
}