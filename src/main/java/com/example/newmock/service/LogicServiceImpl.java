package com.example.newmock.service;

import com.example.newmock.controller.MainController;
import com.example.newmock.exception.LogException;
import com.example.newmock.exception.ValidationException;
import com.example.newmock.model.RequestDTO;
import com.example.newmock.model.ResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

@Service
public class LogicServiceImpl implements LogicService {

    private Logger log = LoggerFactory.getLogger(MainController.class);
    private ObjectMapper mapper = new ObjectMapper();

    public ResponseDTO getResponseJson(RequestDTO requestDTO) {

        char firstDigit = requestDTO.getClientId().charAt(0);

        BigDecimal maxLimit;
        String currency;
        String balance;

        switch (firstDigit) {
            case '8':
                maxLimit = new BigDecimal(2000.00);
                currency = "US";
                balance = getRandomBalance(maxLimit);
                break;
            case '9':
                maxLimit = new BigDecimal(1000.00);
                currency = "EU";
                balance = getRandomBalance(maxLimit);
                break;
            default:
                maxLimit = new BigDecimal(10000.00);
                currency = "RUB";
                balance = getRandomBalance(maxLimit);
        }

        ResponseDTO responseDTO = new ResponseDTO(requestDTO.getRqUID(),
                requestDTO.getClientId(),
                requestDTO.getAccount(),
                currency,
                balance,
                maxLimit.toString());

        try {
            log.info("\n***** Запрос *****" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.info("\n***** Ответ *****" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));
        } catch (JsonProcessingException e) {
            throw new LogException(e.getMessage());
        }

        return responseDTO;

    }

    public void validateRequest(RequestDTO requestDTO) {

        if (!requestDTO.getClientId().matches("\\d+")) {
            throw new ValidationException("clientId is not valid");
        }

        if (requestDTO.getRqUID() == null || requestDTO.getRqUID().length() == 0) {
            throw new ValidationException("rqUID is empty");

        }

        if (requestDTO.getClientId() == null || requestDTO.getClientId().length() == 0) {
            throw new ValidationException("clientId is empty");
        }

        if (requestDTO.getAccount() == null || requestDTO.getAccount().length() == 0) {
            throw new ValidationException("account is empty");
        }

        if (requestDTO.getOpenDate() == null || requestDTO.getOpenDate().length() == 0) {
            throw new ValidationException("openDate is empty");
        }

        if (requestDTO.getCloseDate() == null || requestDTO.getCloseDate().length() == 0) {
            throw new ValidationException("closeDate is empty");
        }
    }

    private String getRandomBalance(BigDecimal maxLimit) {
        return new BigDecimal(BigInteger.valueOf(new Random().nextInt(Integer.parseInt(maxLimit.toString()))),
                2).toString();
    }
}
