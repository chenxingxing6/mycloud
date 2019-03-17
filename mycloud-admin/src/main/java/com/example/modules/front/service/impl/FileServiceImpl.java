package com.example.modules.front.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.common.utils.PageUtils;
import com.example.common.utils.Query;

import com.example.modules.front.dao.FileDao;
import com.example.modules.front.entity.FileEntity;
import com.example.modules.front.service.FileService;


@Service("fileService")
public class FileServiceImpl extends ServiceImpl<FileDao, FileEntity> implements FileService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<FileEntity> page = this.selectPage(
                new Query<FileEntity>(params).getPage(),
                new EntityWrapper<FileEntity>()
        );

        return new PageUtils(page);
    }

}
