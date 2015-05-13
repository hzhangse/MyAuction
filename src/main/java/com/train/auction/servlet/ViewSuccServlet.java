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


@WebServlet(urlPatterns="/android/viewSucc.jsp")
public class ViewSuccServlet extends BaseServlet
{
    public void service(HttpServletRequest request , 
		HttpServletResponse response)
		throws IOException , ServletException
	{
		// 获取userId
		BigInteger userId = (BigInteger)request.getSession(true)
			.getAttribute("userId");
		// 获取业务逻辑组件
		AuctionManager auctionManager = (AuctionManager)getCtx().getBean("mgr");
		// 获取该用户所有竞得的物品
		List<ItemBean> items = auctionManager.getItemByWiner(userId);
		JSONArray jsonArr= new JSONArray(items);
		response.setContentType("text/html; charset=GBK");
		jsonArr.toString();
		response.getWriter().println(jsonArr.toString());
	}
}