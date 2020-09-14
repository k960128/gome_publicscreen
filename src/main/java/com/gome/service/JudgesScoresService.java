package com.gome.service;

import com.gome.pojo.JudgesScores;

import java.util.List;

/**
 * @Description:
 * @Author: WangJinYue
 * @Date: 2020/8/19 4:00
 * @Modified By:
 */
public interface JudgesScoresService {


    /**
     * 获取第四环节，某个选手的总分
     * @param thisLinks
     * @param playerId
     * @return
     */
    List<JudgesScores> getScoreByJudgesNameAndPlayer(String thisLinks,int playerId);

    //获取第一环节，附加分集合列表
    List<JudgesScores> getJudgesScoresList(String thisLinks , int userNum);
}
