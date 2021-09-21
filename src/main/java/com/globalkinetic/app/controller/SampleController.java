package com.globalkinetic.app.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SampleController {
    @GetMapping("/api/sample")
    public Map<String, String> get(HttpServletRequest req) {
    	
        Map<String, String> m = new HashMap<>();
        m.put("serbia", "belgrade");
        return m;
    }
}
