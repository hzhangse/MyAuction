package com.train.auction.model;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class State
{
	//��ʶ����
	private BigInteger id;
	//��Ʒ��״̬��
	private String stateName;
	//��״̬�µ�������Ʒ
	@DBRef
	private Set<Item> items = new HashSet<Item>();

	//�޲����Ĺ�����
	public State()
	{
	}
	//��ʼ��ȫ���������ԵĹ�����
	public State(BigInteger id , String stateName)
	{
		this.id = id;
		this.stateName = stateName;
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

	//stateName���Ե�setter��getter����
	public void setStateName(String stateName)
	{
		this.stateName = stateName;
	}
	public String getStateName()
	{
		return this.stateName;
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