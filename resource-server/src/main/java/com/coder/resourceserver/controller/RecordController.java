package com.coder.resourceserver.controller;

import com.coder.resourceserver.dao.Record;
import com.coder.resourceserver.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/record")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RecordController {

    @Autowired
    private RecordService recordService;

    @PostMapping("/add")
    public void addRecord(@RequestBody Record record, Principal principal) {
        recordService.addRecord(principal.getName(), record);
    }
}
