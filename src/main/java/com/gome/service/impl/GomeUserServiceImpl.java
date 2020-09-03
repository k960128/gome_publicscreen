package com.gome.service.impl;

import com.gome.mapper.GomeUserMapper;
import com.gome.pojo.GomeUser;
import com.gome.pojo.GomeUserExample;
import com.gome.service.GomeUserService;
import com.gome.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.gome.enums.ResultEnums.*;

/**
 * @Description:
 * @Author: WangJinYue
 * @Date: 2020/8/3 19:56
 * @Modified By:
 */
@Service
public class GomeUserServiceImpl implements GomeUserService {
    @Autowired(required = false)
    private GomeUserMapper gomeUserMapper;


    /**
     * 通过选手号查询用户
     *
     * @param competitionOrder
     * @return
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public GomeUser selectAll(Integer competitionOrder) {
        GomeUserExample example = new GomeUserExample();
        GomeUserExample.Criteria criteria = example.createCriteria();
        criteria.andCompetitionOrderEqualTo(competitionOrder);
        List<GomeUser> list = gomeUserMapper.selectByExample(example);
        if (list.size() != 0) {
            for (GomeUser gomeUser : list) {
                return gomeUser;
            }
        }
        return null;
    }

    @Override
    public List<GomeUser> selectAll() {
        GomeUserExample example = new GomeUserExample();
        GomeUserExample.Criteria criteria = example.createCriteria();
        criteria.andCompetitionOrderNotEqualTo(0);
        return gomeUserMapper.selectByExample(example);
    }
}
