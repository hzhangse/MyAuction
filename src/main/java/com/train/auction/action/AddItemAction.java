package com.train.auction.action;

import java.math.BigInteger;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.train.auction.action.base.BaseAction;


public class AddItemAction extends BaseAction
{
	private String name;
	private String desc;
	private String remark;
	private double initPrice;
	private int avail;
	private BigInteger kind;
	private String vercode;
	//处理用户请求的execute方法
	public String execute() throws Exception
	{
		Map session = ActionContext.getContext().getSession();
		String ver2 = (String)session.get("rand");
		//强制系统刚生成的随机验证码失效
		session.put("rand" , null);
		BigInteger userId = (BigInteger)session.get("userId");
		//如果用户输入的验证码与系统随机产生的验证码相同
		if (vercode.equalsIgnoreCase(ver2))
		{
			//根据用户选择有效时间选项，指定实际的有效时间
			switch(avail)
			{
				case 6 :
					avail = 7;
					break;
				case 7 :
					avail = 30;
					break;
				case 8 :
					avail = 365;
					break;
			}
			//添加物品
			mgr.addItem(name , desc , remark , initPrice ,avail , new BigInteger(String.valueOf(kind)), userId);
			//将收集用户输入信息的表单域清空
			return SUCCESS;
		}
		else
		{
			addActionError("验证码不匹配,请重新输入");
			return INPUT;
		}
	}

	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		 return this.name;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}
	public String getDesc()
	{
		 return this.desc;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}
	public String getRemark()
	{
		 return this.remark;
	}

	public void setInitPrice(double initPrice)
	{
		this.initPrice = initPrice;
	}
	public double getInitPrice()
	{
		 return this.initPrice;
	}

	public void setAvail(int avail)
	{
		this.avail = avail;
	}
	public int getAvail()
	{
		 return this.avail;
	}

	public void setKind(BigInteger kind)
	{
		this.kind = kind;
	}
	public BigInteger getKind()
	{
		 return this.kind;
	}

	public void setVercode(String vercode)
	{
		this.vercode = vercode;
	}
	public String getVercode()
	{
		 return this.vercode;
	}
}