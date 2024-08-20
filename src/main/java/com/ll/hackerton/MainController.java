package com.ll.hackerton;


import com.ll.hackerton.user.SiteUser;
import com.ll.hackerton.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;



@Controller
public class MainController {
    private UserService userService;
    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/")
    public String main(Model model, Principal principal) {
        if (principal == null) {
            // Handle the case when the user is not authenticated
            return "redirect:/user/login";  // Or an appropriate error message
        }
        String username = principal.getName();

        SiteUser loggedInUser = this.userService.getUser(principal.getName());
        model.addAttribute("loggedInUser", loggedInUser);
        return "main_board";
    }

}
