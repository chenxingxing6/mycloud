package com.example.controller;


import com.example.annotation.Login;
import com.example.annotation.LoginUser;
import com.example.common.utils.DateUtils;
import com.example.common.utils.R;
import com.example.common.validator.Assert;
import com.example.entity.FileEntity;
import com.example.entity.SysUserEntity;
import com.example.feign.IDiskService;
import com.example.service.TokenService;
import com.example.utils.MapGet;
import com.example.vo.DiskDirVo;
import com.example.vo.FileVo;
import com.example.vo.SourceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 企业网盘-资源库
 */
@RestController
@RequestMapping("/api")
public class ApiSourceController {
    @Autowired
    private IDiskService diskService;
    private final String file_url = "http://193.112.27.123:8012/demo/";

    /**
     * 获取企业网盘资源
     * @param user
     * @param params
     * @return
     */
    @Login
    @RequestMapping("/getSource")
    public R getSource(@LoginUser SysUserEntity user, @RequestParam Map<String, Object> params){
        String diskId = MapGet.getByKey("searchType", params);
        //当前页
        String page = MapGet.getByKey("page", params);
        //每页大小
        String limit = MapGet.getByKey("pageSize", params);
        Assert.isBlank(diskId, "参数错误");
        Assert.isNull(user, "用户信息缺失");
        String deptId = String.valueOf(user.getDeptId());
        List<FileEntity> fileEntities = diskService.listDisk(deptId, diskId, Integer.valueOf(page), Integer.valueOf(limit));
        int total = diskService.listDiskTotal(deptId, diskId);
        Map<String, Object> map = new HashMap<>();
        map.put("list", cvtVos(fileEntities));
        return R.ok().put("data", map).put("page", page).put("total", total);
    }

    private List<SourceVo> cvtVos(List<FileEntity> fileEntities){
        List<SourceVo> list = new ArrayList<>();
        for (FileEntity file : fileEntities) {
            SourceVo vo = new SourceVo();
            vo.setId(file.getId().toString());
            vo.setParentId("0");
            vo.setCreateUser(file.getCreateUser());
            vo.setCreateTime(DateUtils.format(file.getCreateTime(), DateUtils.DATE_TIME_PATTERN));
            vo.setOptTime(DateUtils.format(file.getOpTime(), DateUtils.DATE_TIME_PATTERN));
            vo.setDownloads(String.valueOf(file.getDownloadNum()));
            vo.setLength(file.getLength());
            vo.setOriginalName(file.getOriginalName());
            vo.setOriginalPath(file.getOriginalPath());
            vo.setViewFlag(String.valueOf(file.getViewFlag()));
            vo.setType(String.valueOf(file.getType())); //0：目录  1：文件

            String originalName = file.getOriginalName();
            String suffix = originalName.substring(originalName.lastIndexOf(".") + 1);
            String fileName = originalName.substring(0, originalName.lastIndexOf("."));
            vo.setExtension(suffix);
            vo.setName(fileName);
            vo.setFileUrl(file_url + fileName);
            list.add(vo);
        }
        return list;
    }

    public static void main(String[] args) {
        System.out.println("9Bx98.pptx".substring(0, "9Bx98.pptx".lastIndexOf(".")));
    }

    /**
     * 获取企业网盘资源类型
     * @param user
     * @return
     */
    @Login
    @RequestMapping("/getSourceType")
    public R getSourceType(@LoginUser SysUserEntity user){
        String userId = String.valueOf(user.getUserId());
        String deptId = String.valueOf(user.getDeptId());
        List<DiskDirVo> diskDirVos = diskService.listDiskDirType(userId, deptId);
        Map<String, Object> map = new HashMap<>();
        map.put("list", diskDirVos);
        return R.ok().put("data", map);
    }

}
