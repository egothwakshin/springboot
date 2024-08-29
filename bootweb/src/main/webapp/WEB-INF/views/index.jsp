<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="cr" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP 메인페이지</title>
</head>
<body>
${username}
<ul>
<cr:forEach var="data" begin="1" end="5" step="1">
<li>${data}</li>
</cr:forEach>
</ul>
</body>
</html>