package io.github.ruben.persona.infrastructure.controller;

import io.github.ruben.persona.application.PersonaService;
import io.github.ruben.persona.infrastructure.controller.dto.input.PersonaIdInputDto;
import io.github.ruben.persona.infrastructure.controller.dto.input.PersonaInputDto;
import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaRecordOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("persona")
@RestController
public class CreatePersonaController {

    @Autowired
    PersonaService personaService;

    @PostMapping
    public PersonaRecordOutputDto aniadirPesona(@RequestBody PersonaIdInputDto personaInputDto) throws Exception {
        return personaService.addPerson(personaInputDto);
    }
}
