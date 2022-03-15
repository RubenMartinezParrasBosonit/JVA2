package io.github.ruben.persona.application;

import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaRecordOutputDto;
import io.github.ruben.shared.exceptions.IdNotFoundException;
import io.github.ruben.shared.exceptions.UnprocesableException;
import io.github.ruben.persona.infrastructure.controller.dto.input.PersonaInputDto;
import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaOutputDto;
import io.github.ruben.persona.infrastructure.repository.jpa.PersonaRepositorio;
import io.github.ruben.persona.domain.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PersonaServiceImp implements PersonaService {

  @Autowired PersonaRepositorio personaRepositorio;

  public static final String GREATER_THAN="greater";
  public static final String LESS_THAN="less";
  public static final String EQUAL="equal";

  public static final String USUARIO="usuario";
  public static final String NAME="name";

  @Override
  public List<PersonaRecordOutputDto> todasLasPersonas() {
    List<Persona> personas = personaRepositorio.findAll();
    List<PersonaRecordOutputDto> personasRecordOutputDto = new ArrayList<>();
    for (Persona persona : personas) {
      personasRecordOutputDto.add(new PersonaRecordOutputDto(persona.getId_persona(), persona.getUsuario(), persona.getPassword(),
              persona.getName(), persona.getSurname(), persona.getCompany_email(), persona.getPersonal_email(), persona.getCity(),
              persona.getActive(), persona.getCreated_date(), persona.getImagen_url(), persona.getTermination_date()));
    }
    return personasRecordOutputDto;
  }

  @Override
  public PersonaRecordOutputDto filtrarPersonasPorId(int id) {
    Persona persona =
        personaRepositorio.findById(id).orElseThrow(() -> new IdNotFoundException("Persona con id: "+id+ " no encontrado"));
    PersonaRecordOutputDto personaRecordOutputDto = new PersonaRecordOutputDto(persona.getId_persona(), persona.getUsuario(), persona.getPassword(),
            persona.getName(), persona.getSurname(), persona.getCompany_email(), persona.getPersonal_email(), persona.getCity(),
            persona.getActive(), persona.getCreated_date(), persona.getImagen_url(), persona.getTermination_date());
    return personaRecordOutputDto;
  }

  @Override
  public List<PersonaRecordOutputDto> filtrarPersonaPorNombreUsuario(String usuario) {
    List<Persona> personas = personaRepositorio.findByUsuario(usuario);
    if (personas.size() == 0) throw new NoSuchElementException("Usuario no encontrado");
    List<PersonaRecordOutputDto> personasRecordOutputDto = new ArrayList<>();
    for (Persona persona : personas) {
      personasRecordOutputDto.add(new PersonaRecordOutputDto(persona.getId_persona(), persona.getUsuario(), persona.getPassword(),
              persona.getName(), persona.getSurname(), persona.getCompany_email(), persona.getPersonal_email(), persona.getCity(),
              persona.getActive(), persona.getCreated_date(), persona.getImagen_url(), persona.getTermination_date()));
    }
    return personasRecordOutputDto;
  }

  @Override
  public PersonaRecordOutputDto aniadirPersona(PersonaInputDto personaInputDto) {

    if (personaInputDto.getUsuario() == "")
      throw new UnprocesableException("Usuario no puede ser nulo");
    if (personaInputDto.getUsuario().length() < 6 || personaInputDto.getUsuario().length() > 10) {
      throw new UnprocesableException("La longitud del nombre de usuario no está entre 6 y 10");
    }
    if (personaInputDto.getPassword() == "") {
      throw new UnprocesableException("Password no puede ser nulo");
    }
    if (personaInputDto.getName() == "") {
      throw new UnprocesableException("Name no puede ser nulo");
    }
    if (personaInputDto.getCompany_email() == "") {
      throw new UnprocesableException("Company_email no puede ser nulo");
    }
    if (personaInputDto.getPersonal_email() == "") {
      throw new UnprocesableException("Personal_email no puede ser nulo");
    }
    if (personaInputDto.getCity() == "") {
      throw new UnprocesableException("City no puede ser nulo");
    }
    if (personaInputDto.getActive() == null) {
      throw new UnprocesableException("Password no puede ser nulo");
    }
    if (personaInputDto.getCreated_date() == null) {
      throw new UnprocesableException("Created_date no puede ser nulo");
    }

    PersonaRecordOutputDto personaRecordOutputDto = personaInputDtoToPersonaRecordOutputDto(personaInputDto);
    Persona persona = personaRecordOutputDtoToEntity(personaRecordOutputDto);
    personaRepositorio.saveAndFlush(persona);

    return personaRecordOutputDto;
  }

  @Override
  public PersonaRecordOutputDto modificarPersona(Integer id, PersonaInputDto personaInputDto)
      throws Exception {
    Persona persona =
        personaRepositorio
            .findById(id)
            .orElseThrow(() -> new IdNotFoundException("Persona con id: "+id+ " no encontrado"));
    PersonaOutputDto personaOutputDto = new PersonaOutputDto(persona);

    if (personaInputDto.getUsuario() != null) {
      if (personaInputDto.getUsuario().length() < 6 || personaInputDto.getUsuario().length() > 10) {
        throw new UnprocesableException("La longitud del nombre de usuario no está entre 6 y 10");
      }
      personaOutputDto.setUsuario(personaInputDto.getUsuario());
    }

    if (personaInputDto.getPassword() != "") {
      personaOutputDto.setPassword(personaInputDto.getPassword());
    }

    if (personaInputDto.getName() != "") {
      personaOutputDto.setName(personaInputDto.getName());
    }

    if (personaInputDto.getSurname() != "") {
      personaOutputDto.setSurname(personaInputDto.getSurname());
    }

    if (personaInputDto.getCompany_email() != "") {
      personaOutputDto.setCompany_email(personaInputDto.getCompany_email());
    }

    if (personaInputDto.getPersonal_email() != "") {
      personaOutputDto.setPersonal_email(personaInputDto.getPersonal_email());
    }

    if (personaInputDto.getCity() != "") {
      personaOutputDto.setCity(personaInputDto.getCity());
    }

    if (personaInputDto.getActive() != null) {
      personaOutputDto.setActive(personaInputDto.getActive());
    }

    if (personaInputDto.getCreated_date() != null) {
      personaOutputDto.setCreated_date(personaInputDto.getCreated_date());
    }

    if (personaInputDto.getImagen_url() != "") {
      personaOutputDto.setImagen_url(personaInputDto.getImagen_url());
    }

    if (personaInputDto.getTermination_date() != null) {
      personaOutputDto.setTermination_date(personaInputDto.getTermination_date());
    }
    PersonaRecordOutputDto personaRecordOutputDto = new PersonaRecordOutputDto(personaOutputDto.getId_persona(), personaOutputDto.getUsuario(), personaOutputDto.getPassword(),
            personaOutputDto.getName(), personaOutputDto.getSurname(), personaOutputDto.getCompany_email(), personaOutputDto.getPersonal_email(), personaOutputDto.getCity(),
            personaOutputDto.getActive(), personaOutputDto.getCreated_date(), personaOutputDto.getImagen_url(), personaOutputDto.getTermination_date());
    persona = personaRecordOutputDtoToEntity(personaRecordOutputDto);
    personaRepositorio.saveAndFlush(persona);
    return personaRecordOutputDto;
  }

  @Override
  public void borrarPersona(Integer id) {
    personaRepositorio.delete(
        personaRepositorio
            .findById(id)
            .orElseThrow(() -> new IdNotFoundException("Persona con id: "+id+ " no encontrado")));
  }

  @Override
  public List <PersonaOutputDto> getData(String usuario, String name, String surname, Date created_date, String dateCondition, String order, Integer pagina){
    HashMap<String, Object> data=new HashMap<>();

    if (usuario!=null)
      data.put("usuario",usuario);
    if (name!=null)
      data.put("name",name);
    if (surname!=null)
      data.put("surname",surname);
    if (dateCondition==null)
      dateCondition=GREATER_THAN;
    if (!dateCondition.equals(GREATER_THAN) && 	!dateCondition.equals(LESS_THAN) && !dateCondition.equals(EQUAL))
      dateCondition=GREATER_THAN;
    if (created_date!=null)
    {
      data.put("created_date",created_date);
      data.put("dateCondition",dateCondition);
    }
    if (order!=null)
      data.put("order",order);
    if (pagina!=null)
      data.put("pagina", pagina);


    List <Persona> personas = personaRepositorio.getData(data);
    List <PersonaOutputDto> personasOutputDto = new ArrayList<>();

    for(Persona persona : personas){
      personasOutputDto.add(new PersonaOutputDto(persona));
    }

    return personasOutputDto;
  }

  private Persona personaRecordOutputDtoToEntity(PersonaRecordOutputDto personaRecordOutputDto) {
    Persona persona = new Persona();
    persona.setId_persona(personaRecordOutputDto.id_persona());
    persona.setUsuario(personaRecordOutputDto.usuario());
    persona.setPassword(personaRecordOutputDto.password());
    persona.setName(personaRecordOutputDto.name());
    persona.setSurname(personaRecordOutputDto.surname());
    persona.setCompany_email(personaRecordOutputDto.company_email());
    persona.setPersonal_email(personaRecordOutputDto.personal_email());
    persona.setCity(personaRecordOutputDto.city());
    persona.setActive(personaRecordOutputDto.active());
    persona.setCreated_date(personaRecordOutputDto.created_date());
    persona.setImagen_url(personaRecordOutputDto.imagen_url());
    persona.setTermination_date(personaRecordOutputDto.termination_date());

    return persona;
  }

  private PersonaRecordOutputDto personaInputDtoToPersonaRecordOutputDto(PersonaInputDto personaInputDto) {
    PersonaRecordOutputDto personaRecordOutputDto = new PersonaRecordOutputDto(null, personaInputDto.getUsuario(), personaInputDto.getPassword(),
            personaInputDto.getName(), personaInputDto.getSurname(), personaInputDto.getCompany_email(), personaInputDto.getPersonal_email(), personaInputDto.getCity(),
            personaInputDto.getActive(), personaInputDto.getCreated_date(), personaInputDto.getImagen_url(), personaInputDto.getTermination_date());

    return personaRecordOutputDto;
  }
}
