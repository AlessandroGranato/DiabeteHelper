package com.pyrosandro.config;



import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;


//If producers becomes complitated, split it in many classes, each for a single producer
public class DiabeteProducers {

    @Produces
    @PersistenceContext
    EntityManager entityManager;

    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
        return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }

    //Auto mapper
    @Produces
    public DozerBeanMapper produceDozenBeanMapper() {
        DozerBeanMapper mapper = new DozerBeanMapper();
        List<String> mappingFiles = new ArrayList();
        mappingFiles.add("dozerJdk8Converters.xml");
        mapper.setMappingFiles(mappingFiles);
        return mapper;
    }
}
