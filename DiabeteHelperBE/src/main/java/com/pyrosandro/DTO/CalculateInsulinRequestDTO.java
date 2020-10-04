package com.pyrosandro.DTO;

import java.util.Map;

public class CalculateInsulinRequestDTO {
    Float glycemiaCurrentValue;
    Float lastInsulinQuantity;
    String lastInsulinDateTimeString;
    Map<String, Float> alimentsWithQuantities;

    public Float getGlycemiaCurrentValue() {
        return glycemiaCurrentValue;
    }

    public void setGlycemiaCurrentValue(Float glycemiaCurrentValue) {
        this.glycemiaCurrentValue = glycemiaCurrentValue;
    }

    public Float getLastInsulinQuantity() {
        return lastInsulinQuantity;
    }

    public void setLastInsulinQuantity(Float lastInsulinQuantity) {
        this.lastInsulinQuantity = lastInsulinQuantity;
    }

    public String getLastInsulinDateTimeString() {
        return lastInsulinDateTimeString;
    }

    public void setLastInsulinDateTimeString(String lastInsulinDateTimeString) {
        this.lastInsulinDateTimeString = lastInsulinDateTimeString;
    }

    public Map<String, Float> getAlimentsWithQuantities() {
        return alimentsWithQuantities;
    }

    public void setAlimentsWithQuantities(Map<String, Float> alimentsWithQuantities) {
        this.alimentsWithQuantities = alimentsWithQuantities;
    }

    @Override
    public String toString() {
        return "CalculateInsulinRequestDTO{" +
                "glycemiaCurrentValue=" + glycemiaCurrentValue +
                ", lastInsulinQuantity=" + lastInsulinQuantity +
                ", lastInsulinDateTimeString='" + lastInsulinDateTimeString + '\'' +
                ", alimentsWithQuantities=" + alimentsWithQuantities +
                '}';
    }
}
