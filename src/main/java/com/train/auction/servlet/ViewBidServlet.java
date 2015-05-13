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
		// ��ȡuserId
		BigInteger userId = (BigInteger)request.getSession(true)
			.getAttribute("userId");
		// ��ȡҵ���߼����
		AuctionManager auctionManager = (AuctionManager)getCtx().getBean("mgr");
		// ��ȡ���û��������ȫ������
		List<BidBean> bids = auctionManager.getBidByUser(userId);
		JSONArray jsonArr= new JSONArray(bids);
		response.setContentType("text/html; charset=GBK");
		response.getWriter().println(jsonArr.toString()); 			
	}
}