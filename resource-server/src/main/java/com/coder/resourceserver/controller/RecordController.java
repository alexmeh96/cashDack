package com.coder.resourceserver.controller;

import com.coder.resourceserver.dao.Category;
import com.coder.resourceserver.dao.Record;
import com.coder.resourceserver.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/record")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RecordController {

    @Autowired
    private RecordService recordService;

    @GetMapping("/{id}")
    public Record getRecord(@PathVariable Long id) {
        return recordService.findRecord(id);
    }

    @PostMapping("/add")
    public void addRecord(@RequestBody Record record, Principal principal) {
        recordService.addRecord(principal.getName(), record);
    }

    @GetMapping
    public List<Record> allRecords(Principal principal) {
        return recordService.allRecords(principal.getName());
    }
}
