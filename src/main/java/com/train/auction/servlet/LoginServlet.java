package com.train.auction.servlet;

import java.io.IOException;
import java.math.BigInteger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.train.auction.service.AuctionManager;
import com.train.auction.servlet.base.BaseServlet;


@WebServlet(urlPatterns="/android/login.jsp")
public class LoginServlet extends BaseServlet
{
    public void service(HttpServletRequest request , 
		HttpServletResponse response)
		throws IOException , ServletException
	{
		String user = request.getParameter("user");
		String pass = request.getParameter("pass");
		// 获取系统的业务逻辑组件
		AuctionManager auctionManager = (AuctionManager)getCtx().getBean("mgr");
		// 验证用户登录
		BigInteger userId = auctionManager.validLogin(user , pass);
		response.setContentType("text/html; charset=GBK");
		// 登录成功
		if (userId.compareTo(new BigInteger("0"))>1)
		{
			request.getSession(true).setAttribute("userId" , userId);
		}
		try
		{
			// 把验证的userId封装成JSONObject
			JSONObject jsonObj = new JSONObject()
				.put("userId" , userId);
			// 输出响应
			response.getWriter().println(jsonObj.toString()); 
		}
		catch (JSONException ex)
		{
			ex.printStackTrace();
		}
	}
}