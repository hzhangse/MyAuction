package com.train.auction.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.io.*;
import java.util.*;

import org.json.*;

import com.train.auction.business.*;
import com.train.auction.service.AuctionManager;
import com.train.auction.servlet.base.BaseServlet;


@WebServlet(urlPatterns="/android/viewKind.jsp")
public class ViewKindServlet extends BaseServlet
{
    public void service(HttpServletRequest request , 
		HttpServletResponse response)
		throws IOException , ServletException
	{
		// 获取业务逻辑组件
		AuctionManager auctionManager = (AuctionManager)getCtx().getBean("mgr");
		// 获取系统中所有物品种类
		List<KindBean> kinds = auctionManager.getAllKind();
		// 将所有物品种类包装成JSONArray
		JSONArray jsonArr= new JSONArray(kinds);
		response.setContentType("text/html; charset=GBK");
		// 将JSONArray转换成JSON字符串后输出到客户端
		response.getWriter().println(jsonArr.toString());
	}
}