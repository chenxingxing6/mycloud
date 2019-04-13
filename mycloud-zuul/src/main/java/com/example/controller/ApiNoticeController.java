package com.example.controller;


import com.example.annotation.Login;
import com.example.common.utils.DateUtils;
import com.example.common.utils.R;
import com.example.entity.SysNoticeEntity;
import com.example.feign.INoticeService;
import com.example.service.TokenService;
import com.example.utils.MapGet;
import com.example.vo.NoticeVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private final static String notice_img_url = "http://ppkn5nh6t.bkt.clouddn.com/upload/20190413/fe9a2bfe649748dbbe36706dd75afc31.jpeg";

    @Login
    @RequestMapping("/getNotice")
    public R getNotice(@RequestParam Map<String, Object> params){
        String title = MapGet.getByKey("title", params);
        String date = MapGet.getByKey("date", params);
        List<SysNoticeEntity> noticeEntities = noticeService.listNotice(null, title, date);
        Map<String, Object> map = new HashMap<>();
        List<NoticeVo> noticeVos = cvtVos(noticeEntities);
        map.put("list", noticeVos);
        map.put("total", noticeVos.size());
        return R.ok().put("data", map);
    }

    private List<NoticeVo> cvtVos(List<SysNoticeEntity> entities){
        List<NoticeVo> list = new ArrayList<>();
        for (SysNoticeEntity entity : entities) {
            NoticeVo vo = new NoticeVo();
            vo.setId(entity.getId().toString());
            vo.setTitle(entity.getNoticeTitle());
            vo.setContent(entity.getNoticeContent());
            vo.setCreateUser(entity.getCreateUser());
            vo.setCreateTime(DateUtils.format(entity.getCreateTime(), DateUtils.DATE_TIME_PATTERN));
            vo.setImg(notice_img_url);
            list.add(vo);
        }
        return list;
    }

}
