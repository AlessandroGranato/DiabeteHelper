package com.pyrosandro.services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pyrosandro.DTO.CalculatorDTO;
import com.pyrosandro.entities.Aliment;
import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Stateless
public class AlimentService {

    //TODO - retrieve these values from user's properties
    public static final Float GLYCEMIA_PER_INSULIN_UNIT_DECREASE = 60F;
    public static final Float CARBS_PER_INSULIN_UNIT_DECREASE = 12F;
    public static final Float DESIRED_GLYCEMIA_LEVEL = 115F;
    public static final Float TOTAL_HOURS_OF_GLYCEMIA_EFFECT = 4F;
    public static final Float TOTAL_MINUTES_OF_GLYCEMIA_EFFECT = 240F;

    @Inject
    EntityManager entityManager;

    @Inject
    private Logger LOG;

    public Collection<Aliment> getAllAliments() {
        return entityManager.createNamedQuery(Aliment.ALIMENT_FIND_ALL_ALIMENTS, Aliment.class)
                .getResultList();
    }

    public Aliment saveAliment(Aliment aliment) {
        List<Aliment> aliments = entityManager.createNamedQuery(Aliment.ALIMENT_FIND_ALIMENT_BY_NAME, Aliment.class)
                .setParameter("name", aliment.getName())
                .getResultList();

        if(aliments.isEmpty())
            entityManager.persist(aliment);
        else {
            aliment.setId(aliments.get(0).getId());
            entityManager.merge(aliment);
        }

        return aliment;
    }

    public Aliment getAlimentByName(String name) {
        List<Aliment> aliments = entityManager.createNamedQuery(Aliment.ALIMENT_FIND_ALIMENT_BY_NAME, Aliment.class)
                .setParameter("name", name)
                .getResultList();
        if(aliments.isEmpty())
            return null;
        else
            return aliments.get(0);
    }

    public Float calculateInsulin(String input) {

        // To calculate the insulin we need to take care of three factors: residualInsulin, glycemiaCurrentValue and totalCarbs we are eating
        Float totalInsulin = 0F;
        Float totalCarbs = 0F;

        String myJSONString = input;
        JsonObject jsonObject = new Gson().fromJson(myJSONString, JsonObject.class);
        Float glycemiaCurrentValue = jsonObject.get("glycemiaCurrentValue").getAsFloat();
        Float lastInsulinQuantity = jsonObject.get("lastInsulinQuantity").getAsFloat();
        String lastInsulinDateTimeString = jsonObject.get("lastInsulinDatetime").getAsString();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime lastInsulinDateTime = LocalTime.parse(lastInsulinDateTimeString, formatter);

        LOG.info("glycemiaCurrentValue: " + glycemiaCurrentValue);
        LOG.info("lastInsulinQuantity: " + lastInsulinQuantity);
        LOG.info("lastInsulinDateTime: " + lastInsulinDateTime);

        Float residualInsulin = calculateResidualInsulin(lastInsulinQuantity, lastInsulinDateTime);

        Float correctiveInsulin = calculateCorrectiveInsulin(glycemiaCurrentValue);

        JsonArray alimentValuesArray = jsonObject.getAsJsonArray("alimentValues");

        totalCarbs = calculateCarbs(alimentValuesArray);
        Float insulinForCarbs = totalCarbs / CARBS_PER_INSULIN_UNIT_DECREASE;

        LOG.info("residualInsulin: " + residualInsulin);
        LOG.info("correctiveInsulin: " + correctiveInsulin);
        LOG.info("insulinForCarbs: " + insulinForCarbs);

        totalInsulin =  correctiveInsulin + insulinForCarbs - residualInsulin;

        return totalInsulin;
    }

