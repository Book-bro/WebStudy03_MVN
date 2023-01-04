package kr.or.ddit.memo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import kr.or.ddit.memo.dao.DataBaseMemoDAOImpl;
import kr.or.ddit.memo.dao.MemoDAO;
import kr.or.ddit.memo.dao.MemoDAOImpl;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.vo.MemoVO;

@Controller
public class MemoController{
	private static final Logger log = LoggerFactory.getLogger(MemoController.class);
	
//	private MemoDAO dao = FileSystemMemoDAOImpl.getInstance();
//	private MemoDAO dao = DataBaseMemoDAOImpl.getInstance();
	private MemoDAO dao = new MemoDAOImpl();
	
	
	@RequestMapping("/memo")
	public String doGet(
//			@RequestHeader("Accept") String accept
			HttpServletRequest req
			, HttpServletResponse resp
	) throws ServletException, IOException {
		String accept = req.getHeader("Accept");
		log.info("accept header : {}", accept);
		if(accept.contains("xml")) {
			resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return null;
		}
		
		List<MemoVO> memoList = dao.selectMemoList();
		req.setAttribute("memoList", memoList);
		String viewName = "forward:/jsonView.do";
		return viewName;
	}
	
	@RequestMapping(value="/memo", method=RequestMethod.POST)
	public String doPost(
			HttpServletRequest req
			) throws ServletException, IOException {
//		Post-Redirect-Get : PRG pattern
		MemoVO memo = getMemoFromRequest(req);
		dao.insertMemo(memo);
		return "redirect:/memo"; //다시 돌아가서 리스트 갱신
	}
	
	private MemoVO getMemoFromRequest(HttpServletRequest req) throws IOException{
		String contentType = req.getContentType();
		MemoVO memo = null;
		if(contentType.contains("json")) {
			try(
					BufferedReader br = req.getReader(); //body content read 용 입력 스트림
					){
				memo = new ObjectMapper().readValue(br, MemoVO.class);
			}
			
		}else if(contentType.contains("xml")) {
			try(
					BufferedReader br = req.getReader(); //body content read 용 입력 스트림
					){
				memo = new XmlMapper().readValue(br, MemoVO.class);
			}
		}else {
			memo = new MemoVO();
			memo.setWriter(req.getParameter("writer"));
			memo.setDate(req.getParameter("date"));
			memo.setContent(req.getParameter("content"));
		}
		return memo;
		
//		MemoVO memo = new MemoVO();
//		memo.setWriter(req.getParameter("writer"));
//		memo.setDate(req.getParameter("date"));
//		memo.setContent(req.getParameter("content"));
		
//		try(
//				BufferedReader br = req.getReader();
//				){
//			String tmp = null;
//			StringBuffer strJson = new StringBuffer();
//			while((tmp=br.readLine()) != null) {
//				strJson.append(tmp);
//			}
//			ObjectMapper mapper = new ObjectMapper();
//			memo = mapper.readValue(strJson.toString(), MemoVO.class);
//			
//		}catch(IOException e) {
//			
//		}
//		return memo;
	}

	@RequestMapping(value="/memo", method=RequestMethod.PUT)
	public String doPut(HttpServletRequest req) throws ServletException, IOException {
		return null;
	}
	
	@RequestMapping(value="/memo", method=RequestMethod.DELETE)
	public String doDelete(HttpServletRequest req) throws ServletException, IOException {
		return null;
		
	}
}
