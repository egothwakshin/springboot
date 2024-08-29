package kr.co.lee;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

//AOP (추가코드 작성) - couponService
@Aspect		//AOP 선언
@Component	//특정 클래스 타입을 선언 (부품)
@Slf4j		//로그기록 담당
public class logaspect {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	//@Pointcut("execution(* kr.co.lee..*(..))")	//AOP : 표현식 결합 (해당 package에 있는 모든 interface)
	@Pointcut("execution(* kr.co.lee.couponService.*(..))")
	private void doexecute() {	//AOP 추가 코드 부분
		System.out.println("testtest");
		
	}
	
	@Around("doexecute()")	//해당 interface에 class를 실행시 해당 AOP 발동
	public Object logging(ProceedingJoinPoint joinpoint) throws Throwable {
		String methodname = joinpoint.getSignature().toShortString();
		Object obj = null;
		try {
			log.info(methodname + "해당 메소드 실행됨");
			obj = joinpoint.proceed();
		}
		finally {
			log.info(methodname + "해당 메소드 실행종료");
		}
		return obj;
	}
}
