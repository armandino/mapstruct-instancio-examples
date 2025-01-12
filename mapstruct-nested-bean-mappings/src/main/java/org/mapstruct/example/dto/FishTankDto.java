/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.example.dto;

/**
 * @author Sjaak Derksen
 */
public class FishTankDto {

    private FishDto fish;
    private WaterPlantDto plant;
    private String name;
    private MaterialDto material;
    private OrnamentDto ornament;
    private WaterQualityDto quality;

    public FishDto getFish() {
        return fish;
    }

    public void setFish(FishDto fish) {
        this.fish = fish;
    }

    public WaterPlantDto getPlant() {
        return plant;
    }

    public void setPlant(WaterPlantDto plant) {
        this.plant = plant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MaterialDto getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDto material) {
        this.material = material;
    }

    public OrnamentDto getOrnament() {
        return ornament;
    }

    public void setOrnament(OrnamentDto ornament) {
        this.ornament = ornament;
    }

    public WaterQualityDto getQuality() {
        return quality;
    }

    public void setQuality(WaterQualityDto quality) {
        this.quality = quality;
    }

}
