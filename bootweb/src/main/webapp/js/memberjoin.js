//회원정보 수정
function member_modifyok(){
	
	const dataObject = {};	
	if(frm.utel.value==""){
		alert("연락처를 입력하세요");
	}else{
		const arr = ["uname","uid","upass","utel","uemail",
			"upost","uaddr1","uaddr2","uimg"];
		for(let f=2; f<arr.length; f++){
			const element = document.querySelector("#"+arr[f]).value;
			dataObject[arr[f]]= element;
		}
		
		
		fetch("/member_modifyok",{
			method:"post",
			headers:{
				"Content-Type":"application/json"
			},
			body : JSON.stringify(dataObject)
			
			
		})
		.then(function(response){
			console.log(response);
			alert('개인정보 수정이 완료되었습니다.');
			location.href='/member_list';
		})
		
		
	}
	
}




//아이디 중복체크 -> jpa로 로드받은 값을 핸들링
function ajax_idck(uid){
	
	//console.log(uid);
	
	if(uid==""){
		alert("아이디를 입력하세요.");
		frm.uid.focus();
	}else{	
		fetch("/idcheck?uid="+uid)
		.then(function(response){
			//console.log(response);		
			return response.json();
		})
		.then(function(data){
			//console.log(data);
			
			if(data){
				alert('이미 중복된 아이디입니다.');
			}else{
				alert('사용 가능한 아이디입니다.');
				frm.uid.readOnly = true;
				document.getElementById("checklist").value="ok";
			}		
		})
		
	}
}





function member_joinok(){
	var ck = document.getElementById("checklist").value;
	const dataObject = {};
	if(ck!="ok"){
		alert('아이디 중복확인을 하셔야 합니다.');
	}
	else if(frm.utel.value.length < 10 || frm.utel.value.length > 11){
		alert('올바른 연락처를 입력하세요.');
	}
	else{
		const arr = ["uname","uid","upass","utel","uemail",
		"upost","uaddr1","uaddr2","uimg"];
		for(let f=0; f<arr.length; f++){
			const element = document.querySelector("#"+arr[f]).value;
			//console.log(element);
			dataObject[arr[f]]= element;
		}
		
		fetch('/member_join',{
			method:"post",
			headers:{
				"Content-Type" : "application/json"
			},
			body : JSON.stringify(dataObject)	
		})
		.then(function(response){
			return response.json();
		})
		.then(function(data){
			if(data.status=="success"){
				alert('회원 가입이 완료되었습니다.');
				location.href="/member_list";
			}
			else{
				alert('회원 가입 오류');
				location.href="/checkout/index.do";
			}
		})
		.catch(function(error){
			console.log(error)
		})
		
		
	}
	
}