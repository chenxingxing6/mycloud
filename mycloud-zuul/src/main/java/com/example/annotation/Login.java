package com.example.annotation;

import java.lang.annotation.*;

/**
 * User: lanxinghua
 * Date: 2019/1/22 15:02
 * Desc: 前端登陆校验
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {
}
