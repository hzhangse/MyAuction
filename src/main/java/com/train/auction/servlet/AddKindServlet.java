package com.train.auction.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.io.*;

import org.json.*;

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
		// 获取请求参数
		String name = request.getParameter("kindName");
		String desc = request.getParameter("kindDesc");
		// 获取系统业务逻辑组件
		AuctionManager auctionManager = (AuctionManager)getCtx().getBean("mgr");
		// 调用业务逻辑组件的业务方法添加种类
		int kindId = auctionManager.addKind(name , desc);
		response.setContentType("text/html; charset=GBK");
		// 添加成功
		if (kindId > 0)
		{
			response.getWriter().println("恭喜您，种类添加成功!");
		}
		else
		{
			response.getWriter().println("对不起，种类添加失败!");
		}
	}
}