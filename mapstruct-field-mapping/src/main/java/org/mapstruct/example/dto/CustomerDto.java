/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.example.dto;

import java.util.List;

/**
 * @author Filip Hrisafov
 */
public class CustomerDto {

    public Long id;
    public String customerName;
    public List<OrderItemDto> orders;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(final String customerName) {
        this.customerName = customerName;
    }

    public List<OrderItemDto> getOrders() {
        return orders;
    }

    public void setOrders(final List<OrderItemDto> orders) {
        this.orders = orders;
    }
}
