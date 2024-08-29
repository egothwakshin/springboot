package kr.co.lee.web;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class web_controller2 {

	PrintWriter pw = null;
	
	@Autowired
	cdn_repo cdn_repo;
	
	cdn_dto dto;
	
	//Restful API서버 => JSON형태로 출력
	
	//FTP에 있는 파일 삭제 + DB에 있는 정보 삭제
	@GetMapping("/cdn_del/{filenm}")
	public String cdn_file_delete(@PathVariable String filenm) {
		FTPClient ftp = new FTPClient();
		ftp.setControlEncoding("utf-8");
		FTPClientConfig fc = new FTPClientConfig();
		try {
			String host = "172.30.1.16";	//CDN 서버주소
			String user = "admin";	//FTP 접속아이디
			String pass = "123123";	//FTP 접속패스워드
			int port = 20021;	//FTP 접속포트
			ftp.configure(fc);
			ftp.connect(host,port);	//FTP 접속체크
			if(ftp.login(user, pass)) {	//FTP 로그인
				String ftpurl = "/home/admin/CDN/images/cdn_upload_lee/";	//FTP 절대경로
				List<cdn_dto> origin = cdn_repo.findByImgnameLikeOrderByIdxDesc("%"+filenm+"%");
				
				boolean delok = ftp.deleteFile(ftpurl+origin.get(0).getImgname());
				if(delok==true) {
					//delete: 일반속성, deleteById : primary key 이용하여 삭제
					cdn_repo.deleteById(origin.get(0).getIdx());
					System.out.println("파일삭제 완료");
					ftp.disconnect();
					
				}else {
					System.out.println("파일을 찾을 수 없습니다.");
				}
				
			}
			
			
		}catch(Exception e) {
			System.out.println("FTP 접속 오류!!");
		}
		
		return null;
	}
	
	
	//CDN API 서버
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/cdn_list/{filenm}")
	public @ResponseBody byte[] cdn_listapi(@PathVariable String filenm,
					ServletResponse res, Model m) throws Exception {
		String imgurl = null; //DB에 저장된 이미지 전체 경로를 가져오는 변수
		byte[] img = null;
		try {
			//Database저장된 파일명을 기점으로 select하는 형태 (like)
			List<cdn_dto> result = cdn_repo.findByImgnameLikeOrderByIdxDesc("%"+filenm+"%");
					
			//FTP로 업로드 된 CDN 서버 전체경로
			imgurl = result.get(0).getImgurl()  + result.get(0).getImgname();
			System.out.println("imgurl의 값은?" + imgurl );
			
			//JAVA Net
			URL url = new URL(imgurl);
			//http프로토콜 라이브러리 (해당 경로에 있는 파일을 url에 접속)
			HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();		
			
			
			/* CHAT-GPT 코드
	        // Content-Type 설정
	        res.setContentType("image/jpeg");  // 이미지 유형에 맞게 변경
	        
	        // 이미지 데이터를 읽어와 OutputStream에 직접 쓰기
	        try (InputStream is = httpcon.getInputStream();
	             OutputStream os = res.getOutputStream()) {
	            byte[] buffer = new byte[8192];
	            int bytesRead;
	            while ((bytesRead = is.read(buffer)) != -1) {
	                os.write(buffer, 0, bytesRead);
	            }
	        }
	        
	        httpcon.disconnect();
	        */
			
			
			//해당 경로에 있는 파일을 전체 용량을 읽어들임
			InputStream is = httpcon.getInputStream();
			img = IOUtils.toByteArray(is);	//byte[]로 변환하여 출력시킴
			
			is.close();
			httpcon.disconnect();
			
		
		}catch(Exception e) {
			System.out.println(e);
			System.out.println("Database 오류 발생!!");
		}finally {
		}
		return img;
	}
	
	//ajax로 파일 전송을 받는 메소드
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/ajax_uploadok.do")
	public String ajax_upload(MultipartFile mfile) {
		System.out.println(mfile.getOriginalFilename());
		return null;
	}
	
	//CDN서버로 이미지 및 컨텐츠 파일 전송 메소드
	@PostMapping("/cdn_uploadok.do")
	public String cdn_server(
			@RequestParam(defaultValue = "", required = false)String url,
			MultipartFile mfile, Model m, HttpServletResponse res) {
		System.out.println(mfile.getOriginalFilename());
		
		FTPClient ftp = new FTPClient();
		ftp.setControlEncoding("utf-8");
		FTPClientConfig cf = new FTPClientConfig();
		try {
			String filename = mfile.getOriginalFilename();
			String host = url;
			String user = "admin"; //ftp접속아이디
			String pass = "123123"; //ftp 접속패스워드
			int port = 20021; //접속포트
			ftp.configure(cf);
			ftp.connect(host,port);
			if(ftp.login(user, pass)) {
				ftp.setFileType(FTP.BINARY_FILE_TYPE);
				//ftp.setFileType(FTP.ASCII_FILE_TYPE);	//ASCII(txt,html,js,css...)
				int result = ftp.getReplyCode();
				boolean ok = ftp.storeFile("/home/admin/CDN/images/cdn_upload_lee/"+filename, mfile.getInputStream());
				if(ok==true) {
					String file_url = "http://172.30.1.16:20080/images/cdn_upload_lee/"; //CDN서버경로
					this.dto = new cdn_dto();
					this.dto.setIdx(0);
					this.dto.setImgname(filename);
					this.dto.setImgurl(file_url);
					this.dto.setImgdate(cdn_repo.mysql_times());
					try {
						cdn_repo.save(this.dto);	//DB 저장
						cdn_repo.flush();
						this.pw = res.getWriter();
						this.pw.print("ok");
						//System.out.println("정상적으로 CDN서버로 파일 업로드 하였습니다.");
						this.pw.close();
					}catch (Exception e) {
						System.out.println("Database 연결 실패 오류!!");
					}
					
					System.out.println("정상적으로 CDN서버로 파일 업로드 하였습니다.");
				}else {
					System.out.println("CDN 서버 디렉토리 경로가 올바르지 않습니다.");
				}
			}else {
				System.out.println("FTP 아이디 및 패스워드 오류 발생!!");
			}
			
		}catch(Exception e) {
			System.out.println("CDN 서버 접속 오류 발생!!");
		}finally {
			try {
				ftp.disconnect();
			}catch(Exception e) {
				System.out.println("FTP접속 해제가 실행되지 않습니다.");
			}
		}
		
		return null;
	}
	
	//thymeleaf with 사용법 및 format 사용법
	@GetMapping("/thymeleaf.do")
	public String thymeleaf() {
		return "/thymeleaf.html";
	}
	
}
