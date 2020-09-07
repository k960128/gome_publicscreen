package com.gome.controller;


import com.gome.pojo.FinalScore;
import com.gome.pojo.GomeUser;
import com.gome.service.FinalScoreService;
import com.gome.service.GomeUserService;
import com.gome.service.QaScoresRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Comparator;
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
     *
     * @return
     */
    @GetMapping("/score")
    public String to_Wait(@RequestParam String thisLinks, int userSortnum, HttpServletRequest request, Model model) {

        Double find_score = 0.0;
        GomeUser gomeUser = gomeUserService.selectAll(userSortnum);

        find_score = finalScoreService.getScore(thisLinks, userSortnum);

        //查询当前环节总排名
        List<FinalScore> finalScoreListSort = finalScoreService.findBySortList(thisLinks);
        if(thisLinks.equals("2")){
            //如果是第二环节，进行一个排序
            Collections.sort(finalScoreListSort, new Comparator<FinalScore>() {
                @Override
                public int compare(FinalScore o1, FinalScore o2) {
                    return o2.getFinalScore().compareTo(o1.getFinalScore());
                }
            });
        }

        System.out.println("排序后：");
        for (FinalScore finalSore: finalScoreListSort
        ) {
            System.out.println(finalSore.toString());
        }

        model.addAttribute("finalScoreListSort",finalScoreListSort);
        model.addAttribute("thisLinks", thisLinks);
        model.addAttribute("userSortnum", userSortnum);
        model.addAttribute("finalScores", find_score);
        model.addAttribute("user", gomeUser);
        System.out.println("==========================");
        System.out.println(finalScoreListSort.toString());
        System.out.println(thisLinks);
        System.out.println(userSortnum);
        System.out.println(find_score);
        System.out.println(gomeUser.toString());
        return "score";
    }

}
