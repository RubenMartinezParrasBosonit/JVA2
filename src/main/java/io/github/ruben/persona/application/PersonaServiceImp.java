package io.github.ruben.persona.application;

import io.github.ruben.persona.infrastructure.controller.dto.input.PersonaIdInputDto;
import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaRecordOutputDto;
import io.github.ruben.persona.infrastructure.repository.PersonaRepository;
import io.github.ruben.shared.exceptions.UnprocesableException;
import io.github.ruben.persona.infrastructure.controller.dto.input.PersonaInputDto;
import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaOutputDto;
import io.github.ruben.persona.domain.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PersonaServiceImp implements PersonaService {

  @Autowired
  private PersonaRepository personaRepository;

  @Override
  public List<PersonaRecordOutputDto> findAll() {
    List<Persona> personas = personaRepository.findAll();
    List<PersonaRecordOutputDto> personasRecordOutputDto = new ArrayList<>();
    for (Persona persona : personas) {
      personasRecordOutputDto.add(new PersonaRecordOutputDto(persona.getId_persona(), persona.getUsuario(), persona.getPassword(),
              persona.getName(), persona.getSurname(), persona.getCompany_email(), persona.getPersonal_email(), persona.getCity(),
              persona.getActive(), persona.getCreated_date(), persona.getImagen_url(), persona.getTermination_date()));
    }
    return personasRecordOutputDto;
  }

  @Override
  public List<PersonaRecordOutputDto> getAllPersonPaginated(int pageNumber, int pageSize) {
    List<Persona> personas = personaRepository.findAll(pageNumber, pageSize);
    List<PersonaRecordOutputDto> personasRecordOutputDto = new ArrayList<>();

    for (Persona persona : personas) {
      personasRecordOutputDto.add(new PersonaRecordOutputDto(persona.getId_persona(), persona.getUsuario(), persona.getPassword(),
              persona.getName(), persona.getSurname(), persona.getCompany_email(), persona.getPersonal_email(), persona.getCity(),
              persona.getActive(), persona.getCreated_date(), persona.getImagen_url(), persona.getTermination_date()));
    }
    return personasRecordOutputDto;
  }

  @Override
  public PersonaRecordOutputDto findOneByUser(String user) {
    Persona persona = personaRepository.findOneByUser(user);
    PersonaRecordOutputDto personaRecordOutputDto = new PersonaRecordOutputDto(persona.getId_persona(), persona.getUsuario(), persona.getPassword(),
            persona.getName(), persona.getSurname(), persona.getCompany_email(), persona.getPersonal_email(), persona.getCity(),
            persona.getActive(), persona.getCreated_date(), persona.getImagen_url(), persona.getTermination_date());
    return personaRecordOutputDto;
  }

  @Override
  public List<PersonaRecordOutputDto> findByUser(String user) {
    List<Persona> personas = personaRepository.findByUser(user);
    List<PersonaRecordOutputDto> personasRecordOutputDto = new ArrayList<>();

    for (Persona persona : personas) {
      personasRecordOutputDto.add(new PersonaRecordOutputDto(persona.getId_persona(), persona.getUsuario(), persona.getPassword(),
              persona.getName(), persona.getSurname(), persona.getCompany_email(), persona.getPersonal_email(), persona.getCity(),
              persona.getActive(), persona.getCreated_date(), persona.getImagen_url(), persona.getTermination_date()));
    }
    return personasRecordOutputDto;
  }

  @Override
  public PersonaRecordOutputDto findById(int id) {
    Optional<Persona> personaOptional = personaRepository.findById(id);
    Persona persona = personaOptional.get();

    PersonaRecordOutputDto personaRecordOutputDto = new PersonaRecordOutputDto(persona.getId_persona(), persona.getUsuario(), persona.getPassword(),
            persona.getName(), persona.getSurname(), persona.getCompany_email(), persona.getPersonal_email(), persona.getCity(),
            persona.getActive(), persona.getCreated_date(), persona.getImagen_url(), persona.getTermination_date());
    return personaRecordOutputDto;
  }

  @Override
  public PersonaRecordOutputDto addPerson(PersonaIdInputDto personaInputDto) {

    if (personaInputDto.getId_persona() == null)
      throw new UnprocesableException("Id no puede ser nulo");
    if (personaInputDto.getUsuario() == null)
      throw new UnprocesableException("Usuario no puede ser nulo");
    if (personaInputDto.getUsuario().length() < 6 || personaInputDto.getUsuario().length() > 10) {
      throw new UnprocesableException("La longitud del nombre de usuario no está entre 6 y 10");
    }
    if (personaInputDto.getPassword() == null) {
      throw new UnprocesableException("Password no puede ser nulo");
    }
    if (personaInputDto.getName() == null) {
      throw new UnprocesableException("Name no puede ser nulo");
    }
    if (personaInputDto.getCompany_email() == null) {
      throw new UnprocesableException("Company_email no puede ser nulo");
    }
    if (personaInputDto.getPersonal_email() == null) {
      throw new UnprocesableException("Personal_email no puede ser nulo");
    }
    if (personaInputDto.getCity() == null) {
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
    personaRepository.save(persona);

    return personaRecordOutputDto;
  }

  @Override
  public PersonaRecordOutputDto modifyPerson(Integer id, PersonaInputDto personaInputDto)
      throws Exception {
    Optional<Persona> personaOptional = personaRepository.findById(id);
    Persona persona = personaOptional.get();
    PersonaOutputDto personaOutputDto = new PersonaOutputDto(persona);

    if (personaInputDto.getUsuario() != null) {
      if (personaInputDto.getUsuario().length() < 6 || personaInputDto.getUsuario().length() > 10) {
        throw new UnprocesableException("La longitud del nombre de usuario no está entre 6 y 10");
      }
      personaOutputDto.setUsuario(personaInputDto.getUsuario());
    }

    if (personaInputDto.getPassword() != null) {
      personaOutputDto.setPassword(personaInputDto.getPassword());
    }

    if (personaInputDto.getName() != null) {
      personaOutputDto.setName(personaInputDto.getName());
    }

    if (personaInputDto.getSurname() != null) {
      personaOutputDto.setSurname(personaInputDto.getSurname());
    }

    if (personaInputDto.getCompany_email() != null) {
      personaOutputDto.setCompany_email(personaInputDto.getCompany_email());
    }

    if (personaInputDto.getPersonal_email() != null) {
      personaOutputDto.setPersonal_email(personaInputDto.getPersonal_email());
    }

    if (personaInputDto.getCity() != null) {
      personaOutputDto.setCity(personaInputDto.getCity());
    }

    if (personaInputDto.getActive() != null) {
      personaOutputDto.setActive(personaInputDto.getActive());
    }

    if (personaInputDto.getCreated_date() != null) {
      personaOutputDto.setCreated_date(personaInputDto.getCreated_date());
    }

    if (personaInputDto.getImagen_url() != null) {
      personaOutputDto.setImagen_url(personaInputDto.getImagen_url());
    }

    if (personaInputDto.getTermination_date() != null) {
      personaOutputDto.setTermination_date(personaInputDto.getTermination_date());
    }
    PersonaRecordOutputDto personaRecordOutputDto = new PersonaRecordOutputDto(personaOutputDto.getId_persona(), personaOutputDto.getUsuario(), personaOutputDto.getPassword(),
            personaOutputDto.getName(), personaOutputDto.getSurname(), personaOutputDto.getCompany_email(), personaOutputDto.getPersonal_email(), personaOutputDto.getCity(),
            personaOutputDto.getActive(), personaOutputDto.getCreated_date(), personaOutputDto.getImagen_url(), personaOutputDto.getTermination_date());
    persona = personaRecordOutputDtoToEntity(personaRecordOutputDto);
    personaRepository.save(persona);
    return personaRecordOutputDto;
  }

  @Override
  public void removePerson(Integer id) {
    personaRepository.deleteById(id);
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

  private PersonaRecordOutputDto personaInputDtoToPersonaRecordOutputDto(PersonaIdInputDto personaInputDto) {
    PersonaRecordOutputDto personaRecordOutputDto = new PersonaRecordOutputDto(personaInputDto.getId_persona(),
            personaInputDto.getUsuario(), personaInputDto.getPassword(), personaInputDto.getName(),
            personaInputDto.getSurname(), personaInputDto.getCompany_email(), personaInputDto.getPersonal_email(),
            personaInputDto.getCity(), personaInputDto.getActive(), personaInputDto.getCreated_date(),
            personaInputDto.getImagen_url(), personaInputDto.getTermination_date());

    return personaRecordOutputDto;
  }
}