    public Float calculateInsulin(Float glycemiaCurrentValue, Float lastInsulinQuantity, LocalTime lastInsulinDateTime, Map<String, Float> alimentsWithQuantities) {

        // To calculate the insulin we need to take care of three factors: residualInsulin, glycemiaCurrentValue and totalCarbs we are eating
        Float totalInsulin = 0F;
        Float totalCarbs = 0F;

        LOG.info("glycemiaCurrentValue: " + glycemiaCurrentValue);
        LOG.info("lastInsulinQuantity: " + lastInsulinQuantity);
        LOG.info("lastInsulinDateTime: " + lastInsulinDateTime);

        Float residualInsulin = calculateResidualInsulin(lastInsulinQuantity, lastInsulinDateTime);

        Float correctiveInsulin = calculateCorrectiveInsulin(glycemiaCurrentValue);

        totalCarbs = calculateCarbs(alimentsWithQuantities);
        Float insulinForCarbs = totalCarbs / CARBS_PER_INSULIN_UNIT_DECREASE;

        LOG.info("residualInsulin: " + residualInsulin);
        LOG.info("correctiveInsulin: " + correctiveInsulin);
        LOG.info("insulinForCarbs: " + insulinForCarbs);

        totalInsulin =  correctiveInsulin + insulinForCarbs - residualInsulin;

        return totalInsulin;
    }

    public Float calculateCarbs(JsonArray alimentValuesArray) {

        Aliment aliment;
        Float totalCarbs = 0F;
        ArrayList<CalculatorDTO> alimentValues = new ArrayList<>();
        for(int j=0; j<alimentValuesArray.size(); j++) {
            JsonObject currentAliment = alimentValuesArray.get(j).getAsJsonObject();
            //String currentAlimentName = currentAliment.get("alimentName").getAsString();
            Float currentAlimentQuantity = currentAliment.get("quantity").getAsFloat();
            LOG.info("Current Aliment: " + currentAliment.toString());
            aliment = getAlimentByName(currentAliment.get("alimentName").getAsString());
            LOG.info("Current Aliment info: " + aliment.toString());
            totalCarbs += aliment.getCarbs() * currentAlimentQuantity / 100;
        }
        LOG.info ("Total Carbs = " + totalCarbs);
        return totalCarbs;
    }

    public Float calculateCarbs(Map<String, Float> alimentsAndQuantities) {

        Aliment aliment;
        Float totalCarbs = 0F;

        for(Map.Entry<String, Float> entry : alimentsAndQuantities.entrySet()) {
            aliment = getAlimentByName(entry.getKey());
            LOG.info("Current Aliment info: " + aliment.toString());
            totalCarbs += aliment.getCarbs() * entry.getValue() / 100;
        }

        LOG.info ("Total Carbs = " + totalCarbs);
        return totalCarbs;
    }

    public Float calculateResidualInsulin(Float lastInsulinQuantity, LocalTime lastInsulinDateTime) {

        LocalTime now = LocalTime.now();
        LOG.info("LocalTimeNow: " + now + ", lastInsulinDateTime: " + lastInsulinDateTime);
        long elapsedMinutes = Duration.between(lastInsulinDateTime, now).toMinutes();
        LOG.info("elapsedMinutes: " + elapsedMinutes);
        if(elapsedMinutes > TOTAL_MINUTES_OF_GLYCEMIA_EFFECT)
            return 0F;

        Float insulinPerMinute = lastInsulinQuantity/TOTAL_MINUTES_OF_GLYCEMIA_EFFECT;
        Float injectedInsulin = insulinPerMinute * elapsedMinutes;
        Float residualInsulin = lastInsulinQuantity - insulinPerMinute * elapsedMinutes;

        LOG.info("lastInsulinQuantity: " + lastInsulinQuantity);
        LOG.info("lastInsulinDateTime: " + lastInsulinDateTime);
        LOG.info("insulinPerMinute: " + insulinPerMinute);
        LOG.info("injectedInsulin: " + injectedInsulin);
        LOG.info("residualInsulin: " + residualInsulin);

        return residualInsulin;
    }

    public Float calculateCorrectiveInsulin(Float glycemiaCurrentValue) {
        if(glycemiaCurrentValue < DESIRED_GLYCEMIA_LEVEL)
            return 0F;

        Float correctiveInsulin = ((glycemiaCurrentValue - DESIRED_GLYCEMIA_LEVEL) / GLYCEMIA_PER_INSULIN_UNIT_DECREASE);
        LOG.info("glycemiaCurrentValue: " + glycemiaCurrentValue);
        LOG.info("DESIRED_GLYCEMIA_LEVEL: " + DESIRED_GLYCEMIA_LEVEL);
        LOG.info("GLYCEMIA_PER_INSULIN_UNIT_DECREASE: " + GLYCEMIA_PER_INSULIN_UNIT_DECREASE);
        LOG.info("correctiveInsulin: " + correctiveInsulin);

        return correctiveInsulin;
    }


}
