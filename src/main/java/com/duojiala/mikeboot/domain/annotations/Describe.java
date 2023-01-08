package com.duojiala.mikeboot.domain.annotations;

import java.lang.annotation.*;

/**
 * 自定义描述注解
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Describe {
    String desc();
}
