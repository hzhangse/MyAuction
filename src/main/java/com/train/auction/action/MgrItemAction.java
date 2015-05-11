package com.train.auction.action;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.train.auction.action.base.BaseActionInterface;


public class MgrItemAction extends BaseActionInterface
{
	private List items;
	private List kinds;

	public String execute() throws Exception
	{
		Map session = ActionContext.getContext().getSession();
		BigInteger userId = (BigInteger)session.get("userId");
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