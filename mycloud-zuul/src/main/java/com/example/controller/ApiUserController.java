package com.example.controller;


import com.example.annotation.Login;
import com.example.annotation.LoginUser;
import com.example.common.utils.R;
import com.example.common.validator.Assert;
import com.example.entity.FileEntity;
import com.example.entity.SysUserEntity;
import com.example.feign.IFileService;
import com.example.feign.IUploadService;
import com.example.feign.IUserService;
import com.example.utils.MapGet;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户Controller
 */
@RestController
@RequestMapping("/api")
public class ApiUserController {
    @Autowired
    private IFileService fileService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IUploadService uploadService;

    /**
     * 获取我的网盘
     * @param user
     * @param params
     * @return
     */
    @Login
    @RequestMapping("/getFiles")
    public R getSource(@LoginUser SysUserEntity user, @RequestParam Map<String, Object> params){
        String fileParentId = MapGet.getByKey("parentId", params);
        fileParentId = StringUtils.isEmpty(fileParentId) ? "0" : fileParentId;
        //当前页
        String page = MapGet.getByKey("page", params);
        //每页大小
        String limit = MapGet.getByKey("pageSize", params);
        Assert.isNull(user, "用户信息缺失");
        List<FileEntity> fileEntities = fileService.listFileByPage(user.getUserId().toString(), fileParentId, Integer.valueOf(page), Integer.valueOf(limit));
        int total = fileService.listFileTotal(user.getUserId().toString(), fileParentId);
        Map<String, Object> map = new HashMap<>();
        map.put("list", fileEntities);
        return R.ok().put("data", map).put("page", page).put("total", total);
    }

  /*  @RequestMapping("/updateImg")
    //文件上传
    public R updateImg(@RequestPart(value = "avatar") MultipartFile avatar){
        String ossUrl = uploadService.updateImg(avatar);
        return R.ok().put("data", ossUrl);
    }
*/
    //文件加入企业网盘
    @Login
    @RequestMapping("/addDisk")
    public R addDisk(@LoginUser SysUserEntity user, @RequestParam Map<String, Object> params){
        String fileId = MapGet.getByKey("fileId", params);
        Assert.isNull(user, "用户信息缺失");
        Assert.isBlank(fileId, "文件Id不能为空");
        fileService.addDisk(user.getUserId().toString(), fileId);
        return R.ok();
    }


    //删除文件
    @Login
    @RequestMapping("/delFileById")
    public R delFileById(@LoginUser SysUserEntity user, @RequestParam Map<String, Object> params){
        String fileId = MapGet.getByKey("fileId", params);
        Assert.isNull(user, "用户信息缺失");
        Assert.isBlank(fileId, "文件Id不能为空");
        fileService.delFileById(user.getUserId().toString(), fileId);
        return R.ok();
    }


    //修改用户信息（邮箱，用户名）
    @Login
    @RequestMapping("/updateUserInfo")
    public R updateUserInfo(@LoginUser SysUserEntity user, @RequestParam Map<String, Object> params){
        String email = MapGet.getByKey("email", params);
        String userName = MapGet.getByKey("name", params);
        String avatar = MapGet.getByKey("avatar", params);
        SysUserEntity userEntity = userService.getUserByUserId(user.getUserId().toString());
        if (userEntity == null){
            return R.ok();
        }
        if (StringUtils.isNotEmpty(email) && !email.equals(userEntity.getEmail())){
            user.setEmail(email);
        }
        if (StringUtils.isNotEmpty(userName) && !userName.equals(userEntity.getUsername())){
            user.setUsername(userName);
            SysUserEntity entity = userService.getUserByAccount(userName, null);
            if (entity !=null){
                return R.error("该用户名存在");
            }
        }
        if (StringUtils.isNotEmpty(avatar)){
            user.setImgPath(avatar);
        }
        userService.updateUser(user);
        return R.ok();
    }


    //查询可以分享的用户
    @Login
    @RequestMapping("/findToShareUser")
    public R findToShareUser(@LoginUser SysUserEntity user, @RequestParam Map<String, Object> params){
        String userName = MapGet.getByKey("userName", params);
        Assert.isNull(user, "用户信息缺失");
        List<SysUserEntity> userEntities = userService.findUserByUserName(userName);
        Map<String, Object> map = new HashMap<>();
        map.put("list", userEntities);
        return R.ok().put("data", map);
    }

}
