package com.server.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogMethodRecord {

    String value() default "";

    String uri() default "";

    String type() default "Mybatis-plus";

}
