package com.example.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.annotation.Login;
import com.example.annotation.LoginUser;
import com.example.common.exception.BizException;
import com.example.common.utils.DateUtils;
import com.example.common.utils.R;
import com.example.common.validator.Assert;
import com.example.entity.SysUserEntity;
import com.example.feign.IUserService;
import com.example.oauth.OauthQQ;
import com.example.oauth.OauthSina;
import com.example.service.TokenService;
import com.example.utils.MapGet;
import com.example.utils.TokenUtil;
import com.example.vo.UserVo;
import com.example.websocket.WebSocketServer;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录接口
 */
@RestController
@RequestMapping("/api")
public class ApiLoginController {
    private static final Logger logger = LoggerFactory.getLogger(ApiLoginController.class);
    @Autowired
    private TokenService tokenService;
    @Autowired
    private IUserService userService;

    private static final String PRE_FIX = "CONNQQ-";

    //推送数据接口
    @RequestMapping("/socket/push")
    public R pushToWeb(String mobile) {
        try {
            SysUserEntity userEntity = userService.getUserByMobile(mobile);
            //生成token
            String token = tokenService.createToken(userEntity.getUserId());
            //用户登录
            Map<String, Object> map = new HashMap<>();
            UserVo userVo = new UserVo();
            userVo.setId(userEntity.getUserId().toString());
            userVo.setUsername(userEntity.getUsername());
            userVo.setEmail(userEntity.getEmail());
            userVo.setImgPath(userEntity.getImgPath());
            userVo.setMobile(userEntity.getMobile());
            userVo.setCreateTime(DateUtils.format(userEntity.getCreateTime(), DateUtils.DATE_TIME_PATTERN));
            userVo.setDeptId(userEntity.getDeptId().toString());
            userVo.setDeptName(userEntity.getDeptName());
            userVo.setCompanyName("杭州二维火科技有限公司");
            map.put("member", userVo);
            R r =  R.ok("login").put("data", map).put("token", token);
            WebSocketServer.sendUserInfo(r);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
        return R.ok();
    }

    /**
     * QQ扫码登录 构造授权请求url
     * @return void    返回类型
     * @throws
     */
    @RequestMapping("/qq/login")
    public R qqLogin(HttpServletRequest request){
        //state就是一个随机数，保证安全
        String state = TokenUtil.randomState();
        try {
            String url = OauthQQ.me().getAuthorizeUrl(state);
            return R.ok().put("url", url);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return R.error();
    }

    /**
     * QQ扫码关联
     * @return void    返回类型
     * @throws
     */
    @Login
    @RequestMapping("/qq/conn")
    public R qqConn(@LoginUser SysUserEntity user, HttpServletRequest request){
        Assert.isNull(user.getUserId(), "参数错误");
        //state就是一个随机数，保证安全,这里用用户ID
        String state = TokenUtil.randomState();
        try {
            String url = OauthQQ.me().getAuthorizeUrl(PRE_FIX + user.getUserId());
            return R.ok().put("url", url);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return R.error();
    }


    /**
     * 微博登录 构造授权请求url
     * @return void    返回类型
     * @throws
     */
    @RequestMapping("/sina/login")
    public R sinaLogin(HttpServletRequest request){
        //state就是一个随机数，保证安全
        String state = TokenUtil.randomState();
        try {
            String url = OauthSina.me().getAuthorizeUrl(state);
            return R.ok().put("url", url);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return R.error();
    }

    @RequestMapping("/qq/callback")
    public void callback(HttpServletRequest request){
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        boolean isQQConn = state.startsWith(PRE_FIX);
        // 取消了授权
        if (StringUtils.isBlank(state)||StringUtils.isBlank(code)){
            logger.info("取消了授权");
        }
        //清除state以防下次登录授权失败
        //session.removeAttribute(SESSION_STATE);
        //获取用户信息
        try{
            JSONObject userInfo = OauthQQ.me().getUserInfoByCode(code);
            logger.error(userInfo.toString());
            String type = "qq";
            String openid = userInfo.getString("openid");
            String nickname = userInfo.getString("nickname");
            String photoUrl = userInfo.getString("figureurl_2");
            // 将相关信息存储数据库...
            innerSendMsg(openid, nickname, photoUrl, isQQConn, state);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void innerSendMsg(String openId, String nickName, String photoUrl, boolean isQQConn, String state){
        SysUserEntity userEntity = userService.getUserByOpenId(openId);
        if (userEntity == null){
            if (isQQConn){
                String userId = state.split("-")[1];
                SysUserEntity user = userService.getUserByUserId(userId);
                if (user != null){
                    user.setOpenId(openId);
                    user.setImgPath(photoUrl);
                    userService.updateUser(user);
                    logger.warn("QQ关联成功！ userId:{}", userId);
                }
            }else {
                R r = R.error("用户没和QQ进行关联");
                WebSocketServer.sendUserInfo(r);
            }
            return;
        }
        //生成token
        String token = tokenService.createToken(userEntity.getUserId());
        //用户登录
        Map<String, Object> map = new HashMap<>();
        UserVo userVo = new UserVo();
        userVo.setId(userEntity.getUserId().toString());
        userVo.setUsername(nickName);
        userVo.setEmail(userEntity.getEmail());
        userVo.setImgPath(photoUrl);
        userVo.setMobile(userEntity.getMobile());
        userVo.setCreateTime(DateUtils.format(userEntity.getCreateTime(), DateUtils.DATE_TIME_PATTERN));
        userVo.setDeptId(userEntity.getDeptId().toString());
        userVo.setDeptName(userEntity.getDeptName());
        userVo.setCompanyName("杭州二维火科技有限公司");
        map.put("member", userVo);
        R r =  R.ok("login").put("data", map).put("token", token);
        WebSocketServer.sendUserInfo(r);
    }


    /**
     * 获取手机验证码
     * @param params
     * @return
     */
    @RequestMapping("/getCaptcha")
    public R getTest(@RequestParam Map<String, Object> params){
        String mobile = MapGet.getByKey("mobile", params);
        Assert.isBlank(mobile, "参数错误");
        return R.ok().put("data", tokenService.createCaptcha(mobile));
    }

    /**
     * 忘记密码
     * @param params
     * @return
     */
    @RequestMapping("/forget")
    public R forgetPassWord(@RequestParam Map<String, Object> params){
        String captcha = MapGet.getByKey("captcha", params);
        String mobile = MapGet.getByKey("mobile", params);
        if (!tokenService.checkCaptcha(mobile, captcha)){
            throw new BizException("验证码错误请重试！");
        }
        SysUserEntity userEntity = userService.getUserByMobile(mobile);
        if (userEntity == null){
            return R.error("手机号有误，请重新输入");
        }
        return R.ok("你的密码是：" + userEntity.getPassword());
    }

    /**
     * 登陆
     *
     * @param params
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestParam Map<String, Object> params){
        String type = MapGet.getByKey("type", params);
        Assert.isBlank(type, "参数错误");
        SysUserEntity userEntity = null;
        //账号登录
        if ("0".equals(type)){
            String username = MapGet.getByKey("account", params);
            String password = MapGet.getByKey("password", params);
            userEntity = userService.getUserByAccount(username, password);
        }
        //手机登录
        else if ("1".equals(type)){
            String captcha = MapGet.getByKey("captcha", params);
            String mobile = MapGet.getByKey("mobile", params);
            if (!tokenService.checkCaptcha(mobile, captcha)){
                throw new BizException("验证码错误请重试！");
            }
            userEntity = userService.getUserByMobile(mobile);
        }
        if (userEntity == null){
            throw new BizException("用户账号或密码错误！");
        }

        //生成token
        String token = tokenService.createToken(userEntity.getUserId());

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
        return R.ok().put("data", map).put("token", token);
    }

    @Login
    @GetMapping("/logout")
    public R logout(@RequestHeader("token") String token){
        tokenService.cleanToken(token);
        return R.ok();
    }

}
