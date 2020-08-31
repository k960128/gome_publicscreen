package com.gome.controller;

import com.gome.constant.GomeConstant;
import com.gome.pojo.GomeUser;
import com.gome.pojo.QaCountItems;
import com.gome.service.GomeUserService;
import com.gome.service.QaCountItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.gome.constant.GomeConstant.*;

/**
 * @Description:
 * @Author: WangJinYue
 * @Date: 2020/8/17 0:02
 * @Modified By:
 */
@Controller
public class TopicController {
    @Autowired
    private QaCountItemsService qaCountItemsService;
    @Autowired
    private GomeUserService gomeUserService;

    /**
     * 通过后端控制哪位选手开始选题
     * @param userSortnum
     * @param model
     * @return
     */
    @GetMapping("/topic")
    public String toTopic(@RequestParam Integer userSortnum,String thisLinks,  Model model) {

        GomeUser gomeUser = gomeUserService.selectAll(userSortnum);
        List<QaCountItems> qaCountItemsList = qaCountItemsService.getCountList(thisLinks);
        for(QaCountItems qa : qaCountItemsList){
            System.out.println(qa.toString());
        }
        model.addAttribute("user",gomeUser);
        model.addAttribute("list",qaCountItemsList);
        model.addAttribute("thisLinks",thisLinks);
        return "topic";
    }
}
