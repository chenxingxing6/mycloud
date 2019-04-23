package com.example.feign;

import com.example.entity.DiskFileEntity;
import com.example.entity.FileEntity;
import com.example.entity.ShareEntity;
import com.example.entity.SysUserEntity;
import com.example.vo.FileVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * User: lanxinghua
 * Date: 2019/4/12 13:07
 * Desc: 文件操作
 */

@FeignClient(name = "service", url = "http://localhost:9000")
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
    List<FileEntity> listFileByPage(@RequestParam("userId") String userId,
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

    /**
     * 删除文件
     * @param fileId
     * @return
     */
    @RequestMapping(value = "/front/app/delFileById")
    void delFileById(@RequestParam("userId") String userId,
                       @RequestParam("fileId") String fileId);


    /**
     * 添加到企业网盘
     * @param fileId
     * @return
     */
    @RequestMapping(value = "/front/app/addDisk")
    void addDisk(@RequestParam("userId") String userId,
                       @RequestParam("fileId") String fileId);


    /**
     * @param fileId
     * @return
     */
    @RequestMapping(value = "/front/app/getDiskFileByFileId")
    DiskFileEntity getDiskFileByFileId(@RequestParam("fileId") String fileId);


    /**
     * @param fileId
     * @return
     */
    @RequestMapping(value = "/front/app/getFileByFileId")
    FileEntity getFileByFileId(@RequestParam("fileId") String fileId);

}
