package kr.or.ddit.prod.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.resolvers.RequestPart;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.mvc.multipart.MultipartFile;
import kr.or.ddit.mvc.multipart.MultipartHttpServletRequest;
import kr.or.ddit.prod.dao.OthersDAO;
import kr.or.ddit.prod.dao.OthersDAOImpl;
import kr.or.ddit.prod.service.ProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.ProdVO;

//  /prod/prodUpdate.do(GET,POST)
@Controller
public class ProdUpdateController {
	private ProdService service = new ProdServiceImpl();
	private OthersDAO othersDAO = new OthersDAOImpl();
	
	private void addAttribute(HttpServletRequest req) {
		req.setAttribute("lprodList", othersDAO.selectLprodList());
		req.setAttribute("buyerList", othersDAO.selectBuyerList(null));
	}
	
	@RequestMapping("/prod/prodUpdate.do")
	public String updateForm(
			HttpServletRequest req
			, @RequestParam("what") String prodId  //핸들러 어뎁터가 처리해줌
	) {
		addAttribute(req);
		
		ProdVO prod = service.retrieveProd(prodId); 
		req.setAttribute("prod", prod); 
		return "prod/prodForm";
	}
	
	@RequestMapping(value="/prod/prodUpdate.do",method=RequestMethod.POST)
	public String updateProd(
			@ModelAttribute("prod") ProdVO prod  //command Object 
			,HttpServletRequest req
			,@RequestPart(value="prodImage", required=false) MultipartFile prodImage
	) throws IOException {
		addAttribute(req);
		
		prod.setProdImage(prodImage);
		
		String saveFolderURL = "/resources/prodImages";
		ServletContext application = req.getServletContext(); //어플리케이션 기본객체
		String saveFolderPath = application.getRealPath(saveFolderURL);
		File saveFolder = new File(saveFolderPath);
		if(!saveFolder.exists()) {
			saveFolder.mkdirs(); 
		}
		prod.saveTo(saveFolder);
		
		String viewName = null;
		Map<String, List<String>> errors = new HashMap<>();
		req.setAttribute("errors", errors); //검증 실패시
		boolean valid = ValidationUtils.validate(prod, errors, UpdateGroup.class);
		if(valid) {
			ServiceResult result = service.modifyProd(prod);
			if(ServiceResult.OK == result) {
				viewName = "redirect:/prod/prodView.do?what="+prod.getProdId();
			}else {
				req.setAttribute("message", "서버 오류, 쫌따 다시");
				viewName = "prod/prodForm";
			}
		}else {
			viewName = "prod/prodForm";
		}
		return viewName;
	}
}
