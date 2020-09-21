package com.pyrosandro.utils;

import com.pyrosandro.DTO.AlimentDTO;
import com.pyrosandro.entities.Aliment;
import org.dozer.DozerBeanMapper;

import javax.inject.Inject;

public class DiabeteMapper {

    @Inject
    DozerBeanMapper dozerBeanMapper;

    public AlimentDTO mapAlimentToDTO(Aliment aliment) {
        return dozerBeanMapper.map(aliment, AlimentDTO.class);
    }

    public Aliment mapAlimentFromDTO(AlimentDTO alimentDTO) {
        return dozerBeanMapper.map(alimentDTO, Aliment.class);
    }
}
