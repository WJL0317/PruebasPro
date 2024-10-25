package com.PruebasPro.app.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.PruebasPro.app.Entity.*;

public interface CoordinadorRepository extends MongoRepository<Coordinador, String> {

}
