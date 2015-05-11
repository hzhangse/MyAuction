package com.train.auction.servlet;

import java.io.IOException;
import java.math.BigInteger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.train.auction.service.AuctionManager;
import com.train.auction.servlet.base.BaseServlet;


@WebServlet(urlPatterns="/android/addBid.jsp")
public class AddBidServlet extends BaseServlet
{
    public void service(HttpServletRequest request , 
		HttpServletResponse response)
		throws IOException , ServletException
	{
		// 获取userId
    	BigInteger userId = (BigInteger)request.getSession(true)
			.getAttribute("userId");
		request.setCharacterEncoding("gbk");
		// 获取请求参数
		String itemId = request.getParameter("itemId");
		String bidPrice = request.getParameter("bidPrice");
		// 获取业务逻辑组件
		AuctionManager auctionManager = (AuctionManager)getCtx().getBean("mgr");
		// 调用业务方法来添加竞价
		BigInteger bidId = auctionManager.addBid(new BigInteger(itemId)
			, Double.parseDouble(bidPrice)
			, userId);
		response.setContentType("text/html; charset=GBK");
		// 竞价成功
		if (bidId.doubleValue() > 0)
		{
			response.getWriter().println("恭喜您，竞价成功!");
		}
		else
		{
			response.getWriter().println("对不起，竞价失败!");
		}
	}
}