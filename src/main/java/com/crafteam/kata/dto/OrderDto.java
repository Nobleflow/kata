package com.crafteam.kata.dto;

import com.crafteam.kata.enums.DeliveryType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@ToString

public class OrderDto {

    private  Long id;

    private List<ProductDto> products;


    private DeliveryType deliveryType;


    private ClientDto client;



    private LocalDateTime orderAt;

    private LocalDateTime dateBeginOrder;



    private LocalDateTime dateEndOrder;

    private  String Status;
    private LocalDateTime deliveryDate;
    private String timeSlot;
    private boolean isReserved;




}
