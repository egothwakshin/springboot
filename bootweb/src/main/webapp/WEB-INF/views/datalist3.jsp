<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String msg = (String) request.getAttribute("data");
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View (JSP 전용)</title>
</head>
<body>
<%=msg%>
<br><br>
${view2} 
</body>
</html>