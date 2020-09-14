package com.gome.controller;


import com.gome.pojo.FinalScore;
import com.gome.pojo.FinalToscore;
import com.gome.pojo.GomeUser;
import com.gome.pojo.JudgesScores;
import com.gome.service.*;
import com.gome.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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

    @Autowired
    private JudgesScoresService judgesScoresService;

    @Autowired
    private FinalToScoreService finalToScoreService;
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
        if(finalScoreListSort.size()>0){
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

            if(thisLinks.equals("4")){
                gomeUser = new GomeUser();
            }
        }


        System.out.println("thisLinks:"+thisLinks);

        model.addAttribute("finalScoreListSort",finalScoreListSort);
        model.addAttribute("thisLinks", thisLinks);
        model.addAttribute("userSortnum", userSortnum);
        model.addAttribute("finalScores", find_score);
        model.addAttribute("user", gomeUser);

        return "score";
    }

    /**
     * 公屏端收集第四环节得分
     */
    @GetMapping("result_CountScore")
    @ResponseBody
    public ResultUtil result_CountScore(@RequestParam String thisLinks){

        //1、先查询出选手列表，根据选手参赛顺序进行查询
        List<GomeUser> playerUserList = gomeUserService.selectAllOrder();
        //分数

        //1.1遍历用户
        for (GomeUser gomeUser: playerUserList
             ) {
            System.out.println("用户："+gomeUser.toString());
            //1.2查询当前用户下的评委打分集合
            List<JudgesScores> judgesScoresList = judgesScoresService.getScoreByJudgesNameAndPlayer(thisLinks,gomeUser.getUserId());
            Double score = 0.0;
            FinalScore finalScore = new FinalScore();
            for (JudgesScores judgesScores: judgesScoresList
                 ) {
                score +=judgesScores.getScore();

            }
            score = score/judgesScoresList.size();
            finalScore.setFinalScore(score*0.2);
            finalScore.setThisLinks("4");
            finalScore.setUserName(gomeUser.getUserName());
            finalScore.setUserPersonsName(gomeUser.getUserPersonsName());
            finalScore.setCompetitionOrder(gomeUser.getCompetitionOrder().toString());

            finalScoreService.saveScore(finalScore);
        }

        return ResultUtil.ok();
    }


    @GetMapping("/count_Final")
    @ResponseBody
    public ResultUtil count_Final(@RequestParam String thisLinks){

        //1获取参赛选手集合,根据才赛顺序进行排序
        List<GomeUser> gomeUserList = gomeUserService.selectAllOrder();
        //查询当前参赛选手各环节得分
        for (GomeUser gomeUser: gomeUserList
             ) {
            Double scores = 0.0;
            //当前参赛选手
            //获取第一环节得分
            FinalToscore finalToscore = new FinalToscore();
            finalToscore.setFirstScore(finalScoreService.getScore("1",gomeUser.getCompetitionOrder()));
            finalToscore.setSecondScore(finalScoreService.getScore("2",gomeUser.getCompetitionOrder()));
            finalToscore.setThreeScore(finalScoreService.getScore("3",gomeUser.getCompetitionOrder()));
            finalToscore.setFourScore(finalScoreService.getScore("4",gomeUser.getCompetitionOrder()));
            finalToscore.setFiveScore(finalScoreService.getScore("5",gomeUser.getCompetitionOrder()));

            List<JudgesScores> judgesScoresList= judgesScoresService.getJudgesScoresList("2",gomeUser.getUserId());
            if(judgesScoresList!=null){
                for (JudgesScores judgesScores: judgesScoresList
                     ) {
                    scores+=judgesScores.getScore();
                }
                scores = scores/judgesScoresList.size();
                finalToscore.setOtherScore(scores);
            }else{
                finalToscore.setOtherScore(0.0);
            }

            finalToscore.setFinalScore(finalToscore.getSecondScore()+finalToscore.getThreeScore()+finalToscore.getFiveScore()+finalToscore.getFourScore()+finalToscore.getOtherScore());
            finalToscore.setUserPersonsName(gomeUser.getUserPersonsName());
            finalToscore.setCompetitionOrder(gomeUser.getCompetitionOrder());

            finalToScoreService.saveFinalToScore(finalToscore);
        }


        return ResultUtil.ok();
    }
    @GetMapping("/end")
    public String end(Model model){

        //查询优秀奖
        //查询铜奖
        //查询银奖
        //查询金奖

        List<FinalToscore> finalToscoreList = finalToScoreService.getAllFinalToscore();
        List<String> list1= new ArrayList<>();
        List<String> list2= new ArrayList<>();
        List<String> list3= new ArrayList<>();
        List<String> list4= new ArrayList<>();

        for(int i=0;i<finalToscoreList.size();i++){
            if(i==0){
                list1.add(finalToscoreList.get(i).getUserPersonsName());
            }
            if(i<=2&&i>0){
                list2.add(finalToscoreList.get(i).getUserPersonsName());
            }

            if(i<=5&&i>2){
                list3.add(finalToscoreList.get(i).getUserPersonsName());
            }
            if(i>5&&i<finalToscoreList.size()){
                list4.add(finalToscoreList.get(i).getUserPersonsName());
            }

        }
        model.addAttribute("list1",list1);
        model.addAttribute("list2",list2);
        model.addAttribute("list3",list3);
        model.addAttribute("list4",list4);
        return "end";
    }



}
