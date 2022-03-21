package io.github.ruben;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ruben.persona.domain.Persona;
import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaOutputDto;
import io.github.ruben.persona.infrastructure.repository.jpa.PersonaRepositorio;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;



@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReadPersonaControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	PersonaRepositorio personaRepositorio;

	Persona persona = new Persona();
	Persona persona2 = new Persona();


	@BeforeAll
	public void start(){
		createPersona(persona);

		createPersona(persona2);
	}

	@AfterAll
	public void finish(){
		personaRepositorio.deleteAll();
	}

	@Test
	@DisplayName("Testing get person by Id")
	void testGetPersonById() throws Exception{
		MvcResult resultado = this.mockMvc.perform(get("/persona/"+String.valueOf(persona.getId_persona())))
				.andReturn();

		String contenido = resultado.getResponse().getContentAsString();

		PersonaOutputDto personaOutputDto = new ObjectMapper().readValue(contenido, new TypeReference<PersonaOutputDto>() {
		});

		assertionsPersona(persona, personaOutputDto);
	}

	@Test
	@DisplayName("Testing get all persons")
	void testGetAllPerson() throws Exception{
		MvcResult resultado = this.mockMvc.perform(get("/persona"))
				.andReturn();

		String contenido = resultado.getResponse().getContentAsString();

		List<PersonaOutputDto> personaOutputDto = new ObjectMapper()
				.readValue(contenido, new TypeReference<List<PersonaOutputDto>>() {
		});
		if(persona.getId_persona() == personaOutputDto.get(0).getId_persona()){
			assertionsPersona(persona, personaOutputDto.get(0));
			assertionsPersona(persona2, personaOutputDto.get(1));
		}else{
			assertionsPersona(persona2, personaOutputDto.get(0));
			assertionsPersona(persona, personaOutputDto.get(1));
		}

	}

	private void assertionsPersona(Persona persona, PersonaOutputDto personaOutputDto){
		Assertions.assertEquals(persona.getUsuario(), personaOutputDto.getUsuario());
		Assertions.assertEquals(persona.getName(), personaOutputDto.getName());
		Assertions.assertEquals(persona.getSurname(), personaOutputDto.getSurname());
		Assertions.assertEquals(persona.getPassword(), personaOutputDto.getPassword());
		Assertions.assertEquals(persona.getCity(), personaOutputDto.getCity());
		Assertions.assertEquals(persona.getActive(), personaOutputDto.getActive());
		Assertions.assertEquals(persona.getPersonal_email(), personaOutputDto.getPersonal_email());
		Assertions.assertEquals(persona.getCompany_email(), personaOutputDto.getCompany_email());
		Assertions.assertEquals(persona.getImagen_url(), personaOutputDto.getImagen_url());
		Assertions.assertEquals(persona.getCreated_date(), personaOutputDto.getCreated_date());
		Assertions.assertEquals(persona.getTermination_date(), personaOutputDto.getTermination_date());
	}

	private void createPersona(Persona persona){
		persona.setUsuario("usuario");
		persona.setPassword("contrasenia");
		persona.setName("Ruben");
		persona.setSurname("Martinez");
		persona.setCity("Jaen");
		persona.setActive(true);
		persona.setPersonal_email("r@gmail.com");
		persona.setCompany_email("r@gmail.com");
		persona.setCreated_date(new Date());
		persona.setTermination_date(new Date());
		persona.setImagen_url("imgurl");
		personaRepositorio.save(persona);
	}

}
