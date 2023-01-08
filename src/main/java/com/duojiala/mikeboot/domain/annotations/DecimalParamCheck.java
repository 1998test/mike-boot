package com.duojiala.mikeboot.domain.annotations;

import com.duojiala.mikeboot.utils.StringTool;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DecimalParamCheck.DecimalParamCheckValidator.class)
public @interface DecimalParamCheck {

    String message() default "数值参数格式错误";

    boolean isEmpty() default true;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class DecimalParamCheckValidator implements ConstraintValidator<DecimalParamCheck, Object> {

        private DecimalParamCheck constraintAnnotation;

        @Override
        public void initialize(DecimalParamCheck constraintAnnotation) {
            this.constraintAnnotation = constraintAnnotation;
        }

        @Override
        public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
            if (o instanceof String) {
                return StringTool.isBigDecimal(o.toString());
            }
            if(constraintAnnotation.isEmpty()){
                return true;
            }
            return false;
        }
    }

}

