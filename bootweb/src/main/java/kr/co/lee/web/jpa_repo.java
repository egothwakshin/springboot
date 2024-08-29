package kr.co.lee.web;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface jpa_repo extends JpaRepository<jpa_dao, Integer> {
	
	@Query("select now() as todays")	//@Query : sql ddl 강제 입력시켜서 사용하는 어노테이션
	String mysql_times();
}
