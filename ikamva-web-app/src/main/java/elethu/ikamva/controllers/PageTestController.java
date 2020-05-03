package elethu.ikamva.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageTestController {

    @RequestMapping({"/index"})
    public String getTestPage(Model model) {

        return "index";
    }


}
