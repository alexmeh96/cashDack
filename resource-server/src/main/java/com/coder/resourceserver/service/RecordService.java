package com.coder.resourceserver.service;

import com.coder.resourceserver.dao.Record;
import com.coder.resourceserver.dao.User;
import com.coder.resourceserver.repo.RecordRepository;
import com.coder.resourceserver.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RecordRepository recordRepository;

    public void addRecord(String email, Record record) {
        User user = userRepository.findByEmail(email).get();
        user.getRecords().add(record);
        record.setAuthor(user);
        recordRepository.save(record);
    }
}
