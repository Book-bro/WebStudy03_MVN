package kr.or.ddit.validate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;

public class ValidationUtils {
	private static Validator validator; 
	
	static {
		String messageBaseName = "kr.or.ddit.msgs.errorMessage";
		ValidatorFactory factory = Validation.byDefaultProvider()
						        .configure()
						        .messageInterpolator(
						                new ResourceBundleMessageInterpolator(
						                        new PlatformResourceBundleLocator(messageBaseName)
						                )
						        )
						        .buildValidatorFactory();
        
		validator = factory.getValidator();
	}
	
	public static <T> boolean validate(T target, Map<String, List<String>> errors, Class<?>...groups) {
		boolean valid = true;
		
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(target, groups);
		
		valid = constraintViolations.isEmpty();
		
		if(!valid) {
			constraintViolations.stream()
								.forEach(single->{
									String propertyName = single.getPropertyPath().toString();
									String errorMessage = single.getMessage();
									List<String> errorMessages = Optional.ofNullable(errors.get(propertyName)) //있는지 없는지 확인
																		.orElse(new ArrayList<>());
									errorMessages.add(errorMessage);
									
									errors.put(propertyName,errorMessages);
								});
		}
		
		return valid;
	}
}


