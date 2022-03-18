package io.github.ruben;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.github.ruben.persona.domain.Persona;
import io.github.ruben.persona.infrastructure.controller.dto.input.PersonaInputDto;
import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaRecordOutputDto;
import io.github.ruben.persona.infrastructure.repository.jpa.PersonaRepositorio;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UpdatePersonaControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	PersonaRepositorio personaRepositorio;

	Persona persona = new Persona();

	PersonaInputDto personaInputDto = new PersonaInputDto();


	@BeforeAll
	public void start(){
		createPersonaAndPersonaInputDto(persona, personaInputDto);
	}

	@Test
	@DisplayName("Testing update person by Id")
	void testUpdatePersona() throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson=ow.writeValueAsString(personaInputDto);

		MvcResult resultado = this.mockMvc.perform(put("/persona/"+String.valueOf(persona.getId_persona()))
						.contentType(MediaType.APPLICATION_JSON).content(requestJson)).andExpect(status().isOk()).andReturn();

		String contenido = resultado.getResponse().getContentAsString();

		PersonaRecordOutputDto personaOutputDto = new ObjectMapper()
				.readValue(contenido, new TypeReference<PersonaRecordOutputDto>() {
				});

		assertionsPersona(personaOutputDto);

	}


	private void createPersonaAndPersonaInputDto(Persona persona, PersonaInputDto personaInputDto){
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

		personaInputDto.setUsuario("rubencito");
		personaInputDto.setPassword("feiqon");
		personaInputDto.setName("Alfredo");
		personaInputDto.setSurname("Mendez");
		personaInputDto.setCity("Madrid");
		personaInputDto.setActive(false);
		personaInputDto.setPersonal_email("re@gmail.com");
		personaInputDto.setCompany_email("re@gmail.com");
		personaInputDto.setCreated_date(new Date());
		personaInputDto.setTermination_date(new Date());
		personaInputDto.setImagen_url("imagenUrl");
	}

	private void assertionsPersona(PersonaRecordOutputDto personaOutputDto){
		Assertions.assertEquals(personaInputDto.getUsuario(), personaOutputDto.usuario());
		Assertions.assertEquals(personaInputDto.getName(), personaOutputDto.name());
		Assertions.assertEquals(personaInputDto.getSurname(), personaOutputDto.surname());
		Assertions.assertEquals(personaInputDto.getPassword(), personaOutputDto.password());
		Assertions.assertEquals(personaInputDto.getCity(), personaOutputDto.city());
		Assertions.assertEquals(personaInputDto.getActive(), personaOutputDto.active());
		Assertions.assertEquals(personaInputDto.getPersonal_email(), personaOutputDto.personal_email());
		Assertions.assertEquals(personaInputDto.getCompany_email(), personaOutputDto.company_email());
		Assertions.assertEquals(personaInputDto.getImagen_url(), personaOutputDto.imagen_url());
		Assertions.assertEquals(personaInputDto.getCreated_date(), personaOutputDto.created_date());
		Assertions.assertEquals(personaInputDto.getTermination_date(), personaOutputDto.termination_date());

		Assertions.assertNotEquals(persona.getUsuario(), personaOutputDto.usuario());
		Assertions.assertNotEquals(persona.getName(), personaOutputDto.name());
		Assertions.assertNotEquals(persona.getSurname(), personaOutputDto.surname());
		Assertions.assertNotEquals(persona.getPassword(), personaOutputDto.password());
		Assertions.assertNotEquals(persona.getCity(), personaOutputDto.city());
		Assertions.assertNotEquals(persona.getActive(), personaOutputDto.active());
		Assertions.assertNotEquals(persona.getPersonal_email(), personaOutputDto.personal_email());
		Assertions.assertNotEquals(persona.getCompany_email(), personaOutputDto.company_email());
		Assertions.assertNotEquals(persona.getImagen_url(), personaOutputDto.imagen_url());
		Assertions.assertNotEquals(persona.getCreated_date(), personaOutputDto.created_date());
		Assertions.assertNotEquals(persona.getTermination_date(), personaOutputDto.termination_date());
	}


}
