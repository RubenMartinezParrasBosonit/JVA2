package io.github.ruben.persona.infrastructure.controller;

import io.github.ruben.persona.application.PersonaService;
import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaOutputDto;
import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaRecordOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("persona")
@RestController
public class ReadPersonaController {

    @Autowired
    PersonaService personaService;

    @GetMapping
    public List<PersonaRecordOutputDto> findAll(){
        return personaService.todasLasPersonas();
    }

    @GetMapping("{id}")
    public PersonaRecordOutputDto getPersonaById(@PathVariable Integer id){
        return personaService.filtrarPersonasPorId(id);
    }

    @GetMapping("/{usuario}/usuario")
    public List<PersonaRecordOutputDto> getPersonaByUsuario(@PathVariable String usuario){
        return personaService.filtrarPersonaPorNombreUsuario(usuario);
    }
}
