package com.example.controller;


import com.example.annotation.Login;
import com.example.common.utils.R;
import com.example.entity.SysNoticeEntity;
import com.example.feign.INoticeService;
import com.example.service.TokenService;
import com.example.vo.NoticeVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统通知
 */
@RestController
@RequestMapping("/api")
public class ApiNoticeController {
    @Autowired
    private INoticeService noticeService;

    @Login
    @RequestMapping("/getNotice")
    public R getNotice(){
        List<SysNoticeEntity> noticeEntities = noticeService.listNotice(0);
        Map<String, Object> map = new HashMap<>();
        map.put("list", cvtVos(noticeEntities));
        return R.ok().put("data", map);
    }

    private List<NoticeVo> cvtVos(List<SysNoticeEntity> entities){
        List<NoticeVo> list = new ArrayList<>();
        return list;
    }

}
