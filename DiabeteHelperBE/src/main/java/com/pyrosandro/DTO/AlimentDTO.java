package com.pyrosandro.DTO;

public class AlimentDTO {

    private Long id;
    private String name;
    private Float energy;
    private Float fat;
    private Float saturatedFat;
    private Float carbs;
    private Float sugarCarbs;
    private Float fibers;
    private Float proteins;
    private Float salt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
        return "AlimentDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
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
