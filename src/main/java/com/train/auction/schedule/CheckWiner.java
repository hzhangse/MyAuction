package com.train.auction.schedule;

import com.train.auction.exception.AuctionException;
import com.train.auction.service.AuctionManager;

public class CheckWiner 
{
	//该任务所依赖业务逻辑组件
	private AuctionManager mgr;
	//依赖注入业务逻辑组件必须的setter方法
	public void setMgr(AuctionManager mgr)
	{
		this.mgr = mgr;
	}
	//该任务的执行体
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
