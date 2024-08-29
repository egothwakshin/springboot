package ja.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class java_aop {

	public static void main(String[] args) {
		//interface를 로드 후 해당 inerface를 implements 한 즉시실행 메소드를 호출
		examinterface ex = new exam(100, 80, 90);
		
		//AOP (Proxy)
		//Throwable (클래스) : 예외처리 (AOP > Exception > RuntimeException, IOException...)
		/*
			Proxy.newProxyInstance: 동적 프록시 ClassLoad(프록시 클래스를 만들 클래스로더)
 			Class: 프록시 클래스가 구현할 인터페이스 목록(배열)
 			InvocationHandler: 메소드가 호출되었을 때 실행되는 추가 코드
 		*/
 		examinterface aops = (examinterface)Proxy.newProxyInstance(examinterface.class.getClassLoader(), 
				new Class[] {examinterface.class}, 
				new InvocationHandler() {
					
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						
						//본코드에 없는 추가된 코드
						long start2 = System.currentTimeMillis();
						Object result = method.invoke(ex, args);
											
						long end = System.currentTimeMillis();
						String msg = (end-start2) + "처리시간 소요됨";
						System.out.println(msg);
							
						return result;
					}
				} );

		int result = ex.total();
		System.out.println("본코드에서 실행된 결과값: " + result);
		
		//aop에서 실행되는 값
		int result_aop = aops.total();
		float result_avg = aops.avg();
		System.out.println("AOP에서 실행된 결과값1: " + result_aop);
		System.out.println("AOP에서 실행된 결과값2: " + result_avg);
	}

}
