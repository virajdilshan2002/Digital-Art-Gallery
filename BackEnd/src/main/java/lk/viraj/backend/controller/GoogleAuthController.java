package lk.viraj.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login/oauth2/code/google")
@CrossOrigin
public class GoogleAuthController {

    @GetMapping
    public String googleAuth() {
        System.out.println("Google Auth");
        return "Google Auth";
    }
}
