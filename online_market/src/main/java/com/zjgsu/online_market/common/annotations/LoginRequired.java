package com.zjgsu.online_market.common.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(ElementType.METHOD)
public @interface LoginRequired {
    boolean required() default false;
}
