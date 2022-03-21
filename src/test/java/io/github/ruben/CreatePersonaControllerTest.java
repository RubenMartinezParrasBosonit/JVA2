package io.github.ruben;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreatePersonaControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	PersonaRepositorio personaRepositorio;


	PersonaInputDto personaInputDto = new PersonaInputDto();


	@BeforeAll
	public void start(){
		createPersonaInput(personaInputDto);
	}
	@AfterAll
	public void finish(){
		personaRepositorio.deleteAll();
	}

	@Test
	@DisplayName("Testing create person")
	void testCreatePerson() throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson=ow.writeValueAsString(personaInputDto);

		MvcResult resultado = this.mockMvc.perform(post("/persona").contentType(MediaType.APPLICATION_JSON)
						.content(requestJson))
				.andExpect(status().isOk()).andReturn();

		String contenido = resultado.getResponse().getContentAsString();

		PersonaRecordOutputDto personaOutputDto = new ObjectMapper()
				.readValue(contenido, new TypeReference<PersonaRecordOutputDto>() {
		});

		assertionsPersona(personaOutputDto);

	}


	private void createPersonaInput(PersonaInputDto personaInputDto){
		personaInputDto.setUsuario("usuario");
		personaInputDto.setPassword("contrasenia");
		personaInputDto.setName("Ruben");
		personaInputDto.setSurname("Martinez");
		personaInputDto.setCity("Jaen");
		personaInputDto.setActive(true);
		personaInputDto.setPersonal_email("r@gmail.com");
		personaInputDto.setCompany_email("r@gmail.com");
		personaInputDto.setCreated_date(new Date());
		personaInputDto.setTermination_date(new Date());
		personaInputDto.setImagen_url("imgurl");
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
	}

}
