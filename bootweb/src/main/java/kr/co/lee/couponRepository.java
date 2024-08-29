package kr.co.lee;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper	//Mapper와 연동
public interface couponRepository {	//Mapper에서 연동되는 id를 로드

	List<coupon_dao> getAllcoupon();	//전체리스트 출력 (mapper ID명)
	int incoupon(coupon_dao dao);
	int delcoupon(int cidx);
	
	
}
