package com.train.auction.servlet.base;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.train.auction.service.AuctionManager;

@WebServlet(urlPatterns = "/android/init.do")
public class InitData extends BaseServlet {

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// ��ȡҵ���߼����
		AuctionManager auctionManager = (AuctionManager) getCtx()
				.getBean("mgr");
		// ��ȡϵͳ���������ĵ���Ʒ
		auctionManager.initData();
		response.setContentType("text/html; charset=GBK");
		response.getWriter().println("Data init success");
	}
}
