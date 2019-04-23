package com.example.feign;

import com.example.entity.ShareEntity;
import com.example.entity.SysUserEntity;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * User: lanxinghua
 * Date: 2019/4/12 13:07
 * Desc: 分享
 */

@FeignClient(name = "service", url = "http://localhost:9000")
public interface IShareService {

    /**
     * 分享列表
     * @param userId
     * @param type   0:我的分享  1:我收到的分享
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/front/app/listShare")
    List<ShareEntity> listShare(@RequestParam("userId") String userId,
                                @RequestParam("type") String type,
                                @RequestParam("page") Integer page,
                                @RequestParam("limit") Integer limit);

    @RequestMapping(value = "/front/app/listShareTotal")
    int listShareTotal(@RequestParam("userId") String userId,
                                @RequestParam("type") String type);


    /**
     * 分享给用户
     * @param fromUserId
     * @param toUserId
     * @param fileId
     * @return
     */
    @RequestMapping(value = "/front/app/toShare")
    void toShare(@RequestParam("fromUserId") String fromUserId,
                @RequestParam("toUserId") String toUserId,
                @RequestParam("fileId") String fileId);


    /**
     * 删除分享
     * @param shareId
     * @return
     */
    @RequestMapping(value = "/front/app/delByShareId")
    void delByShareId(@RequestParam("shareId") String shareId);


}
