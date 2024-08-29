<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ajax로 이미지 전송 및 JPA</title>
</head>
<body>
<!-- ajax파일전송 - 주로 모바일에서 활용 -->
파일명 : <input type="file" id="mfile" accept="/image/*" capture="camera">
<input type="button" value="ajax_ 파일전송" onclick="fileupload()">
</body>
<script>
function fileupload(){
	var f = document.getElementById("mfile");
	var ajax = new XMLHttpRequest();
	let formdata = new FormData(); // <form>태그를 사용한거와 동일함
	formdata.append("mfile",f.files[0]);	//첨부파일 형태 -> 원시배열
	formdata.append("url","172.30.1.16");
	ajax.onreadyStatechange = function(){
		if(ajax.readyState==4){
			if(ajax.status==200){
				var result = this.response;
				console.log(response);
				if(result=="ok"){
					alert('정상적으로 이미지가 반영되었습니다.');
					location.reload();
				}
			}
		}
		else{
			console.log("통신오류 발생!!");
		}
	}
	ajax.open("POST","/cdn_uploadok.do",false);
	//ajax.open("POST","/ajax_uploadok.do",false);
	ajax.send(formdata);
}
</script>
</html>