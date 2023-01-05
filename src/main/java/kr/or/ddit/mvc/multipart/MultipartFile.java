package kr.or.ddit.mvc.multipart;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface MultipartFile {
	public byte[] getBytes() throws IOException;
	public String getContentType();
	public InputStream getInputStream() throws IOException;
	public String getName();
	public String getOriginalFilename();
	public long getSize();
	public boolean isEmpty(); //파트가 비어있는지 확인
	public void transferTo(File dest) throws IOException;
	
}
