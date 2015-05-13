package com.train.auction.servlet;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.train.auction.business.BidBean;
import com.train.auction.service.AuctionManager;
import com.train.auction.servlet.base.BaseServlet;


@WebServlet(urlPatterns="/android/viewBid.jsp")
public class ViewBidServlet extends BaseServlet
{
    public void service(HttpServletRequest request , 
		HttpServletResponse response)
		throws IOException , ServletException
	{
		// 获取userId
		BigInteger userId = (BigInteger)request.getSession(true)
			.getAttribute("userId");
		// 获取业务逻辑组件
		AuctionManager auctionManager = (AuctionManager)getCtx().getBean("mgr");
		// 获取该用户所参与的全部竞价
		List<BidBean> bids = auctionManager.getBidByUser(userId);
		JSONArray jsonArr= new JSONArray(bids);
		response.setContentType("text/html; charset=GBK");
		response.getWriter().println(jsonArr.toString()); 			
	}
}