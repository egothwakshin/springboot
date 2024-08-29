<!DOCTYPE html>
<html th:fragment="body">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link href="../css/bootstrap.min.css" rel="stylesheet">
<title>Bootstrap - 회원가입</title>
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
 <link href="../css/form-validation.css" rel="stylesheet">
</head>
<body class="bg-light">
<form id="frm" class="needs-validation">
<!-- 중복확인 여부를 체크하는 hidden값 -->
<input type="hidden" id="checklist" value="">
<input type="hidden" name="niceck" value="">
<div class="container">
  <div class="py-5 text-center">
    <h2>회원가입</h2>
    <p class="lead">회원가입 샘플 페이지</p>
  </div>
    <div class="order-md-0">
      <h4 class="mb-3">MEMBER-SHIP</h4>
        <div class="row">
          <div class="col-md-6 mb-3">
            <label for="firstName">고객명</label>
            <input type="text" class="form-control" id="uname" name="uname" placeholder="고객명을 입력하세요" value="" required>
          </div>
          <div class="col-md-6 mb-3">
            <label for="lastName">아이디</label>           
            <div class="row" style="padding-left: 15px;">
	            <input type="text" class="form-control" id="uid" name="uid" placeholder="아이디를 입력하세요" value="" required>
	            <input type="button" class="btn btn-secondary" value="중복확인" onclick="ajax_idck(frm.uid.value)">
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-md-6 mb-3">
            <label for="firstName">패스워드</label>
            <input type="password" class="form-control" id="upass" uname="upass" placeholder=" 패스워드를 입력하세요" value="" required>
          </div>
          <div class="col-md-6 mb-3">
            <label for="lastName">패스워드 확인</label>
            <input type="password" class="form-control" id="upass2" placeholder="동일한 패스워드를 입력하세요" value="" required>
          </div>
        </div>
        <div class="mb-3">
          <label for="username">연락처</label>
          <div class="input-group">
            <input type="text" class="form-control" id="utel" name="utel" placeholder="연락처를 입력하세요" required>
            <button class="btn btn-secondary" type="button" onclick="fnPopup()">인증확인</button>
          </div>
        </div>

        <div class="mb-3">
          <label for="email">이메일</label>
          <input type="email" class="form-control" id="uemail" uname="uemail" placeholder="이메일을 입력하세요" required>
        </div>

        <div class="mb-3">
          <label for="address">주소입력</label>
          <div class="row" style="padding-left: 15px;">
          <input type="text" class="form-control" style="width:100px;" id="upost" uname="upost" placeholder="우편번호" readonly>
          <button class="btn btn-secondary" type="button" onclick="sample2_execDaumPostcode()">주소찾기</button>
          </div>
          <div id="layer" style="display:none;position:fixed;overflow:hidden;z-index:1;-webkit-overflow-scrolling:touch;">
		  <img src="//t1.daumcdn.net/postcode/resource/images/close.png" id="btnCloseLayer" style="cursor:pointer;position:absolute;right:-3px;top:-3px;z-index:1" onclick="closeDaumPostcode()" alt="닫기 버튼">
		  </div>
          
          <input type="text" style="margin-top: 5px;" class="form-control" id="uaddr1"  uname="uaddr1" placeholder="도로명 주소" readonly>
       	  <input type="text" style="margin-top: 5px;" class="form-control" id="uaddr2"  uname="uaddr2" placeholder="상세번호" >
        </div>

        <div class="mb-3">
          <label for="address2">캐릭터 이미지 <span class="text-muted">(Optional)</span></label>
          <input type="file" class="form-control" id="uimg" uname="uimg" placeholder="대표 이미지">
        </div>
        </div>
        <hr class="mb-4">
        <button class="btn btn-primary btn-lg btn-block" type="button" onclick="member_joinok()">회원가입</button>
    </div>


  <footer class="my-5 pt-5 text-muted text-center text-small">
    <p class="mb-1">&copy; 2017-2022 Company Name</p>
    <ul class="list-inline">
      <li class="list-inline-item"><a href="#">Privacy</a></li>
      <li class="list-inline-item"><a href="#">Terms</a></li>
      <li class="list-inline-item"><a href="#">Support</a></li>
    </ul>
  </footer>
