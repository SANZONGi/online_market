package com.zjgsu.online_market.common.annotations;

import java.lang.annotation.*;
import java.lang.reflect.Type;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(ElementType.METHOD)
public @interface RoleRequired {
    boolean required() default false;
}
