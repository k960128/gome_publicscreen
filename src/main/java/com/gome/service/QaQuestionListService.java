package com.gome.service;

import com.gome.pojo.QaQuestionList;
import com.gome.pojo.QaQuestionReply;
import com.gome.util.ResultUtil;

import java.util.List;

/**
 * @Description:
 * @Author: WangJinYue
 * @Date: 2020/8/5 18:06
 * @Modified By:
 */
public interface QaQuestionListService {
    // 查询n套题的所有题，并将此套提放入登录的用户中
    List<QaQuestionList> getQuestionList(Integer thisNumber, String questionType);

    QaQuestionList getQuestion(int id);
}
