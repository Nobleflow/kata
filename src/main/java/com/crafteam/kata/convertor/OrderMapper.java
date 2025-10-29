package com.crafteam.kata.convertor;

import com.crafteam.kata.dto.OrderDto;
import com.crafteam.kata.model.Order;
import com.crafteam.kata.dto.ProductDto;
import com.crafteam.kata.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class for converting between Order entity and OrderDto.
 * 
 * Handles complex mapping including nested objects (Client, Products)
 * and provides bidirectional conversion between domain and DTO layers.
 */
@Component
public class OrderMapper {

    private final ClientMapper clientMapper = new ClientMapper();

    /**
     * Converts an Order entity to an OrderDto.
     * 
     * @param order the Order entity to convert
     * @return the corresponding OrderDto with all fields mapped, or null if input is null
     */
    public OrderDto toDto(Order order) {
        if (order == null) {
            return null;
        }

        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setOrderAt(order.getOrderAt());
        orderDto.setDateBeginOrder(order.getDateBeginOrder());
        orderDto.setDateEndOrder(order.getDateEndOrder());
        orderDto.setDeliveryType(order.getDeliveryType());
        orderDto.setStatus(order.getStatus());
        orderDto.setDeliveryDate(order.getDeliveryDate());
        orderDto.setTimeSlot(order.getTimeSlot());
        orderDto.setReserved(order.isReserved());
        orderDto.setClient(clientMapper.toDto(order.getClient()));
        orderDto.setProducts(toProductDtoList(order.getProducts()));

        return orderDto;
    }

    /**
     * Converts an OrderDto to an Order entity.
     * 
     * @param orderDto the OrderDto to convert
     * @return the corresponding Order entity with all fields mapped, or null if input is null
     */
    public Order toEntity(OrderDto orderDto) {
        if (orderDto == null) {
            return null;
        }

        Order order = new Order();
        order.setId(orderDto.getId());
        order.setOrderAt(orderDto.getOrderAt());
        order.setDateBeginOrder(orderDto.getDateBeginOrder());
        order.setDateEndOrder(orderDto.getDateEndOrder());
        order.setDeliveryType(orderDto.getDeliveryType());
        order.setStatus(orderDto.getStatus());
        order.setDeliveryDate(orderDto.getDeliveryDate());
        order.setTimeSlot(orderDto.getTimeSlot());
        order.setReserved(orderDto.isReserved());
        order.setClient(clientMapper.toEntity(orderDto.getClient()));
        order.setProducts(toProductEntityList(orderDto.getProducts()));

        return order;
    }

    /**
     * Converts a list of Product entities to a list of ProductDtos.
     * 
     * @param products the list of Product entities
     * @return the list of ProductDtos, or null if input is null
     */
    private List<ProductDto> toProductDtoList(List<Product> products) {
        if (products == null) {
            return null;
        }
        return products.stream()
                .map(product -> {
                    ProductDto productDto = new ProductDto();
                    productDto.setLabel(product.getLabel());
                    return productDto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Converts a list of ProductDtos to a list of Product entities.
     * 
     * @param productDtos the list of ProductDtos
     * @return the list of Product entities, or null if input is null
     */
    private List<Product> toProductEntityList(List<ProductDto> productDtos) {
        if (productDtos == null) {
            return null;
        }
        return productDtos.stream()
                .map(productDto -> {
                    Product product = new Product();
                    product.setLabel(productDto.getLabel());
                    return product;
                })
                .collect(Collectors.toList());
    }
}
