package com.train.auction.servlet;

import java.io.IOException;
import java.math.BigInteger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@WebFilter(urlPatterns="/android/*")
public class Authority implements Filter
{
	public void init(FilterConfig config)
		throws ServletException
	{
	}

    public void doFilter(ServletRequest request, 
		ServletResponse response, FilterChain chain) 
		throws IOException , ServletException
	{
		HttpServletRequest hrequest = (HttpServletRequest)request;
		// 获取HttpSession对象
		HttpSession session = hrequest.getSession(true);
		BigInteger userId = (BigInteger)session.getAttribute("userId");
		// 如果用户已经登录，或用户正在登录
		if ((userId != null && userId.doubleValue() > 0)
			|| hrequest.getRequestURI().endsWith("/login.jsp")|| hrequest.getRequestURI().endsWith("/regist.jsp"))
		{
			// “放行”请求
			chain.doFilter(request , response);
		}
		else
		{
			response.setContentType("text/html; charset=GBK");
			// 生成错误提示。
			response.getWriter().println("您还没有登录系统，请先系统！"); 
		}
	}

    public void destroy()
    {
    }
}