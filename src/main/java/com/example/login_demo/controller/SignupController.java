package com.example.login_demo.controller;

import com.example.login_demo.domain.User;
import com.example.login_demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignupController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 생성자 주입: Spring이 알아서 Repository와 암호화기를 넣어줌
    public SignupController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/signup")          // 가입 화면 보여주기
    public String signupForm() {
        return "signup";            // templates/signup.html
    }

    @PostMapping("/signup")         // 가입 버튼 눌렀을 때
    public String signup(@RequestParam String username, @RequestParam String password) {
        if (userRepository.existsByUsername(username)) {
            return "redirect:/signup?exists";
        }
        String encoded = passwordEncoder.encode(password);   // 평문 → BCrypt 해시
        userRepository.save(new User(username, encoded, "USER"));
        return "redirect:/login?registered";
    }
}