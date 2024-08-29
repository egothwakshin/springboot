package kr.co.lee.web;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//회원가입 및 로그인, 회원리스트, 회원삭제, 회원정보수정
public interface web_repo extends JpaRepository<jpa_dao, Integer> {
	@Query("select now()")
	String mysql_times();
	
	//아이디 체크 메소드
	//Optional : 데이터 1개를 기준으로 select, List : 여러개 데이터
	//findBy : where
	//findAll : 전체데이터
	Optional<jpa_dao> findByUid(String uid);
	List<jpa_dao> findAll();
	List<jpa_dao> findByOrderByUidxDesc(); //order by uidx desc
	List<jpa_dao> findAllByOrderByUidxDesc(); //order by uidx desc
	List<jpa_dao> findByUidx(int uidx);	//한개의 데이터 정보만 출력
	// like 형태 
	//List<jpa_dao> findByUnameOrderBy(String search_id);
	
	List<jpa_dao> findAllByOrderByUidxDesc(PageRequest limit);
}
