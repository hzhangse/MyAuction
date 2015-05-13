package com.train.auction.servlet;

import java.io.IOException;
import java.math.BigInteger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.train.auction.service.AuctionManager;
import com.train.auction.servlet.base.BaseServlet;


@WebServlet(urlPatterns="/android/addKind.jsp")
public class AddKindServlet extends BaseServlet
{
    public void service(HttpServletRequest request , 
		HttpServletResponse response)
		throws IOException , ServletException
	{
		request.setCharacterEncoding("gbk");
		// ��ȡ�������
		String name = request.getParameter("kindName");
		String desc = request.getParameter("kindDesc");
		// ��ȡϵͳҵ���߼����
		AuctionManager auctionManager = (AuctionManager)getCtx().getBean("mgr");
		// ����ҵ���߼������ҵ�񷽷��������
		BigInteger kindId = auctionManager.addKind(name , desc);
		response.setContentType("text/html; charset=GBK");
		// ��ӳɹ�
		if (kindId.doubleValue()>0)
		{
			response.getWriter().println("��ϲ����������ӳɹ�!");
		}
		else
		{
			response.getWriter().println("�Բ����������ʧ��!");
		}
	}
}