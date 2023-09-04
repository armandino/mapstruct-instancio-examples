/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.jpa;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;

import javax.persistence.EntityManager;

/**
 * @author Sjaak Derksen
 */
public class JpaContext {

    private final EntityManager em;

    private ParentEntity parentEntity;

    public JpaContext(EntityManager em) {
        this.em = em;
    }

    @BeforeMapping
    public void setEntity(@MappingTarget ParentEntity parentEntity) {
        this.parentEntity = parentEntity;
        // you could do stuff with the EntityManager here
    }

    @AfterMapping
    public void establishRelation(@MappingTarget ChildEntity childEntity) {
        childEntity.setMyParent(parentEntity);
        // you could do stuff with the EntityManager here
    }

}
