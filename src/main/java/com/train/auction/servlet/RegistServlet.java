package com.train.auction.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.train.auction.service.AuctionManager;
import com.train.auction.service.impl.ExecuteResult;
import com.train.auction.servlet.base.BaseServlet;

@WebServlet(urlPatterns = "/android/regist.jsp")
public class RegistServlet extends BaseServlet {
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String username = request.getParameter("user");
		String pass = request.getParameter("pass");
		String email = request.getParameter("email");

		if (username != null && pass != null && email != null) {
			AuctionManager auctionManager = (AuctionManager) getCtx().getBean(
					"mgr");

			ExecuteResult result = auctionManager.registUser(username, pass,
					email);
			response.setContentType("text/html; charset=GBK");

			JSONObject jsonObj = new JSONObject(result);

			//  ‰≥ˆœÏ”¶
			response.getWriter().println(jsonObj.toString());

		}
	}
}