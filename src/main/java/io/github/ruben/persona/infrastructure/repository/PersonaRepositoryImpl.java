package io.github.ruben.persona.infrastructure.repository;

import io.github.ruben.persona.domain.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class PersonaRepositoryImpl implements PersonaRepository{

    private final MongoTemplate mongoTemplate;
    @Autowired
    public PersonaRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public <S extends Persona> S save(S entity) {
        return mongoTemplate.save(entity);
    }

    @Override
    public <S extends Persona> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Persona> findById(Integer integer) {
        return Optional.of(mongoTemplate.findById(integer, Persona.class));
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<Persona> findAll() {
        return mongoTemplate.findAll(Persona.class);
    }

    @Override
    public Iterable<Persona> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public Persona findOneByUser(String usuario) {
        Query query = new Query();
        query.addCriteria(Criteria.where("usuario").is(usuario));
        return mongoTemplate.findOne(query, Persona.class);
    }

    @Override
    public List<Persona> findByUser(String usuario) {
        Query query = new Query();
        query.addCriteria(Criteria.where("usuario").is(usuario));
        return mongoTemplate.find(query, Persona.class);
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {
        mongoTemplate.remove(mongoTemplate.findById(integer, Persona.class));
    }

    @Override
    public void delete(Persona entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Persona> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Persona> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Persona> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Persona> findAll(int pageNumber, int pageSize) {
        Query query = new Query();
        query.skip(pageNumber * pageSize);
        query.limit(pageSize);
        return mongoTemplate.find(query, Persona.class);
    }

    @Override
    public <S extends Persona> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends Persona> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends Persona> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Persona> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Persona> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Persona> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Persona> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Persona> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Persona, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
