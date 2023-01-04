package kr.or.ddit.mvc.annotation;

import java.lang.reflect.Method;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
//equals가 필요없는 이유는 value로 들어가기때문
public class RequestMappingInfo {
	private RequestMappingCondition mappingCondition; //url, method
	private Object commandHandler; //컨트롤러 객체 , 매개변수를 지웠기에 object
	private Method handlerMethod; //메서드 정보
	
	public RequestMappingInfo(RequestMappingCondition mappingCondition, Object commandHandler, Method handlerMethod) {
		super();
		this.mappingCondition = mappingCondition;
		this.commandHandler = commandHandler;
		this.handlerMethod = handlerMethod;
	}
	
	
}
