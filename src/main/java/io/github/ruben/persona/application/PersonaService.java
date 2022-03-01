package io.github.ruben.persona.application;

import io.github.ruben.persona.infrastructure.controller.dto.input.PersonaInputDto;
import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaOutputDto;
import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaRecordOutputDto;

import java.util.Date;
import java.util.List;

public interface PersonaService {
    public List<PersonaRecordOutputDto> todasLasPersonas();
    public PersonaRecordOutputDto filtrarPersonasPorId(int id);
    public List<PersonaRecordOutputDto> filtrarPersonaPorNombreUsuario(String usuario);
    public PersonaRecordOutputDto aniadirPersona(PersonaInputDto personaInputDto) throws Exception;
    public PersonaRecordOutputDto modificarPersona(Integer id, PersonaInputDto personaInputDto) throws Exception;
    public void borrarPersona(Integer id);
    public List <PersonaOutputDto> getData(String usuario, String name, String surname, Date created_date, String dateCondition, String order, Integer pagina);
}
