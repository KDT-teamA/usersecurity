package com.example.usersecurity.Controller.Member;

import com.example.usersecurity.DTO.Member.MemberDTO;
import com.example.usersecurity.Service.Member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/")
    public String index(Model model) {

        return "index";
    }

    @GetMapping("/register")
    public String register() {
        return "member/register";
    }

    @PostMapping("/register")
    public String registerProc(MemberDTO memberDTO, Model model) {
        memberService.saveUser(memberDTO);
        return "member/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "member/login";
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        //세션 해제
        return "redirect:/"; //로그아웃 후 index페이지로 이동
    }
}
