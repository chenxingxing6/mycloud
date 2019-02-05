package com.example.form;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 登录表单
 */
public class LoginForm {
    @NotBlank(message="账号不能为空")
    private String username;

    @NotBlank(message="密码不能为空")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
