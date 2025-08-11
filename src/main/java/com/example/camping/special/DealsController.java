package com.example.camping.special;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DealsController {

    @GetMapping("/specials")
    public String specials(Model model, HttpSession session) {
        // 로그인한 사용자명 (세션에 저장해둔 값)
        Object username = session.getAttribute("LOGIN_USERNAME");
        model.addAttribute("loginUsername", username);

        // 세션 ID
        model.addAttribute("sessionId", session.getId());

        // 간단한 특가 이벤트 더미 데이터 (필요 시 DB 연동으로 대체)
        model.addAttribute("eventTitle", "⛺ 특가 캠핑 대전 48시간 한정!");
        model.addAttribute("eventDesc", "전국 인기 캠핑장 최대 60% 할인 · 오늘 자정 오픈");
        model.addAttribute("highlights", new String[]{
                "지리산/설악산/한라산 메가딜",
                "주말 피크타임 얼리버드",
                "리뷰 4.7★ 이상 캠핑장 한정"
        });

        return "specials";
    }
}
