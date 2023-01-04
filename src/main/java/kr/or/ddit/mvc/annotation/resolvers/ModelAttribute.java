package kr.or.ddit.mvc.annotation.resolvers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 핸들러 메소드의 인자 하나를 Command Object 로 받을때 사용.
 * 파라미터의 값들을 Getter, Setter, 생성자를  통해 주입하기 위해 사용
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ModelAttribute {
	String value() default "";
}
