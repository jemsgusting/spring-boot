package com.example.camping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Camping System";
    }

    // ❌ 아래 세 개는 삭제하세요:
    // @GetMapping("/home")     -> 뷰 이름 반환이라 RestController와 안 맞음, 그리고 "/" 이미 있음
    // @GetMapping("/login")    -> AuthController와 충돌
    // @GetMapping("/register") -> AuthController와 충돌
}
