package elethu.ikamva.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LoginController {

    @RequestMapping({"/login", "/"})
    public String getLogin(Model model) {
        model.addAttribute("selected", "l");

        return "login";
    }
}
