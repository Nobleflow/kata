package com.crafteam.kata.controller;

import com.crafteam.kata.dto.OrderDto;
import com.crafteam.kata.services.OrderService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping
    public Flux<EntityModel<OrderDto>> getAllOrders() {
        return orderService.getAllOrders()
                .map(order -> {
                    EntityModel<OrderDto> model = EntityModel.of(order);
                    model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).getOrderById(order.getId())).withSelfRel());
                    return model;
                });
    }


    @PostMapping
    public Mono<EntityModel<OrderDto>> saveOrder(@RequestBody OrderDto orderDto) {
        return orderService.saveOrder(orderDto)
                .map(order -> {
                    EntityModel<OrderDto> model = EntityModel.of(order);
                    model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).getOrderById(order.getId())).withSelfRel());
                    model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).getAllOrders()).withRel("all-orders"));
                    return model;
                });
    }


    @GetMapping("/{id}")
    public Mono<EntityModel<OrderDto>> getOrderById(@PathVariable Long id) {
        return orderService.getAllOrders()
                .filter(order -> order.getId().equals(id))
                .next()
                .map(order -> {
                    EntityModel<OrderDto> model = EntityModel.of(order);
                    model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).getAllOrders()).withRel("all-orders"));
                    model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).getOrderById(order.getId())).withSelfRel());
                    return model;
                });
    }


    @GetMapping(value = "/stream", produces = "text/event-stream")
    public Flux<OrderDto> streamOrders() {
        return orderService.getAllOrders();
    }
}
