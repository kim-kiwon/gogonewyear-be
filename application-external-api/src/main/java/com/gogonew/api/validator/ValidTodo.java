package com.gogonew.api.validator;

import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Target({ElementType.TYPE_USE, ElementType.FIELD})
@Constraint(validatedBy={})
@Retention(RUNTIME)
@NotBlank(message = "목표를 작성해주세요.")
@Size(max = 100, message = "목표는 100자 이내로 작성해주세요.")
public @interface ValidTodo {
	String message() default "잘못된 형태의 Todo 입니다";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}