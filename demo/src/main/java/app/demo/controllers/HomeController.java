package app.demo.controllers;

import jakarta.annotation.security.PermitAll;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PermitAll
@RestController
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public String index() {
        return "Welcome to Rate My Teacher app!";
    }
}
