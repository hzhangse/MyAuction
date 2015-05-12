

<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>出错提示页</title>
</head>
<body>
<table width="780" align="center" cellspacing="0"
	background="images/bodybg.jpg">
<tr> 
	<td height="39" valign="top">
		<div align="center" style="font-size:11pt;color:red;font-weight:bold">
			系统处理过程中发生了一个错误，信息如下：</div>
	</td>
</tr>
<tr>
	<td height="100">
		<center>
		<div class="error"><br /><br />
			<s:property value="errMsg"/>
		</div>
		</center>
	</td>
</tr>

</table>
</body>
</html>