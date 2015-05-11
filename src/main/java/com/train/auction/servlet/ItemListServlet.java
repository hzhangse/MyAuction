package com.train.auction.servlet;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.train.auction.business.ItemBean;
import com.train.auction.service.AuctionManager;
import com.train.auction.servlet.base.BaseServlet;


@WebServlet(urlPatterns="/android/itemList.jsp")
public class ItemListServlet extends BaseServlet
{
    public void service(HttpServletRequest request , 
		HttpServletResponse response)
		throws IOException , ServletException
	{
		request.setCharacterEncoding("gbk");
		// ��ȡ��Ʒ����ID
		String kindId = request.getParameter("kindId");
		// ��ȡҵ���߼����
		AuctionManager auctionManager = (AuctionManager)getCtx().getBean("mgr");
		// ����ҵ���߼���������ȡȫ����Ʒ
		List<ItemBean> items = auctionManager
			.getItemsByKind(new BigInteger(kindId));
		// ����Ʒ�б��װ��JSONArray
		JSONArray jsonArr= new JSONArray(items);
		response.setContentType("text/html; charset=GBK");
		response.getWriter().println(jsonArr.toString()); 
	}
}