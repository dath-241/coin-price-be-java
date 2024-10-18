package com.javaweb.controller;

import com.javaweb.model.mongo_entity.userData;
import com.javaweb.repository.UserRepository;
import com.javaweb.service.discordBot.entryBot;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private entryBot entryBot;

    @Autowired
    UserRepository userRepository;

    @DeleteMapping("/removeUserByUsername")
    public ResponseEntity<?> getUser(@RequestParam List<String> username) {
        entryBot.setImg_url("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSll683Wj2BKonhIpgn--paTzycD06XnvqblQ&s");
        entryBot.setUsername("Admin Bot");

        for (String user : username) {
            userData userData = userRepository.findByUsername(user);
            if (userData == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %s not found", user));
            }

            userRepository.deleteByUsername(userData.getUsername());
            entryBot.sendMessage(String.format("Admin vừa xóa user ***%s*** ra khỏi hệ thống", userData.getUsername()));
        }

        return new ResponseEntity<>(
                new Responses(
                        new Date(),
                        "200",
                        "Xóa user thành công!",
                        "/admin/removeUserByUsername"),
                HttpStatus.OK);
    }

    @GetMapping("/getAllUser")
    public List<userData> getAllUser() {
        List<userData> userDataList = userRepository.findAll();

        return userDataList;
    }

    @Data
    @AllArgsConstructor
    private class Responses{
        private Date timestamp;
        private String status;
        private String message;
        private String path;
    }
}
