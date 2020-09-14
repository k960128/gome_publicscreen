package com.gome.service.impl;

import com.gome.mapper.JudgesScoresMapper;
import com.gome.pojo.JudgesScores;
import com.gome.pojo.JudgesScoresExample;
import com.gome.service.JudgesScoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 */
@Service
public class JudgesScoresServiceImpl implements JudgesScoresService {
    @Autowired(required = false)
    private JudgesScoresMapper judgesScoresMapper;


    @Override
    public List<JudgesScores> getScoreByJudgesNameAndPlayer(String thisLinks, int playerId) {


        List<JudgesScores> judgesScoresList = new ArrayList<JudgesScores>();
        JudgesScoresExample example = new JudgesScoresExample();
        example.setOrderByClause("JUDGES_ID ASC");
        JudgesScoresExample.Criteria criteria = example.createCriteria();
        criteria.andThisLinksEqualTo(thisLinks);
        criteria.andPlayerIdEqualTo(playerId);
        if(judgesScoresMapper.selectByExample(example).size()>0){
            judgesScoresList = judgesScoresMapper.selectByExample(example);
        }
        return judgesScoresList;
    }

    @Override
    public List<JudgesScores> getJudgesScoresList(String thisLinks, int userNum) {
        List<JudgesScores> judgesScoresList = new ArrayList<JudgesScores>();
        JudgesScoresExample example = new JudgesScoresExample();
        JudgesScoresExample.Criteria criteria = example.createCriteria();
        criteria.andPlayerIdEqualTo(userNum);
        criteria.andThisLinksEqualTo("1");
        criteria.andEvaluationIdEqualTo(7);
        judgesScoresList = judgesScoresMapper.selectByExample(example);
        if (judgesScoresList.size()>0){
            return judgesScoresList;
        }
        return null;
    }
}
