<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 로그인 (외부 로그인 API 연동)</title>
<link href="./css/bootstrap.min.css" rel="stylesheet" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
</head>
<meta name="theme-color" content="#563d7c">
    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
    </style>
<link href="./css/signin.css" rel="stylesheet">    
<body class="text-center">
<form id="frm" class="form-signin" method="post" action="./web_loginok.do" onsubmit="return loginok()">
  <input type="hidden" name="external" value="0">
  <input type="hidden" name="id" value="">
  <input type="hidden" name="email" value="">
  <h1 class="h3 mb-3 font-weight-normal">회원로그인</h1>
  <label for="inputEmail" class="sr-only">아이디를 입력하세요</label>
  <input type="text" name="uid" id="inputEmail" class="form-control" placeholder="아이디를 입력하세요" required autofocus>
  <label for="inputPassword" class="sr-only">Password</label>
  <input type="password" name="upass" id="inputPassword" class="form-control" placeholder="패스워드를 입력하세요" required>
  <div class="checkbox mb-3">
    <label>
      <input type="checkbox" value="remember-me"> 아이디 저장
    </label>
  </div>
  <button class="btn btn-lg btn-primary btn-block" type="submit">로그인</button>
  <span style="display: block;" class="mt-4 mb-3"><a href="./checkout/index.jsp">신규 회원가입</a> | 아이디 및 패스워드 찾기</span>
  <br>
  <img src="./image/kakao_login.png" title="카카오 로그인" style="cursor: pointer;" onclick="kakao_login()">
  <p class="mt-4 mb-3 text-muted" style="font-size: 12px;">Copyright @ lys.co.kr All Rights Reserved 2024</p>
</form>

</body>
<script src="https://t1.kakaocdn.net/kakao_js_sdk/v1/kakao.js"></script>
<script>
	Kakao.init('551f6256cde5f98a333489f3e09f25c9');
	//카카오 API 로그인 파트
	function kakao_login(){
		
		Kakao.Auth.login({
			success:function(response){
				Kakao.API.request({
					url: '/v2/user/me',
					success:function(response){
						console.log(response);
						
						let id = response['id'];
						let email = response["kakao_account"]["email"];
						frm.external.value="kakao";
						frm.id.value=id;
						frm.email.value=email;
						frm.submit();
						
					},
					fail:function(error){
						console.log("카카오 API 접속 오류");
					}
				});			
			},
			fail:function(error){
				console.log("카카오 key 접속 오류");
			}		   
		});
		
		
	}

	function loginok(){
		frm.submit();
	}

</script>
</html>