package kr.or.ddit.member.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.mvc.view.InternalResourceViewResolver;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.MemberVO;

@Controller
public class MemberUpdateController{
	private MemberService  service = new MemberServiceImpl(); 
	
	@RequestMapping("/member/memberUpdate.do")
	public String updateForm(
			HttpSession session
//			, @SessionAttribute("authMember") MemberVO authMember
			,HttpServletRequest req
	) {
		MemberVO authMember = (MemberVO) session.getAttribute("authMember");
		
		MemberVO member = service.retrieveMember(authMember.getMemId());
		//모델로 공유
		req.setAttribute("member", member);
		//뷰 선택
		String viewName = "member/memberForm";
		
		return viewName;
	}
	
	@RequestMapping(value="/member/memberUpdate.do",method=RequestMethod.POST)
	public String updateProcess(
			HttpServletRequest req
			,@ModelAttribute("member") MemberVO member
			) {
		
		String viewName = null;
		
		Map<String, List<String>> errors = new LinkedHashMap<>(); 
		req.setAttribute("errors", errors);
		
		boolean valid = ValidationUtils.validate(member, errors, UpdateGroup.class);
		
		if(valid) {
			ServiceResult result = service.modifyMember(member);
			switch(result){
			case INVALIDPASSWORD:
				req.setAttribute("message", "비밀번호 오류");
				viewName = "member/memberForm";
				break;
			case FAIL:
				req.setAttribute("message", "서버 오류, 좀따 다시");
				viewName = "member/memberForm";
				break;
				
			default:
				viewName = "redirect:/mypage.do";
				break;
			}
		}else {
			viewName = "member/memberForm";
		}
		return viewName;
	}
}





