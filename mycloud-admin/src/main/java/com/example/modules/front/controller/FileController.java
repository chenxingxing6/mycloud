package com.example.modules.front.controller;

import java.util.Arrays;
import java.util.Map;

import com.example.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.modules.front.entity.FileEntity;
import com.example.modules.front.service.FileService;
import com.example.common.utils.PageUtils;
import com.example.common.utils.R;



/**
 * 用户与文件对应关系
 *
 * @author lanxinghua
 * @email lanxinghua@2dfire.com
 * @date 2019-03-17 21:38:15
 */
@RestController
@RequestMapping("front/file")
public class FileController {
    @Autowired
    private FileService fileService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("front:file:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = fileService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("front:file:info")
    public R info(@PathVariable("id") Long id){
        FileEntity file = fileService.selectById(id);

        return R.ok().put("file", file);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("front:file:save")
    public R save(@RequestBody FileEntity file){
        fileService.insert(file);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("front:file:update")
    public R update(@RequestBody FileEntity file){
        ValidatorUtils.validateEntity(file);
        fileService.updateAllColumnById(file);//全部更新

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("front:file:delete")
    public R delete(@RequestBody Long[] ids){
        fileService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
