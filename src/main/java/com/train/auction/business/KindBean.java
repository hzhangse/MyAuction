package com.train.auction.business;

import java.math.BigInteger;


public class KindBean
{
	private BigInteger id;
	private String kindName;
	private String kindDesc;

	//�޲����Ĺ�����
	public KindBean()
	{
	}
	//��ʼ��ȫ�����ԵĹ�����
	public KindBean(BigInteger id , String kindName , String kindDesc)
	{
		this.id = id;
		this.kindName = kindName;
		this.kindDesc = kindDesc;
	}

	//id���Ե�setter��getter����
	public void setId(BigInteger id)
	{
		this.id = id;
	}
	public BigInteger getId()
	{
		return this.id;
	}

	//kindName���Ե�setter��getter����
	public void setKindName(String kindName)
	{
		this.kindName = kindName;
	}
	public String getKindName()
	{
		return this.kindName;
	}

	//kindDesc���Ե�setter��getter����
	public void setKindDesc(String kindDesc)
	{
		this.kindDesc = kindDesc;
	}
	public String getKindDesc()
	{
		return this.kindDesc;
	}
}