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


@WebServlet(urlPatterns="/android/viewOwnerItem.jsp")
public class ViewOwnerItemServlet extends BaseServlet
{
    public void service(HttpServletRequest request , 
		HttpServletResponse response)
		throws IOException , ServletException
	{
		// ��ȡuserId
		String userId = (String)request.getSession(true)
			.getAttribute("userId");
		// ��ȡҵ���߼����
		AuctionManager auctionManager = (AuctionManager)getCtx().getBean("mgr");
		// ��ȡ���û���ǰ���������е�������Ʒ
		List<ItemBean> items = auctionManager.getItemsByOwner(new BigInteger(userId));
		// ����ѯ�õ�����Ʒ��װ��JSONArray����
		JSONArray jsonArr= new JSONArray(items);
		response.setContentType("text/html; charset=GBK");
		response.getWriter().println(jsonArr.toString());
	}
}