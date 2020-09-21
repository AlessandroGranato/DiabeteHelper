package com.pyrosandro.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotEmpty;

@Entity
@NamedQuery(name = Aliment.ALIMENT_FIND_ALL_ALIMENTS, query = "select a from Aliment a")
@NamedQuery(name = Aliment.ALIMENT_FIND_ALIMENT_BY_NAME, query = "select a from Aliment a where name = :name")
public class Aliment extends AbstractEntity {

    public static final String ALIMENT_FIND_ALL_ALIMENTS = "Aliment.findAllAliments";
    public static final String ALIMENT_FIND_ALIMENT_BY_NAME = "Aliment.findAlimentByName";

    @NotEmpty(message = "name must be set!")
    @Column(unique = true)
    private String name;
    private Float energy;
    private Float fat;
    private Float saturatedFat;
    private Float carbs;
    private Float sugarCarbs;
    private Float fibers;
    private Float proteins;
    private Float salt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getEnergy() {
        return energy;
    }

    public void setEnergy(Float energy) {
        this.energy = energy;
    }

    public Float getFat() {
        return fat;
    }

    public void setFat(Float fat) {
        this.fat = fat;
    }

    public Float getSaturatedFat() {
        return saturatedFat;
    }

    public void setSaturatedFat(Float saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public Float getCarbs() {
        return carbs;
    }

    public void setCarbs(Float carbs) {
        this.carbs = carbs;
    }

    public Float getSugarCarbs() {
        return sugarCarbs;
    }

    public void setSugarCarbs(Float sugarCarbs) {
        this.sugarCarbs = sugarCarbs;
    }

    public Float getFibers() {
        return fibers;
    }

    public void setFibers(Float fibers) {
        this.fibers = fibers;
    }

    public Float getProteins() {
        return proteins;
    }

    public void setProteins(Float proteins) {
        this.proteins = proteins;
    }

    public Float getSalt() {
        return salt;
    }

    public void setSalt(Float salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "Aliment{" +
                "name='" + name + '\'' +
                ", energy=" + energy +
                ", fat=" + fat +
                ", saturatedFat=" + saturatedFat +
                ", carbs=" + carbs +
                ", sugarCarbs=" + sugarCarbs +
                ", fibers=" + fibers +
                ", proteins=" + proteins +
                ", salt=" + salt +
                '}';
    }
}
