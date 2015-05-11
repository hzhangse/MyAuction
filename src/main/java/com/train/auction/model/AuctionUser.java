package com.train.auction.model;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;


public class AuctionUser
{
	//��ʶ����
	@Id
	private BigInteger id;
	//�û�������
	private String username;
	//��������
	private String userpass;
	//�����ʼ�����
	private String email;

	//����������������Ʒʵ��
	@DBRef
	private Set<Item> itemsByOwner = new HashSet<Item>();
	//����Ӯȡ�߹�������Ʒʵ��
	@DBRef
	private Set<Item> itemsByWiner = new HashSet<Item>();
	//���û��������ȫ������
	@DBRef
	private Set<Bid> bids = new HashSet<Bid>();

	//�޲����Ĺ�����
	public AuctionUser()
	{
	}
	//��ʼ��ȫ���������ԵĹ�����
	public AuctionUser(BigInteger id , String username ,
		String userpass , String email)
	{
		this.id = id;
		this.username = username;
		this.userpass = userpass;
		this.email = email;
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

	//username���Ե�setter��getter����
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getUsername()
	{
		return this.username;
	}

	//userpass���Ե�setter��getter����
	public void setUserpass(String userpass)
	{
		this.userpass = userpass;
	}
	public String getUserpass()
	{
		return this.userpass;
	}

	//email���Ե�setter��getter����
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getEmail()
	{
		return this.email;
	}

	//itemsByOwner���Ե�setter��getter����
	public void setItemsByOwner(Set<Item> itemsByOwner)
	{
		this.itemsByOwner = itemsByOwner;
	}
	public Set<Item> getItemsByOwner()
	{
		return this.itemsByOwner;
	}

	//itemsByWiner���Ե�setter��getter����
	public void setItemsByWiner(Set<Item> itemsByWiner)
	{
		this.itemsByWiner = itemsByWiner;
	}
	public Set<Item> getItemsByWiner()
	{
		return this.itemsByWiner;
	}

	//bids���Ե�setter��getter����
	public void setBids(Set<Bid> bids)
	{
		this.bids = bids;
	}
	public Set<Bid> getBids()
	{
		return this.bids;
	}
}