package kr.or.ddit.prod.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.prod.dao.OthersDAO;
import kr.or.ddit.prod.dao.OthersDAOImpl;
import kr.or.ddit.prod.service.ProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;

@Controller
public class ProdListController{
	private ProdService service = new ProdServiceImpl();
	private OthersDAO othersDAO = new OthersDAOImpl();
	
	private void addAttribute(HttpServletRequest req) {
		req.setAttribute("lprodList", othersDAO.selectLprodList());
		req.setAttribute("buyerList", othersDAO.selectBuyerList(null));
	}
	
	private String listUI(HttpServletRequest req) {
		addAttribute(req);
		return "prod/prodList";
	}
	private String listData(
		int currentPage
		, ProdVO detailCondition
		, HttpServletRequest req
	) throws ServletException {
//		String pageParam = req.getParameter("page");
//		ProdVO detailCondition = new ProdVO();
//		req.setAttribute("detailCondition", detailCondition);
//		detailCondition.setProdLgu(req.getParameter("prodLgu"));
//		detailCondition.setProdBuyer(req.getParameter("prodBuyer"));
//		detailCondition.setProdName(req.getParameter("prodName"));
		
//		try {
//			BeanUtils.populate(detailCondition, req.getParameterMap());
//		} catch (IllegalAccessException | InvocationTargetException e) {
//			throw new ServletException(e);
//		}
		
//		int currentPage = 1;
//		if(StringUtils.isNumeric(pageParam)) {
//			currentPage = Integer.parseInt(pageParam);
//		}
		
		PagingVO<ProdVO> pagingVO = new PagingVO<>(5, 2);
		pagingVO.setCurrentPage(currentPage);
		pagingVO.setDetailCondition(detailCondition);
		
		service.retrieveProdList(pagingVO);
		req.setAttribute("pagingVO", pagingVO);
		return "forward:/jsonView.do";
		
	}
	
	@RequestMapping("/prod/prodList.do")
	public String prodList(
		@RequestParam(value="page", required=false, defaultValue="1") int currentPage
		, @ModelAttribute("detailCondition") ProdVO detailCondition
		, HttpServletRequest req
	) throws ServletException{
		String accept = req.getHeader("Accept");
		String viewName = null;
		
		if(accept.contains("json")) {
			viewName = listData(currentPage, detailCondition, req);
		}else {
			viewName = listUI(req);
		}
		
		return viewName;
		
	}
}















