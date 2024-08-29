<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="cr" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쿠폰 리스트 Spring-boot 페이지</title>
</head>
<body>
쿠폰 총 개수 : ${ea}
<ul>
<cr:forEach var="data" items="${all}">
<li>${data.cpname} : <input type="button" value="삭제" onclick="쿠폰삭제('${data.cidx}')"></li>
</cr:forEach>
</ul>
</body>
<script>
function 쿠폰삭제(cidx){
	
	console.log(cidx);
	location.href="./coupon_delok.do?cidx="+cidx;
	
}

</script>
</html>