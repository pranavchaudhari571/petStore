package com.example.petstore.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OrderDTO {

    private Long customerId;
    private Long petId;
    private Date orderDate;
    private Boolean returned;
}
