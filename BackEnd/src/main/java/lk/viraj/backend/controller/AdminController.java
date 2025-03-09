package lk.viraj.backend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/admin")
public class AdminController {
    @PostMapping(path = "/test")
    public String test() {
        return "Admin Controller";
    }
}
