package kr.co.lee;

import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

//@Resource : class를 로드
//@Repository : class를 내보낼 때
@Repository("userdao")
public class user_dao {
	int uidx;
	String uid,upass,uname,uemail,ujoin;
	
	//AOP에서 활용할 변수
	String aop_uid;
	
}
