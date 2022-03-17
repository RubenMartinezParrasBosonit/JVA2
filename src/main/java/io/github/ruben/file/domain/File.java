package io.github.ruben.file.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@ToString
@NoArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_file")
    private Integer idFile;

    @NotBlank
    @NotNull
    @Column(name = "name")
    private String name;

    @NotBlank
    @NotNull
    @Column(name = "location")
    private String location;

    @NotNull
    @NotBlank
    @Column(name = "category")
    private String category;

    @Column(name = "upload_date")
    private Date uploadDate;
}
