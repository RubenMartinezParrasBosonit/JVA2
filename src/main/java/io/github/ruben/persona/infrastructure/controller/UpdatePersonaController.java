package io.github.ruben.persona.infrastructure.controller;

import io.github.ruben.persona.application.PersonaService;
import io.github.ruben.persona.infrastructure.controller.dto.input.PersonaInputDto;
import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaOutputDto;
import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaRecordOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("persona")
@RestController
public class UpdatePersonaController {

    @Autowired
    PersonaService personaService;

    @PutMapping("{id}")
    public PersonaRecordOutputDto modificarPersona(@PathVariable Integer id, @RequestBody PersonaInputDto personaInputDto) throws Exception {

            return personaService.modificarPersona(id, personaInputDto);

    }
}
