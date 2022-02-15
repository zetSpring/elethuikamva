package elethu.ikamva.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Controller
public class LoginController {
    //@RequestMapping({"/login"})
    public String getLogin(Model model) {
        model.addAttribute("selected", "l");

        return "login";
    }
}
