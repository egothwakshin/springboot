package kr.co.lee;

import java.util.List;

import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j	//로그기록 담당
public class logaspect2 {	//AOP class는 Controller와 관계없이 프로젝트 실행시 무조건 실행하는 Module
	
	//Controller에서 setter값을 입력 받은 사항을 AOP에서 getter로 받아서 추가 코드를 작성하기 위함
	@Resource(name = "userdao")
	user_dao dao;
	
	//Database를 활용하기 위함
	@Autowired
	userService us;
	
	//AOP에서만 사용하는 변수
	private long start = 0;
	private long end = 0;
		
	//login 메소드에 대한 추가 코드사항
	@After("execution(* kr.co.lee.main_controller.loginok(..))")
	public void loginop() throws Throwable {
		
		try {
			//aop에 Request 사용하는 방식		
			HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
			
			System.out.println("session 제작되는 aop");
			//HttpSession hs = req.getSession();	//session 생성
			//hs.setAttribute("siteurl", "http://naver.com");
			
			//Controller => DAO => setter값을 getter로 받는코드
			String uid = this.dao.getAop_uid();
			if(uid!=null) {	//로그인이 확인된 사용자에 한하여 Database에 log 기록 남김
				int result = us.setlogin_log(uid);
				if(result==0) {
					log.info("Database log Table 오류로 인하여 저장실패!!");
				}

			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
		
	//Controller class중 여러개의 메소드 중 하나를 실행 전 작동하는 코드
	@Before("execution(* kr.co.lee.main_controller.*(..))")
	public void logBefore(JoinPoint joinpoint) {
		this.start = System.currentTimeMillis();
		log.info("AOP에서 실행된 log 기록 Before값: " + joinpoint.getSignature().getName());
	}
	
	//Controller class중 여러개의 메소드 중 하나를 실행 후 작동하는 코드
	@After("execution(* kr.co.lee.main_controller.*(..))")
	public void logAfter(JoinPoint joinpoint2) {
		this.end = System.currentTimeMillis();
		long result = this.end - this.start;
		
		try {
			Thread.sleep(2000);
			//System.out.println(result);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		log.info("AOP에서 실행 후 log 기록 After값: " + joinpoint2.getSignature().getName());
	}
}
