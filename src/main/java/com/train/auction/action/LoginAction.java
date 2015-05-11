package com.train.auction.action;

import java.math.BigInteger;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.train.auction.action.base.BaseAction;

public class LoginAction extends BaseAction
{
	private String username;
	private String password;
	private String vercode;

	@Override
	public String execute() throws Exception
	{
		Map session = ActionContext.getContext().getSession();
		String ver2 = (String )session.get("rand");
		//����û�Session�������֤���ַ�����
		session.put("rand" , null);
		if (vercode.equalsIgnoreCase(ver2))
		{
			BigInteger userId = mgr.validLogin(username,password);
			if (userId != null && userId.doubleValue() > 0)
			{
				session.put("userId" , userId);
				return SUCCESS;
			}
			else
			{
				addActionError("�û���/���벻ƥ��");
				return "failure";
			}
		}
		else
		{
			addActionError("��֤�벻ƥ��,����������");
			return "failure";
		}
	}
	//username���Ե�setter��getter����
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getUsername()
	{
		return this.username;
	}

	//password���Ե�setter��getter����
	public void setPassword(String password)
	{
		this.password = password;
	}
	public String getPassword()
	{
		return this.password;
	}

	//vercode���Ե�setter��getter����
	public void setVercode(String vercode)
	{
		this.vercode = vercode;
	}
	public String getVercode()
	{
		return this.vercode;
	}

}