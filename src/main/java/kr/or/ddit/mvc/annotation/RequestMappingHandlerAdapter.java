package kr.or.ddit.mvc.annotation;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.mvc.annotation.resolvers.HandlerMethodArgumentResolver;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttributeMethodProcessor;
import kr.or.ddit.mvc.annotation.resolvers.RequestParamMethodArgumentResolver;
import kr.or.ddit.mvc.annotation.resolvers.RequestParamMethodArgumentResolver.BadRequestExeption;
import lombok.extern.slf4j.Slf4j;
import kr.or.ddit.mvc.annotation.resolvers.ServletRequestMethodArgumentResolver;
import kr.or.ddit.mvc.annotation.resolvers.ServletResponseMethodArgumentResolver;

@Slf4j
public class RequestMappingHandlerAdapter implements HandlerAdapter {

	private List<HandlerMethodArgumentResolver> argumentResolvers;
	{
		argumentResolvers = new ArrayList<>();
		argumentResolvers.add(new ServletRequestMethodArgumentResolver());
		argumentResolvers.add(new ServletResponseMethodArgumentResolver());
		argumentResolvers.add(new RequestParamMethodArgumentResolver());
		argumentResolvers.add(new ModelAttributeMethodProcessor());
	}
	
	private HandlerMethodArgumentResolver findArgumentResolver(Parameter param) {
		HandlerMethodArgumentResolver finded = null;
		for(HandlerMethodArgumentResolver resolver : argumentResolvers) {
			if(resolver.supportsParameter(param)) {
				finded = resolver;
				break;
			}
		}
		return finded;
	}
	
	@Override
	public String invokeHandler(RequestMappingInfo mappingInfo, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Object handlerObject = mappingInfo.getCommandHandler();
		Method handlerMethod = mappingInfo.getHandlerMethod();
		int parameterCount = handlerMethod.getParameterCount();
		try {
			//캐스팅 할수 있는 이유는 리턴타입을 설정해놨기 때문
			//경우에따라 (handlerObject, req, resp)의 값을 받을 수 있게 만들어야함
			String viewName = null;
			if(parameterCount>0) {//한개 이상의 파라미터
				Parameter[] parameters = handlerMethod.getParameters();	
				Object[] arguments = new Object[parameterCount];
				for(int i=0; i<parameterCount; i++) {
					Parameter param = parameters[i];
					HandlerMethodArgumentResolver findedResolver = findArgumentResolver(param);
					if(findedResolver==null) {
						throw new RuntimeException(String.format("%s 타입의 메소드 인자는 현재 처리 가능한 resolver가 없음.", param.getType()));
					}else {
						arguments[i] = findedResolver.resolveArgument(param, req, resp);
					}
				}
				
				viewName = (String) handlerMethod.invoke(handlerObject,arguments);
			}else {
				viewName = (String) handlerMethod.invoke(handlerObject); 
			}
			return viewName;
		}catch(BadRequestExeption e) {
			log.error("handler adapter 가 handler method argument resolver 사용 중 문제 발생", e);
			resp.sendError(400, e.getMessage());
			return null;
		}catch(Exception e) {
			throw new ServletException(e);
		}
	}

	

}
