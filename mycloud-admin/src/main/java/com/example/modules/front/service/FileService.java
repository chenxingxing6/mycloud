package com.example.modules.front.service;

import com.baomidou.mybatisplus.service.IService;
import com.example.common.utils.PageUtils;
import com.example.modules.front.entity.FileEntity;

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
}

