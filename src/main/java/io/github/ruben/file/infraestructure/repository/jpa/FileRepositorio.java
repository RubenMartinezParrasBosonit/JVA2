package io.github.ruben.file.infraestructure.repository.jpa;

import io.github.ruben.file.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepositorio extends JpaRepository<File, Integer> {
    public File findByName(String name);
}
