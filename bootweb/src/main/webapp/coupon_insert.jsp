<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spring-boot로 쿠폰등록 페이지</title>
</head>
<body>
<form id="frm" method="post" action="./coupon_insertok.do">
<p>쿠폰 등록 페이지</p>
쿠폰명 : <input type="text" name="cpname"><br>
쿠폰할인율 : <input type="text" name="cprate"><br>
쿠폰 사용 유/무 : <input type="radio" name="cpuse" value="Y" checked="checked">사용함<br>
			   <input type="radio" name="cpuse" value="N">사용안함<br>
쿠폰사용기한 : <input type="date" name="cpdate"><br>
<input type="button" value="쿠폰등록" onclick="쿠폰등록()"> 
</form>
</body>
<script>
function 쿠폰등록(){
	frm.submit();
}

</script>
</html>