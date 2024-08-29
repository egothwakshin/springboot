package kr.co.lee.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class web_ServiceImpl implements web_Service {
	
	@Autowired
	private kr.co.lee.web.web_Mapper wmp;
	
	@Override
	public int external_login(String id, String email) {
		int result = wmp.external_login(id, email);
		return result;
	}
	
	
	
	@Override
	public List<userDB_dao> userlogin(String uid) {
		List<userDB_dao> info = wmp.userlogin(uid);
		return info;
	}
}
