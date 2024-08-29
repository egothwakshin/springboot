package kr.co.lee.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.print.DocFlavor.STRING;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class web_controller {
	
	PrintWriter pw = null;	
	//@GetMapping(value= {"/web_loginok.do","/web_loginok2.do"})
	
	@Resource(name = "userDB_dao")
	userDB_dao dao;
	
	@Autowired
	web_Service ws;
	
	@GetMapping("/arrays.do")
	public String arrays(Model m) {
		
		String arr[][] = {
				{"홍길동","강감찬","이순신"},
				{"22","33","44"}
		};

		m.addAttribute("arr", arr);
		
		ArrayList<ArrayList<String>> all = new ArrayList<>();
		ArrayList<String> al = new ArrayList<>();
		al.add("서울지사");
		al.add("경기도지사");
		al.add("강원도지사");
		all.add(al);
		ArrayList<String> al2 = new ArrayList<>();
		al2.add("52");
		al2.add("22");
		al2.add("16");
		all.add(al2);
		
		m.addAttribute("all", all);
		System.out.println(all);
		return "/arrays.html";
	}
	
	//타임리프로 레이아웃 연습 페이지
	@GetMapping("/layout.do")
	public String layout(Model m) {
		String copyright = "Copyright © 2023~2024 Maker By Leeyunseok All reserved.";
		m.addAttribute("copyright", copyright);
		Map<String, String> mdlist = new HashMap<>();
		mdlist.put("productnm", "삼성 냉장고");
		mdlist.put("price", "680000");
		
		m.addAttribute("mdlist", mdlist);
		return "/layout.html";
	}
	
	@Autowired
	web_repo web_repo;
	
	//post 전용, produces 언어셋 (JSON, XML, HTML(thymeleaf) 한글이 깨질경우) script (X)
	@RequestMapping(value = "/member_modifyok",
					method = RequestMethod.POST,
					produces = "text/html;charset=utf-8")
	public String member_modifyok(@RequestBody jpa_dao dao) {
		
		System.out.println(dao);
		//findById: primary key (@Id)
		/*
		  findById: select * from member_ship where uidx=?;
		  orElse: select 시 값이 없을 경우 null로 반환하는 메소드	
		  
		  save() : insert, update (select 후에 변경된 값이 있을 경우)
		  */
		try {
			//방식2 : 배열로 update하는 방식
			List<jpa_dao> list = web_repo.findByUidx(dao.getUidx());
			System.out.println("list.get(0).getUtel()의 값은?" + list.get(0).getUtel());
			list.get(0).setUtel(dao.getUtel());
			jpa_dao result = web_repo.save(list.get(0));	//update가 발생
			System.out.println("result의 값은?" + result);
						
			/*
			 //방식1 : dto 로 update하는 방식
			jpa_dao jd = web_repo.findById(dao.getUidx()).orElse(null);
			jd.setUtel(dao.getUtel());
			jd.setUemail(dao.getUemail());
			
			if(!dao.getUpass().equals(null)) {	//패스워드란 입력시 패스워드 수정
				jd.setUpass(dao.getUpass());
			}
				web_repo.save(jd);
			*/
				web_repo.flush();	//database 지연시 메모리에 쌓여있는 값을 소모시킴(insert,update,delete)
		}catch(Exception e) {
			
		}
		
		
		return null;
	}
	
	
	
	//회원정보 수정 jpa + thymeleaf
	@PostMapping("/member_modify")
	public String member_modify(int uidx, Model m) {
		try {
			List<jpa_dao> onedata = web_repo.findByUidx(uidx);
			if(onedata.get(0)==null) {
				System.out.println("해당 데이터가 없습니다.");
			}else {
				m.addAttribute("onedata", onedata);				
			}
		}catch(Exception e) {
			System.out.println("DB 쿼리 오류발생!!");
		}
		return "/checkout/member_modify.html";
	}
	
	
	//회원 삭제 jpa
	@GetMapping("/member_del")
	public String member_del(int uidx, ServletResponse res) {
		res.setContentType("text/html;charset=utf-8");
		try {
			this.pw = res.getWriter();
			if(uidx!=0) {
				jpa_dao jd = new jpa_dao();
				jd.setUidx(uidx);
				web_repo.delete(jd);
				this.pw.print("<script>"
						+ "alert('해당 사용자가 삭제 되었습니다.');"
						+ "location.href='/member_list';"
						+ "</script>");
			}
			
		}catch(Exception e) {
			this.pw.print("<script>"
					+ "alert('Database 오류 발생');"
					+ "location.href='/member_list';"
					+ "</script>");
		}
		return null;
	}
	
	
	//thymeleaf로 출력
	@GetMapping("/member_list")
	public String member_list(Model m,
							@RequestParam(value = "", required = false) String search_part,
							@RequestParam(value = "", required = false) String search_id,
							@RequestParam(value = "", required = false) Integer pageno ) {
			
		List<jpa_dao> all = null;
		//page번호를 노드 버호로 변경
		Integer pgn = 0;
		if(pageno==null) {
			pageno = 1;
		}
		else if(pageno>0) {
			pgn = pageno - 1;
		}
		m.addAttribute("pageno", pageno);	//thymeleaf에서 페이지 번호 체크
		
		//전체갯수파악
		List<jpa_dao> alldata = web_repo.findByOrderByUidxDesc();
		int dataea = alldata.size();
		m.addAttribute("dataea", dataea);	// 전체회원수
		int pg = (int)Math.ceil((double)dataea/2);
		m.addAttribute("pg", pg);
		
		//검색이 없을 경우
		/* PageRequest : jpa에서 limit(X)없음, 그룹형태 분할해서 data출력 */		
		//검색에 대한 데이터를 DTO에 SETTER로 담아서 thymeleaf로 데이터 전달
		//DB에 있는 전체 데이터를 DAO를 이용하여 thymeleaf 로 데이터 전달
		if(search_part==null && search_id==null) {
			PageRequest limit = PageRequest.of(pgn, 2);	//(node,출력개수)
			all = web_repo.findAllByOrderByUidxDesc(limit);;
		}
		else {
			System.out.println(search_part);
			System.out.println(search_id);
			if(search_part.equals("nm")) {
				
			}
			else if(search_part.equals("id")) {
				
			}
			else{
				
			}
		}
			m.addAttribute("search_part", search_part);
			m.addAttribute("search_id", search_id);
			m.addAttribute("all", all);
		
			return "/checkout/member_list.html";
	}
	
	//★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
	@PostMapping("/member_join")
	@ResponseBody
	public Map<String, String> member_join(@RequestBody jpa_dao jd) {
		jd.setToday(web_repo.mysql_times());
		Map<String, String> mp = new HashMap<>();
		
 		try {			
			jpa_dao insert = web_repo.save(jd);
			mp.put("status", "success");
			
		}catch(Exception e) {
			mp.put("status", "error");
		}		
		return mp;
	}
	//★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
	
	
	//ajax(get통신) id 중복체크
	@GetMapping("/idcheck")
	@ResponseBody
	public boolean idcheck(@RequestParam(value = "", required = false) String uid) {
		//System.out.println(uid);
		
		//isPresent : true (존재함. 아이디중복됨.), false(존재하지않음. 사용가능한 아이디) 회신을 해줌
		boolean result = web_repo.findByUid(uid).isPresent();
		web_repo.flush();

		return result;
	}

	
	//thymeleaf, < JSP, JSTL (우선권) - Spring MVC
	//thymeleaf 외부 파일로드 규칙 : resources 안에서 모든 것을 핸들링 해야 합니다.
	
	@GetMapping("/checkout/index.do")
	public String membership(Model m) {
		
		//return null //	Spring MVC
		//return "checkout/index.html";	// WEB-INF/views/
		return "/checkout/index.html";	//src/main/resources/>templates/
	}
	
	
	
	//thymeleaf로 데이터 출력 => html, jsp
	@GetMapping("/datalist.do")
	public String datalist(Model m) {
		String user = "홍길동";
		m.addAttribute("user", user);
		List<String> all = new ArrayList<String>();
		all.add("Java");
		all.add("HTML");
		all.add("Spring-boot");
		m.addAttribute("all", all);
		
		Map<String, String> member = new HashMap<String,String>();
		member.put("mid", "apink");
		member.put("mname", "에이핑크");
		member.put("memail", "apink@naver.com");
		m.addAttribute("member", member);
		
		//DAO를 이용하여 thymeleaf로 출력
		userDB_dao da = new userDB_dao();
		da.setUidx(10);
		da.setUname("강감찬");
		da.setUemail("kang@gmail.com");
		m.addAttribute("da", da);
		
		//이벤트 참여 여.부
		String agree = "Y";
		m.addAttribute("agree", agree);
		
		//select 목록
		String chart = "이름";
		m.addAttribute("chart", chart);
		
		return "/datalist.html"; //기본 thymeleaf 속성 html view 출력
	}
	
	@GetMapping("/datalist2.do")
	public String datalist2(Model m) {
		m.addAttribute("view", "JSP & JSTL");
		return "/datalist2";	//properties에서 적용한 suffix가 적용된 view 출력
	}
	
	//JSP와 JSTL view에 데이터를 출력하는 방식
	@GetMapping("/datalist3.do")
	public String datalist3(Model m, ServletRequest req) {
		req.setAttribute("data", "JSP변수 값에 대한 데이터 출력" );
		m.addAttribute("view2", "JSTL 데이터 출력");		
		return null;
	}
	
	
	
	//Mutiple형태로 여러개의 파일을 처리
	@PostMapping("/fileuploadok3.do")
	//public void fileupload3(@RequestParam("file2") MultipartFile files[]) throws Exception{
	//public void	fileupload3(@RequestParam("file2") List<MultipartFile> files) throws Exception {
	public void	fileupload3(@RequestPart("file2") MultipartFile files[]) throws Exception {	

		/*
		@RequestParam("file2") List<MultipartFile> files 로 쓴 경우
		
		//System.out.println(files.size());
		for(MultipartFile fi : files) {
			System.out.println(fi.getSize());
		}
		*/
	}
	
	
	@PostMapping("/fileuploadok2.do")
	//RequestPart형태의 파일 업로드 기능
	public void fileupload2(@RequestPart(value="file1", required = false) MultipartFile files,
			ServletResponse res) throws IOException {
		System.out.println(files.getOriginalFilename());
		//이하 받는방식 RequestParam와 동일함
	}
	
	
	
	//HttpServletResponse => ServletResponse (상위모형)
	@PostMapping("/fileuploadok.do")
	//RequestParam형태의 파일 업로드 기능
	public void fileupload(@RequestParam("file1") MultipartFile files,
			ServletResponse res, ServletRequest req) throws IOException {
		this.pw = res.getWriter();
		long filesize = files.getSize();
		String filenm = files.getOriginalFilename();
		String url = req.getServletContext().getRealPath("/upload/");
		//확장자명 추출
		String nm = filenm.substring(filenm.lastIndexOf("."));	// .jpg 출력됨
		
		//파일업로드시 중복이름 피하기 위한 이름생성 (UUID - 암호화 알고리즘)
		//UUID version1 : PC - Macaddress 를 이용하는 형태
		//UUID version3 : MD5 hash
		//UUID version4 : Random 함수
		//UUID version5 : SHA-1 함수
		String newname = UUID.randomUUID().toString();	// ec7fba95-1ee5-410d-bd3e-a50a74862a5d
		String copys = url + newname + nm; 	//디렉토리경로+새로운파일명+확장자
		FileCopyUtils.copy(files.getBytes(), new File(copys));
		
		/*
		//MD5, SHA-1 (UUID v3,v5)
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] by = md.digest();
			String newname = UUID.nameUUIDFromBytes(by).toString();
			System.out.println(newname);
			
		}catch (Exception e) {
		
		}
		*/

		
		this.pw.print("<script>"
				+ "alert('파일 업로드가 완료되었습니다.');"
				+ "</script>");	
	}
	
	
	
	
	
	@RequestMapping("/web_loginok.do")
	public String web_loginok(@RequestParam(defaultValue = "0", required = true)String external ,
			String uid, String upass, HttpServletRequest req, HttpServletResponse res) {	//로그인 및 API 로그인
		res.setContentType("text/html;charset=utf-8");
		
		if(external.equals("0")) {
			List<userDB_dao> info = ws.userlogin(uid);
			System.out.println(info.get(0).getUname());			
		}
		//카카오 API를 이용한 로그인
		else if(external.equals("kakao")) {
			String id = req.getParameter("id");
			String email = req.getParameter("email");
			int result = ws.external_login(id, email);
			if(result>0) {	//미회원 가입자
				try {
					this.pw = res.getWriter();
					this.pw.print("<script>"
							+ "alert('가입을 완료시켜 주셔야 합니다.');"
							+ "location.href='./web_join.jsp';"
							+ "</script>");
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					this.pw.close();
				}
				
			}
			else {	//가입자 (select 확인)
				
				
			}
		}
		
		
		
		
	
		return null;
	}
}
