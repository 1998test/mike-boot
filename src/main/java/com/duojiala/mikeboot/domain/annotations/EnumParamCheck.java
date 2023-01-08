package com.duojiala.mikeboot.domain.annotations;

import com.duojiala.mikeboot.utils.EnumUtil;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EnumParamCheck.DecimalParamCheckValidator.class)
public @interface EnumParamCheck {

    String message() default "枚举类型参数格式错误";

    boolean isEmpty() default true;

    Class<? extends Enum<?>> enumClass(); // 枚举的class

    String enumMethod() default "getType"; // 枚举值的getter

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class DecimalParamCheckValidator implements ConstraintValidator<EnumParamCheck, Object> {

        private EnumParamCheck constraintAnnotation;

        @Override
        public void initialize(EnumParamCheck constraintAnnotation) {
            this.constraintAnnotation = constraintAnnotation;
        }

        @Override
        public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
            if(null == o) {
                return constraintAnnotation.isEmpty();
            }else {
                Enum<?> anEnum = EnumUtil.valueOf(constraintAnnotation.enumClass(), o, constraintAnnotation.enumMethod());
                return anEnum != null;
            }
        }
    }

}

