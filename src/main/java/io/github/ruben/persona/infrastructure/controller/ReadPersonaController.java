package io.github.ruben.persona.infrastructure.controller;

import io.github.ruben.persona.application.PersonaService;
import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaOutputDto;
import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaRecordOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@RequestMapping("persona")
@RestController
public class ReadPersonaController {

    @Autowired
    PersonaService personaService;

    @Autowired
    EntityManager em;



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

    @GetMapping("/get")
    public List<PersonaOutputDto> getData(@RequestParam(required=false, name="usuario") String usuario,
                                          @RequestParam(required=false, name="name") String name,
                                          @RequestParam(required=false, name="surname") String surname,
                                          @RequestParam(required=false, name="created_date") @DateTimeFormat(pattern="dd-MM-yyyy") Date created_date,
                                          @RequestParam(required=false, name="dateCondition") String dateCondition,
                                          @RequestParam(required=false, name="order") String order,
                                          @RequestParam(required=false, name="pagina") Integer pagina) {
        return personaService.getData(usuario, name, surname, created_date, dateCondition, order, pagina);
    }

}
