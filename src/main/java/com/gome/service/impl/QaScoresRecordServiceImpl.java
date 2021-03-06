package com.gome.service.impl;

import com.gome.mapper.QaScoresRecordMapper;
import com.gome.pojo.QaScoresRecord;
import com.gome.pojo.QaScoresRecordExample;
import com.gome.service.QaScoresRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:
 * @Author: WangJinYue
 * @Date: 2020/8/13 19:08
 * @Modified By:
 */
@Service
public class QaScoresRecordServiceImpl implements QaScoresRecordService {
    @Autowired(required = false)
    private QaScoresRecordMapper scoresRecordMapper;

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public Boolean saveScore(double score, String userName, String stage) {
        QaScoresRecord scoresRecord = new QaScoresRecord();
        scoresRecord.setScore(score);
        scoresRecord.setStage(stage);
        scoresRecord.setUserName(userName);
        int i = scoresRecordMapper.insertSelective(scoresRecord);
        if (i != 1) {
            return false;
        }
        return true;
    }

    @Override
    public QaScoresRecord getScore(String username) {
        QaScoresRecordExample example = new QaScoresRecordExample();
        QaScoresRecordExample.Criteria criteria = example.createCriteria();
        criteria.andUserNameEqualTo(username);
        if(scoresRecordMapper.selectByExample(example).size()>0){
            return scoresRecordMapper.selectByExample(example).get(0);
        }

        return null;
    }

}
