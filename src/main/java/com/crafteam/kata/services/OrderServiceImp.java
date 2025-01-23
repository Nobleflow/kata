package com.crafteam.kata.services;

import com.crafteam.kata.conf.DeliveryTimeSlotConfig;
import com.crafteam.kata.convertor.ClientMapper;
import com.crafteam.kata.convertor.OrderMapper;
import com.crafteam.kata.dto.ClientDto;
import com.crafteam.kata.dto.OrderDto;
import com.crafteam.kata.model.Order;
import com.crafteam.kata.repository.OrderRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImp implements OrderService {

    private static final int DELIVERY_TODAY_CUTOFF_HOUR = 16;
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

    @Override
    public Flux<OrderDto> getAllOrders() {
        return Mono.fromCallable(orderRepository::findAll)
                .subscribeOn(Schedulers.boundedElastic())
                .flatMapMany(Flux::fromIterable)
                .map(orderMapper::toDto);
    }

    @Override
    public Mono<OrderDto> saveOrder(OrderDto orderDTO) {
        return Mono.fromCallable(() -> {

                    if (orderDTO.getDeliveryType() != null) {
                        String deliveryType = orderDTO.getDeliveryType().name();
                        String timeSlotConfigString = timeSlotConfig.getTimeSlots().get(deliveryType);

                        if (timeSlotConfigString == null) {
                            throw new IllegalArgumentException("Invalid delivery type: " + deliveryType);
                        }

                        List<String> availableSlots = List.of(timeSlotConfigString.split(","));
                        if (!availableSlots.contains(orderDTO.getTimeSlot())) {
                            throw new IllegalArgumentException("Invalid time slot selected: " + orderDTO.getTimeSlot());
                        }


                        if (orderDTO.getDeliveryType().name().equals("DELIVERY_TODAY")) {
                            if (LocalDateTime.now().getHour() >= DELIVERY_TODAY_CUTOFF_HOUR) {
                                throw new IllegalArgumentException("Orders can't be delivered today.");
                            }
                        }


                        if (orderDTO.getDeliveryType().name().equals("DELIVERY_ASAP")) {
                            orderDTO.setDateBeginOrder(LocalDateTime.now());
                            orderDTO.setDateEndOrder(LocalDateTime.now().plusHours(2));
                        }
                    }

                    return orderMapper.toDto(orderRepository.save(orderMapper.toEntity(orderDTO)));
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

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
                    System.err.println("Erreur lors de la recuperation des commandes : " + e.getMessage());
                    return Flux.empty();
                });
    }
}