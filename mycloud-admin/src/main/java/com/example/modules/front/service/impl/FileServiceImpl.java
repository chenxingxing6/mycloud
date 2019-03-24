package com.example.modules.front.service.impl;

import com.example.common.constants.FileEnum;
import com.example.common.utils.IdGen;
import com.example.modules.front.dao.HdfsDao;
import com.example.modules.front.entity.UserFileEntity;
import com.example.modules.front.service.UserFileService;
import com.example.modules.front.vo.FileVo;
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
    @Resource
    private IdGen idGen;
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
        List<FileEntity> fileEntities = this.selectBatchIds(fileIds);
        return fileEntities.stream().filter(e->e.getParentId().equals(parentId)).collect(Collectors.toList());
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
    public void deleteFileRecursion(SysUserEntity user, FileEntity file) {
        if (file.getType().equals(FileEnum.FILE.getType())){
            this.deleteById(file.getId());
            userFileService.delete(new EntityWrapper<UserFileEntity>().eq("user_id", user.getUserId()).and().eq("file_id", file.getId()));
            return;
        }
        //文件夹
        else if (file.getType().equals(FileEnum.FOLDER.getType())){
            //获取该文件下的子文件
            List<FileEntity> fileEntities = getFileList(user, file.getId());
            //该目录下没有文件，删除目录
            if (CollectionUtils.isEmpty(fileEntities)){
                this.deleteById(file.getId());
                userFileService.delete(new EntityWrapper<UserFileEntity>().eq("user_id", user.getUserId()).and().eq("file_id", file.getId()));
                return;
            }
            for (FileEntity subFile : fileEntities) {
                if (subFile.getType().equals(FileEnum.FILE)){
                    deleteFileRecursion(user, subFile);
                }
                this.deleteById(subFile.getId());
                userFileService.delete(new EntityWrapper<UserFileEntity>().eq("user_id", user.getUserId()).and().eq("file_id", subFile.getId()));
            }
        }
    }
}
