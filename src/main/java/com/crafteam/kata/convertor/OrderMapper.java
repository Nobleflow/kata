package com.crafteam.kata.convertor;



import com.crafteam.kata.dto.OrderDto;
import com.crafteam.kata.model.Order;

import com.crafteam.kata.dto.ProductDto;
import com.crafteam.kata.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class OrderMapper {

    private final ClientMapper clientMapper = new ClientMapper();

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
        orderDto.setClient(clientMapper.toDto(order.getClient()));
        orderDto.setProducts(toProductDtoList(order.getProducts()));

        return orderDto;
    }

    public Order toEntity(OrderDto orderDto) {
        if (orderDto == null) {
            return null;
        }

        Order order = new Order();
        orderDto.setId(order.getId());
        order.setOrderAt(orderDto.getOrderAt());
        order.setDateBeginOrder(orderDto.getDateBeginOrder());
        order.setDateEndOrder(orderDto.getDateEndOrder());
        order.setDeliveryType(orderDto.getDeliveryType());
        order.setClient(clientMapper.toEntity(orderDto.getClient()));
        order.setProducts(toProductEntityList(orderDto.getProducts()));

        return order;
    }

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
