package com.example.modules.front.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.common.constants.FileEnum;
import com.example.common.constants.ViewEnum;
import com.example.common.exception.BizException;
import com.example.common.utils.IdGen;
import com.example.common.validator.Assert;
import com.example.common.validator.ValidatorUtils;
import com.example.modules.sys.controller.AbstractController;
import com.example.modules.sys.entity.SysUserEntity;
import org.apache.commons.collections.CollectionUtils;
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
public class FileController extends AbstractController {
    final static IdGen idGen = IdGen.get();

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


    /**
     * 创建文件
     *
     * @param dirName     文件路径名   /39166745223986
     * @param originalDir 原始文件路径 /file
     * @param mkdir       文件夹名     /a
     * @param parentid    父文件ID
     * @return
     */
    @RequestMapping("createFile")
    public R createFile(@RequestParam(value="dirName") String dirName,
                        @RequestParam(value="originalDir") String originalDir,
                        @RequestParam(value="mkdir") String mkdir,
                        @RequestParam(value="parentid") long parentid){
        //封装文件对象
        FileEntity file = new FileEntity();
        file.setId(idGen.nextId());
        SysUserEntity user = getUser();
        file.setType(FileEnum.FOLDER.getType());
        //另取文件名
        String name = System.nanoTime() + "";
        file.setName(name);
        file.setOriginalName(mkdir);
        file.setParentId(parentid);
        file.setLength("0");
        file.setViewFlag(ViewEnum.N.getType());
        file.setCreateTime(System.currentTimeMillis());
        file.setCreateUser(user.getUserId().toString());
        if (dirName.equals("/")){
            file.setPath(dirName + name);
            file.setOriginalPath(originalDir + mkdir);
        }else {
            file.setPath(dirName + "/"+ name);
            file.setOriginalPath(originalDir +"/"+ mkdir);
        }
        R r = new R();
        List<FileEntity> fileList = fileService.getFileList(user, parentid);
        if (CollectionUtils.isEmpty(fileList)){
            fileService.makeFolder(file, user, parentid);
            r.put("msg", "创建文件夹成功");
        }else {
            Boolean flag = true;
            for (FileEntity fileEntity : fileList) {
                if (fileEntity.getType().equals(FileEnum.FOLDER.getType()) && fileEntity.getOriginalName().equals(file.getOriginalName())){
                    flag = false;
                    break;
                }
            }
            if (flag){
                throw new BizException("文件夹已经存在");
            }
        }
        return r;
    }

    /**
     * 删除文件或者文件夹
     * @param ids
     * @param parentid
     * @return
     */
    @RequestMapping("/deleteFileOrFolder")
    public R deleteFileOrFolder(@RequestParam(value="ids") Long[] ids,
                                @RequestParam(value="parentid") long parentid) {
        if (ids.length ==0){
            throw new BizException("删除条数为空");
        }
        SysUserEntity user = getUser();
        R r = new R();
        try {
            List<Long> idList = Arrays.asList(ids);
            List<FileEntity> files = fileService.selectBatchIds(idList);
            Map<Long/*fileId*/, FileEntity> fileEntityMap = files.stream().collect(Collectors.toMap(FileEntity::getId, e->e));
            for (Long id : idList) {
                if (fileEntityMap.get(id) == null){
                    continue;
                }
                FileEntity file = fileEntityMap.get(id);

                //删除Hdfs中文件
                fileService.deleteHdfs(user, file);

                //递归删除file和user_file文件
                fileService.deleteFileRecursion(user, file, parentid);
            }
            r.put("msg", "删除成功！");
        } catch (Exception e) {
            r.put("code", 500);
            r.put("msg", "删除失败！");
            e.printStackTrace();
        }
        return r;
    }
}
