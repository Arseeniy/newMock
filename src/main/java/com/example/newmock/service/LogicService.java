package com.example.newmock.service;

import com.example.newmock.model.RequestDTO;

public interface LogicService {
    public Object getResponseJson(RequestDTO requestDTO);
    public void validateRequest(RequestDTO requestDTO);

}
