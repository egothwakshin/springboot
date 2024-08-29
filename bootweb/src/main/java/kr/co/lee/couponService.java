package kr.co.lee;

import java.util.List;

//Repository + mapper.xml 를 로드할 수 있는 interface 생성
public interface couponService {
	public List<coupon_dao> getAllcoupon();
	public int insert_service(coupon_dao dao);
	public int delete_service(int cidx);
}
