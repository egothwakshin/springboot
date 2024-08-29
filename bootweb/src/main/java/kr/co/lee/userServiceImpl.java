package kr.co.lee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class userServiceImpl implements userService {

	@Autowired
	private userRepository ur;
	

	@Override
	public List<user_dao> login(String uid) {	//로그인		
		return ur.getlogin(uid);
	}
	
	@Override
	public int setlogin_log(String log_uid) {
		return ur.setlogin_log(log_uid);
	}
	
	
	
	


}
