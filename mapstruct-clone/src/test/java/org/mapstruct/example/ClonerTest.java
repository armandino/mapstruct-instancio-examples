/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.example;

import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.example.dto.CustomerDto;
import org.mapstruct.example.mapper.Cloner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Sjaak Derksen
 */
@ExtendWith(InstancioExtension.class)
class ClonerTest {

    @Test
    void cloneDto() {
        // Given
        CustomerDto customer = Instancio.create(CustomerDto.class);

        // When
        CustomerDto clone = Cloner.MAPPER.clone(customer);

        // Then
        assertThat(clone)
                .isNotSameAs(customer)
                .usingRecursiveComparison()
                .isEqualTo(customer);
    }
}
