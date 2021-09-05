package org.example.tests.spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("greeting/{user}")
    public String sayHello(@PathVariable String user){
        return "Hello, " + user;
    }
}
