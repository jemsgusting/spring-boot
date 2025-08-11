package com.example.camping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;


@Controller
@RequestMapping("/reservation")
public class ReservationController {

    @GetMapping("/")
    public String index() {
        return "index"; // templates/index.html 렌더링
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // templates/login.html 렌더링
    }

    @GetMapping("/reserve")
    public String reserveForm(Model model) {
        // 예시: 예약 가능한 캠핑장 리스트 모델에 전달
        model.addAttribute("campgrounds", List.of(
                new Campground(1L, "지리산 캠핑장"),
                new Campground(2L, "설악산 캠핑장"),
                new Campground(3L, "한라산 캠핑장")
        ));
        return "reserve"; // templates/reserve.html 렌더링
    }

    @GetMapping("/success")
    public String reservationSuccess() {
        return "success"; // templates/success.html 렌더링
    }

    // 내부 정적 클래스: 실제 구현 시 DB에서 받아오면 이건 삭제 가능
    public static class Campground {
        private Long id;
        private String name;

        public Campground(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
