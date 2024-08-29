package kr.co.lee;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class main_controller {
	
	//Service(interface) <=> ServiceImpl (구현 클래스): Impl(implementation)
	@Autowired
	private couponService couponService;	//interface를 최종 호출
	
	@Autowired
	private userService userservice;	//userTable 정보 관련 interface
	
	PrintWriter pw = null;

	@Resource(name = "userdao")
	user_dao dao;
	
	@PostMapping("/loginok.do")
	public String loginok(@RequestParam(value="",required = false) String uid, 
						  @RequestParam(value="",required = false) String upass,
						  HttpServletResponse res, HttpServletRequest req) {
		
		res.setContentType("text/html;charset=utf-8");
		try {
			this.pw = res.getWriter();
			List<user_dao> one = userservice.login(uid);
			this.dao.setUid(one.get(0).getUid());
			
			int ea = one.size();
			if(ea==0) {
				this.pw.print("<script>"
						+ "alert('아이디 및 패스워드를 다시 확인하세요.');"
						+ "history.go(-1);"
						+ "</script>");
			}else {
				if(upass.equals(one.get(0).getUpass())){
					this.pw.print("<script>"
							+ "alert('로그인 하셨습니다.');"						
							+ "</script>");
					//aop에서 추가 코드를 작성하여 프로그램 활성화
					this.dao.setAop_uid(one.get(0).getUid());

					
				}else {
					this.pw.print("<script>"
							+ "alert('아이디 및 패스워드를 다시 확인하세요.');"
							+ "history.go(-1);"
							+ "</script>");
				}
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			this.pw.close();
		}
		
		return null;
	}
	
	
	
	
	
	@GetMapping("/coupon_delok.do")
	public void coupon_deleteok(@RequestParam(value = "",required = false)int cidx, HttpServletResponse res) {
		res.setContentType("text/html;charset=utf-8");
		try {		
			this.pw = res.getWriter();
			int result = couponService.delete_service(cidx);
			if(result>0) {
				this.pw.print("<script>"
						+ "alert('정상적으로 삭제되었습니다.');"
						+ "location.href='./list.do';"
						+ "</script>");			
			}else {
				this.pw.print("<script>"
						+ "alert('데이터 오류로 인하여 삭제가 되지 않았습니다.');"
						+ "history.go(-1);"
						+ "</script>");					
			}
					
		}catch(Exception e) {
			
		}finally {
			this.pw.close();
		}
		
	}
	
	@PostMapping("/coupon_insertok.do")
	public void coupon_insertok(@ModelAttribute("cp")coupon_dao dao,HttpServletResponse res) {
		res.setContentType("text/html;charset=utf-8");
		try {
			
			this.pw = res.getWriter();
			
			int result = couponService.insert_service(dao);
			if(result>0) {
				this.pw.print("<script>"
						+ "alert('정상적으로 등록 완료되었습니다.');"
						+ "location.href='./list.do';"
						+ "</script>");			
			}else {
				this.pw.print("<script>"
						+ "alert('데이터 오류로 인하여 정보가 저장되지 않았습니다.');"
						+ "history.go(-1);"
						+ "</script>");					
			}
			
		}catch(Exception e) {
			
		}finally {
			this.pw.close();
		}
		
	}

	@GetMapping("/list.do")
	public String coupon_list(Model m) {
		List<coupon_dao> all = couponService.getAllcoupon();
		//System.out.println("EA: "+ all.size());
		int ea = all.size();
		m.addAttribute("ea", ea);
		m.addAttribute("all",all);
		return null;
	}
	
	@GetMapping("/index.do")
	public String index(Model m) {
		String username = "홍길동";
		m.addAttribute("username", username);
		return null;
	}
}
