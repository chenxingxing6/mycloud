package com.example.controller;


import com.example.annotation.Login;
import com.example.annotation.LoginUser;
import com.example.common.utils.R;
import com.example.common.validator.Assert;
import com.example.common.validator.ValidatorUtils;
import com.example.entity.FileEntity;
import com.example.entity.SysUserEntity;
import com.example.feign.IFileService;
import com.example.form.LoginForm;
import com.example.service.TokenService;
import com.example.utils.MapGet;
import io.swagger.models.auth.In;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户Controller
 */
@RestController
@RequestMapping("/api")
public class ApiUserController {
    @Autowired
    private IFileService fileService;

    /**
     * 获取我的网盘
     * @param user
     * @param params
     * @return
     */
    @Login
    @RequestMapping("/getFiles")
    public R getSource(@LoginUser SysUserEntity user, @RequestParam Map<String, Object> params){
        String fileParentId = MapGet.getByKey("parentId", params);
        fileParentId = StringUtils.isEmpty(fileParentId) ? "0" : fileParentId;
        //当前页
        String page = MapGet.getByKey("page", params);
        //每页大小
        String limit = MapGet.getByKey("pageSize", params);
        Assert.isNull(user, "用户信息缺失");
        List<FileEntity> fileEntities = fileService.listFileByPage(user.getUserId().toString(), fileParentId, Integer.valueOf(page), Integer.valueOf(limit));
        int total = fileService.listFileTotal(user.getUserId().toString(), fileParentId);
        Map<String, Object> map = new HashMap<>();
        map.put("list", fileEntities);
        return R.ok().put("data", map).put("page", page).put("total", total);
    }

}
