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
		// 获取userId
    	BigInteger userId = (BigInteger)request.getSession(true)
			.getAttribute("userId");
		request.setCharacterEncoding("gbk");
		// 解析请求参数
		String itemName = request.getParameter("itemName");
		String itemDesc = request.getParameter("itemDesc");
		String remark = request.getParameter("itemRemark");
		String initPrice = request.getParameter("initPrice");
		String kindId = request.getParameter("kindId");
		String avail = request.getParameter("availTime");
		// 获取业务逻辑组件
		AuctionManager auctionManager = (AuctionManager)getCtx().getBean("mgr");
		// 调用业务逻辑组件的方法来添加物品
		BigInteger itemId = auctionManager.addItem(itemName ,itemDesc , remark
			, Double.parseDouble(initPrice) , Integer.parseInt(avail) 
			, new BigInteger(kindId) , userId);
		response.setContentType("text/html; charset=GBK");
		// 添加成功
		if (itemId.doubleValue()> 0)
		{
			response.getWriter().println("恭喜您，物品添加成功!");
		}
		else
		{
			response.getWriter().println("对不起，物品添加失败!");
		}
	}
}