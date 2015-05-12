package com.train.auction.action;

import java.math.BigInteger;
import java.util.List;

import com.train.auction.action.base.BaseAction;

public class ViewItemAction extends BaseAction
{
	private BigInteger kindId;
	private String kind;
	private List items; 

	public String execute()throws Exception
	{
		if (kindId.doubleValue() <= 0)
		{
			addActionError("您必须选择有效的种类");
			return ERROR;
		}
		else
		{
			setKind(mgr.getKind(new BigInteger(String.valueOf(kindId))));
			setItems(mgr.getItemsByKind(new BigInteger(String.valueOf(kindId))));
			return SUCCESS;
		}
	}

	public void setKindId(BigInteger kindId)
	{
		this.kindId = kindId;
	}
	public BigInteger getKindId()
	{
		 return this.kindId;
	}

	public void setKind(String kind)
	{
		this.kind = kind;
	}
	public String getKind()
	{
		 return this.kind;
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