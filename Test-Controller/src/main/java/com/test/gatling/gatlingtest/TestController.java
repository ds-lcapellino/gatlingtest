package com.test.gatling.gatlingtest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j

public class TestController {

    @GetMapping("/hello")
    public String sayHello() {
        log.info("API /hello called.");
        return "Hello World";
    }

    @GetMapping("/mightFail")
    public ResponseEntity<String> mightFail() {
        log.info("API /mightFail called.");

        double random = Math.random(); //returns value between 0.0 and 1.0
        if(random < 0.2){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fail");
        }

        return ResponseEntity.ok("Success");
    }

    @PostMapping("/postSomething")
    public ResponseEntity<String> postSomething(@RequestBody String string) {
        log.info("API /postSomething called with string: {}.", string);
        return ResponseEntity.ok("Success");
    }
}
