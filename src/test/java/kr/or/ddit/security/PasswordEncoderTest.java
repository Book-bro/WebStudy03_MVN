package kr.or.ddit.security;

import org.junit.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PasswordEncoderTest {
	
	PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	
	String password = "java";
	String mem_pass = "{bcrypt}$2a$10$NGvpApSl4ZHga0O8QXu4L.uduceXxCukgMK.GaXe1PtUeQJEANm4q"; //단방향 암호화라 원본 복원이 안됨
	
	public void encodeTest() {
		String encoded = encoder.encode(password);
		log.info("mem_pass : {}", encoded);
	}
	
	@Test
	public void matchTest() {
//		String encoded = encoder.encode(password); //한번더 암호화 시켜서 기존 암호와 달라짐
//		log.info("match result : {}", encoded.equals(mem_pass));
		log.info("match result : {}",encoder.matches(password, mem_pass)); //입력한 비번, db에 저장되어있는 비번
	}
}
