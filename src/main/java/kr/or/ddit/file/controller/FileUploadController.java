package kr.or.ddit.file.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class FileUploadController {
	@RequestMapping(value="/file/upload.do", method=RequestMethod.POST)
//	@PostMapping("/file/upload.do")
	//파일업로드할려면 버전을 체크해야함 시간상 3.으로 통일
	public String upload(HttpServletRequest req, HttpSession session) throws IOException, ServletException { 
		//없던 파라미터를 만들어줌
		String textPart = req.getParameter("textPart");
		String numPart = req.getParameter("numPart");
		String datePart = req.getParameter("datePart");
		log.info("textPart : {}", textPart);
		log.info("numPart : {}", numPart);
		log.info("datePart : {}", datePart);
		session.setAttribute("textPart", textPart);
		session.setAttribute("numPart", numPart);
		session.setAttribute("datePart", datePart);
		
		String saveFolderURL = "/resources/prodImages";
		ServletContext application = req.getServletContext(); //어플리케이션 기본객체
		String saveFolderPath = application.getRealPath(saveFolderURL);
		File saveFolder = new File(saveFolderPath);
		if(!saveFolder.exists()) {
			saveFolder.mkdirs(); //mkdir가 아닌 mkdirs를 써야 계층 구조에 따라 쭉 만들어짐
		}
		
		List<String>metadata = req.getParts().stream() //여러파트
					.filter((p)->p.getContentType()!=null)
					.map((p)->{
						//원본 파일명으론 저장하면 안됨
						String originalFilename = p.getSubmittedFileName(); //원본파일명, 백도어 공격에 당할 수 있음
						String saveFilename = UUID.randomUUID().toString(); //파일명을 랜덤으로 바꿔줌
						File saveFile = new File(saveFolder, saveFilename);
						try {
							p.write(saveFile.getCanonicalPath());//saveFile은 문자열이 아니라 전체경로를 꺼내옴
							String saveFileURL = saveFolderURL + "/" + saveFilename;
							return saveFileURL; //문자열 반환
						} catch (IOException e) {
							throw new RuntimeException(e);
						} 
					}).collect(Collectors.toList()); //메타데이터 2개 생성
		
		session.setAttribute("fileMetadata", metadata);
		
		return "redirect:/fileupload/uploadForm.jsp"; //세션스코프로 보내줘야함
	}
}
