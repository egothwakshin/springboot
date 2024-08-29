<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 + Spring-boot + AOP</title>
</head>
<body>
<form id="frm" method="post" action="./loginok.do">
아이디 : <input type="text" name="uid" oninvalid="this.setCustomValidity('아이디를 입력하세요')" required><br>
패스워드 : <input type="password" name="upass" required><br>
<input type="submit" value="로그인">
</form>
</body>
</html>