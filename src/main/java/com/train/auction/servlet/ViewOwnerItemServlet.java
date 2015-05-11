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
		// 获取userId
		String userId = (String)request.getSession(true)
			.getAttribute("userId");
		// 获取业务逻辑组件
		AuctionManager auctionManager = (AuctionManager)getCtx().getBean("mgr");
		// 获取该用户当前处于拍卖中的所有物品
		List<ItemBean> items = auctionManager.getItemsByOwner(new BigInteger(userId));
		// 将查询得到的物品封装成JSONArray对象
		JSONArray jsonArr= new JSONArray(items);
		response.setContentType("text/html; charset=GBK");
		response.getWriter().println(jsonArr.toString());
	}
}