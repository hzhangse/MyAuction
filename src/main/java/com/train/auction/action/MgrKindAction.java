package com.train.auction.action;

import java.util.*;

import com.opensymphony.xwork2.ActionContext;
import com.train.auction.action.base.BaseActionInterface;
import com.train.auction.exception.AuctionException;
import com.train.auction.service.AuctionManager;


public class MgrKindAction extends BaseActionInterface
{
	private List kinds;
	private String errMsg;

	public String execute() throws Exception
	{
		Map session = ActionContext.getContext().getSession();
		setKinds(mgr.getAllKind());
		setErrMsg(errMsg);
		return SUCCESS;
	}

	public void setKinds(List kinds)
	{
		this.kinds = kinds;
	}
	public List getKinds()
	{
		 return this.kinds;
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