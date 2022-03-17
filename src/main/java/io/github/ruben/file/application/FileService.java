package io.github.ruben.file.application;

import io.github.ruben.file.domain.File;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public interface FileService {

    void init(String arg);

    void store(MultipartFile file);

    void storeType(String type, MultipartFile file);

    List<File> loadAll();

    Resource loadAsResource(String filename);

    Resource loadAsResourceId(Integer id);

    void deleteAll();

    void deleteFiles(String path);

    void changeLocation(String path);
}
