package kr.co.lee.web;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface web_Mapper {
	List<userDB_dao> userlogin(String uid);
	int external_login(String id, String email);
}
