package com.coder.resourceserver.service;

import com.coder.resourceserver.dao.Info;
import com.coder.resourceserver.dao.User;
import com.coder.resourceserver.repo.UserRepository;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

@Service
public class InfoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BeanUtilsBean beanUtilsBean;

    public Info getInfo(String email) {
        User user = userRepository.findByEmail(email).get();
        return user.getInfo();
    }

    public void updateInfo(String email, Info info) {
        User user = userRepository.findByEmail(email).get();
        Info newInfo = user.getInfo();
        try {
            beanUtilsBean.copyProperties(newInfo, info);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        user.setInfo(newInfo);

        userRepository.save(user);
    }
}
