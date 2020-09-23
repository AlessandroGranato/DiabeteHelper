package com.pyrosandro.resources;

import com.google.gson.Gson;
import com.pyrosandro.DTO.AlimentDTO;
import com.pyrosandro.DTO.CalculatorDTO;
import com.pyrosandro.entities.Aliment;
import com.pyrosandro.services.AlimentService;
import com.pyrosandro.utils.DiabeteMapper;
import org.slf4j.Logger;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;

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

    @Path("hello")
    @GET
    public Response hello() {
        return Response.ok("Hello world").build();
    }

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
        Gson gson = new Gson();
        CalculatorDTO[] calculatorDTOS = gson.fromJson(input, CalculatorDTO[].class);

        String text = "";
        for (CalculatorDTO calculatorDTO : calculatorDTOS) {
            LOG.info("Single calculatorDTO: " +  calculatorDTO.toString());
            text += calculatorDTO.getAlimentName();
        }

        return Response.ok(text).build();
    }


}
