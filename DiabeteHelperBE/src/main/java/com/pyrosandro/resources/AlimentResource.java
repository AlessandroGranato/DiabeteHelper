package com.pyrosandro.resources;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pyrosandro.DTO.AlimentDTO;
import com.pyrosandro.DTO.CalculateInsulinRequestDTO;
import com.pyrosandro.DTO.CalculatorDTO;
import com.pyrosandro.DTO.TestJsonMapDTO;
import com.pyrosandro.entities.Aliment;
import com.pyrosandro.services.AlimentService;
import com.pyrosandro.utils.DiabeteMapper;
import org.slf4j.Logger;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Path("aliments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AlimentResource {

    @Inject
    private AlimentService alimentService;

    @Inject
    DiabeteMapper diabeteMapper;

    @Inject
    Logger LOG;



    @Path("all")
    @GET
    public Response getAllAliments() {
        Collection<Aliment> aliments = alimentService.getAllAliments();
        Collection<AlimentDTO> alimentDTOs = new ArrayList<>();
        for(Aliment aliment : aliments) {
            alimentDTOs.add(diabeteMapper.mapAlimentToDTO(aliment));
        }
        return Response.ok(alimentDTOs).build();
    }

    @Path("new")
    @POST
    public Response saveAliment(AlimentDTO alimentDTO) {
        Aliment aliment = diabeteMapper.mapAlimentFromDTO(alimentDTO);
        alimentService.saveAliment(aliment);

        alimentDTO = diabeteMapper.mapAlimentToDTO(aliment);
        return Response.ok(alimentDTO).build();
    }

    @Path("update")
    @POST
    public Response updateAliment(AlimentDTO alimentDTO) {
        Aliment aliment = diabeteMapper.mapAlimentFromDTO(alimentDTO);
        alimentService.saveAliment(aliment);

        alimentDTO = diabeteMapper.mapAlimentToDTO(aliment);
        return Response.ok(alimentDTO).build();
    }

    @Path("calculateInsulin")
    @POST
    public Response calculateInsulin(String input) {

        LOG.info("Input string: " + input);

        Float insulin = alimentService.calculateInsulin(input);

//        Gson gson = new Gson();
//        CalculatorDTO[] calculatorDTOS = gson.fromJson(input, CalculatorDTO[].class);
//
//
//        String text = "";
//        for (CalculatorDTO calculatorDTO : calculatorDTOS) {
//            LOG.info("Single calculatorDTO: " +  calculatorDTO.toString());
//            text += calculatorDTO.getAlimentName();
//        }
//
//        return Response.ok(text).build();

        return Response.ok(insulin).build();
    }

    @Path("calculateInsulinNew")
    @POST
    public Response calculateInsulin(CalculateInsulinRequestDTO calculateInsulinRequestDTO) {

        LOG.info(" calculateInsulin - START");

        LOG.info("calculateInsulinRequestDTO" + calculateInsulinRequestDTO.getGlycemiaCurrentValue());
        LOG.info("calculateInsulinRequestDTO" + calculateInsulinRequestDTO.toString());

        Float glycemiaCurrentValue = calculateInsulinRequestDTO.getGlycemiaCurrentValue();
        Float lastInsulinQuantity = calculateInsulinRequestDTO.getLastInsulinQuantity();
        String lastInsulinDateTimeString = calculateInsulinRequestDTO.getLastInsulinDateTimeString();
        Map<String, Float> alimentsWithQuantities = calculateInsulinRequestDTO.getAlimentsWithQuantities();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime lastInsulinDateTime = LocalTime.parse(lastInsulinDateTimeString, formatter);

        Float insulin = alimentService.calculateInsulin(glycemiaCurrentValue, lastInsulinQuantity, lastInsulinDateTime, alimentsWithQuantities);

        LOG.info(" calculateInsulin - END");
        return Response.ok(insulin).build();
    }


//    --------------------------------------------------------
    @Path("testJson")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response testJson(TestJsonMapDTO testJsonMapDTO) {

        LOG.info(" testJsonMapDTO: " + testJsonMapDTO.toString());

        return Response.ok().build();
    }

    @Path("hello")
    @GET
    public Response hello() {
        return Response.ok("Hello world").build();
    }

    @Path("hello/{name}")
    @GET
    public Response hello(@PathParam("name") String name) {
        String responseString = "Hello " + name;
        return Response.ok(responseString).build();
    }

    @Path("helloName")
    @POST
    public Response helloName(String name) {
        LOG.info("Name is: " + name);
        String responseString = "Hello " + name;
        return Response.ok().entity(responseString).build();
    }


}
