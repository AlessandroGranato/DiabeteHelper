package com.pyrosandro.DTO;

public class CalculatorDTO {

    private String alimentName;
    private float quantity;

    public String getAlimentName() {
        return alimentName;
    }

    public void setAlimentName(String alimentName) {
        this.alimentName = alimentName;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CalculatorDTO{" +
                "alimentName='" + alimentName + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
