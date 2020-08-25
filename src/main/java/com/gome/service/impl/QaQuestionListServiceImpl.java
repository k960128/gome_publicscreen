package com.gome.service.impl;

import com.gome.mapper.QaQuestionListMapper;
import com.gome.pojo.QaQuestionList;
import com.gome.pojo.QaQuestionListExample;
import com.gome.pojo.QaQuestionReply;
import com.gome.service.QaQuestionListService;
import com.gome.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:
 * @Author: WangJinYue
 * @Date: 2020/8/5 18:10
 * @Modified By:
 */
@Service
public class QaQuestionListServiceImpl implements QaQuestionListService {
    @Autowired(required = false)
    private QaQuestionListMapper questionListMapper;

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public List<QaQuestionList> getQuestionList(Integer thisNumber, String questionType) {
        // 1.查询n套题的所有题目
        QaQuestionListExample example = new QaQuestionListExample();
        QaQuestionListExample.Criteria criteria = example.createCriteria();
        criteria.andThisNumberEqualTo(thisNumber);
        // 判断是什么类型的题
        if (questionType.equals("2")) {
            criteria.andQuestionTypeBetween("1", "2");
        }
        if (questionType.equals("3")) {
            criteria.andQuestionTypeEqualTo("3");
        }
        if (questionType.equals("4")) {
            criteria.andQuestionTypeEqualTo("4");
        }
        if (questionType.equals("5")) {
            criteria.andQuestionTypeEqualTo("6");
        }
        List<QaQuestionList> qaQuestionLists = questionListMapper.selectByExample(example);
        return qaQuestionLists;
    }




}
