package com.example.modules.front.service.impl;

import com.example.common.constants.FileEnum;
import com.example.common.utils.IdGen;
import com.example.modules.front.dao.HdfsDao;
import com.example.modules.front.entity.UserFileEntity;
import com.example.modules.front.service.UserFileService;
import com.example.modules.sys.entity.SysUserEntity;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.common.utils.PageUtils;
import com.example.common.utils.Query;

import com.example.modules.front.dao.FileDao;
import com.example.modules.front.entity.FileEntity;
import com.example.modules.front.service.FileService;

import javax.annotation.Resource;


@Service("fileService")
public class FileServiceImpl extends ServiceImpl<FileDao, FileEntity> implements FileService {
    final static IdGen idGen = IdGen.get();
    @Resource
    private UserFileService userFileService;
    @Resource
    private HdfsDao hdfsDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<FileEntity> page = this.selectPage(
                new Query<FileEntity>(params).getPage(),
                new EntityWrapper<FileEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<FileEntity> getFileList(SysUserEntity user, long parentId) {
        List<UserFileEntity> userFileEntities = userFileService.getFilesByUserId(user.getUserId());
        if (CollectionUtils.isEmpty(userFileEntities)){
            return new ArrayList<>();
        }
        List<Long> fileIds = userFileEntities.stream().map(e->e.getFileId()).collect(Collectors.toList());
        return this.selectBatchIds(fileIds);
    }

    @Override
    public void makeFolder(FileEntity file, SysUserEntity user, long parentid) {
        //用户文件关联表
        UserFileEntity userFileEntity = new UserFileEntity();
        userFileEntity.setId(idGen.nextId());
        userFileEntity.setFileId(file.getId());
        userFileEntity.setUserId(user.getUserId());
        userFileService.insert(userFileEntity);

        //hdfs上传文件
        hdfsDao.mkDir(file, user);

        //文件表
        this.insert(file);
    }


    @Override
    public void deleteHdfs(SysUserEntity user, FileEntity file) {
        hdfsDao.delete(file, user);
    }

    @Override
    public void deleteFileRecursion(SysUserEntity user, FileEntity file, long parentid) {
        //文件夹
        if (file.getType().equals(FileEnum.FOLDER.getType())){
            List<UserFileEntity> userFileEntities = userFileService.getFilesByUserId(user.getUserId());
            if (CollectionUtils.isEmpty(userFileEntities)){
                return;
            }
            List<Long> fileIds = userFileEntities.stream().map(e->e.getFileId()).collect(Collectors.toList());
            //查询该目录下的子文件
            List<FileEntity> files = this.selectBatchIds(fileIds);
            for (FileEntity fileEntity : files) {
                if (fileEntity.getParentId().equals(parentid)){
                    deleteFileRecursion(user, file, parentid);
                }
            }

        }
        //文件
        this.deleteById(file.getId());
        userFileService.delete(new EntityWrapper<UserFileEntity>()
        .eq("user_id", user.getUserId()).and().eq("file_id", file.getId()));
    }
}
