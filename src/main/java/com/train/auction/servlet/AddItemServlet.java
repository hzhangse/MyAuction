package com.train.auction.servlet;

import java.io.IOException;
import java.math.BigInteger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.train.auction.service.AuctionManager;
import com.train.auction.servlet.base.BaseServlet;


@WebServlet(urlPatterns="/android/addItem.jsp")
public class AddItemServlet extends BaseServlet
{
    public void service(HttpServletRequest request , 
		HttpServletResponse response)
		throws IOException , ServletException
	{
		// ��ȡuserId
    	BigInteger userId = (BigInteger)request.getSession(true)
			.getAttribute("userId");
		request.setCharacterEncoding("gbk");
		// �����������
		String itemName = request.getParameter("itemName");
		String itemDesc = request.getParameter("itemDesc");
		String remark = request.getParameter("itemRemark");
		String initPrice = request.getParameter("initPrice");
		String kindId = request.getParameter("kindId");
		String avail = request.getParameter("availTime");
		// ��ȡҵ���߼����
		AuctionManager auctionManager = (AuctionManager)getCtx().getBean("mgr");
		// ����ҵ���߼�����ķ����������Ʒ
		BigInteger itemId = auctionManager.addItem(itemName ,itemDesc , remark
			, Double.parseDouble(initPrice) , Integer.parseInt(avail) 
			, new BigInteger(kindId) , userId);
		response.setContentType("text/html; charset=GBK");
		// ��ӳɹ�
		if (itemId.doubleValue()> 0)
		{
			response.getWriter().println("��ϲ������Ʒ��ӳɹ�!");
		}
		else
		{
			response.getWriter().println("�Բ�����Ʒ���ʧ��!");
		}
	}
}