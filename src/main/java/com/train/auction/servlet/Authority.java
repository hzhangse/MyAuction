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
		// ��ȡHttpSession����
		HttpSession session = hrequest.getSession(true);
		BigInteger userId = (BigInteger)session.getAttribute("userId");
		// ����û��Ѿ���¼�����û����ڵ�¼
		if ((userId != null && userId.doubleValue() > 0)
			|| hrequest.getRequestURI().endsWith("/login.jsp")|| hrequest.getRequestURI().endsWith("/regist.jsp"))
		{
			// �����С�����
			chain.doFilter(request , response);
		}
		else
		{
			response.setContentType("text/html; charset=GBK");
			// ���ɴ�����ʾ��
			response.getWriter().println("����û�е�¼ϵͳ������ϵͳ��"); 
		}
	}

    public void destroy()
    {
    }
}