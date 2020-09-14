package com.gome.service;

import com.gome.pojo.QaScoresRecord;

/**
 * @Description:
 * @Author: WangJinYue
 * @Date: 2020/8/13 19:08
 * @Modified By:
 */
public interface QaScoresRecordService {
    // 保存分数
    Boolean saveScore(double score, String userName, String stage);

    QaScoresRecord getScore(String username);
}
