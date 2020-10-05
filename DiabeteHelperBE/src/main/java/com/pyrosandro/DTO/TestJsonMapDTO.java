package com.pyrosandro.DTO;

import java.util.Map;

public class TestJsonMapDTO {

    private Map<String, String> myMap;

    public Map<String, String> getMyMap() {
        return myMap;
    }

    public void setMyMap(Map<String, String> myMap) {
        this.myMap = myMap;
    }

    @Override
    public String toString() {
        return "TestJsonMapDTO{" +
                "myMap=" + myMap +
                '}';
    }
}
