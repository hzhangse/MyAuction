package com.train.auction.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.io.*;

import org.json.*;

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
		Integer userId = (Integer)request.getSession(true)
			.getAttribute("userId");
		request.setCharacterEncoding("gbk");
		// 获取请求参数
		String itemId = request.getParameter("itemId");
		String bidPrice = request.getParameter("bidPrice");
		// 获取业务逻辑组件
		AuctionManager auctionManager = (AuctionManager)getCtx().getBean("mgr");
		// 调用业务方法来添加竞价
		int bidId = auctionManager.addBid(Integer.parseInt(itemId)
			, Double.parseDouble(bidPrice)
			, userId);
		response.setContentType("text/html; charset=GBK");
		// 竞价成功
		if (bidId > 0)
		{
			response.getWriter().println("恭喜您，竞价成功!");
		}
		else
		{
			response.getWriter().println("对不起，竞价失败!");
		}
	}
}