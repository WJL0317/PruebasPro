package com.PruebasPro.app.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.PruebasPro.app.Entity.*;

public interface EstudianteRepository extends MongoRepository<Estudiante, String> {

	Estudiante findByUsuario(String usuario);

}
