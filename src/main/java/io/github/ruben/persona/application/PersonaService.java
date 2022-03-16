package io.github.ruben.persona.application;

import io.github.ruben.persona.infrastructure.controller.dto.input.PersonaIdInputDto;
import io.github.ruben.persona.infrastructure.controller.dto.input.PersonaInputDto;
import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaRecordOutputDto;

import java.util.List;

public interface PersonaService {
    public List<PersonaRecordOutputDto> findAll();
    public PersonaRecordOutputDto findById(int id);
    public PersonaRecordOutputDto addPerson(PersonaIdInputDto personaInputDto) throws Exception;
    public PersonaRecordOutputDto modifyPerson(Integer id, PersonaInputDto personaInputDto) throws Exception;
    public void removePerson(Integer id);
    public List<PersonaRecordOutputDto> getAllPersonPaginated(int pageNumber, int pageSize);
    public PersonaRecordOutputDto findOneByUser(String user);
    public List<PersonaRecordOutputDto> findByUser(String user);
}
