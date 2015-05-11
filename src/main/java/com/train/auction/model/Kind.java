package com.train.auction.model;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Kind
{
	//��ʶ����
	private BigInteger id;
	//������
	private String kindName;
	//��������
	private String kindDesc;
	//�������µ�������Ʒ
	@DBRef
	private Set<Item> items = new HashSet<Item>();

	//�޲����Ĺ�����
	public Kind()
	{
	}
	//��ʼ��ȫ���������ԵĹ�����
	public Kind(BigInteger id , String kindName , String kindDesc)
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

	//items���Ե�setter��getter����
	public void setItems(Set<Item> items)
	{
		this.items = items;
	}
	public Set<Item> getItems()
	{
		return this.items;
	}

}