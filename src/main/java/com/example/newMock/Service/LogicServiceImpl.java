package com.example.newMock.Service;

import com.example.newMock.Controller.MainController;
import com.example.newMock.Model.RequestDTO;
import com.example.newMock.Model.ResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

@Service
public class LogicServiceImpl implements LogicService {

    private Logger log = LoggerFactory.getLogger(MainController.class);
    ObjectMapper mapper = new ObjectMapper();
    private ResponseEntity response;

    public Object getResponseJson(RequestDTO requestDTO) {

        if (!validateRequest(requestDTO)) {
            return response;
        }

        char firstDigit = requestDTO.getClientId().charAt(0);

        BigDecimal maxLimit;
        String currency;
        String balance;

        switch (firstDigit) {
            case '8':
                maxLimit = new BigDecimal(2000.00);
                currency = "US";
                balance = new BigDecimal(BigInteger.valueOf(new Random().nextInt(Integer.parseInt(maxLimit.toString()))),
                        2).toString();
                break;
            case '9':
                maxLimit = new BigDecimal(1000.00);
                currency = "EU";
                balance = new BigDecimal(BigInteger.valueOf(new Random().nextInt(Integer.parseInt(maxLimit.toString()))),
                        2).toString();
                break;
            default:
                maxLimit = new BigDecimal(10000.00);
                currency = "RUB";
                balance = new BigDecimal(BigInteger.valueOf(new Random().nextInt(Integer.parseInt(maxLimit.toString()))),
                        2).toString();
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return responseDTO;

    }

    private Boolean validateRequest(RequestDTO requestDTO) {
        if (requestDTO.getRqUID() == null || requestDTO.getRqUID().length() == 0) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("rqUID is empty");
            return false;
        }
        if (requestDTO.getClientId() == null || requestDTO.getClientId().length() == 0) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("clientId is empty");
            return false;
        }
        if (requestDTO.getAccount() == null || requestDTO.getAccount().length() == 0) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("account is empty");
            return false;
        }
        if (requestDTO.getOpenDate() == null || requestDTO.getOpenDate().length() == 0) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("openDate is empty");
            return false;
        }
        if (requestDTO.getCloseDate() == null || requestDTO.getCloseDate().length() == 0) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("closeDate is empty");
            return false;
        }
        return true;
    }
}
