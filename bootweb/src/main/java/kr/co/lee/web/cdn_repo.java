package kr.co.lee.web;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface cdn_repo extends JpaRepository<cdn_dto, Integer> {

	@Query("select now()")
	String mysql_times();
	
	List<cdn_dto> findByImgnameLikeOrderByIdxDesc(String imgname);
}
