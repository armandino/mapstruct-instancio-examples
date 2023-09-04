/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.jpa;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Sjaak Derksen
 */
class SourceTargetMapperTest {

    /**
     * Test of toTarget method, of class SourceTargetMapper.
     */
    @Test
    void testToTarget() {
        // Given
        ParentDto parent = Instancio.create(ParentDto.class);
        JpaContext jpaCtx = new JpaContext(null);

        // When
        ParentEntity parentEntity = SourceTargetMapper.MAPPER.toEntity(parent, jpaCtx);

        // Then
        assertThat(parentEntity.getName()).isEqualTo(parent.getName());
        assertThat(parentEntity.getChildren()).hasSameSizeAs(parent.getChildren());

        for (int i = 0; i < parentEntity.getChildren().size(); i++) {
            ChildEntity childEntity = parentEntity.getChildren().get(i);
            ChildDto childDto = parent.getChildren().get(i);

            assertThat(childEntity.getName()).isEqualTo(childDto.getName());
            assertThat(childEntity.getMyParent()).isEqualTo(parentEntity);
        }
    }
}
