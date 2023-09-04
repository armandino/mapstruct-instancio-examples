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
import org.mapstruct.example.dto.FishTankDto;
import org.mapstruct.example.dto.FishTankWithNestedDocumentDto;
import org.mapstruct.example.mapper.FishTankMapper;
import org.mapstruct.example.mapper.FishTankMapperConstant;
import org.mapstruct.example.mapper.FishTankMapperExpression;
import org.mapstruct.example.mapper.FishTankMapperWithDocument;
import org.mapstruct.example.model.FishTank;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Sjaak Derksen
 */
@ExtendWith(InstancioExtension.class)
class FishTanksMappersTest {

    @Test
    void shouldAutomapAndHandleSourceAndTargetPropertyNesting() {

        // -- prepare
        FishTank source = createFishTank();

        // -- action
        FishTankDto target = FishTankMapper.INSTANCE.map(source);

        // -- result
        assertThat(target.getName()).isEqualTo(source.getName());

        // fish and fishDto can be automapped
        assertThat(target.getFish()).isNotNull();
        assertThat(target.getFish().getKind()).isEqualTo(source.getFish().getType());
        assertThat(target.getFish().getName()).isNull();

        // automapping takes care of mapping property "waterPlant".
        assertThat(target.getPlant()).isNotNull();
        assertThat(target.getPlant().getKind()).isEqualTo(source.getPlant().getKind());

        // ornament (nested asymetric source)
        assertThat(target.getOrnament()).isNotNull();
        assertThat(target.getOrnament().getType()).isEqualTo(source.getInterior().getOrnament().getType());

        // material (nested asymetric target)
        assertThat(target.getMaterial()).isNotNull();
        assertThat(target.getMaterial().getManufacturer()).isNull();
        assertThat(target.getMaterial().getMaterialType()).isNotNull();
        assertThat(target.getMaterial().getMaterialType().getType()).isEqualTo(source.getMaterial().getType());

        //  first symetric then asymetric
        assertThat(target.getQuality()).isNotNull();
        assertThat(target.getQuality().getReport()).isNotNull();
        assertThat(target.getQuality().getReport().getVerdict())
                .isEqualTo(source.getQuality().getReport().getVerdict());
        assertThat(target.getQuality().getReport().getOrganisation().getApproval()).isNull();
        assertThat(target.getQuality().getReport().getOrganisation()).isNotNull();
        assertThat(target.getQuality().getReport().getOrganisation().getName())
                .isEqualTo(source.getQuality().getReport().getOrganisationName());
    }

    @Test
    void shouldAutomapAndHandleSourceAndTargetPropertyNestingReverse() {

        // -- prepare
        FishTank source = createFishTank();

        // -- action
        FishTankDto target = FishTankMapper.INSTANCE.map(source);
        FishTank source2 = FishTankMapper.INSTANCE.map(target);

        // -- result
        assertThat(source2.getName()).isEqualTo(source.getName());

        // fish
        assertThat(source2.getFish()).isNotNull();
        assertThat(source2.getFish().getType()).isEqualTo(source.getFish().getType());

        // interior, designer will not be mapped (asymetric) to target. Here it shows.
        assertThat(source2.getInterior()).isNotNull();
        assertThat(source2.getInterior().getDesigner()).isNull();
        assertThat(source2.getInterior().getOrnament()).isNotNull();
        assertThat(source2.getInterior().getOrnament().getType())
                .isEqualTo(source.getInterior().getOrnament().getType());

        // material
        assertThat(source2.getMaterial()).isNotNull();
        assertThat(source2.getMaterial().getType()).isEqualTo(source.getMaterial().getType());

        // plant
        assertThat(source2.getPlant().getKind()).isEqualTo(source.getPlant().getKind());

        // quality
        assertThat(source2.getQuality().getReport()).isNotNull();
        assertThat(source2.getQuality().getReport().getOrganisationName())
                .isEqualTo(source.getQuality().getReport().getOrganisationName());
        assertThat(source2.getQuality().getReport().getVerdict())
                .isEqualTo(source.getQuality().getReport().getVerdict());
    }

    @Test
    void shouldAutomapAndHandleSourceAndTargetPropertyNestingAndConstant() {

        // -- prepare
        FishTank source = createFishTank();

        // -- action
        FishTankDto target = FishTankMapperConstant.INSTANCE.map(source);

        // -- result

        // fixed value
        assertThat(target.getFish().getName()).isEqualTo("Nemo");

        // automapping takes care of mapping property "waterPlant".
        assertThat(target.getPlant()).isNotNull();
        assertThat(target.getPlant().getKind()).isEqualTo(source.getPlant().getKind());

        // non-nested and constant
        assertThat(target.getMaterial()).isNotNull();
        assertThat(target.getMaterial().getManufacturer()).isEqualTo("MMM");
        assertThat(target.getMaterial().getMaterialType()).isNotNull();
        assertThat(target.getMaterial().getMaterialType().getType()).isEqualTo(source.getMaterial().getType());

        assertThat(target.getOrnament()).isNull();
        assertThat(target.getQuality()).isNull();

    }

    @Test
    void shouldAutomapAndHandleSourceAndTargetPropertyNestingAndExpresion() {

        // -- prepare
        FishTank source = createFishTank();

        // -- action
        FishTankDto target = FishTankMapperExpression.INSTANCE.map(source);

        // -- result
        assertThat(target.getFish().getName()).isEqualTo("Jaws");

        assertThat(target.getMaterial()).isNull();
        assertThat(target.getOrnament()).isNull();
        assertThat(target.getPlant()).isNull();

        assertThat(target.getQuality()).isNotNull();
        assertThat(target.getQuality().getReport()).isNotNull();
        assertThat(target.getQuality().getReport().getVerdict())
                .isEqualTo(source.getQuality().getReport().getVerdict());
        assertThat(target.getQuality().getReport().getOrganisation()).isNotNull();
        assertThat(target.getQuality().getReport().getOrganisation().getApproval()).isNull();
        assertThat(target.getQuality().getReport().getOrganisation().getName()).isEqualTo("Dunno");
    }

    @Test
    void shouldAutomapIntermediateLevelAndMapConstant() {

        // -- prepare
        FishTank source = createFishTank();

        // -- action
        FishTankWithNestedDocumentDto target = FishTankMapperWithDocument.INSTANCE.map(source);

        // -- result
        assertThat(target.getFish().getName()).isEqualTo("Jaws");

        assertThat(target.getMaterial()).isNull();
        assertThat(target.getOrnament()).isNull();
        assertThat(target.getPlant()).isNull();

        assertThat(target.getQuality()).isNotNull();
        assertThat(target.getQuality().getDocument()).isNotNull();
        assertThat(target.getQuality().getDocument().getVerdict())
                .isEqualTo(source.getQuality().getReport().getVerdict());
        assertThat(target.getQuality().getDocument().getOrganisation()).isNotNull();
        assertThat(target.getQuality().getDocument().getOrganisation().getApproval()).isNull();
        assertThat(target.getQuality().getDocument().getOrganisation().getName()).isEqualTo("NoIdeaInc");
    }

    private FishTank createFishTank() {
        return Instancio.create(FishTank.class);
    }
}
