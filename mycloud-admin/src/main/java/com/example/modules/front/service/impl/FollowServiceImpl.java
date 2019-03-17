package com.example.modules.front.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.common.utils.PageUtils;
import com.example.common.utils.Query;

import com.example.modules.front.dao.FollowDao;
import com.example.modules.front.entity.FollowEntity;
import com.example.modules.front.service.FollowService;


@Service("followService")
public class FollowServiceImpl extends ServiceImpl<FollowDao, FollowEntity> implements FollowService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<FollowEntity> page = this.selectPage(
                new Query<FollowEntity>(params).getPage(),
                new EntityWrapper<FollowEntity>()
        );

        return new PageUtils(page);
    }

}
