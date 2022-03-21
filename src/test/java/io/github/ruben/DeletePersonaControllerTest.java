package io.github.ruben;

import io.github.ruben.persona.domain.Persona;
import io.github.ruben.persona.infrastructure.repository.jpa.PersonaRepositorio;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeletePersonaControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	PersonaRepositorio personaRepositorio;

	Persona persona = new Persona();


	@BeforeAll
	public void start(){
		createPersona(persona);
	}

	@AfterAll
	public void finish(){
		personaRepositorio.deleteAll();
	}

	@Test
	@DisplayName("Testing delete person by Id")
	void testDeletePersona() throws Exception{
		this.mockMvc.perform(get("/persona/"+String.valueOf(persona.getId_persona())))
				.andExpect(status().isOk());
		this.mockMvc.perform(delete("/persona/"+String.valueOf(persona.getId_persona())))
				.andExpect(status().isOk());
		this.mockMvc.perform(get("/persona/"+String.valueOf(persona.getId_persona())))
				.andExpect(status().isNotFound());
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
