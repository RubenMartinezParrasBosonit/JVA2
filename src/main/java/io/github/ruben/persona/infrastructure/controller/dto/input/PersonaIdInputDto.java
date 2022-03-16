package io.github.ruben.persona.infrastructure.controller.dto.input;

import io.github.ruben.persona.domain.Persona;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class PersonaIdInputDto extends PersonaInputDto  {
    private Integer id_persona;


    public PersonaIdInputDto(Persona persona){
        super(persona);
        setId_persona(persona.getId_persona());
    }
}
