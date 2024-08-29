package kr.co.lee;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

//mapper.xml
@Mapper
public interface userRepository {
	List<user_dao> getlogin(String uid);	//로그인
	int setlogin_log(String log_uid);		//로그인 후 log 기록
}
