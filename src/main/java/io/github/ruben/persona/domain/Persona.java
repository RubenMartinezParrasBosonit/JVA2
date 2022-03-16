package io.github.ruben.persona.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import java.util.Date;

@Document(collection = "Persona")
@Data
@NoArgsConstructor
public class Persona {

    @Id
    private Integer id_persona;

    private String usuario;

    private String password;

    private String name;

    private String surname;

    @Email
    private String company_email;

    @Email
    private String personal_email;

    private String city;

    private Boolean active;

    private Date created_date;

    private String imagen_url;

    private Date termination_date;
}
