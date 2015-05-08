package com.train.auction.action.base;

import com.opensymphony.xwork2.ActionSupport;
import com.train.auction.exception.AuctionException;
import com.train.auction.service.AuctionManager;

public class BaseAction extends ActionSupport
{
	protected AuctionManager mgr;

	public void setMgr(AuctionManager mgr)
	{
		this.mgr = mgr;
	}
}