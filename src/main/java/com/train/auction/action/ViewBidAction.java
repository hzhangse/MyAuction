package com.train.auction.action;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.train.auction.action.base.BaseAction;

public class ViewBidAction extends BaseAction
{
	private List bids;
	
	public String execute()throws Exception
	{
		Map session = ActionContext.getContext().getSession();
		BigInteger userId = (BigInteger)session.get("userId");
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