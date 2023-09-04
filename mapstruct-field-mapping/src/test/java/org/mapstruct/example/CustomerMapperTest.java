/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.example;

import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.instancio.junit.WithSettings;
import org.instancio.settings.Keys;
import org.instancio.settings.Settings;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.example.dto.Customer;
import org.mapstruct.example.dto.CustomerDto;
import org.mapstruct.example.mapper.CustomerMapper;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@ExtendWith(InstancioExtension.class)
class CustomerMapperTest {

    @WithSettings
    private final Settings settings = Settings.create()
            .set(Keys.COLLECTION_MIN_SIZE, 0)
            .set(Keys.COLLECTION_NULLABLE, true);

    @RepeatedTest(10)
    void dtoToEntity() {
        // Given
        CustomerDto dto = Instancio.create(CustomerDto.class);

        // When
        Customer entity = CustomerMapper.MAPPER.toCustomer(dto);

        // Then
        assertEquals(entity, dto);
    }

    @RepeatedTest(10)
    void entityToDto() {
        // Given
        Customer entity = Instancio.create(Customer.class);

        // When
        CustomerDto dto = CustomerMapper.MAPPER.fromCustomer(entity);

        // Then
        assertEquals(entity, dto);
    }

    private static void assertEquals(Customer entity, CustomerDto dto) {
        assertThat(dto.getId()).isEqualTo(entity.getId());
        assertThat(dto.getCustomerName()).isEqualTo(entity.getName());
        assertThat(dto.getOrders())
                .usingRecursiveComparison()
                .isEqualTo(entity.getOrderItems());
    }
}
