package com.gome.service.impl;

import com.gome.mapper.QaQuestionReplyMapper;
import com.gome.pojo.QaQuestionReply;
import com.gome.service.QaQuestionReplyService;
import com.gome.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @Description:
 * @Author: WangJinYue
 * @Date: 2020/8/7 21:28
 * @Modified By:
 */
@Service
public class QaQuestionReplyServiceImpl implements QaQuestionReplyService {
    @Autowired(required = false)
    private QaQuestionReplyMapper questionReplyMapper;


}
