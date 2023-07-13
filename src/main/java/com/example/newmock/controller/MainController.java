package com.example.newmock.controller;

import com.example.newmock.exception.ValidationException;
import com.example.newmock.model.RequestDTO;
import com.example.newmock.service.LogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MainController {
    @Autowired
    private LogicService logicService;

    @PostMapping(value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity postBalances(@RequestBody RequestDTO requestDTO) {

        try {
            logicService.validateRequest(requestDTO);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok(logicService.getResponseJson(requestDTO));

    }
}