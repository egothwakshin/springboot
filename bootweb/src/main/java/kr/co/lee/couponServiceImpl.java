package kr.co.lee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service	//해당 interface를 로드하면 해당 class를 실행시키는 어노테이션
public class couponServiceImpl implements couponService {
	
	//Impl : AOP에서 구현 형태의 인터페이스 객체 (추상화) => 다형성 (OCP)
	
	//Repository 를 로드함 (mapper.xml에 있는 DDL 명령어 실행)
	@Autowired
	private couponRepository couponRepository;
	
	//쿠폰삭제
	@Override
	public int delete_service(int cidx) {
		int result = couponRepository.delcoupon(cidx);
		return result;
	}
	
	
	//쿠폰등록
	@Override
	public int insert_service(coupon_dao dao) {
		int result = couponRepository.incoupon(dao);
		return result;
	}
	
	@Override
	public List<coupon_dao> getAllcoupon() {
		
		return couponRepository.getAllcoupon();
	}
	
	
}
