package kr.co.lee.web;

import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Repository("userDB_dao")
public class userDB_dao {
	int uidx;
	String uid,upass,uname,uemail,ujoin;
}
