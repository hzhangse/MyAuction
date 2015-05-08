package com.train.auction.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.io.*;

import org.json.*;

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
		Integer userId = (Integer)request.getSession(true)
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
		int itemId = auctionManager.addItem(itemName ,itemDesc , remark
			, Double.parseDouble(initPrice) , Integer.parseInt(avail) 
			, Integer.parseInt(kindId) , userId);
		response.setContentType("text/html; charset=GBK");
		// 添加成功
		if (itemId > 0)
		{
			response.getWriter().println("恭喜您，物品添加成功!");
		}
		else
		{
			response.getWriter().println("对不起，物品添加失败!");
		}
	}
}