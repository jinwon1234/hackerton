package com.ll.hackerton.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



import java.security.Principal;


@RequiredArgsConstructor
    @Controller
    @RequestMapping("/user")
    public class UserController {
        private final UserService userService;

        @GetMapping("/signup")
        public String signup(UserCreateForm userCreateForm) {
            return "user_signup_form";
        }

        @PostMapping("/signup")
        public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
            if (bindingResult.hasErrors()) {

                return "user_signup_form";
            }
            if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
                bindingResult.rejectValue("password2", "passwordInCorrect",
                        "2개의 패스워드가 일치하지 않습니다.");
                return "user_signup_form";
            }
            try {
                userService.create(userCreateForm.getUsername(),
                        userCreateForm.getPassword1(), userCreateForm.getContactNumber(), userCreateForm.getEmail(), userCreateForm.getRole());
            }catch(DataIntegrityViolationException e) {
                e.printStackTrace();
                bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
                return "user_signup_form";
            }catch(Exception e) {
                e.printStackTrace();
                bindingResult.reject("signupFailed", e.getMessage());
                return "user_signup_form";
            }
            return "redirect:/";
        }

        @GetMapping("/login")
        public String login() {
            return "user_login_form";
        }

        @GetMapping("/profile")
        public String profile(Model model, Principal principal) {
            SiteUser loggedInUser = this.userService.getUser(principal.getName());
            model.addAttribute("loggedInUser", loggedInUser);
            return "profile_form";
        }

        @GetMapping("/profile/modify")
        public String profileModify(Model model, UserModifyForm userModifyForm, Principal principal) {
            SiteUser loggedInUser = this.userService.getUser(principal.getName());
            userModifyForm.setUsername(loggedInUser.getUsername());
            userModifyForm.setContactNumber(loggedInUser.getContactNumber());
            return "profile_modify";
        }

        @PostMapping("/profile/modify")
        public String profileModify(@Valid UserModifyForm userModifyForm,
                                    BindingResult bindingResult, Principal principal) {

            SiteUser loggedInUser = this.userService.getUser(principal.getName());

            if (bindingResult.hasErrors()) {
                return "profile_modify";
            }

            this.userService.modify(loggedInUser, userModifyForm.getUsername(), userModifyForm.getContactNumber(), userModifyForm.getEmail());
            return "redirect:/user/profile";
        }
}
