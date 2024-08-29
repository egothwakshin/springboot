package kr.co.lee.web;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class jpa_controller {

	@Autowired
	private jpa_repo repo;	//jpa interface 를 load함
	
	
	@GetMapping("/jpa_delete.do")
	public String jpa_delete() {
		jpa_dao jd = new jpa_dao();
		jd.setUidx(4);
		repo.delete(jd);	//delete : DB데이터를 삭제
		return null;
	}
	
	
	
	@GetMapping("/jpa_insert.do")
	public String jpa_insert() {
		jpa_dao jd = new jpa_dao();
		jd.setUidx(0);
		jd.setUid("hong");
		jd.setUname("홍길동");
		jd.setUpass("a1234");
		jd.setUtel("01022223333");
		jd.setUemail("hong@nate.com");
		jd.setUpost("05612");
		jd.setUaddr1("서울시 마포구");
		jd.setUaddr2("중앙빌딩 4F");
		jd.setUimg("");
		
		//LocalDateTime dt = LocalDateTime.now(); //자신의 PC시간 => 이건 잘 쓰지않음
		
		String dt = repo.mysql_times();	//interface에 있는 @Query에 값을 받아서 DB에 저장
		System.out.println(dt);
		jd.setToday(dt);
		
		//save : insert 역할 dao를 활용하여 적용함
		jpa_dao inserts = repo.save(jd);
		System.out.println(inserts);
		
		return null;
	}
	
}
