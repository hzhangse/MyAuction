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
		// ��ȡuserId
    	BigInteger userId = (BigInteger)request.getSession(true)
			.getAttribute("userId");
		request.setCharacterEncoding("gbk");
		// ��ȡ�������
		String itemId = request.getParameter("itemId");
		String bidPrice = request.getParameter("bidPrice");
		// ��ȡҵ���߼����
		AuctionManager auctionManager = (AuctionManager)getCtx().getBean("mgr");
		// ����ҵ�񷽷�����Ӿ���
		BigInteger bidId = auctionManager.addBid(new BigInteger(itemId)
			, Double.parseDouble(bidPrice)
			, userId);
		response.setContentType("text/html; charset=GBK");
		// ���۳ɹ�
		if (bidId.doubleValue() > 0)
		{
			response.getWriter().println("��ϲ�������۳ɹ�!");
		}
		else
		{
			response.getWriter().println("�Բ��𣬾���ʧ��!");
		}
	}
}