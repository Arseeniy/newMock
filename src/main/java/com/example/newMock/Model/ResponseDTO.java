package com.example.newMock.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseDTO {
    private String rqUID;
    private String clientID;
    private String account;
    private String currency;
    private String balance;
    private String maxLimit;
}
