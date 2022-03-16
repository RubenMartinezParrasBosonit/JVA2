package io.github.ruben.persona.infrastructure.controller;

import io.github.ruben.persona.application.PersonaService;
import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaRecordOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("persona")
@RestController
public class ReadPersonaController {

    @Autowired
    PersonaService personaService;

    @GetMapping
    public List<PersonaRecordOutputDto> findAll(){
        return personaService.findAll();
    }

    @GetMapping("paginated")
    public List<PersonaRecordOutputDto> findAllPaginated(@RequestParam(name="pageNumber") Integer pageNumber,
                                                         @RequestParam(name="pageSize") Integer pageSize){
        return personaService.getAllPersonPaginated(pageNumber, pageSize);
    }

    @GetMapping("{id}")
    public PersonaRecordOutputDto getPersonaById(@PathVariable Integer id){
        return personaService.findById(id);
    }

    @GetMapping("/{usuario}/usuarios")
    public List<PersonaRecordOutputDto> getPersonasByUsuario(@PathVariable String usuario){
        return personaService.findByUser(usuario);
    }

    @GetMapping("/{usuario}/usuario")
    public PersonaRecordOutputDto getPersonaByUsuario(@PathVariable String usuario){
        return personaService.findOneByUser(usuario);
    }

}
