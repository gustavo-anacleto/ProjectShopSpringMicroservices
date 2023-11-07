package com.bosch.order.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private String id;
    private String orderNumber;
    private List<OrderItemDTO> orderItems = new ArrayList<>();
}
