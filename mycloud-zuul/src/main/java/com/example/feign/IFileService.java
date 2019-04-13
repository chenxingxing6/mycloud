package com.example.feign;

import com.example.entity.ShareEntity;
import com.example.entity.SysUserEntity;
import com.example.vo.FileVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * User: lanxinghua
 * Date: 2019/4/12 13:07
 * Desc: 文件操作
 */

@FeignClient(name = "service")
public interface IFileService {
    /**
     * 文件重命名
     * @param fileId
     * @param fileName
     * @return
     */
    @RequestMapping(value = "/front/app/fileRename")
    boolean fileRename(@RequestParam("fileId") String fileId,
                       @RequestParam("fileName") String fileName,
                       @RequestParam("optUserId") String optUserId);

    /**
     * 我的文件列表
     * @param userId
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/front/app/listFileByPage")
    List<FileVo> listFileByPage(@RequestParam("userId") String userId,
                            @RequestParam("fileParentId") String fileParentId,
                            @RequestParam("page") Integer page,
                            @RequestParam("limit") Integer limit);

    /**
     * 获取总页数
     * @param userId
     * @param fileParentId
     * @return
     */
    @RequestMapping(value = "/front/app/listFileTotal")
    int listFileTotal(@RequestParam("userId") String userId,
                      @RequestParam("fileParentId") String fileParentId);


    /**
     * 下载文件，到本地
     * @param fileId
     * @return  originalName
     */
    @RequestMapping(value = "/front/app/downloadFile")
    String downloadLocal(@RequestParam("fileId") String fileId);

}
