package org.example.authservice.Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/health")
public class TestContoller {


    @GetMapping("/jwt")
    public  String test(){
        return "comformerd";
    }
}
