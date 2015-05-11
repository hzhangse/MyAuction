package com.train.auction.model;

import java.math.BigInteger;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;


public class Bid
{
	//��ʶ����
	private BigInteger id;
	//���۵ļ۸�
	private double bidPrice;
	//���۵�����
	private Date bidDate;
	//���ξ��������ĵ���Ʒ
	@DBRef
	private Item bidItem;
	//���뾺�۵��û�
	@DBRef
	private AuctionUser bidUser;


	//�޲����Ĺ�����
	public Bid()
	{
	}
	//��ʼ��ȫ���������ԵĹ�����
	public Bid(BigInteger id , double bidPrice , Date bidDate)
	{
		this.id = id;
		this.bidPrice = bidPrice;
		this.bidDate = bidDate;
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

	//bidPrice���Ե�setter��getter����
	public void setBidPrice(double bidPrice)
	{
		this.bidPrice = bidPrice;
	}
	public double getBidPrice()
	{
		return this.bidPrice;
	}

	//bidDate���Ե�setter��getter����
	public void setBidDate(Date bidDate)
	{
		this.bidDate = bidDate;
	}
	public Date getBidDate()
	{
		return this.bidDate;
	}

	//bidItem���Ե�setter��getter����
	public void setBidItem(Item bidItem)
	{
		this.bidItem = bidItem;
	}
	public Item getBidItem()
	{
		return this.bidItem;
	}

	//bidUser���Ե�setter��getter����
	public void setBidUser(AuctionUser bidUser)
	{
		this.bidUser = bidUser;
	}
	public AuctionUser getBidUser()
	{
		return this.bidUser;
	}

	public int hashCode()
	{
		return bidUser.getUsername().hashCode()
			+ bidItem.hashCode() * 13 + (int)bidPrice * 19;
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj != null && obj.getClass() == Bid.class)
		{
			Bid bid = (Bid)obj;
			if (bid.getBidUser().getUsername().equals(bidUser.getUsername())
				&& bid.getBidItem().equals(this.getBidItem()) 
				&& bid.getBidPrice() == this.getBidPrice())
			{
				return true;
			}
		}
		return false;
	}
}