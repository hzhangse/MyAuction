package com.train.auction.schedule;

import com.train.auction.exception.AuctionException;
import com.train.auction.service.AuctionManager;

public class CheckWiner 
{
	//������������ҵ���߼����
	private AuctionManager mgr;
	//����ע��ҵ���߼���������setter����
	public void setMgr(AuctionManager mgr)
	{
		this.mgr = mgr;
	}
	//�������ִ����
	public void run()
	{
		try
		{
			mgr.updateWiner();
		}
		catch (AuctionException ae)
		{
			ae.printStackTrace();
		}
	}
}
