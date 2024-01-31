package com.make.backendroadmap.domain.controller;

import com.make.backendroadmap.domain.exception.UndefinedAddressException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home(@RequestParam String path) {
        if (path.equals("RoadMap")) {
            return "category/roadMap";
        }
        if (path.equals("Practice")) {
            return "category/practice";
        }
        if (path.equals("Coding Test")) {
            return "category/codingTest";
        }
        throw new UndefinedAddressException();
    }
}
