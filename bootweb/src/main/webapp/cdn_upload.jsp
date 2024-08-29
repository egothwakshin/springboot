<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CDN FTP 파일 업로드 사용법</title>
</head>
<body>
<form id="frm" method="post" action="/cdn_uploadok.do" enctype="multipart/form-data">
<input type="hidden" name="url" value="172.30.1.16">
<input type="file" name="mfile">
<input type="button" value="CDN 전송" onclick="fileok()">
</form>
</body>
<script>
function fileok(){
	frm.submit();
}
</script>
</html>