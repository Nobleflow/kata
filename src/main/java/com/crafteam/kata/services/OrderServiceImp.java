package com.crafteam.kata.services;

import com.crafteam.kata.conf.DeliveryTimeSlotConfig;
import com.crafteam.kata.convertor.ClientMapper;
import com.crafteam.kata.convertor.OrderMapper;
import com.crafteam.kata.dto.ClientDto;
import com.crafteam.kata.dto.OrderDto;
import com.crafteam.kata.model.Order;
import com.crafteam.kata.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of the OrderService interface.
 * 
 * Handles all business logic related to orders including validation,
 * delivery type constraints, and time slot management. Uses reactive
 * programming with Project Reactor for non-blocking operations.
 */
@Service
public class OrderServiceImp implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImp.class);
    private static final int DELIVERY_TODAY_CUTOFF_HOUR = 16;
    private static final int DELIVERY_ASAP_HOURS = 2;
    
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ClientMapper clientMapper;
    private final DeliveryTimeSlotConfig timeSlotConfig;

    public OrderServiceImp(OrderRepository orderRepository,
                           OrderMapper orderMapper,
                           ClientMapper clientMapper,
                           DeliveryTimeSlotConfig timeSlotConfig) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.clientMapper = clientMapper;
        this.timeSlotConfig = timeSlotConfig;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Flux<OrderDto> getAllOrders() {
        return Mono.fromCallable(orderRepository::findAll)
                .subscribeOn(Schedulers.boundedElastic())
                .flatMapMany(Flux::fromIterable)
                .map(orderMapper::toDto);
    }

    /**
     * {@inheritDoc}
     * 
     * Performs the following validations:
     * 1. Validates delivery type exists in configuration
     * 2. Validates selected time slot is available for the delivery type
     * 3. For DELIVERY_TODAY: ensures order is placed before 4 PM
     * 4. For DELIVERY_ASAP: automatically sets delivery window to 2 hours from now
     */
    @Override
    public Mono<OrderDto> saveOrder(OrderDto orderDTO) {
        return Mono.fromCallable(() -> {
                    // Validate delivery type and time slot
                    if (orderDTO.getDeliveryType() != null) {
                        String deliveryType = orderDTO.getDeliveryType().name();
                        String timeSlotConfigString = timeSlotConfig.getTimeSlots().get(deliveryType);

                        if (timeSlotConfigString == null) {
                            throw new IllegalArgumentException("Invalid delivery type: " + deliveryType);
                        }

                        // Validate the selected time slot is available for this delivery type
                        List<String> availableSlots = List.of(timeSlotConfigString.split(","));
                        if (!availableSlots.contains(orderDTO.getTimeSlot())) {
                            throw new IllegalArgumentException("Invalid time slot selected: " + orderDTO.getTimeSlot());
                        }

                        // Special handling for same-day delivery
                        if (orderDTO.getDeliveryType().name().equals("DELIVERY_TODAY")) {
                            if (LocalDateTime.now().getHour() >= DELIVERY_TODAY_CUTOFF_HOUR) {
                                throw new IllegalArgumentException("Orders can't be delivered today after " + 
                                        DELIVERY_TODAY_CUTOFF_HOUR + ":00.");
                            }
                        }

                        // Special handling for ASAP delivery - automatically set delivery window
                        if (orderDTO.getDeliveryType().name().equals("DELIVERY_ASAP")) {
                            orderDTO.setDateBeginOrder(LocalDateTime.now());
                            orderDTO.setDateEndOrder(LocalDateTime.now().plusHours(DELIVERY_ASAP_HOURS));
                        }
                    }

                    return orderMapper.toDto(orderRepository.save(orderMapper.toEntity(orderDTO)));
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * {@inheritDoc}
     * 
     * Returns an empty Flux if an error occurs during retrieval,
     * logging the error for debugging purposes.
     */
    @Override
    public Flux<OrderDto> getAllOrderByClients(ClientDto clientDto) {
        return Mono.fromCallable(() -> {
                    var client = clientMapper.toEntity(clientDto);
                    List<Order> orders = orderRepository.findByClient(client);
                    return orders != null ? orders : List.<Order>of();
                })
                .subscribeOn(Schedulers.boundedElastic())
                .flatMapMany(Flux::fromIterable)
                .map(orderMapper::toDto)
                .onErrorResume(e -> {
                    logger.error("Error retrieving orders for client: {}", e.getMessage(), e);
                    return Flux.empty();
                });
    }
}