package com.pyrosandro.services;

import com.pyrosandro.entities.Aliment;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Stateless
public class AlimentService {

    @Inject
    EntityManager entityManager;

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
}
