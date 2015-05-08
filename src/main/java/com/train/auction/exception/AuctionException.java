package com.train.auction.exception;


public class AuctionException extends RuntimeException
{
	//定义一个无参数的构造器
	public AuctionException()
	{
	}
	//定义一个带message参数的构造参数
	public AuctionException(String message)
	{
		super(message);
	}
}
