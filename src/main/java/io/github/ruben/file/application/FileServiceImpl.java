package io.github.ruben.file.application;

import io.github.ruben.file.domain.File;
import io.github.ruben.file.infraestructure.configuration.StorageProperties;
import io.github.ruben.file.infraestructure.repository.jpa.FileRepositorio;
import io.github.ruben.shared.exceptions.IdNotFoundException;
import io.github.ruben.shared.exceptions.StorageException;
import io.github.ruben.shared.exceptions.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@Service
public class FileServiceImpl implements FileService{

    private Path rootLocation;

    @Autowired
    FileRepositorio fileRepositorio;

    @Autowired
    public FileServiceImpl(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void init(String arg) {
        if(arg!=null){
            rootLocation = Paths.get(arg);
        }

        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage");
        }
    }

    @Override
    public void store(MultipartFile file) {
        if(!rootLocation.toFile().exists()){
            init(null);
        }

        storageFunction(file, getExtension(file.getOriginalFilename()));
    }

    @Override
    public void storeType(String type, MultipartFile file) {
        String extension = getExtension(file.getOriginalFilename());
        if(extension.equals(type)){
            storageFunction(file, extension);
        }else{
            throw new StorageException("Specified type does not match file extension");
        }
    }

    @Override
    public List<File> loadAll() {
        return fileRepositorio.findAll();
    }

    @Override
    public Resource loadAsResource(String filename) {
        return returnResource(filename);
    }

    @Override
    public Resource loadAsResourceId(Integer id) {
        File fileObj = fileRepositorio.findById(id).orElseThrow(()->new IdNotFoundException("Id del archivo no encontrado"));
        String filename = fileObj.getName();

        return returnResource(filename);
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());

    }

    @Override
    public void deleteFiles(String path){
        FileSystemUtils.deleteRecursively(Paths.get(path).toFile());
        fileRepositorio.findAll().stream().
                filter(elemento -> elemento.getLocation().equals(path)).forEach((e)->fileRepositorio.delete(e));
    }

    @Override
    public void changeLocation(String path){
        rootLocation = Paths.get(path);
    }

    private void storageFunction(MultipartFile file, String extension){
        File fileObj = new File();
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            if(fileRepositorio.findByName(file.getOriginalFilename()) != null){
                throw new StorageException("File already exists");
            }
            Path destinationFile = this.rootLocation.resolve(
                            Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
                fileObj.setName(file.getOriginalFilename());
                fileObj.setUploadDate(new Date());
                fileObj.setCategory(extension);
                fileObj.setLocation(rootLocation.toFile().getName());
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file.");
        }

        fileRepositorio.save(fileObj);
    }

    private String getExtension(String originalName){
        int i = originalName.lastIndexOf('.');
        if (i > 0) {
            return originalName.substring(i + 1);
        }else{
            throw new StorageException("File has no extension");
        }
    }

    private Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    private Resource returnResource(String filename){
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename + ". Your current location is: " + rootLocation.toFile().getName()
                + " , maybe you should swap to another.");

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename);
        }
    }
}
