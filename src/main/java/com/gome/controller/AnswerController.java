package com.gome.controller;

import com.gome.constant.GomeConstant;
import com.gome.dto.QuestionDTO;
import com.gome.pojo.GomeUser;
import com.gome.pojo.QaQuestionItems;
import com.gome.pojo.QaQuestionList;
import com.gome.pojo.QaQuestionReply;
import com.gome.service.*;
import com.gome.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.text.ParseException;
import java.util.*;

import static com.gome.constant.GomeConstant.*;
import static com.gome.enums.ResultEnums.*;

/**
 * @Description:
 * @Author: WangJinYue
 * @Date: 2020/8/5 18:04
 * @Modified By:
 */
@Controller
public class AnswerController {
    @Autowired
    private QaCountItemsService countItemsService;
    @Autowired
    private QaQuestionListService questionListService;
    @Autowired
    private QaQuestionItemsService questionItemsService;
    @Autowired
    private QaQuestionReplyService questionReplyService;
    @Autowired
    private QaScoresRecordService scoresRecordService;
    @Autowired
    private GomeUserService gomeUserService;

    @GetMapping("/answer")
    public String answer(@RequestParam Integer thisNumber, Integer competitionOrder, @RequestParam String thisLinks, Model model) {

        GomeUser gomeUser = gomeUserService.selectAll(competitionOrder);
        System.out.println(gomeUser);

        //当前为第二环节。
        if (thisLinks.equals("2")) {
            // 2.获取本套题所有的选择题
            List<QaQuestionList> questionList = questionListService.getQuestionList(thisNumber, thisLinks);
            List<QuestionDTO> list = new ArrayList<>();
            for (QaQuestionList question : questionList) {
                QuestionDTO questionDTO = new QuestionDTO();
                questionDTO.setId(question.getId());
                questionDTO.setAnswer(question.getAnswer());
                questionDTO.setQuestionTitle(question.getQuestionTitle());
                questionDTO.setQuestionType(question.getQuestionType());
                questionDTO.setThisNumber(question.getThisNumber());
                // 3.查询 abcd
                List<QaQuestionItems> questionItemsList = questionItemsService.getQuestionItemsList(question.getId());
                questionDTO.setItem(questionItemsList);

                list.add(questionDTO);
            }
            model.addAttribute("list", list);
            model.addAttribute("user", gomeUser);
            return "answer";
        }
        //第三环节
        if (thisLinks.equals("3")) {
            //第三环节
            List<QaQuestionList> questionList = questionListService.getQuestionList(thisNumber, thisLinks);
            model.addAttribute("list", questionList);
            //将当前答题人信息存入model
            model.addAttribute("user",gomeUser);
            model.addAttribute("competitionOrder",competitionOrder);
            model.addAttribute("thisLinks",thisLinks);
            return "subjective";
        }else if (thisLinks.equals("5")) {
            System.out.println(5);
            //第五环节
            List<QaQuestionList> questionList = questionListService.getQuestionList(thisNumber, thisLinks);
            //获取当前答题人信息

            model.addAttribute("list", questionList);

            //将当前答题人信息存入model
            model.addAttribute("user",gomeUser);
            model.addAttribute("competitionOrder",competitionOrder);
            model.addAttribute("thisLinks",thisLinks);
            return "subjective";
        }else{
        return "wait";
        }
    }


}
