package kr.or.ddit.login.service;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.UserNotFoundException;
import kr.or.ddit.member.dao.MemberDAO;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.vo.MemberVO;

public class AuthenticateServiceImpl implements AuthenticateService {
	private MemberDAO memberDAO = new MemberDAOImpl();
	PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder(); //비밀번호 암호화
	
	@Override
	public ServiceResult authenticate(MemberVO member) {  //입력한 아이디 비밀번호가 저장된 객체 받아옴
		MemberVO savedMember = memberDAO.selectMember(member.getMemId()); //아이디에 맞는 회원 검색후 저장
		if(savedMember==null || savedMember.isMemDelete())  //삭제되어 존재하지 않을때
			throw new UserNotFoundException(String.format("%s 사용자 없음.", member.getMemId()));
		String inputPass = member.getMemPass();  //입력받은 비밀번호 
		String savedPass = savedMember.getMemPass(); //입력받은 아이디의 비밀번호
		ServiceResult result = null;
		if(encoder.matches(inputPass, savedPass)) { //입력받는 비번과 암호화된 비번이 같으면 true
			
//			member.setMemName(savedMember.getMemName());
			try {
				//copyProperties : 원본 객체를 복사
				BeanUtils.copyProperties(member, savedMember);//(원본객체, 복사할 객체)
				result = ServiceResult.OK;  //검증 완료
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
			
			
		}else {
			result = ServiceResult.INVALIDPASSWORD; //비밀번호가 다름
		}
		return result;
	}

}