</form>
<form name="form_chk" method="post">
<input type="hidden" name="m" value="checkplusSerivce">					
<input type="hidden" name="EncodeData" value="AgAFRzk5MTZ5bGwjPvVVgtPgjjtX60MpxLCLNasf+OES5QU4oR2M6TDy6dxQgVnXcRehs54OqG7Ks3/alMFewELcvKADU9zW/4Po5W9HNsgaJWdjMRkMcgAPjkKCsOteYxYy4P85W6gx67fhMc5MEw4Ub3XAw32pTnjnZkho1/34K0cnooSsrSD0GIuuuC68mC/fVrm7nhNJ0xmvkEtW1K9MJWpppYzVEsiwltK1kVgOuo5bhZ3z2TUbgv8yFrInvCFboa9+6mx1ejBsOGheYo+Uqxqv7lhD4y9Yd7Y5FtlIZQDKk7rE+LVojHsdK7eHFf66e7BQDGiODH0WMJGMc4MJaqpFjoWJW8woB3adYSyxeUFGXJhJbDOqOSgF6UDLdfEmpFBzRXeA7KR45dy3cm6OKdBGE0OeuXG+KueH9ntMcSZfScGvlcEFWHPmhRR0Z8voozXD0tmAEOQsUH4cTgyf/DbQjZIEop8EWE9ddQZ9x0qTy3oj3lOtbC5PuIRrZEL1QxD0oZM=">	

<input type="hidden" name="param_r1" value="">
<input type="hidden" name="param_r2" value="">
<input type="hidden" name="param_r3" value="">
</form>
</body>
<script src="../js/memberjoin.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    // 우편번호 찾기 화면을 넣을 element
    var element_layer = document.getElementById('layer');

    function closeDaumPostcode() {
        // iframe을 넣은 element를 안보이게 한다.
        element_layer.style.display = 'none';
    }

    function sample2_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    document.getElementById("sample2_extraAddress").value = extraAddr;
                
                } else {
                    document.getElementById("sample2_extraAddress").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('address').value = data.zonecode;
                document.getElementById("address2").value = addr;

                // iframe을 넣은 element를 안보이게 한다.
                // (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
                element_layer.style.display = 'none';
            },
            width : '100%',
            height : '100%',
            maxSuggestItems : 5
        }).embed(element_layer);

        // iframe을 넣은 element를 보이게 한다.
        element_layer.style.display = 'block';

        // iframe을 넣은 element의 위치를 화면의 가운데로 이동시킨다.
        initLayerPosition();
    }

    // 브라우저의 크기 변경에 따라 레이어를 가운데로 이동시키고자 하실때에는
    // resize이벤트나, orientationchange이벤트를 이용하여 값이 변경될때마다 아래 함수를 실행 시켜 주시거나,
    // 직접 element_layer의 top,left값을 수정해 주시면 됩니다.
    function initLayerPosition(){
        var width = 300; //우편번호서비스가 들어갈 element의 width
        var height = 400; //우편번호서비스가 들어갈 element의 height
        var borderWidth = 5; //샘플에서 사용하는 border의 두께

        // 위에서 선언한 값들을 실제 element에 넣는다.
        element_layer.style.width = width + 'px';
        element_layer.style.height = height + 'px';
        element_layer.style.border = borderWidth + 'px solid';
        // 실행되는 순간의 화면 너비와 높이 값을 가져와서 중앙에 뜰 수 있도록 위치를 계산한다.
        element_layer.style.left = (((window.innerWidth || document.documentElement.clientWidth) - width)/2 - borderWidth) + 'px';
        element_layer.style.top = (((window.innerHeight || document.documentElement.clientHeight) - height)/2 - borderWidth) + 'px';
    }
</script>
<script>
document.title = "NICE I-PIN 인증 및 휴대폰 인증";

function fnPopup(){
	window.open('', 'popupChk', 'width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
	document.form_chk.action = "https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb";
	document.form_chk.target = "popupChk";
	document.form_chk.submit();
	frm.niceck.value="ok";
}
</script>
</html>