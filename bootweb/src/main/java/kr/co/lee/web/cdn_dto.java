package kr.co.lee.web;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "img_lee")
@NoArgsConstructor
@AllArgsConstructor
public class cdn_dto {
	
	@Id
	private int idx;
	
	private String imgname;
	
	private String imgurl;
	
	private String imgdate;
}
