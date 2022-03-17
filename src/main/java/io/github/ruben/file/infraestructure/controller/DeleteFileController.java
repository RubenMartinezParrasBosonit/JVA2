package io.github.ruben.file.infraestructure.controller;

import io.github.ruben.file.application.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("file")
@RestController
public class DeleteFileController {

    private final FileService fileService;

    @Autowired
    public DeleteFileController(FileService fileService) {
        this.fileService = fileService;
    }

    @DeleteMapping
    public void deleteFiles(@RequestParam String path){
        fileService.deleteFiles(path);
    }
}
