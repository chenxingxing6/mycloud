package com.example.controller;


import com.example.annotation.Login;
import com.example.annotation.LoginUser;
import com.example.common.utils.DateUtils;
import com.example.common.utils.R;
import com.example.common.validator.Assert;
import com.example.entity.ShareEntity;
import com.example.entity.SysUserEntity;
import com.example.feign.IShareService;
import com.example.utils.MapGet;
import com.example.vo.ShareVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分享Controller
 */
@RestController
@RequestMapping("/api")
public class ApiShareController {
    @Autowired
    private IShareService shareService;


    @Login
    @RequestMapping("/getShares")
    public R getShares(@LoginUser SysUserEntity user, @RequestParam Map<String, Object> params){
        String type = MapGet.getByKey("searchType", params);
        //当前页
        String page = MapGet.getByKey("page", params);
        //每页大小
        String limit = MapGet.getByKey("pageSize", params);
        Assert.isBlank(type, "参数错误");
        Assert.isNull(user, "用户信息缺失");
        List<ShareEntity> shareEntities = shareService.listShare(String.valueOf(user.getUserId()), type, Integer.valueOf(page), Integer.valueOf(limit));
        int total = shareService.listShareTotal(String.valueOf(user.getUserId()), type);
        Map<String, Object> map = new HashMap<>();
        map.put("list", cvtVos(shareEntities));
        return R.ok().put("data", map).put("page", page).put("total", total);
    }


    private List<ShareVo> cvtVos(List<ShareEntity> shareEntities){
        List<ShareVo> list = new ArrayList<>();
        for (ShareEntity shareEntity : shareEntities) {
            ShareVo vo = new ShareVo();
            vo.setId(shareEntity.getId().toString());
            vo.setFromUserId(shareEntity.getFromUserId().toString());
            vo.setFromUserName(shareEntity.getFromUserName());
            vo.setToUserId(shareEntity.getToUserId().toString());
            vo.setToUserName(shareEntity.getToUserName());
            vo.setCreateUser(shareEntity.getCreateUser());
            vo.setCreateTime(DateUtils.format(shareEntity.getCreateTime(), DateUtils.DATE_TIME_PATTERN));
            vo.setFileId(shareEntity.getFileId().toString());
            vo.setFileName(shareEntity.getFileName());
            vo.setExpiredTime(DateUtils.format(shareEntity.getExpiredTime(), DateUtils.DATE_TIME_PATTERN));
            list.add(vo);
        }
        return list;
    }
}
