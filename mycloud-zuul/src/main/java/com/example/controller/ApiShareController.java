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
import com.example.utils.RedisUtil;
import com.example.vo.ShareVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 分享Controller
 */
@RestController
@RequestMapping("/api")
public class ApiShareController {
    @Autowired
    private IShareService shareService;
    @Autowired
    private RedisUtil redisUtil;


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
        map.put("total", total);
        return R.ok().put("data", map).put("page", page);
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

    @Login
    @RequestMapping("/toShare")
    public R toShare(@LoginUser SysUserEntity user, @RequestParam Map<String, Object> params){
        String toUserId = MapGet.getByKey("userId", params);
        String fileId = MapGet.getByKey("fileId", params);
        Assert.isNull(user, "用户信息缺失");
        Assert.isBlank(toUserId, "分享用户不能为空");
        shareService.toShare(user.getUserId().toString(), toUserId, fileId);
        return R.ok();
    }


    /**
     * 创建链接
     * @param user
     * @param params
     * @return
     */
    @Login
    @RequestMapping("/createLink")
    public R createLink(@LoginUser SysUserEntity user, @RequestParam Map<String, Object> params){
        String fileId = MapGet.getByKey("fileId", params);
        Assert.isNull(user, "用户信息缺失");
        String code = getCode(user.getUserId().toString());
        Map<String, Object> map = new HashMap<>();
        map.put("fromUserId", user.getUserId().toString());
        map.put("fileId", user.getUserId().toString());
        redisUtil.setObject(code, map, 60*60*24);
        return R.ok().put("data", code);
    }

    private String getCode(String userId) {
        return UUID.randomUUID().toString().replace("-", "") + userId;
    }

    /**
     * 读取链接
     * @param user
     * @param code
     * @return
     */
    @Login
    @RequestMapping("/readLink/{code}")
    public R readLink(@LoginUser SysUserEntity user, @PathVariable String code){
        Assert.isNull(user, "用户信息缺失");
        Assert.isBlank(code, "链接无效");
        Object object = redisUtil.get(code);
        if (object == null){
            return R.ok("链接失效!");
        }
        Map<String, Object> map = (Map) object;
        String fromUserId = map.get("fromUserId").toString();
        String fileId = map.get("fileId").toString();
        shareService.toShare(fromUserId, user.getUserId().toString(), fileId);
        return R.ok();
    }

    /**
     * 删除
     * @param params
     * @return
     */
    @Login
    @RequestMapping("/delByShareId")
    public R delByShareId(@RequestParam Map<String, Object> params){
        String shareId = MapGet.getByKey("shareId", params);
        Assert.isBlank(shareId, "参数错误");
        shareService.delByShareId(shareId);
        return R.ok();
    }
}
