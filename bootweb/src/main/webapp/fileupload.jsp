<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일 업로드 - Spring boot</title>
</head>
<body>
<form id="frm" method="post" action="./fileuploadok3.do" enctype="multipart/form-data">
단일 파일 업로드 : <input type="file" name="file1"><br>
복합 파일 업로드 : <input type="file" name="file2" multiple="multiple"><br>
<input type="button" value="전송" onclick="fileok()">
</form>
</body>
<script>
function fileok(){
	frm.submit();
}

</script>
</html>