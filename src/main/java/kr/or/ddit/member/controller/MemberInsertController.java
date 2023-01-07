package kr.or.ddit.member.controller;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestPart;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.mvc.multipart.MultipartFile;
import kr.or.ddit.mvc.multipart.MultipartHttpServletRequest;
import kr.or.ddit.mvc.view.InternalResourceViewResolver;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.MemberVO;

/**
 * Backend controller(command handler) --> Plain Old Java Object
 */
@Controller
public class MemberInsertController {
	private MemberService service = new MemberServiceImpl();
	
	//get 방식으로 들어옴
	@RequestMapping("/member/memberInsert.do")
	public String memberForm()  {
		return "member/memberForm";
	}
	
	//post 방식
	@RequestMapping(value="/member/memberInsert.do", method=RequestMethod.POST)
	public String memberForm(
			HttpServletRequest req
			, @ModelAttribute("member") MemberVO member
			, @RequestPart(value="memImage", required=false) MultipartFile memImage
	) throws ServletException, IOException {
		
		//blob방식
//		if(req instanceof MultipartHttpServletRequest) {  //요청이 원본인지 wrapper인지 판단
//			MultipartFile memImage = ((MultipartHttpServletRequest)req).getFile("memImage");
		member.setMemImage(memImage);
//		}
		
		Map<String, List<String>> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		
		boolean valid = ValidationUtils.validate(member, errors, InsertGroup.class);
		
		String viewName = null;
		
		if(valid) {
			ServiceResult result = service.createMember(member);
			switch (result) {
			case PKDUPLICATED:  //이미 있거나
				req.setAttribute("message", "아이디 중복");
				viewName = "member/memberForm";
				break;
			case FAIL:	//문제발생시
				req.setAttribute("message", "서버에 문제 있음. 쫌따 다시 하셈.");
				viewName = "member/memberForm";
				break;

			default:
				viewName = "redirect:/";
				break;
			}
		}else {
			viewName = "member/memberForm";
		}
		return viewName;
		
		
	}
}




















