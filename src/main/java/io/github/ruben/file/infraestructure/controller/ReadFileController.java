package io.github.ruben.file.infraestructure.controller;

import java.io.IOException;
import java.util.List;

import io.github.ruben.file.application.FileService;
import io.github.ruben.file.domain.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("file")
@RestController
public class ReadFileController {

    private final FileService fileService;

    @Autowired
    public ReadFileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public List<File> listUploadedFiles() throws IOException {
        return fileService.loadAll();
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = fileService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/setpath")
    public void changeLocation(@RequestParam String path){
        fileService.changeLocation(path);
        fileService.init(null);
    }

    /*@GetMapping("/downloadName/{filename:.+}")
    public ResponseEntity<Resource> downloadFileName(@PathVariable String filename) {
        return fileService.downloadFileName(filename);
    }*/

    @GetMapping("/id/{id}")
    public ResponseEntity<Resource> serveFileId(@PathVariable Integer id) {
        Resource file = fileService.loadAsResourceId(id);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

}
