package kr.or.ddit.mvc.multipart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Part -> MultipartFile로 바꿈
 *
 */
public class MultipartHttpServletRequest extends HttpServletRequestWrapper{

	private Map<String, List<MultipartFile>> fileMap;
	
	public MultipartHttpServletRequest(HttpServletRequest request) throws IOException, ServletException { //필터로 예외가 넘어감
		super(request);
		parseRequest(request);
	}

	private void parseRequest(HttpServletRequest request) throws IOException, ServletException {//부모로 예외 보냄
		fileMap = new LinkedHashMap<>();
		request.getParts().stream()
					.filter((p)->p.getContentType()!=null)
					.forEach((p)->{
						String partName = p.getName();
						MultipartFile file = new StandardServletMultipartFile(p);
						List<MultipartFile> fileList = Optional.ofNullable(fileMap.get(partName)) //있을수도 있고 없을수도 있다
															.orElse(new ArrayList<>());//없으면
						fileList.add(file);
						fileMap.put(partName, fileList);
					});
	}

	public Map<String, List<MultipartFile>> getFileMap() {
		return fileMap;
	}
	
	public MultipartFile getFile(String name){ //파트이름
		List<MultipartFile> files = fileMap.get(name);
		if(files!=null && !files.isEmpty())
			return files.get(0);
		else return null;
	}
	
	public List<MultipartFile> getFiles(String name){
		return fileMap.get(name);
	}
	
	public Enumeration<String> getFileNames(){
		Iterator<String> names = fileMap.keySet().iterator(); 
		return new Enumeration<String>() {

			@Override
			public boolean hasMoreElements() { //hasnext
				return names.hasNext();
			}

			@Override
			public String nextElement() { //next
				return names.next();
			}
			
		};
	}

}
