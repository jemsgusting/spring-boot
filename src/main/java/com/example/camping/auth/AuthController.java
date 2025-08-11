package com.example.camping.auth;

import com.example.camping.user.User;
import com.example.camping.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// 303 리다이렉트를 쓰기 위한 import
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    public AuthController(UserRepository userRepository) { this.userRepository = userRepository; }

    // 홈 (상태 확인)
    @GetMapping("/")
    public String home() {
        return "home";
    }

    // 페이지
    @GetMapping("/login")
    public String loginPage() { return "login"; }

    @GetMapping("/register")
    public String registerPage() { return "register"; }

    // 회원가입 처리
    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           RedirectAttributes ra) {
        if (username.isBlank() || password.isBlank()) {
            ra.addFlashAttribute("err", "아이디/비밀번호를 입력하세요.");
            return "redirect:/register";
        }
        if (userRepository.existsByUsername(username)) {
            ra.addFlashAttribute("err", "이미 존재하는 아이디입니다.");
            return "redirect:/register";
        }
        User u = new User();
        u.setUsername(username);
        u.setPassword(password);
        userRepository.save(u);

        ra.addFlashAttribute("msg", "회원가입 성공! 로그인하세요.");
        return "redirect:/login";
    }

    // 로그인 처리  ⬅️ 반환 타입을 RedirectView로 변경
    @PostMapping("/login")
    public RedirectView login(@RequestParam String username,
                              @RequestParam String password,
                              HttpSession session,
                              RedirectAttributes ra) {
        var opt = userRepository.findByUsername(username);
        if (opt.isEmpty() || !opt.get().getPassword().equals(password)) {
            ra.addFlashAttribute("err", "아이디 또는 비밀번호가 올바르지 않습니다.");
            RedirectView rv = new RedirectView("/login");
            rv.setStatusCode(HttpStatus.SEE_OTHER); // 303: POST 후 GET으로 전환
            return rv;
        }

        User user = opt.get();
        session.setAttribute("LOGIN_USER_ID", user.getId());
        session.setAttribute("LOGIN_USERNAME", user.getUsername());
        ra.addFlashAttribute("msg", "로그인 성공!");

        RedirectView rv = new RedirectView("/specials");
        rv.setStatusCode(HttpStatus.SEE_OTHER); // 303
        return rv;
    }

    // 로그아웃
    @PostMapping("/logout")
    public RedirectView logout(HttpSession session, RedirectAttributes ra) {
        session.invalidate();
        ra.addFlashAttribute("msg", "로그아웃 완료");
        RedirectView rv = new RedirectView("/login");
        rv.setStatusCode(HttpStatus.SEE_OTHER); // 303: POST 후 GET 강제
        return rv;
    }
}
