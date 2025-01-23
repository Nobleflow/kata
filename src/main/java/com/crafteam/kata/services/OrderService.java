package com.crafteam.kata.services;

import com.crafteam.kata.dto.ClientDto;
import com.crafteam.kata.dto.OrderDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderService {


    Flux<OrderDto> getAllOrders();

    Mono<OrderDto> saveOrder(OrderDto orderDTO);


    Flux<OrderDto> getAllOrderByClients(ClientDto clientDto);
}
