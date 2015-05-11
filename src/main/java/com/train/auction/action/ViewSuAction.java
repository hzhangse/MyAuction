package com.train.auction.action;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.train.auction.action.base.BaseAction;


public class ViewSuAction extends BaseAction
{
	private List items;

	public String execute() throws Exception
	{
		Map session = ActionContext.getContext().getSession();
		BigInteger userId = (BigInteger)session.get("userId");
		setItems(mgr.getItemByWiner(userId));
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

}