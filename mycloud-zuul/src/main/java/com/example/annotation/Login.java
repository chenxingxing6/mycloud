package com.example.annotation;

import java.lang.annotation.*;

/**
 * 登录效验
 * @date 2019/2/05 14:30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {
}
