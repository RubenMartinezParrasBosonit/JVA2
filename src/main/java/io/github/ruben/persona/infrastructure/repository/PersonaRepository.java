package io.github.ruben.persona.infrastructure.repository;

import io.github.ruben.persona.domain.Persona;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PersonaRepository extends MongoRepository<Persona, Integer> {
    public List<Persona> findAll(int pageNumber, int pageSize);
    public Persona findOneByUser(String usuario);
    public List<Persona> findByUser(String usuario);
}
