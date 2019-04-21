package com.example.feign;

import com.example.entity.SysNoticeEntity;
import com.example.entity.SysUserEntity;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * User: lanxinghua
 * Date: 2019/4/12 13:07
 * Desc: 通知
 */

@FeignClient(name = "service", url = "http://localhost:9000")
public interface INoticeService {

    /**
     * 获取系统通知
     * @param type
     * @return
     */
    @RequestMapping(value = "/front/app/listNotice")
    List<SysNoticeEntity> listNotice(@RequestParam("type") Integer type,
                                     @RequestParam("title") String title,
                                     @RequestParam("date") String date);


}
