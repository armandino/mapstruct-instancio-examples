/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.example;

import org.junit.Test;
import org.mapstruct.example.dto.Customer;
import org.mapstruct.example.dto.CustomerDto;
import org.mapstruct.example.dto.OrderItem;
import org.mapstruct.example.dto.OrderItemDto;
import org.mapstruct.example.mapper.CustomerMapper;

import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

/**
 * @author Filip Hrisafov
 */
public class CustomerMapperTest {

    @Test
    public void testMapDtoToEntity() {

        CustomerDto customerDto = new CustomerDto();
        customerDto.id = 10L;
        customerDto.customerName = "test-name";
        OrderItemDto order1 = new OrderItemDto();
        order1.name = "Table";
        order1.quantity = 2L;
        customerDto.orders = new ArrayList<>(Collections.singleton(order1));

        Customer customer = CustomerMapper.MAPPER.toCustomer(customerDto);

        assertThat(customer.getId()).isEqualTo(10);
        assertThat(customer.getName()).isEqualTo("test-name");
        assertThat(customer.getOrderItems())
                .extracting("name", "quantity")
                .containsExactly(tuple("Table", 2L));
    }

    @Test
    public void testEntityDtoToDto() {

        Customer customer = new Customer();
        customer.setId(10L);
        customer.setName("test-name");
        OrderItem order1 = new OrderItem();
        order1.setName("Table");
        order1.setQuantity(2L);
        customer.setOrderItems(Collections.singleton(order1));

        CustomerDto customerDto = CustomerMapper.MAPPER.fromCustomer(customer);

        assertThat(customerDto.id).isEqualTo(10);
        assertThat(customerDto.customerName).isEqualTo("test-name");
        assertThat(customerDto.orders)
                .extracting("name", "quantity")
                .containsExactly(tuple("Table", 2L));
    }
}
