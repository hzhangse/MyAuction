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
		// 获取物品种类ID
		String kindId = request.getParameter("kindId");
		// 获取业务逻辑组件
		AuctionManager auctionManager = (AuctionManager)getCtx().getBean("mgr");
		// 调用业务逻辑方法来获取全部物品
		List<ItemBean> items = auctionManager
			.getItemsByKind(new BigInteger(kindId));
		// 将物品列表包装成JSONArray
		JSONArray jsonArr= new JSONArray(items);
		response.setContentType("text/html; charset=GBK");
		response.getWriter().println(jsonArr.toString()); 
	}
}