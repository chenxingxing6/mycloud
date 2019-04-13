package com.example.feign;

import com.example.vo.DiskDirVo;
import com.example.vo.FileVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * User: lanxinghua
 * Date: 2019/4/12 13:07
 * Desc: 企业网盘
 */

@FeignClient(name = "service")
public interface IDiskService {

    /**
     * 获取企业网盘资源
     * @param userId
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/front/app/listDisk")
    List<FileVo> listDisk(@RequestParam("userId") String userId,
                          @RequestParam("diskId") String diskId,
                           @RequestParam("page") Integer page,
                           @RequestParam("limit") Integer limit);

    /**
     * 获取总页数
     * @param userId
     * @param diskId
     * @return
     */
    @RequestMapping(value = "/front/app/listDiskTotal")
    int listDiskTotal(@RequestParam("userId") String userId,
                          @RequestParam("diskId") String diskId);

    /**
     * 获取企业网盘目录类型
     * @param userId
     * @param deptId
     * @return
     */
    @RequestMapping(value = "/front/app/listDiskDirType")
    List<DiskDirVo> listDiskDirType(@RequestParam("userId") String userId,
                                    @RequestParam("deptId") String deptId);
}
