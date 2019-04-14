package com.example.controller;


import com.example.annotation.Login;
import com.example.annotation.LoginUser;
import com.example.common.utils.DateUtils;
import com.example.common.utils.R;
import com.example.common.validator.Assert;
import com.example.entity.DiskFileEntity;
import com.example.entity.FileEntity;
import com.example.entity.SysUserEntity;
import com.example.feign.IFileService;
import com.example.feign.IUploadService;
import com.example.feign.IUserService;
import com.example.service.TokenService;
import com.example.utils.MapGet;
import com.example.vo.SourceVo;
import com.example.vo.UserVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
    @Autowired
    private TokenService tokenService;


    //文件完整路径
    @Value("${file.seeUrl}")
    String fileSeeUrl;

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
        map.put("list", cvtVos(fileEntities));
        return R.ok().put("data", map).put("page", page).put("total", total);
    }


    private List<SourceVo> cvtVos(List<FileEntity> fileEntities){
        List<SourceVo> list = new ArrayList<>();
        for (FileEntity file : fileEntities) {
            SourceVo vo = new SourceVo();
            vo.setId(file.getId().toString());
            vo.setParentId(String.valueOf(file.getParentId()));
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
            if ("1".equals(file.getType().toString())){
                String suffix = originalName.substring(originalName.lastIndexOf(".") + 1);
                String fileName = originalName.substring(0, originalName.lastIndexOf("."));
                vo.setExtension(suffix);
                vo.setName(fileName);
            }else {
                vo.setName(originalName);
            }
            vo.setFileUrl(fileSeeUrl + originalName);
            list.add(vo);
        }
        return list;
    }

    //文件加入企业网盘
    @Login
    @RequestMapping("/addDisk")
    public R addDisk(@LoginUser SysUserEntity user, @RequestParam Map<String, Object> params){
        String fileId = MapGet.getByKey("fileId", params);
        Assert.isNull(user, "用户信息缺失");
        Assert.isBlank(fileId, "文件Id不能为空");
        //判断是否存在
        DiskFileEntity entity = fileService.getDiskFileByFileId(fileId);
        if (entity !=null){
            return R.error("企业网盘里面已经有该文件了");
        }
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

    //修改绑定信息
    @Login
    @RequestMapping("/bindUpdate")
    public R bindUpdate(@LoginUser SysUserEntity user, @RequestParam Map<String, Object> params){
        String type = MapGet.getByKey("type", params);
        if (StringUtils.isEmpty(type)){
            return R.ok();
        }
        SysUserEntity userEntity = userService.getUserByUserId(user.getUserId().toString());
        if (userEntity == null){
            return R.ok();
        }
        //密码
        if ("1".equals(type)){
            String newPassword = MapGet.getByKey("newPassword", params);
            String password = MapGet.getByKey("password", params);
            SysUserEntity entity = userService.getUserByAccount(user.getUsername(), password);
            if (entity == null){
                return R.error("原密码输入错误");
            }
            userEntity.setPassword(newPassword);
        }
        //手机号
        else if ("2".equals(type)){
            String mobile = MapGet.getByKey("mobile", params);
            String captcha = MapGet.getByKey("captcha", params);
            SysUserEntity userEntity1 = userService.getUserByMobile(mobile);
            if (userEntity1 !=null){
                return R.error("手机号已存在");
            }
            if (!tokenService.checkCaptcha(mobile, captcha)){
                return R.error("验证码输入错误");
            }
            userEntity.setMobile(mobile);
        }
        //邮箱
        else if ("3".equals(type)){
            String email = MapGet.getByKey("mail", params);
            userEntity.setEmail(email);
        }
        userService.updateUser(userEntity);

        //用户登录
        Map<String, Object> map = new HashMap<>();
        UserVo userVo = new UserVo();
        userVo.setId(userEntity.getUserId().toString());
        userVo.setUsername(userEntity.getUsername());
        userVo.setEmail(userEntity.getEmail());
        userVo.setImgPath(userEntity.getImgPath());
        //userVo.setPassword(userEntity.getPassword());
        userVo.setMobile(userEntity.getMobile());
        userVo.setCreateTime(DateUtils.format(userEntity.getCreateTime(), DateUtils.DATE_TIME_PATTERN));
        userVo.setDeptId(userEntity.getDeptId().toString());
        userVo.setDeptName(userEntity.getDeptName());
        userVo.setCompanyName("杭州二维火科技有限公司");
        map.put("member", userVo);
        return R.ok().put("data", map);
    }

    //查询可以分享的用户
    @Login
    @RequestMapping("/findToShareUser")
    public R findToShareUser(@LoginUser SysUserEntity user, @RequestParam Map<String, Object> params){
        String userName = MapGet.getByKey("userName", params);
        Assert.isNull(user, "用户信息缺失");
        List<SysUserEntity> userEntities = userService.findUserByUserName(userName);
        userEntities = userEntities.stream().filter(new Predicate<SysUserEntity>() {
            @Override
            public boolean test(SysUserEntity sysUserEntity) {
                return !sysUserEntity.getUserId().equals(user.getUserId());
            }
        }).collect(Collectors.toList());
        Map<String, Object> map = new HashMap<>();
        map.put("list", userEntities);
        return R.ok().put("data", map);
    }

}
