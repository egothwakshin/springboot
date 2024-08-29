package ja.aop;

public class exam implements examinterface {

	private int js,html,spring;
	
	public exam(int a, int b, int c) {	//즉시실행
		this.js=a;
		this.html=b;
		this.spring=c;
	}
	@Override
	public int total(){		//합계
		long start = System.currentTimeMillis();
		int result = this.js + this.html + this.spring;
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		/*
		long end = System.currentTimeMillis();
		String msg = (end-start) + "처리시간 소요됨";
		System.out.println(msg);
		*/
		return result;
		
	}
	@Override
	public float avg() {	//평균값
		float avgs = this.total() / 3.0f;
		return avgs;
	}
}
