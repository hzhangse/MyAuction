

<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><decorator:title default="商业拍卖"/></title>
<!-- 使用s:head标签引入Struts 2标签的CSS样式文件 -->
<link href="images/css.css" rel="stylesheet" type="text/css">
<s:head/>
</head>
<body>
<table width="780" height="110" border="0" align="center" 
	cellspacing="0" background="images/bodybg.jpg">
<tr>

	<td width="440" height="65"><div align="center" 
	style="font-size:16pt;color:#cc6600;font-weight:bold">
		拍卖模拟</div></td>
	
</tr>

<tr>
<td colspan="3"><br>
<table width="578" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
	<td width="116"><a href="viewItemSu.action">查看竞得的物品</a></td>
	<td width="101"><a href="viewFailItem.action" >浏览流拍物品</a></td>
	<td width="79"><a href="mgrKind.action" >管理种类</a></td>
	<td width="79"><a href="mgrItem.action" >管理物品</a></td>
	<td width="105"><a href="viewKind.action" >浏览拍卖物品</a></td>
	<td width="107"><a href="viewBid.action" >查看自己的竞标</a></td>
	<td width="70"><a href="index.action" >返回首页</a></div></td>
</tr>
</table>
</td>
</tr>
<tr>
<td height="5" colspan="3"><hr /></td>
</tr>
</table>
<!-- 输出被装饰页面的body部分 -->
<decorator:body/>
<table width="780" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
<td background="images/bodybg.jpg">
<br><br>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">	
<tr>
<td width="283"><img src="images/struts2.png" width="188" height="66"></td>
<td width="246"><img src="images/spring.gif" width="152" height="66"></td>
<td width="243"><img src="images/hibernate.png" width="203" height="56"></td>
</tr>
</table>
</td>
</tr>
<tr>
<td height="68" background="images/bodybg.jpg">   </td>
</tr>
<tr height="5"><td background="images/bottom.jpg"></td></tr>
</table>
</body>
</html>
