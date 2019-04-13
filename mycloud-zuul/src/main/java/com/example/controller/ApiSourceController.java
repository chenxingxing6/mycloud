package com.example.controller;


import com.example.annotation.Login;
import com.example.annotation.LoginUser;
import com.example.common.utils.R;
import com.example.common.validator.Assert;
import com.example.entity.SysUserEntity;
import com.example.feign.IDiskService;
import com.example.service.TokenService;
import com.example.utils.MapGet;
import com.example.vo.DiskDirVo;
import com.example.vo.FileVo;
import com.example.vo.SourceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 企业网盘-资源库
 */
@RestController
@RequestMapping("/api")
public class ApiSourceController {
    @Autowired
    private IDiskService diskService;

    /**
     * 获取企业网盘资源
     * @param user
     * @param params
     * @return
     */
    @Login
    @RequestMapping("/getSource")
    public R getSource(@LoginUser SysUserEntity user, @RequestParam Map<String, Object> params){
        String diskId = MapGet.getByKey("type", params);
        //当前页
        String page = MapGet.getByKey("page", params);
        //每页大小
        String limit = MapGet.getByKey("limit", params);
        Assert.isBlank(diskId, "参数错误");
        Assert.isNull(user, "用户信息缺失");
        String userId = String.valueOf(user.getUserId());
        List<FileVo> fileVos = diskService.listDisk(userId, diskId, Integer.valueOf(page), Integer.valueOf(limit));
        int total = diskService.listDiskTotal(userId, diskId);
        Map<String, Object> map = new HashMap<>();
        map.put("list", cvtVos(fileVos));
        return R.ok().put("data", map).put("page", page).put("total", total);
    }

    private List<SourceVo> cvtVos(List<FileVo> fileVos){
        List<SourceVo> list = new ArrayList<>();
        return list;
    }


    /**
     * 获取企业网盘资源类型
     * @param user
     * @return
     */
    @Login
    @RequestMapping("/getSourceType")
    public R getSourceType(@LoginUser SysUserEntity user){
        String userId = String.valueOf(user.getUserId());
        String deptId = String.valueOf(user.getDeptId());
        List<DiskDirVo> diskDirVos = diskService.listDiskDirType(userId, deptId);
        Map<String, Object> map = new HashMap<>();
        map.put("list", diskDirVos);
        return R.ok().put("data", map);
    }

}
