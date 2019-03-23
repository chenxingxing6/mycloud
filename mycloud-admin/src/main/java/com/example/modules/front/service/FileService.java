package com.example.modules.front.service;

import com.baomidou.mybatisplus.service.IService;
import com.example.common.constants.FileEnum;
import com.example.common.utils.PageUtils;
import com.example.modules.front.entity.FileEntity;
import com.example.modules.sys.entity.SysUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 用户与文件对应关系
 *
 * @author lanxinghua
 * @email lanxinghua@2dfire.com
 * @date 2019-03-17 21:38:15
 */
public interface FileService extends IService<FileEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 获取文件列表，查看文件或目录列表
     *
     * @param user
     * @param parentId
     * @return
     */
    public List<FileEntity> getFileList(SysUserEntity user, long parentId);


    /**
     * 创建文件夹
     * @param file
     * @param user
     * @param parentid
     */
    public void makeFolder(FileEntity file, SysUserEntity user, long parentid);

    /**
     * 删除hdfs中的文件，删除文件或目录时使用
     * @param user
     * @param file
     */
    public void deleteHdfs(SysUserEntity user, FileEntity file);

    /**
     * 递归删除file表和user_file表的文件信息，删除文件或目录时使用
     * @param user
     * @param file
     * @param parentid
     */
    public void deleteFileRecursion(SysUserEntity user, FileEntity file, long parentid);

}

