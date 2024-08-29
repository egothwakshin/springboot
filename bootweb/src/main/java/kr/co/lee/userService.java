package kr.co.lee;

import java.util.List;

public interface userService {
	public List<user_dao> login(String uid);
	public int setlogin_log(String log_uid);
}
