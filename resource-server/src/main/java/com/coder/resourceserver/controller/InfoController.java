package com.coder.resourceserver.controller;

import com.coder.resourceserver.dao.Info;
import com.coder.resourceserver.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/info")
@CrossOrigin(origins = "*", maxAge = 3600)
public class InfoController {

    @Autowired
    private InfoService infoService;

    @GetMapping
    public Info getInfo(Principal principal) {
        return infoService.getInfo(principal.getName());
    }

    @PostMapping("/updateInfo")
    public void updateInfo(@RequestBody Info info, Principal principal) {
        infoService.updateInfo(principal.getName(), info);

    }
}
