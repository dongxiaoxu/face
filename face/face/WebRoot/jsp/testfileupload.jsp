<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'testfileupload.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <body>
    <form action="test/FileUploadTest" method="post" enctype="multipart/form-data">
    	<table>
    		<tr>
    			<td>姓名：</td>
    			<td><input type="text" name="name"/></td>
    		</tr>
    		<tr>
    			<td>爱好：</td>
    			<td></td>
    		</tr>
    		<tr>
    			<td></td>
    			<td><input type="checkbox" name="hobby" value="baskedball"/>篮球</td>
    		</tr>
    		<tr>
    			<td></td>
    			<td><input type="checkbox" name="hobby" value="dance"/>跳舞</td>
    		</tr>
    		<tr>
    			<td></td>
    			<td><input type="checkbox" name="hobby" value="sing"/>唱歌</td>
    		</tr>
    		<tr>
    			<td></td>
    			<td><input type="file" name="picture1"/></td>
    		</tr>
    		<tr>
    			<td></td>
    			<td><input type="file" name="picture2"/></td>
    		</tr>
    	</table>
    	<input type="submit" vlaue="提交"/>
    </form>
  </body>
</html>
