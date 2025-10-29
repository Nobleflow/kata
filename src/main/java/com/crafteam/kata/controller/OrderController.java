package com.crafteam.kata.controller;

import com.crafteam.kata.dto.OrderDto;
import com.crafteam.kata.services.OrderService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST controller for managing orders.
 * 
 * Provides endpoints for creating, retrieving, and streaming orders
 * with HATEOAS support for hypermedia links. All endpoints return
 * reactive types for non-blocking operations.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Retrieves all orders with HATEOAS links.
     * 
     * @return a Flux of EntityModel containing OrderDto objects with self links
     */
    @GetMapping
    public Flux<EntityModel<OrderDto>> getAllOrders() {
        return orderService.getAllOrders()
                .map(order -> {
                    EntityModel<OrderDto> model = EntityModel.of(order);
                    model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class)
                            .getOrderById(order.getId())).withSelfRel());
                    return model;
                });
    }

    /**
     * Creates a new order.
     * 
     * Validates delivery type and time slot constraints before saving.
     * Returns the created order with HATEOAS links.
     * 
     * @param orderDto the order to create
     * @return a Mono of EntityModel containing the created OrderDto with links
     */
    @PostMapping
    public Mono<EntityModel<OrderDto>> saveOrder(@RequestBody OrderDto orderDto) {
        return orderService.saveOrder(orderDto)
                .map(order -> {
                    EntityModel<OrderDto> model = EntityModel.of(order);
                    model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class)
                            .getOrderById(order.getId())).withSelfRel());
                    model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class)
                            .getAllOrders()).withRel("all-orders"));
                    return model;
                });
    }

    /**
     * Retrieves a specific order by ID.
     * 
     * Note: This implementation fetches all orders and filters by ID,
     * which is inefficient. Consider optimizing with a repository method.
     * 
     * @param id the order ID to retrieve
     * @return a Mono of EntityModel containing the OrderDto with HATEOAS links,
     *         or empty if not found
     */
    @GetMapping("/{id}")
    public Mono<EntityModel<OrderDto>> getOrderById(@PathVariable Long id) {
        return orderService.getAllOrders()
                .filter(order -> order.getId().equals(id))
                .next()
                .map(order -> {
                    EntityModel<OrderDto> model = EntityModel.of(order);
                    model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class)
                            .getAllOrders()).withRel("all-orders"));
                    model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class)
                            .getOrderById(order.getId())).withSelfRel());
                    return model;
                });
    }

    /**
     * Streams all orders as Server-Sent Events.
     * 
     * Useful for real-time updates and monitoring. Clients can subscribe
     * to this endpoint to receive order updates as they occur.
     * 
     * @return a Flux of OrderDto objects streamed as text/event-stream
     */
    @GetMapping(value = "/stream", produces = "text/event-stream")
    public Flux<OrderDto> streamOrders() {
        return orderService.getAllOrders();
    }
}
