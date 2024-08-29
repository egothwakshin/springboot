package kr.co.lee.web;

import java.util.List;

public interface web_Service {
	List<userDB_dao> userlogin(String uid);
	int external_login(String id, String email);
}
