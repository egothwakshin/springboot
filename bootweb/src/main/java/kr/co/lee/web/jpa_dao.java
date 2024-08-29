package kr.co.lee.web;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data	//@Setter, @Getter 모두 사용
@Entity	//JPA를 활용하는 어노테이션 @Table, @Id, @Column...
@NoArgsConstructor	//파라미터가 없는 디폴트 생성자를 생성
@AllArgsConstructor	//모든 필드 값을 파라미터로 받는 생성자를 말함
@Table(name = "member_ship")
public class jpa_dao {
	
	//*****jpa로 테이블을 생성시 컬럼 순서로 설정이 되지 않습니다.*****
	//unique (unique key 활용)
	@Id	 //primary key (기본키)	columnDefinition : 데이터 속성 및 추가 default 등 입력시키는 형태
	@Column(nullable = false, columnDefinition = "int auto_increment") // nullable = true (null) / false (not null)
	private int uidx;
	
	@Column(nullable = false, unique = true,length = 30)
	private String uid;
	
	@Column(nullable = false, length = 50)
	private String uname;
	
	@Column(nullable = false, length = 100)
	private String upass;	
	
	@Column(nullable = false, length = 11)
	private String utel;	
	
	@Column(nullable = false, length = 50)
	private String uemail;	
	
	@Column(nullable = false, length = 5)
	private String upost;	
	
	@Column(nullable = false, columnDefinition = "text")
	private String uaddr1;	
	
	@Column(nullable = false, columnDefinition = "text")
	private String uaddr2;	
	
	@Column(nullable = true, columnDefinition = "text")
	private String uimg;
	
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private String today;
	
	public String todays() {
		return null;
	}
	
}
