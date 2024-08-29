package kr.co.lee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class BootwebApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootwebApplication.class, args);
	}

}
