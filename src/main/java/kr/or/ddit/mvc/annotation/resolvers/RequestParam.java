package kr.or.ddit.mvc.annotation.resolvers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 요청 파라미터(request parameter) 중 특정 파라미터(value) 하나의 값을 획득하기 위한 설정.
 * ex) @RequestParam("who") : request.getParameter("who"), req.getParameterValues("who")
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface RequestParam {
	String value(); //파라미터 이름
	boolean required() default true; //해당 파라미터가 필수인지 여부
	String defaultValue() default ""; //파라미터 값이 없을시 기본값
}
