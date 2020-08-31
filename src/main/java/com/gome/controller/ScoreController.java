package com.gome.controller;


import com.gome.pojo.FinalScore;
import com.gome.service.FinalScoreService;
import com.gome.service.GomeUserService;
import com.gome.service.QaScoresRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ScoreController {

    @Autowired
    private GomeUserService gomeUserService;
    @Autowired
    private QaScoresRecordService qaScoresRecordService;
    @Autowired
    private FinalScoreService finalScoreService;


    /**
     * 跳转至得分界面
     * @return
     */
    @GetMapping("/score")
    public String to_Wait(@RequestParam String thisLinks, int userSortnum, HttpServletRequest request , Model model){

        //第二环节
        if(thisLinks.equals("2")){
            //查询成绩
            List<FinalScore> finalScores = finalScoreService.findByAll();
            model.addAttribute("finalScores",finalScores);
        }
        return "score";
    }

}
