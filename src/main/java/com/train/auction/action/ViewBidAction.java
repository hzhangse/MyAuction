package com.train.auction.action;

import java.util.*;

import com.opensymphony.xwork2.ActionContext;
import com.train.auction.action.base.BaseAction;
import com.train.auction.exception.AuctionException;
import com.train.auction.service.AuctionManager;

public class ViewBidAction extends BaseAction
{
	private List bids;
	
	public String execute()throws Exception
	{
		Map session = ActionContext.getContext().getSession();
		Integer userId = (Integer)session.get("userId");
		setBids(mgr.getBidByUser(userId));
		return SUCCESS;
	}

	public void setBids(List bids)
	{
		this.bids = bids;
	}

	public List getBids()
	{
		 return this.bids;
	}

}