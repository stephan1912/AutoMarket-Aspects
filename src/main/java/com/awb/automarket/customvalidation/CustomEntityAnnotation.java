package com.awb.automarket.customvalidation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CustomEntityAnnotation {
    public String fieldName() default "";
    public boolean required() default false;
    public int min() default 0;
    public int max() default 0;
}
